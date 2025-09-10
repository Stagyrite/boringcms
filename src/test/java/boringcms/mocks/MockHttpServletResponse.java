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
package boringcms.mocks;

import static org.apache.commons.logging.LogFactory.getLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;

/**
 * A HTTP servlet response mock.
 * 
 * @author stagyrite
 *
 */
public class MockHttpServletResponse implements HttpServletResponse {
	private static final Log LOG = getLog(MockHttpServletResponse.class);

	private MockServletOutputStream servletOutputStream = new MockServletOutputStream();

	private int status;

	@Override
	public String getCharacterEncoding() {
		LOG.debug("getCharacterEncoding");
		return null;
	}

	@Override
	public String getContentType() {
		LOG.debug("getContentType");
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return servletOutputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		LOG.debug("getWriter");
		return null;
	}

	@Override
	public void setCharacterEncoding(String charset) {
		LOG.debug("setCharacterEncoding");

	}

	@Override
	public void setContentLength(int len) {
		LOG.debug("setContentLength");

	}

	@Override
	public void setContentLengthLong(long len) {
		LOG.debug("setContentLengthLong");

	}

	@Override
	public void setContentType(String type) {
		LOG.debug("setContentType");

	}

	@Override
	public void setBufferSize(int size) {
		LOG.debug("setBufferSize");

	}

	@Override
	public int getBufferSize() {
		LOG.debug("getBufferSize");
		return 0;
	}

	@Override
	public void flushBuffer() throws IOException {
		LOG.debug("flushBuffer");

	}

	@Override
	public void resetBuffer() {
		LOG.debug("resetBuffer");

	}

	@Override
	public boolean isCommitted() {
		LOG.debug("isCommitted");
		return false;
	}

	@Override
	public void reset() {
		LOG.debug("reset");

	}

	@Override
	public void setLocale(Locale loc) {
		LOG.debug("setLocale");

	}

	@Override
	public Locale getLocale() {
		LOG.debug("getLocale");
		return null;
	}

	@Override
	public void addCookie(Cookie cookie) {
		LOG.debug("addCookie");

	}

	@Override
	public boolean containsHeader(String name) {
		LOG.debug("containsHeader");
		return false;
	}

	@Override
	public String encodeURL(String url) {
		LOG.debug("encodeURL");
		return null;
	}

	@Override
	public String encodeRedirectURL(String url) {
		LOG.debug("encodeRedirectURL");
		return null;
	}

	@Override
	public String encodeUrl(String url) {
		LOG.debug("encodeUrl");
		return null;
	}

	@Override
	public String encodeRedirectUrl(String url) {
		LOG.debug("encodeRedirectUrl");
		return null;
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		LOG.debug("sendError");

	}

	@Override
	public void sendError(int sc) throws IOException {
		LOG.debug("sendError");

	}

	@Override
	public void sendRedirect(String location) throws IOException {
		LOG.debug("sendRedirect");

	}

	@Override
	public void setDateHeader(String name, long date) {
		LOG.debug("setDateHeader");

	}

	@Override
	public void addDateHeader(String name, long date) {
		LOG.debug("addDateHeader");

	}

	@Override
	public void setHeader(String name, String value) {
		LOG.debug("setHeader");

	}

	@Override
	public void addHeader(String name, String value) {
		LOG.debug("addHeader");

	}

	@Override
	public void setIntHeader(String name, int value) {
		LOG.debug("setIntHeader");

	}

	@Override
	public void addIntHeader(String name, int value) {
		LOG.debug("addIntHeader");

	}

	@Override
	public void setStatus(int sc) {
		status = sc;
	}

	@Override
	public void setStatus(int sc, String sm) {
		LOG.debug("setStatus");

	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public String getHeader(String name) {
		LOG.debug("getHeader");
		return null;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		LOG.debug("getHeaders");
		return null;
	}

	@Override
	public Collection<String> getHeaderNames() {
		LOG.debug("getHeaderNames");
		return null;
	}

	public String getResponse() {
		ByteArrayOutputStream outputStream = servletOutputStream.getOutputStream();
		try {
			return new String(outputStream.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("out of the blue: " + e.getMessage(), e);
		}
	}

}
