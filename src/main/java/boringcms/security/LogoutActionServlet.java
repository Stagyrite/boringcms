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

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A logout action servlet: removes the username from HTTP session and performs
 * a redirect to the logout page.
 * 
 * @author stagyrite
 *
 */
public class LogoutActionServlet extends HttpServlet {

	private static final Log LOG = LogFactory.getLog(LogoutActionServlet.class);

	/**
	 * the version number
	 */
	private static final long serialVersionUID = -1243071904479797897L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		// Remove session attribute if present.
		HttpSession session = req.getSession(false);

		if (session != null) {
			session.removeAttribute("console.username");
		}

		try {
			resp.sendRedirect("/admin/login.xml?logout");
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			resp.setStatus(SC_INTERNAL_SERVER_ERROR);
		}

	}

}
