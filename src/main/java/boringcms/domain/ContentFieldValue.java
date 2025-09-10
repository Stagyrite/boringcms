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

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;

/**
 * A content field value
 * 
 * @author stagyrite
 *
 */
@Cache(usage = READ_WRITE)
@Entity
public class ContentFieldValue {
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "content_field_id")
	private ContentField contentField;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Long contentFieldValueId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "language_id")
	private Language language;

	private String value;

	public ContentField getContentField() {
		return contentField;
	}

	public Long getContentFieldValueId() {
		return contentFieldValueId;
	}

	public Language getLanguage() {
		return language;
	}

	public String getValue() {
		return value;
	}

	public void setContentField(ContentField contentField) {
		this.contentField = contentField;
	}

	public void setContentFieldValueId(Long contentFieldValueId) {
		this.contentFieldValueId = contentFieldValueId;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
