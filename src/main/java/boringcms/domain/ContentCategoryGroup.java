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

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;

/**
 * A content category group
 * 
 * @author stagyrite
 *
 */
@Cache(usage = READ_WRITE)
@Entity
public class ContentCategoryGroup implements Ordered {
	@OneToMany(mappedBy = "contentCategoryGroup", cascade = ALL)
	private List<ContentCategory> categories = new ArrayList<>();

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Long contentCategoryGroupId;

	@Column
	private String code;

	/**
	 * the number of columns to display
	 */
	@Column
	private int columns;

	@Column
	private String description;

	@Column
	private String name;

	@Column
	private int sequence;

	public List<ContentCategory> getCategories() {
		return categories;
	}

	public String getCode() {
		return code;
	}

	public int getColumns() {
		return columns;
	}

	public Long getContentCategoryGroupId() {
		return contentCategoryGroupId;
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

	public void setCategories(List<ContentCategory> categories) {
		this.categories = categories;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public void setContentCategoryGroupId(Long contentCategoryGroupId) {
		this.contentCategoryGroupId = contentCategoryGroupId;
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

