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

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.apache.commons.lang3.StringUtils.appendIfMissing;

import java.io.IOException;
import java.io.File;
import java.nio.file.FileSystems;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import boringcms.resolver.FrontContentResolver;
import boringcms.resolver.XmlResolver;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.InputSource;

/**
 * An XML file service: searches for the file path, which is used also by the an
 * processor.
 * 
 * @author stagyrite
 *
 */
class XmlFile implements URIResolver {
	/**
	 * the file separator
	 */
	private static final String FS = FileSystems.getDefault().getSeparator();

	/**
	 * the logger
	 */
	private static final Log LOG = LogFactory.getLog(XmlFile.class);

	private static final XmlResolver RESOLVER = new FrontContentResolver();

	private final ServletContext context;

	XmlFile(ServletContext context) {
		this.context = context;
	}

	@Override
	public Source resolve(String href, String base) {
		String realPath = getPath(href);
		return new SAXSource(new InputSource("file:///" + realPath));
	}

	/**
	 * Gets an XML document from a path resolved from a given filename. If the given
	 * filename doesn't resolve to an existing file, then the result value is null.
	 *
	 * @param xmlFileName the XML file name
	 * @return the XML document
	 */
	private Document getXml(String xmlFileName) throws SAXException, IOException {
		String path = getPath(xmlFileName);
		File file = new File(path);
		Document document = null;

		if (file.exists() && file.isFile()) {
			DOMParser xmlparser = new DOMParser();
			xmlparser.parse("file:///" + path);
			document = xmlparser.getDocument();
		} else {
			LOG.info(xmlFileName + " file doesn't exist");
		}

		return document;
	}

	/**
	 * Gets the real file path. The root path can be replaced with an optional
	 * "boringcms.base.path" property.
	 * 
	 * @param path the web path
	 * @return the absolute path
	 */
	String getPath(String path) {
		String basePath = System.getProperty("boringcms.base.path");
		String absolutePath = context.getRealPath(path);

		if (isNotBlank(basePath)) {
			// Replace the root path.
			String rootPath = context.getRealPath("/");

			if (startsWith(absolutePath, rootPath)) {
				String relativePath = removeStart(absolutePath, rootPath);
				// Append file separator to the directory.
				absolutePath = appendIfMissing(basePath, FS) + relativePath;
			} else {
				LOG.error(basePath + ": base path must start with " + rootPath);
			}

		}

		return absolutePath;
	}

	/**
	 * Reads an XML document with a given URI. If the requested URI is the index
	 * page, then its overriden by /main.xml. Then it tries to get a CMS document
	 * instance. If the requested document doesn't exist, then it's replaced with
	 * /not_found.xml and the HTTP status is 404 Not Found.
	 *
	 * @param requestUri the request URI
	 * @param response   the HTTP response
	 * @return the XML document
	 */
	Document getXmlDocument(String requestUri, HttpServletResponse response) throws IOException, SAXException {
		String uri;

		if (requestUri.matches("[/\\.]*(index.html)?")) {
			// Override the URI of the index page.
			uri = "/main.xml";
		} else {
			uri = requestUri;
		}

		// get the real path for xml and xsl files.
		Document xmlDocument = getXml(uri);

		if (xmlDocument == null) {
			// The URI doesn't resolve to an existing file.
			xmlDocument = RESOLVER.getXML(uri, null);
			
			if (xmlDocument == null) {
				// There's no such document instance.
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				xmlDocument = getXml("/not_found.xml");
			}
			
		}

		return xmlDocument;
	}

}
