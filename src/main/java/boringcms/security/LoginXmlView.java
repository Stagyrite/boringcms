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
package boringcms.security;

import static org.apache.commons.text.StringEscapeUtils.escapeEcmaScript;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import boringcms.xmlview.XmlView;

/**
 * The login page XML producer.
 * 
 * @author stagyrite
 *
 */
public class LoginXmlView implements XmlView {
	private final List<JavaScriptMessage> messages = new ArrayList<>();

	private final HttpServletRequest request;

	public LoginXmlView(final HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public Node getXml(String params, Document document) {
		resolveParameters();
		Element view = document.createElement("view");
		// Put all messages in the XML document.

		for (JavaScriptMessage message : messages) {
			view.appendChild(message.toXml(document));
		}

		return view;
	}

	private void resolveParameters() {

		if (request.getParameter("logout") != null) {
			// It's supposed to be a redirect from the logout action.
			addMessage("You've been succesfully logged out.");
		}

		if (request.getParameter("error") != null) {
			// It's supposed to be a redirect from the login action.
			addMessage("Invalid login or password!");
		}

	}

	protected void addMessage(String message) {
		addMessage(message, null);
	}

	/**
	 * Adds a JavaScript message.
	 * 
	 * @param message
	 * @param code
	 */
	protected void addMessage(String message, String code) {
		String escapedMessage = escapeEcmaScript(message);
		messages.add(new JavaScriptMessage(code, escapedMessage));
	}

}
