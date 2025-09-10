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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;

import org.hibernate.annotations.Cache;

/**
 * A content folder
 * 
 * @author stagyrite
 *
 */
@Cache(usage = READ_WRITE)
@Entity
@NamedQuery(
	    name = "ContentFolder.findByPath",
	    query = "SELECT d FROM ContentFolder d WHERE d.path = :path",
	    hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class ContentFolder implements Ordered {
	/**
	 * the back XSL
	 */
	@Column
	private String backXsl;

	/**
	 * the group code
	 */
	@Column
	private String code;

	/**
	 * the content folder with statistics counting
	 */
	@Column
	private boolean countStatistics;

	/**
	 * the schemes filter description
	 */
	@Column
	private String description;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long contentFolderId;

	@OneToMany(mappedBy = "folder", cascade = ALL)
	private List<Content> contents = new ArrayList<>();

	/**
	 * the HREF reference
	 */
	@Column
	private String href;

	/**
	 * the additional name
	 */
	@Column
	private String label;

	/**
	 * the language
	 */
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "language_id")
	private Language lang;

	/**
	 * the human readable group name
	 */
	@Column
	private String name;

	@Column
	private String path;

	/**
	 * the schemes filter
	 */
	@ManyToMany(fetch = LAZY, cascade = ALL)
	@JoinTable(name = "content_folder_schema",
		joinColumns = @JoinColumn(name = "content_folder_id",
			referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "content_schema_id",
			referencedColumnName = "id"))
	private List<ContentSchema> schemesFilter = new ArrayList<>();

	/**
	 * the security parameter
	 */
	@Column
	private String securityParam;

	@Column
	private int sequence;

	@Column
	private String subcontents;

	/**
	 * the content folder visible in console menu
	 */
	@Column
	private boolean visibleInMenu;

	/**
	 * the web path
	 */
	@Column
	private String webPath;

	/**
	 * the WWW links domain
	 */
	@Column
	private String wwwDomain;

	/**
	 * the WWW links protocol
	 */
	@Column
	private String wwwProtocol;

	public String getBackXsl() {
		return backXsl;
	}

	public String getCode() {
		return code;
	}

	public Long getContentFolderId() {
		return contentFolderId;
	}

	public List<Content> getContents() {
		return contents;
	}

	public String getDescription() {
		return description;
	}

	public String getHref() {
		return href;
	}

	public String getLabel() {
		return label;
	}

	public Language getLang() {
		return lang;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public List<ContentSchema> getSchemesFilter() {
		return schemesFilter;
	}

	public String getSecurityParam() {
		return securityParam;
	}

	@Override
	public int getSequence() {
		return sequence;
	}

	public String getSubcontents() {
		return subcontents;
	}

	public String getWebPath() {
		return webPath;
	}

	public String getWwwDomain() {
		return wwwDomain;
	}

	public String getWwwProtocol() {
		return wwwProtocol;
	}

	public boolean isCountStatistics() {
		return countStatistics;
	}

	public boolean isVisibleInMenu() {
		return visibleInMenu;
	}

	public void setBackXsl(String backXsl) {
		this.backXsl = backXsl;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setContentFolderId(Long contentFolderId) {
		this.contentFolderId = contentFolderId;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public void setCountStatistics(boolean countStatistics) {
		this.countStatistics = countStatistics;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setLang(Language lang) {
		this.lang = lang;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setSchemesFilter(List<ContentSchema> schemesFilter) {
		this.schemesFilter = schemesFilter;
	}

	public void setSecurityParam(String securityParam) {
		this.securityParam = securityParam;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public void setSubcontents(String subcontents) {
		this.subcontents = subcontents;
	}

	public void setVisibleInMenu(boolean visibleInMenu) {
		this.visibleInMenu = visibleInMenu;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public void setWwwDomain(String wwwDomain) {
		this.wwwDomain = wwwDomain;
	}

	public void setWwwProtocol(String wwwProtocol) {
		this.wwwProtocol = wwwProtocol;
	}

}
