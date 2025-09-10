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
 * A content stylesheet
 * 
 * @author stagyrite
 *
 */
@Cache(usage = READ_WRITE)
@Entity
public class ContentStylesheet {

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "content_id")
	private Content content;

	@Column
	private String href;

	@Column
	private String media;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "stylesheet_id")
	private Stylesheet systemStylesheet;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Long contentStylesheetId;

	public Content getContent() {
		return content;
	}

	public Long getContentStylesheetId() {
		return contentStylesheetId;
	}

	public String getHref() {
		return href;
	}

	public String getMedia() {
		return media;
	}

	public Stylesheet getSystemStylesheet() {
		return systemStylesheet;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public void setContentStylesheetId(Long contentStylesheetId) {
		this.contentStylesheetId = contentStylesheetId;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public void setSystemStylesheet(Stylesheet systemStylesheet) {
		this.systemStylesheet = systemStylesheet;
	}

}
