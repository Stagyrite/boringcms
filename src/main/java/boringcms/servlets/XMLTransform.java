/*
 * Copyright (c) 2018 Maciej Matiaszowski
 * 
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 * 
 */
package boringcms.servlets;

import static java.util.regex.Pattern.compile;
import static org.apache.xmlgraphics.util.MimeConstants.MIME_PDF;
import static org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An XML transformation service: searches for the XSL input file and performs
 * the XSL transformations with HTML or PDF output.
 * 
 * @author stagyrite
 *
 */
class XmlTransform {
	private static final Log LOG = LogFactory.getLog(XmlTransform.class);

	/**
	 * the parameter name group number
	 */
	private static final int PARAM = 1;

	/**
	 * the regular expression for the attribute in the processing instructions
	 */
	private static final Pattern PI_PATTERN = compile("[\\s]*([^\\s]*)[\\s]*=[\\s]*\"([^\"]*)\"[\\s]*[\\n\\s]*");

	/**
	 * the parameter value group number
	 */
	private static final int VALUE = 2;

	private final HttpServletRequest request;

	/**
	 * the HTTP servlet response
	 */
	private final HttpServletResponse response;

	/**
	 * the XML files service
	 */
	private final XmlFile xmlFile;

	XmlTransform(XmlFile xmlFile, HttpServletRequest request, HttpServletResponse response) {
		this.xmlFile = xmlFile;
		this.request = request;
		this.response = response;
	}

	private static void findXslFileNames(Map<String, String> xsls, Node node) {

		if (node.getNodeType() == PROCESSING_INSTRUCTION_NODE) {
			findXslFileNames(xsls, node.getNodeValue());
		}

		if (node.hasChildNodes()) {
			NodeList childList = node.getChildNodes();
			int listLength = childList.getLength();

			for (int i = 0; i < listLength; i++) {
				findXslFileNames(xsls, childList.item(i));
			}

		}

	}

	private static void findXslFileNames(Map<String, String> xsls, String nodeValue) {
		Map<String, String> attrs = parseAttributes(nodeValue);
		String type = attrs.get("type");

		if ("text/dxsl".equals(type)) {
			String media = attrs.getOrDefault("media", "html");
			String href = attrs.get("href");

			if (href != null) {
				xsls.putIfAbsent(media, href);
			}

		}

	}

	private static byte[] getTranformationResult(Transformer transformer, Document xmlDocument)
			throws TransformerException {
		DOMSource xmlstream = new DOMSource(xmlDocument);
		ByteArrayOutputStream resultBuf = new ByteArrayOutputStream();
		transformer.transform(xmlstream, new StreamResult(resultBuf));
		return resultBuf.toByteArray();
	}

	/**
	 * Wraps the document element in a node named "all".
	 *
	 * @param xmlDocument the XML document
	 */
	private static void wrapInAllTag(Document xmlDocument) {
		Element allNode = xmlDocument.createElement("all");
		Element docNode = xmlDocument.getDocumentElement();

		if (docNode != null) {
			allNode.appendChild(docNode);
		}

		xmlDocument.appendChild(allNode);
	}

	static String findXslFileName(Document xmlDocument, String media) {
		Map<String, String> xsls = new HashMap<>();

		if (xmlDocument != null) {
			findXslFileNames(xsls, xmlDocument);
			wrapInAllTag(xmlDocument);
		}

		return xsls.getOrDefault(media, xsls.get("html"));
	}

	/**
	 * Runs the XML attributes parser on a given string.
	 * 
	 * @param data the string
	 * @return the parsed-out attributes
	 */
	static Map<String, String> parseAttributes(String data) {
		Map<String, String> attrs = new HashMap<>();
		Matcher matcher = PI_PATTERN.matcher(data);

		while (matcher.find()) {
			attrs.put(matcher.group(PARAM), matcher.group(VALUE));
		}

		return attrs;
	}

	/**
	 * Creates a transformer with a given XSL file path. All imports are resolved
	 * within the application directory, as returned from the
	 * {@link XmlFile#getPath(String)} method.
	 *
	 * @param path the path to the XSL file
	 */
	private Transformer createTransformer(String path) throws TransformerConfigurationException {
		File xsltFile = new File(path);
		Transformer transformer = null;

		if (xsltFile.exists() && xsltFile.canRead()) {
			TransformerFactory factory = TransformerFactory.newInstance();
			// Add an error handler, which should log any exception.
			factory.setErrorListener(new XmlErrorHandler());
			factory.setURIResolver(xmlFile);
			Source xsltSrc = new StreamSource(xsltFile);
			transformer = factory.newTransformer(xsltSrc);
		} else {
			LOG.error("The file " + xsltFile.getAbsolutePath() + " can't be read.");
		}

		return transformer;
	}

	private void outputPdf(String source) throws FOPException, TransformerException, IOException {
		// Setup input.
		File fopDir = new File(xmlFile.getPath("WEB-INF/fop"));
		FopFactory fopFactory = FopFactory.newInstance(fopDir.toURI());
		// Setup a buffer to obtain the content length.
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Fop fop = fopFactory.newFop(MIME_PDF, out);
		// Make sure the XSL transformation's result is piped through to FOP.
		Result res = new SAXResult(fop.getDefaultHandler());
		// Setup a transformer.
		Transformer transformer = createTransformer(xmlFile.getPath("xhtml-to-xslfo.xsl"));

		if (transformer != null) {
			// Start the transformation and rendering process.
			transformer.transform(new StreamSource(new StringReader(source)), res);
			send(out.toByteArray(), "application/pdf");
		}

	}

	private void send(byte[] bytes, String contentType) throws IOException {
		// Prepare response
		response.setContentType(contentType);
		response.setContentLength(bytes.length);
		// Send content to Browser
		OutputStream stream = response.getOutputStream();
		stream.write(bytes);
		stream.flush();
		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * Transform a given XML document.
	 * 
	 * @param xmlDocument
	 * @param             xslPath, may be null
	 * @throws IOException
	 * @throws TransformerException
	 * @throws FOPException
	 */
	void transform(Document xmlDocument, String xslPath) throws IOException, TransformerException, FOPException {
		Transformer transformer = createTransformer(xslPath);

		if (transformer != null) {
			transformer.setParameter("REQUEST", request);
			String method = transformer.getOutputProperty("method");
			byte[] tranformationResult = getTranformationResult(transformer, xmlDocument);

			if ("pdf".equals(method)) {
				// Create and send a PDF file based on the transformation result.
				outputPdf(new String(tranformationResult, StandardCharsets.UTF_8));
			} else {
				// Send the transformation result in HTML format.
				send(tranformationResult, "text/html; charset=UTF-8");
			}

		}

	}

}
