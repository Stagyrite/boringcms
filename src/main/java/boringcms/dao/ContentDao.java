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
package boringcms.dao;

import static boringcms.util.UtilMethods.getFirst;

import java.util.List;

import javax.persistence.EntityManager;

import boringcms.domain.Content;
import boringcms.domain.ContentFolder;
import boringcms.domain.ContentStylesheet;

public final class ContentDao {

	private ContentDao() {
		// utility class
	}

	public static Content getById(long documentId, EntityManager em) {
		return em.find(Content.class, documentId);
	}

	public static Content findByPath(String path, String href, EntityManager em) {
		List<Content> documentsList = em
				.createNamedQuery("Content.findByPath", Content.class).setParameter("path", path)
				.setParameter("href", href).getResultList();
		return getFirst(documentsList);
	}

	public static void createMainDocument(EntityManager em) {
		ContentFolder mainFolder = ContentFolderDao.findByPath("main", em);

		if (mainFolder == null) {
			// Create the main folder.
			mainFolder = new ContentFolder();
			mainFolder.setPath("main");
			List<Content> contentList = mainFolder.getContents();
			Content content = new Content();
			contentList.add(content);
			content.setBody(
			  "<h3>Alice's Adventures in Wonderland</h3>\n"
			  + "<h4>CHAPTER I.</h4>\n"
              + "<h5>Down the Rabbit-Hole</h5>\n"
              + "\n"
              + "\n"
              + "<p>Alice was beginning to get very tired of sitting by her sister on the\n"
              + "bank, and of having nothing to do: once or twice she had peeped into\n"
              + "the book her sister was reading, but it had no pictures or\n"
              + "conversations in it, “and what is the use of a book,” thought Alice\n"
              + "“without pictures or conversations?”</p>\n"
              + "\n"
              + "<p>So she was considering in her own mind (as well as she could, for the\n"
              + "hot day made her feel very sleepy and stupid), whether the pleasure of\n"
              + "making a daisy-chain would be worth the trouble of getting up and\n"
              + "picking the daisies, when suddenly a White Rabbit with pink eyes ran\n"
              + "close by her.</p>\n");
			content.setFolder(mainFolder);
			List<ContentStylesheet> stylesheets = content.getStylesheets();
			ContentStylesheet stylesheet = new ContentStylesheet();
			stylesheet.setHref("default.xsl");
			stylesheet.setContent(content);
			stylesheets.add(stylesheet);
			em.persist(mainFolder);
		}

	}
}
