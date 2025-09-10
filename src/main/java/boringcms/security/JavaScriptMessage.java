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

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import boringcms.domain.XmlContent;

/**
 * A JavaScript message: contains code and an escaped JavaScript string.
 * 
 * @author stagyrite
 *
 */
public class JavaScriptMessage implements XmlContent {
	/**
	 * the code
	 */
	private String code;

	/**
	 * the escaped JavaScript string
	 */
	private final String value;

	public JavaScriptMessage(final String code, final String value) {
		super();
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public Node toXml(Document document) {
		Element node = document.createElement("message");

		if (isNotBlank(code)) {
			node.setAttribute("code", code);
		}

		if (isNotBlank(value)) {
			node.appendChild(document.createTextNode(value));
		}

		return node;
	}

}
