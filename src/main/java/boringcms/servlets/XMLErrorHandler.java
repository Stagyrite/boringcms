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

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An XML error handler: logs errors in the transformer factory.
 * 
 * @author stagyrite
 *
 */
class XmlErrorHandler implements ErrorListener {
	private static final Log LOG = LogFactory.getLog(XmlErrorHandler.class);

	@Override
	public void error(TransformerException exception) throws TransformerException {
		// Log the exception as an error.
		LOG.error(exception.getMessage(), exception);
	}

	@Override
	public void fatalError(TransformerException exception) throws TransformerException {
		// Log the exception as a fatal error.
		LOG.fatal(exception.getMessage(), exception);
	}

	@Override
	public void warning(TransformerException exception) throws TransformerException {
		// Log the exception as a warning.
		LOG.warn(exception.getMessage(), exception);
	}

}
