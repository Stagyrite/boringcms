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
package boringcms.servlets;

import static boringcms.dao.TransactionWrapper.run;
import static boringcms.dao.IdentityDao.createAdminIdentity;
import static org.apache.commons.logging.LogFactory.getLog;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;

import boringcms.dao.ContentDao;

/**
 * An initialization servlet: initializes the database, system properties and does some other work.
 * 
 * @author stagyrite
 *
 */
public class InitServlet extends HttpServlet {

	/**
	 * the booting ASCII-Art
	 */
	private static final String ASCII_ART = "\n    ____             _             ________  ________\n"
			+ "   / __ )____  _____(_)___  ____ _/ ____/  |/  / ___/\n"
			+ "  / __  / __ \\/ ___/ / __ \\/ __ `/ /   / /|_/ /\\__ \\ \n"
			+ " / /_/ / /_/ / /  / / / / / /_/ / /___/ /  / /___/ / \n"
			+ "/_____/\\____/_/  /_/_/ /_/\\__, /\\____/_/  /_//____/  \n"
			+ "                         /____/\n"
			+ "\n"
			+ "                           Content Management System\n\n";

	private static final Log LOG = getLog(InitServlet.class);

	/**
	 * the version number
	 */
	private static final long serialVersionUID = -8810453587660817426L;

	@Override
	public void init() throws ServletException {
		super.init();
		// Print the ASCII-Art.
		LOG.info(ASCII_ART);
		// Load the system properties.
		loadProperties();
		// Execute Liquibase.
		LiquibaseWork liquibaseWork = new LiquibaseWork();
		liquibaseWork.executeLiquibase();
		// Create an admin user.
		createAdminIdentity();
		run(ContentDao::createMainDocument);
	}

	/**
	 * Loads the system properties file.
	 */
	private void loadProperties() {
		
		try (InputStream inputStream = getClass().getResourceAsStream("/boringcms.properties")) {
			Properties properties = System.getProperties();
			properties.load(inputStream);
		} catch (IOException e) {
			LOG.fatal(e.getMessage(), e);
		}

		/*
		 * The application is configurable via boringcms.properties. Currently, one can set
		 * the "boringcms.base.path" system property, in order to change the root directory
		 * (e.g. /home/stagyrite/boringcms/src/main/webapp/) .
		 */
	}

}
