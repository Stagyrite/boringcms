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
 * A relation between content
 * 
 * @author stagyrite
 *
 */
@Cache(usage = READ_WRITE)
@Entity
public class ContentRelation {

	@Column
	private String description;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "content_from_id")
	private Content contentFrom;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Long contentRelationId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "content_to_id")
	private Content contentTo;

	@Column
	private String href;

	@Column
	private String target;

	public Content getContentFrom() {
		return contentFrom;
	}

	public Long getContentRelationId() {
		return contentRelationId;
	}

	public Content getContentTo() {
		return contentTo;
	}

	public String getDescription() {
		return description;
	}

	public String getHref() {
		return href;
	}

	public String getTarget() {
		return target;
	}

	public void setContentFrom(Content contentFrom) {
		this.contentFrom = contentFrom;
	}

	public void setContentRelationId(Long contentRelationId) {
		this.contentRelationId = contentRelationId;
	}

	public void setContentTo(Content contentTo) {
		this.contentTo = contentTo;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
