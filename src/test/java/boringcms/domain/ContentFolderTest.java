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

import java.util.List;

import org.junit.Test;

/**
 * A test case
 * 
 * @author stagyrite
 *
 */
public class ContentFolderTest {

	public static ContentFolder createContentFolder() {
		ContentFolder group = new ContentFolder();
		group.setBackXsl("");
		group.setCode("");
		group.setDescription("");
		group.setHref("");
		group.setLabel("");
		group.setLang(LanguageTest.createLanguage());
		group.setName("");
		group.setPath("");
		group.setSecurityParam("");
		group.setSequence(1);
		group.setSubcontents("");
		group.setWebPath("");
		group.setWwwDomain("");
		group.setWwwProtocol("");
		return group;
	}

	public static ContentFolder createMainContentFolder() {
		ContentFolder group = createContentFolder();
		group.setCode("main_group");
		group.setName("Main page");
		List<ContentSchema> schemesFilter = group.getSchemesFilter();
		schemesFilter.add(ContentSchemaTest.createContentSchema());
		return group;
	}

	@Test
	public void shouldFilterSchemes() {
		ContentFolder contentFolder = createMainContentFolder();
		List<ContentSchema> schemesFilter = contentFolder.getSchemesFilter();
		assertEquals(1, schemesFilter.size());
	}

	@Test
	public void test() {
		ContentFolder contentFolder = createContentFolder();
		assertEquals("", contentFolder.getLabel());
	}

}
