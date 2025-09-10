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
package boringcms.security;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static boringcms.dao.EntityManagerHolder.createEntityManager;
import static boringcms.dao.IdentityDao.findByName;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import boringcms.domain.Identity;

/**
 * A login action servlets: checks whether or not a requested user can be logged
 * in. Eventually, the given user is signed in or is redirected to the error
 * page.
 * 
 * @author stagyrite
 *
 */
public class LoginActionServlet extends HttpServlet {

	private static final Log LOG = LogFactory.getLog(LoginActionServlet.class);

	/**
	 * the version number
	 */
	private static final long serialVersionUID = 5629495420768824519L;

	private static boolean authorize(String username, String password, HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		EntityManager em = createEntityManager();
		Identity identity = findByName(username, em);
		boolean authorized = identity != null && "samedi".equals(password);

		if (authorized) {
			HttpSession session = req.getSession();
			session.setAttribute("console.username", username);
			resp.sendRedirect("/admin/content.xml");
		}

		return authorized;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		try {

			if (isBlank(username) || isBlank(password) || !authorize(username, password, req, resp)) {
				resp.sendRedirect("/admin/login.xml?error");
			}

		} catch (IOException e) {
			LOG.error(username + ": " + e.getMessage(), e);
			resp.setStatus(SC_INTERNAL_SERVER_ERROR);
		}

	}

}
