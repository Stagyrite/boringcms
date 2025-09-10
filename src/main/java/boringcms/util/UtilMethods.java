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
package boringcms.util;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * An assortment of various static utility methods.
 * 
 * @author stagyrite
 *
 */
public final class UtilMethods {

	private UtilMethods() {
		// utility class
	}

	/**
	 * Provides syntactic sugar for appending a string value to a parent XML
	 * document element.
	 * 
	 * @param name   the name
	 * @param value  the value
	 * @param parent the parent element
	 */
	public static void appendStringValue(String name, String value, Element parent) {
		Document document = parent.getOwnerDocument();
		parent.appendChild(createStringValue(name, value, document));
	}

	/**
	 * Creates a text node with a given name and value string value in a given XML
	 * document.
	 * 
	 * @param name  the tag name
	 * @param value the text node data
	 * @param doc   the XML document
	 * @return the newly created element
	 */
	public static Element createStringValue(String name, String value, Document doc) {
		Element element = doc.createElement(name);
		Node child;

		if (value == null) {
			child = doc.createElement("empty");
		} else {
			child = doc.createTextNode(value);
		}

		element.appendChild(child);
		return element;
	}

	/**
	 * Returns the first element in {@code iterable} or null if the iterable is
	 * empty.
	 *
	 * @param defaultValue the default value to return if the iterable is empty
	 */
	public static <T> T getFirst(Iterable<? extends T> iterable) {
		return getFirst(iterable, null);
	}

	/**
	 * Returns the first element in {@code iterable} or {@code defaultValue} if the
	 * iterable is empty.
	 *
	 * @param defaultValue the default value to return if the iterable is empty
	 * @return the first element of {@code iterable} or the default value
	 */
	public static <T> T getFirst(Iterable<? extends T> iterable, T defaultValue) {
		Iterator<? extends T> iterator = iterable.iterator();
		T next;

		if (iterator.hasNext()) {
			next = iterator.next();
		} else {
			next = defaultValue;
		}

		return next;
	}

}
