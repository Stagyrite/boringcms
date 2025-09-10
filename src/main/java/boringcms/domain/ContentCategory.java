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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;

/**
 * A content category
 * 
 * @author stagyrite
 *
 */
@Cache(usage = READ_WRITE)
@Entity
public class ContentCategory implements Ordered {
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "content_category_group_id")
	private ContentCategoryGroup contentCategoryGroup;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Long contentCategoryId;

	@Column
	private String code;

	@Column
	private String description;

	@ManyToMany(mappedBy = "categories")
	private List<Content> contents = new ArrayList<>();

	@Column
	private String name;

	@Column
	private int sequence;

	public String getCode() {
		return code;
	}

	public ContentCategoryGroup getContentCategoryGroup() {
		return contentCategoryGroup;
	}

	public Long getContentCategoryId() {
		return contentCategoryId;
	}

	public List<Content> getContents() {
		return contents;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	@Override
	public int getSequence() {
		return sequence;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setContentCategoryGroup(ContentCategoryGroup contentCategoryGroup) {
		this.contentCategoryGroup = contentCategoryGroup;
	}

	public void setContentCategoryId(Long contentCategoryId) {
		this.contentCategoryId = contentCategoryId;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

}

