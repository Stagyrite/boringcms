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

import static org.apache.commons.lang3.reflect.ConstructorUtils.invokeConstructor;
import static org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE;
import static org.w3c.dom.Node.DOCUMENT_NODE;

import boringcms.xmlview.XmlView;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import java.lang.reflect.InvocationTargetException;

/**
 * An XML expander service: expands an XML node by calling
 * {@link XmlView#getXml(String, Document)}, as defined
 * in a processing instructions node.
 * 
 * @author stagyrite
 *
 */
class XmlExpander {
	private static final Log LOG = LogFactory.getLog(XmlExpander.class);

	/**
	 * the HTTP servlet request
	 */
	private final HttpServletRequest request;

	XmlExpander(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Inserts an expanded node to an XSL document.
	 *
	 * @param node      the node
	 * @param className the class name
	 * @param params    the expanding method parameter
	 */
	private void expandProcessingInstructionToXsl(Node node, String className, String params) {
		Node newNode = getNodeFromXmlProducer(className, params, node.getOwnerDocument());

		if (newNode != null) {
			Node parent = node.getParentNode();

			if (parent != null) {
				// Insert the new before its next sibling.
				parent.insertBefore(newNode, node.getNextSibling());
			}

		}

	}

	/**
	 * Creates a node by calling
	 * {@link XmlView#getXml(String, Document)} with the
	 * given parameters.
	 * 
	 * @param className   the class name
	 * @param params      the method parameter
	 * @param destination the destination docume
	 */
	private Node getNodeFromXmlProducer(String className, String params, Document destination) {
		Node newNode = null;

		try {
			Class<?> clazz = Class.forName(className);
			XmlView xmlproducer = (XmlView) invokeConstructor(clazz, request);
			newNode = xmlproducer.getXml(params, destination);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException
				| InvocationTargetException e) {
			LOG.error(className + ": " + e.getMessage(), e);
		}

		return newNode;
	}

	/**
	 * Checks whether a given node is a processing instruction node with target set
	 * to "java". An example node is &lt;?java class =
	 * "boringcms.xmlview.ContentEditController" ?&gt;.
	 *
	 * @param node the node
	 * @return true if successful
	 */
	private boolean isJava(Node node) {
		boolean result = node.getNodeType() == PROCESSING_INSTRUCTION_NODE;

		if (result) {
			ProcessingInstruction piNode = (ProcessingInstruction) node;
			result = "java".equals(piNode.getTarget());
		}

		return result;
	}

	/**
	 * Expands a given XML node using an object defined in this node. Appends
	 * content created by invoking
	 * {@link XmlView#getXml(String, Document)} .
	 * 
	 * @param node the XML node
	 */
	void expand(Node node) {

		if (node != null) {

			if (isJava(node)) {
				Map<String, String> params = XmlTransform.parseAttributes(node.getNodeValue());
				expandProcessingInstructionToXsl(node, params.get("class"), params.get("params"));
			}

			// Expand all children nodes.

			for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
				expand(child);
			}

		}

	}

	void expandPItoXML(Node node, String className, String params) {
		Document destDocument;

		if (node.getNodeType() == DOCUMENT_NODE) {
			destDocument = (Document) node;
		} else {
			destDocument = node.getOwnerDocument();
		}

		Node newNode = getNodeFromXmlProducer(className, params, destDocument);

		if (newNode != null) {
			Element element = destDocument.getDocumentElement();
			element.appendChild(newNode);
		}

	}
}
