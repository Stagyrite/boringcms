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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import org.apache.commons.logging.Log;

/**
 * A HTTP servlet request mock.
 * 
 * @author stagyrite
 *
 */
public class MockHttpServletRequest implements HttpServletRequest {
	private static final Log LOG = getLog(MockHttpServletRequest.class);

	private String requestURI;

	@Override
	public Object getAttribute(String name) {
		LOG.debug("getAttribute");
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		LOG.debug("getAttributeNames");
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		LOG.debug("getCharacterEncoding");
		return null;
	}

	@Override
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
		LOG.debug("setCharacterEncoding");

	}

	@Override
	public int getContentLength() {
		LOG.debug("getContentLength");
		return 0;
	}

	@Override
	public long getContentLengthLong() {
		LOG.debug("getContentLengthLong");
		return 0;
	}

	@Override
	public String getContentType() {
		LOG.debug("getContentType");
		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		LOG.debug("getInputStream");
		return null;
	}

	@Override
	public String getParameter(String name) {
		LOG.debug("getParameter");
		return null;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		LOG.debug("getParameterNames");
		return null;
	}

	@Override
	public String[] getParameterValues(String name) {
		LOG.debug("getParameterValues");
		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		LOG.debug("getParameterMap");
		return null;
	}

	@Override
	public String getProtocol() {
		LOG.debug("getProtocol");
		return null;
	}

	@Override
	public String getScheme() {
		LOG.debug("getScheme");
		return null;
	}

	@Override
	public String getServerName() {
		LOG.debug("getServerName");
		return null;
	}

	@Override
	public int getServerPort() {
		LOG.debug("getServerPort");
		return 0;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		LOG.debug("getReader");
		return null;
	}

	@Override
	public String getRemoteAddr() {
		// The remote address is an IPv4 localhost address.
		return "127.0.0.1";
	}

	@Override
	public String getRemoteHost() {
		LOG.debug("getRemoteHost");
		return null;
	}

	@Override
	public void setAttribute(String name, Object o) {
		LOG.debug("setAttribute");

	}

	@Override
	public void removeAttribute(String name) {
		LOG.debug("removeAttribute");

	}

	@Override
	public Locale getLocale() {
		LOG.debug("getLocale");
		return null;
	}

	@Override
	public Enumeration<Locale> getLocales() {
		LOG.debug("getLocales");
		return null;
	}

	@Override
	public boolean isSecure() {
		LOG.debug("isSecure");
		return false;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		LOG.debug("getRequestDispatcher");
		return null;
	}

	@Override
	public String getRealPath(String path) {
		LOG.debug("getRealPath");
		return null;
	}

	@Override
	public int getRemotePort() {
		LOG.debug("getRemotePort");
		return 0;
	}

	@Override
	public String getLocalName() {
		LOG.debug("getLocalName");
		return null;
	}

	@Override
	public String getLocalAddr() {
		LOG.debug("getLocalAddr");
		return null;
	}

	@Override
	public int getLocalPort() {
		LOG.debug("getLocalPort");
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		LOG.debug("getServletContext");
		return null;
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		LOG.debug("startAsync");
		return null;
	}

	@Override
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
			throws IllegalStateException {
		LOG.debug("startAsync");
		return null;
	}

	@Override
	public boolean isAsyncStarted() {
		LOG.debug("isAsyncStarted");
		return false;
	}

	@Override
	public boolean isAsyncSupported() {
		LOG.debug("isAsyncSupported");
		return false;
	}

	@Override
	public AsyncContext getAsyncContext() {
		LOG.debug("getAsyncContext");
		return null;
	}

	@Override
	public DispatcherType getDispatcherType() {
		LOG.debug("getDispatcherType");
		return null;
	}

	@Override
	public String getAuthType() {
		LOG.debug("getAuthType");
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		LOG.debug("getCookies");
		return null;
	}

	@Override
	public long getDateHeader(String name) {
		LOG.debug("getDateHeader");
		return 0;
	}

	@Override
	public String getHeader(String name) {
		LOG.debug("getHeader");
		return null;
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		LOG.debug("getHeaders");
		return null;
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		LOG.debug("getHeaderNames");
		return null;
	}

	@Override
	public int getIntHeader(String name) {
		LOG.debug("getIntHeader");
		return 0;
	}

	@Override
	public String getMethod() {
		LOG.debug("getMethod");
		return null;
	}

	@Override
	public String getPathInfo() {
		LOG.debug("getPathInfo");
		return null;
	}

	@Override
	public String getPathTranslated() {
		LOG.debug("getPathTranslated");
		return null;
	}

	@Override
	public String getContextPath() {
		LOG.debug("getContextPath");
		return null;
	}

	@Override
	public String getQueryString() {
		LOG.debug("getQueryString");
		return null;
	}

	@Override
	public String getRemoteUser() {
		LOG.debug("getRemoteUser");
		return null;
	}

	@Override
	public boolean isUserInRole(String role) {
		LOG.debug("isUserInRole");
		return false;
	}

	@Override
	public Principal getUserPrincipal() {
		LOG.debug("getUserPrincipal");
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		LOG.debug("getRequestedSessionId");
		return null;
	}

	@Override
	public String getRequestURI() {
		return requestURI;
	}

	@Override
	public StringBuffer getRequestURL() {
		LOG.debug("getRequestURL");
		return null;
	}

	@Override
	public String getServletPath() {
		LOG.debug("getServletPath");
		return null;
	}

	@Override
	public HttpSession getSession(boolean create) {
		LOG.debug("getSession");
		return null;
	}

	@Override
	public HttpSession getSession() {
		LOG.debug("getSession");
		return null;
	}

	@Override
	public String changeSessionId() {
		LOG.debug("changeSessionId");
		return null;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		LOG.debug("isRequestedSessionIdValid");
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		LOG.debug("isRequestedSessionIdFromCookie");
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		LOG.debug("isRequestedSessionIdFromURL");
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		LOG.debug("isRequestedSessionIdFromUrl");
		return false;
	}

	@Override
	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("authenticate");
		return false;
	}

	@Override
	public void login(String username, String password) throws ServletException {
		LOG.debug("login");

	}

	@Override
	public void logout() throws ServletException {
		LOG.debug("logout");

	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		LOG.debug("getParts");
		return null;
	}

	@Override
	public Part getPart(String name) throws IOException, ServletException {
		LOG.debug("getPart");
		return null;
	}

	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
		LOG.debug("upgrade");
		return null;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

}
