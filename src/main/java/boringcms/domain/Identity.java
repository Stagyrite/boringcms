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

import static java.time.ZoneOffset.UTC;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;

/**
 * An identity: contains login credentials and the associated content data.
 * 
 * @author stagyrite
 *
 */
@Cache(usage = READ_WRITE)
@Entity
@Table(name = "identity")
@NamedQuery(
	    name = "Identity.findByName",
	    query = "SELECT u FROM Identity u WHERE u.name = :name",
	    hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Identity {

	@OneToMany(mappedBy = "identity", cascade = ALL)
	private List<SendToApproval> approvals = new ArrayList<>();

	/**
	 * the user creation date
	 */
	@Column
	private OffsetDateTime created;

	@OneToMany(mappedBy = "author", cascade = ALL)
	private List<Content> contents = new ArrayList<>();

	@Column
	private String name;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Long identityId;

	public Identity() {
		// default constructor
	}

	public Identity(String name) {
		this.name = name;
		created = OffsetDateTime.now(UTC);
	}

	public List<SendToApproval> getApprovals() {
		return approvals;
	}

	public List<Content> getContents() {
		return contents;
	}

	public OffsetDateTime getCreated() {
		return created;
	}

	public Long getIdentityId() {
		return identityId;
	}

	public String getName() {
		return name;
	}

	public void setApprovals(List<SendToApproval> approvals) {
		this.approvals = approvals;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public void setCreated(OffsetDateTime created) {
		this.created = created;
	}

	public void setIdentityId(Long identityId) {
		this.identityId = identityId;
	}

	public void setName(String name) {
		this.name = name;
	}

}
