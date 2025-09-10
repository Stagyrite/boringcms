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
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;

/**
 * A content schema field
 * 
 * @author stagyrite
 *
 */
@Cache(usage = READ_WRITE)
@Entity
public class ContentField {
	private static final int DEFAULT_SIZE = 50;

	@Column
	private boolean display = true;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Long contentFieldId;

	@Column
	private String help;

	@Column
	private boolean multilang;

	@Column
	private String name;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "content_schema_id")
	private ContentSchema parent;

	@Column
	private String reserved1;

	@Column
	private String reserved2;

	@Column
	private String reserved3;

	@Column
	private int size = DEFAULT_SIZE;

	@ManyToMany(mappedBy = "contentFields")
	private List<Language> supportedLanguages = new ArrayList<>();

	@OneToMany(mappedBy = "contentField", cascade = ALL)
	private List<ContentFieldValue> values = new ArrayList<>();

	public Long getContentFieldId() {
		return contentFieldId;
	}

	public String getHelp() {
		return help;
	}

	public String getName() {
		return name;
	}

	public ContentSchema getParent() {
		return parent;
	}

	public String getReserved1() {
		return reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public String getReserved3() {
		return reserved3;
	}

	public int getSize() {
		return size;
	}

	public List<Language> getSupportedLanguages() {
		return supportedLanguages;
	}

	public List<ContentFieldValue> getValues() {
		return values;
	}

	public boolean isDisplay() {
		return display;
	}

	public boolean isMultilang() {
		return multilang;
	}

	public void setContentFieldId(Long contentFieldId) {
		this.contentFieldId = contentFieldId;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public void setMultilang(boolean multilang) {
		this.multilang = multilang;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(ContentSchema parent) {
		this.parent = parent;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSupportedLanguages(List<Language> supportedLanguages) {
		this.supportedLanguages = supportedLanguages;
	}

	public void setValues(List<ContentFieldValue> values) {
		this.values = values;
	}
}
