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
package boringcms.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * A test case
 * 
 * @author stagyrite
 *
 */
public class StylesheetTest {

	public static Stylesheet createStylesheet() {
		Stylesheet stylesheet = new Stylesheet();
		stylesheet.setCode("main_page");
		stylesheet.setPath("main_page.xsl");
		stylesheet.setDescription("Main Page");
		return stylesheet;
	}

	@Test
	public void test() {
		Stylesheet stylesheet = createStylesheet();
		assertEquals("main_page", stylesheet.getCode());
	}

}
