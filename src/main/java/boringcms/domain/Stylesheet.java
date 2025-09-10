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
 * A stylesheet
 * 
 * @author stagyrite
 *
 */
@Cache(usage = READ_WRITE)
@Entity
public class Stylesheet {
	@Column
	private String code;

	@Column
	private String description;

	@OneToMany(mappedBy = "systemStylesheet", cascade = ALL)
	private List<ContentStylesheet> contentStylesheets = new ArrayList<>();

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Long stylesheetId;

	@Column
	private String path;

	public String getCode() {
		return code;
	}

	public List<ContentStylesheet> getContentStylesheets() {
		return contentStylesheets;
	}

	public String getDescription() {
		return description;
	}

	public String getPath() {
		return path;
	}

	public Long getStylesheetId() {
		return stylesheetId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setContentStylesheets(List<ContentStylesheet> contentStylesheets) {
		this.contentStylesheets = contentStylesheets;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setStylesheetId(Long stylesheetId) {
		this.stylesheetId = stylesheetId;
	}

}
