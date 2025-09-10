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

import static boringcms.dao.EntityManagerHolder.createEntityManager;
import static boringcms.domain.ContentFolderTest.createContentFolder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.OffsetDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;

/**
 * A test case
 * 
 * @author stagyrite
 *
 */
public class ContentTest {

	private Content createContent() {
		Content document = new Content();
		document.setAuthor(IdentityTest.createIdentity());
		document.setAutoId(1);
		document.setBody("");
		document.setDate1(OffsetDateTime.now());
		document.setDate2(OffsetDateTime.now());
		document.setDhcm(1);
		document.setDihc(0);
		document.setContentHref("");
		document.setFolder(createContentFolder());
		document.setInt1(1);
		document.setInt2(1);
		document.setIntroduction("");
		document.setKeys("");
		document.setLastVersion(1);
		document.setParentId(1);
		document.setParentVerId(1);
		document.setPublishedDate(OffsetDateTime.now());
		document.setPublished(document);
		document.setSchema(ContentSchemaTest.createContentSchema());
		document.setSequence(1);
		document.setString1("");
		document.setTitle("");
		document.setVerId(1);
		return document;
	}

	private Content createMainContent() {
		Content document = createContent();
		document.setTitle("Welcome!");
		return document;
	}

	@Test
	public void shouldPersistEntity() {
		EntityManager em = createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		try {
			Content content = createContent();
			ContentFolder folder = content.getFolder();
			em.persist(folder.getLang());
			em.persist(folder);
			em.persist(content.getSchema());
			em.persist(content.getAuthor());
			em.persist(content);
			em.flush();
			assertNotNull(content.getContentId());
		} finally {
			transaction.rollback();
			em.close();
		}

	}

	@Test
	public void test() {
		Content document = createContent();
		assertEquals(ContentStatus.PUBLISHED, ContentStatus.getStatus(document));
	}

	@Test
	public void testMain() {
		Content document = createMainContent();
		assertEquals("Welcome!", document.getTitle());
	}

}
