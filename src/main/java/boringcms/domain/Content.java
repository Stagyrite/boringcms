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

import static java.lang.String.valueOf;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static boringcms.util.UtilMethods.appendStringValue;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;

import org.hibernate.annotations.Cache;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * A content item: contains body, categories, parent folder, author and many other.
 * 
 * @author stagyrite
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@NamedQuery(
	    name = "Content.findByPath",
	    query = "SELECT d FROM Content d WHERE d.folder.path = :path AND d.contentHref = :href",
	    hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Content implements Ordered, XmlContent {
	@OneToMany(mappedBy = "content", cascade = ALL)
	private List<SendToApproval> approvals = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "author_id")
	private Identity author;

	/**
	 * the version id
	 */
	@Column
	private int autoId;

	/**
	 * the body content in HTML format
	 */
	@Column
	@Lob
	private String body;

	@ManyToMany(fetch = LAZY, cascade = ALL)
	@JoinTable(name = "content_category",
		joinColumns = @JoinColumn(name = "content_id",
			referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "content_category_id",
			referencedColumnName = "id"))
	private List<ContentCategory> categories = new ArrayList<>();

	@Column
	private OffsetDateTime date1;

	@Column
	private OffsetDateTime date2;

	/**
	 * the hit count measure
	 */
	@Column
	private int dhcm;

	/**
	 * the hit count
	 */
	@Column
	private int dihc;

	@Column
	private String contentHref;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contentId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "content_folder_id")
	private ContentFolder folder;

	@Column
	private int int1;

	@Column
	private int int2;

	@Column
	@Lob
	private String introduction;

	/**
	 * the content HREF
	 */
	@Column
	private String keys;

	/**
	 * the last created version
	 */
	@Column
	private int lastVersion;

	/**
	 * the parent id
	 */
	@Column
	private int parentId;

	/**
	 * the parent version id
	 */
	@Column
	private int parentVerId;

	/**
	 * the published version
	 */
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "published_id")
	private Content published;

	@Column
	private OffsetDateTime publishedDate;

	@OneToMany(mappedBy = "contentFrom", cascade = ALL)
	private List<ContentRelation> relations1 = new ArrayList<>();

	@OneToMany(mappedBy = "contentTo", cascade = ALL)
	private List<ContentRelation> relations2 = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "content_schema_id")
	private ContentSchema schema;

	@Column
	private int sequence;

	@Column
	private String string1;

	@Column
	private String title;

	/**
	 * the version id
	 */
	@Column
	private int verId;

	@OneToMany(mappedBy = "content", cascade = ALL)
	private List<ContentStylesheet> stylesheets = new ArrayList<>();

	public List<SendToApproval> getApprovals() {
		return approvals;
	}

	public Identity getAuthor() {
		return author;
	}

	public int getAutoId() {
		return autoId;
	}

	public String getBody() {
		return body;
	}

	public List<ContentCategory> getCategories() {
		return categories;
	}

	public String getContentHref() {
		return contentHref;
	}

	public Long getContentId() {
		return contentId;
	}

	public OffsetDateTime getDate1() {
		return date1;
	}

	public OffsetDateTime getDate2() {
		return date2;
	}

	public int getDhcm() {
		return dhcm;
	}

	public int getDihc() {
		return dihc;
	}

	public ContentFolder getFolder() {
		return folder;
	}

	public int getInt1() {
		return int1;
	}

	public int getInt2() {
		return int2;
	}

	public String getIntroduction() {
		return introduction;
	}

	public String getKeys() {
		return keys;
	}

	public int getLastVersion() {
		return lastVersion;
	}

	public int getParentId() {
		return parentId;
	}

	public int getParentVerId() {
		return parentVerId;
	}

	public Content getPublished() {
		return published;
	}

	public OffsetDateTime getPublishedDate() {
		return publishedDate;
	}

	public List<ContentRelation> getRelations1() {
		return relations1;
	}

	public List<ContentRelation> getRelations2() {
		return relations2;
	}

	public ContentSchema getSchema() {
		return schema;
	}

	@Override
	public int getSequence() {
		return sequence;
	}

	public String getString1() {
		return string1;
	}

	public List<ContentStylesheet> getStylesheets() {
		return stylesheets;
	}

	public String getTitle() {
		return title;
	}

	public int getVerId() {
		return verId;
	}

	public void setApprovals(List<SendToApproval> approvals) {
		this.approvals = approvals;
	}

	public void setAuthor(Identity author) {
		this.author = author;
	}

	public void setAutoId(int autoId) {
		this.autoId = autoId;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setCategories(List<ContentCategory> categories) {
		this.categories = categories;
	}

	public void setContentHref(String contentHref) {
		this.contentHref = contentHref;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public void setDate1(OffsetDateTime date1) {
		this.date1 = date1;
	}

	public void setDate2(OffsetDateTime date2) {
		this.date2 = date2;
	}

	public void setDhcm(int dhcm) {
		this.dhcm = dhcm;
	}

	public void setDihc(int dihc) {
		this.dihc = dihc;
	}

	public void setFolder(ContentFolder folder) {
		this.folder = folder;
	}

	public void setInt1(int int1) {
		this.int1 = int1;
	}

	public void setInt2(int int2) {
		this.int2 = int2;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public void setLastVersion(int lastVersion) {
		this.lastVersion = lastVersion;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setParentVerId(int parentVerId) {
		this.parentVerId = parentVerId;
	}

	public void setPublished(Content published) {
		this.published = published;
	}

	public void setPublishedDate(OffsetDateTime publishedDate) {
		this.publishedDate = publishedDate;
	}

	public void setRelations1(List<ContentRelation> relations1) {
		this.relations1 = relations1;
	}

	public void setRelations2(List<ContentRelation> relations2) {
		this.relations2 = relations2;
	}

	public void setSchema(ContentSchema schema) {
		this.schema = schema;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public void setString1(String string1) {
		this.string1 = string1;
	}

	public void setStylesheets(List<ContentStylesheet> stylesheets) {
		this.stylesheets = stylesheets;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVerId(int verId) {
		this.verId = verId;
	}

	@Override
	public Node toXml(Document document) {
		Element parent = document.createElement("document");
		appendStringValue("title", title, parent);
		appendStringValue("introduction", introduction, parent);
		appendStringValue("body", body, parent);
		appendStringValue("keys", keys, parent);
		appendStringValue("int_1", valueOf(getInt1()), parent);
		appendStringValue("int_2", valueOf(getInt2()), parent);
		return parent;
	}

}
