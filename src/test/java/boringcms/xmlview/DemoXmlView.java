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

import static boringcms.util.UtilMethods.createStringValue;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * A demo XML producer: creates an XML node with an ASCII-art text and user
 * remote address.
 * 
 * @author stagyrite
 *
 */
public class DemoXmlView implements XmlView {
	private static final String ASCII_ART = "\n    ____             _             ________  ________\n"
			+ "   / __ )____  _____(_)___  ____ _/ ____/  |/  / ___/\n"
			+ "  / __  / __ \\/ ___/ / __ \\/ __ `/ /   / /|_/ /\\__ \\ \n"
			+ " / /_/ / /_/ / /  / / / / / /_/ / /___/ /  / /___/ / \n"
			+ "/_____/\\____/_/  /_/_/ /_/\\__, /\\____/_/  /_//____/  \n"
			+ "                         /____/\n"
			+ "\n"
			+ "                           Content Management System\n\n";
	
	private HttpServletRequest request;

	public DemoXmlView(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public Node getXml(String params, Document document) {
		Element main = document.createElement("main");
		main.appendChild(createStringValue("ascii-art", ASCII_ART, document));
		main.appendChild(createStringValue("remoteAddr", request.getRemoteAddr(), document));
		return main;
	}

}
