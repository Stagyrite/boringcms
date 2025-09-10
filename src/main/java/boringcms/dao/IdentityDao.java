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
package boringcms.dao;

import static boringcms.util.UtilMethods.getFirst;

import java.util.List;

import javax.persistence.EntityManager;

import boringcms.domain.Identity;

/**
 * A user data access object utility class: performs some user database
 * operations.
 * 
 * @author stagyrite
 *
 */
public final class IdentityDao {

	/**
	 * the admin user name
	 */
	private static final String ADMIN = "admin";
	
	private IdentityDao() {
		// utility class
	}

	/**
	 * Creates an admin user, unless one already exist.
	 */
	public static void createAdminIdentity() {
		TransactionWrapper.run(em -> {

			if (findByName(ADMIN, em) == null) {
				em.persist(new Identity(ADMIN));
			}
			
		});
	}

	/**
	 * Finds an user by name.
	 * 
	 * @param name the user name
	 * @param em the entity manager
	 * @return the user
	 */
	public static Identity findByName(String name, EntityManager em) {
		List<Identity> usersList = em.createNamedQuery("Identity.findByName", Identity.class)
				.setParameter("name", name)
				.getResultList();
		return getFirst(usersList);
	}

}
