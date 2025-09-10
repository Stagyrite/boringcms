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

import boringcms.domain.ContentFolder;

public final class ContentFolderDao {
	
	private ContentFolderDao() {
		// utility class
	}

	public static ContentFolder findByPath(String path, EntityManager em) {
		List<ContentFolder> groupsList = em.createNamedQuery("ContentFolder.findByPath", ContentFolder.class)
				.setParameter("path", path)
				.getResultList();
		return getFirst(groupsList);
	}
	
}
