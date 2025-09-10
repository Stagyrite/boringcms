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

import static liquibase.database.DatabaseFactory.getInstance;
import static boringcms.dao.EntityManagerHolder.createEntityManager;
import static org.apache.commons.logging.LogFactory.getLog;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * A Liquibase work: executes the changes from a Liquibase changeset file.
 * 
 * @author GenB
 *
 */
public class LiquibaseWork implements Work {

	private static final Log LOG = getLog(LiquibaseWork.class);

	@Override
	public void execute(Connection connection) throws SQLException {
		// A PostgreSQL database should be created.
		try {
			DatabaseFactory factory = getInstance();
			Database database = factory.findCorrectDatabaseImplementation(new JdbcConnection(connection));
			Liquibase liquibase = new Liquibase("/liquibase-boringcms.xml", new ClassLoaderResourceAccessor(), database);
			liquibase.update(new Contexts(), new LabelExpression());
		} catch (LiquibaseException e) {
			LOG.error(e.getMessage(), e);
		}

	}

	/**
	 * Execute the Liquibase changeset.
	 */
	void executeLiquibase() {
		EntityManager em = createEntityManager();
		Session session = em.unwrap(Session.class);
		session.doWork(this);
	}

}
