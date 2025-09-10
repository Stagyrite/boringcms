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
package boringcms.xmlview;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * An XML producer: interface of the objects which can be defined in the XML
 * files, in order to be transformed by {@link boringcms.servlets.XmlTransform#transform(Document, String)}.
 * An object implementing this interface must have a constructor with a
 * {@code javax.servlet.http.HttpServletRequest} object.
 * 
 * @author stagyrite
 *
 */
public interface XmlView {

	/**
	 * Gets an XML node, in order to append it to the result document
	 * via {@link boringcms.servlets.XmlExpander#getNodeFromXmlProducer(String, String, Document)}.
	 *
	 * @param params the params specified in the processing instructions, may be null
	 * @param document the result document
	 */
	Node getXml(String params, Document document);
}
