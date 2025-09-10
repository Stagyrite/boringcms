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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;

/**
 * A language
 * 
 * @author stagyrite
 *
 */
@Cache(usage = READ_WRITE)
@Entity
public class Language {
	@Column
	private String code;

	/**
	 * the default language
	 */
	private boolean defaultLang;

	@ManyToMany(fetch = LAZY, cascade = ALL)
	@JoinTable(name = "content_field_language",
		joinColumns = @JoinColumn(name = "language_id",
			referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "content_field_id",
			referencedColumnName = "id"))
	private List<ContentField> contentFields = new ArrayList<>();

	@OneToMany(mappedBy = "lang", cascade = ALL)
	private List<ContentFolder> contentFolders = new ArrayList<>();

	@OneToMany(mappedBy = "language", cascade = ALL)
	private List<ContentFieldValue> contentFieldValues = new ArrayList<>();

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Long languageId;

	public String getCode() {
		return code;
	}

	public List<ContentField> getContentFields() {
		return contentFields;
	}

	public List<ContentFieldValue> getContentFieldValues() {
		return contentFieldValues;
	}

	public List<ContentFolder> getContentFolders() {
		return contentFolders;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public boolean isDefaultLang() {
		return defaultLang;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setContentFields(List<ContentField> contentFields) {
		this.contentFields = contentFields;
	}

	public void setContentFieldValues(List<ContentFieldValue> contentFieldValues) {
		this.contentFieldValues = contentFieldValues;
	}

	public void setContentFolders(List<ContentFolder> contentFolders) {
		this.contentFolders = contentFolders;
	}

	public void setDefaultLang(boolean defaultLang) {
		this.defaultLang = defaultLang;
	}

	public void setLanguageId(Long id) {
		this.languageId = id;
	}

}
