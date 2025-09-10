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

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static boringcms.servlets.XmlTransform.findXslFileName;
import static org.apache.commons.logging.LogFactory.getLog;
import static org.apache.xml.serializer.SerializerFactory.getSerializer;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.xalan.templates.OutputProperties;
import org.apache.xml.serializer.DOMSerializer;
import org.apache.xml.serializer.Serializer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * An XML page generator: sends a transformed XML to user.
 * 
 * @author stagyrite
 *
 */
public class XmlServlet extends HttpServlet {

	private static final int INDENT = 2;

	private static final Log LOG = getLog(XmlServlet.class);

	/**
	 * the version number
	 */
	private static final long serialVersionUID = 6177327000873170064L;

	/**
	 * Serializes the XML document.
	 * 
	 * @param xmlDocument the XML document
	 * @param output      the output stream
	 * @throws IOException if an I/O exception occurred while serializing
	 */
	public static void serialize(Document xmlDocument, OutputStream output) throws IOException {
		OutputProperties op = new OutputProperties();
		op.setProperty("indent", "yes");
		op.setIntProperty("{http://xml.apache.org/xalan}indent-amount", INDENT);
		op.setProperty("encoding", "UTF-8");
		op.setProperty("method", "xml");
		Serializer xmlSerializer = getSerializer(op.getProperties());
		xmlSerializer.setOutputStream(output);
		DOMSerializer domSerializer = xmlSerializer.asDOMSerializer();
		domSerializer.serialize(xmlDocument);
	}

	/**
	 * Respond to HTTP GET requests from browsers.
	 *
	 * @param request  the HTTP servlet request
	 * @param response the HTTP servlet response
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		try {
			XmlFile xmlFile = new XmlFile(getServletContext());
			Document xmlDocument = xmlFile.getXmlDocument(request.getRequestURI(), response);

			if (xmlDocument != null) {
				XmlExpander xmlExpander = new XmlExpander(request);
				xmlExpander.expand(xmlDocument);
				XmlTransform xmlTransform = new XmlTransform(xmlFile, request, response);
				// The XSL path is located within a "href" attribute in the XML document.
				String xslPath = findXslFileName(xmlDocument, null);
				// Get the XSL file path based on the "href" attribute.
				xslPath = xmlFile.getPath(xslPath);

				if (xslPath == null) {
					serialize(xmlDocument, response.getOutputStream());
				} else {
					// Transform the XML document with XSL stylesheet and send the response.
					xmlTransform.transform(xmlDocument, xslPath);
				}

			}

		} catch (SAXException | TransformerException e) {
			// There might be an error in one of the XSL files.
			LOG.error(request.getRequestURI() + ": " + e.getMessage(), e);
			response.setStatus(SC_INTERNAL_SERVER_ERROR);
		}

	}

}
