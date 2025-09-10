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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;

/**
 * A content schema
 * 
 * @author stagyrite
 *
 */
@Cache(usage = READ_WRITE)
@Entity
public class ContentSchema {
	@Column
	private String backXsl;

	@Column
	private boolean canHaveChildren;

	@Column
	private String code;

	@OneToMany(mappedBy = "schema", cascade = ALL)
	private List<Content> contents = new ArrayList<>();

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Long contentSchemaId;

	@OneToMany(mappedBy = "parent", cascade = ALL)
	private List<ContentField> contentFields = new ArrayList<>();

	@ManyToMany(mappedBy = "schemesFilter")
	private List<ContentFolder> contentFolders = new ArrayList<>();

	@Column
	private boolean navigate;

	@Column
	private boolean relations;

	@Column
	private String reserved1;

	@Column
	private String schemesFilter;

	@Column
	private boolean uniqueNames;

	/**
	 * the new line separated list of XSLs
	 */
	private String xsls;

	public String getBackXsl() {
		return backXsl;
	}

	public String getCode() {
		return code;
	}

	public List<ContentField> getContentFields() {
		return contentFields;
	}

	public List<ContentFolder> getContentFolders() {
		return contentFolders;
	}

	public List<Content> getContents() {
		return contents;
	}

	public Long getContentSchemaId() {
		return contentSchemaId;
	}

	public String getReserved1() {
		return reserved1;
	}

	public String getSchemesFilter() {
		return schemesFilter;
	}

	public String getXsls() {
		return xsls;
	}

	public boolean isCanHaveChildren() {
		return canHaveChildren;
	}

	public boolean isNavigate() {
		return navigate;
	}

	public boolean isRelations() {
		return relations;
	}

	public boolean isUniqueNames() {
		return uniqueNames;
	}

	public void setBackXsl(String backXsl) {
		this.backXsl = backXsl;
	}

	public void setCanHaveChildren(boolean canHaveChildren) {
		this.canHaveChildren = canHaveChildren;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setContentFields(List<ContentField> contentFields) {
		this.contentFields = contentFields;
	}

	public void setContentFolders(List<ContentFolder> contentFolders) {
		this.contentFolders = contentFolders;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public void setContentSchemaId(Long contentSchemaId) {
		this.contentSchemaId = contentSchemaId;
	}

	public void setNavigate(boolean navigate) {
		this.navigate = navigate;
	}

	public void setRelations(boolean relations) {
		this.relations = relations;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public void setSchemesFilter(String schemesFilter) {
		this.schemesFilter = schemesFilter;
	}

	public void setUniqueNames(boolean uniqueNames) {
		this.uniqueNames = uniqueNames;
	}

	public void setXsls(String xsls) {
		this.xsls = xsls;
	}

}

