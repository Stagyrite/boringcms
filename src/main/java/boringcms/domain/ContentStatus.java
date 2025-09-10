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

/**
 * A content status
 * 
 * @author stagyrite
 *
 */
public enum ContentStatus {
	ARCHIVE, NONE, PUBLISHED, WORK;

	public static ContentStatus getStatus(Content content) {
		ContentStatus status;
		int verId = content.getVerId();

		if (verId < 0) {
			status = WORK;
		} else if (verId > 0) {
			Content published = content.getPublished();
			
			if (published == null || published.getVerId() != verId) {
				status = ARCHIVE;
			} else {
				status = PUBLISHED;
			}

		} else {
			status = NONE;
		}

		return status;
	}

}
