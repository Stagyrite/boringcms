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

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import org.apache.commons.logging.Log;

/**
 * A servlet context mock.
 * 
 * @author stagyrite
 *
 */
public class MockServletContext implements ServletContext  {
	private static final Log LOG = getLog(MockServletContext.class);

	@Override
	public String getContextPath() {
		LOG.debug("getContextPath");
		return null;
	}

	@Override
	public ServletContext getContext(String uripath) {
		LOG.debug("getContext");
		return null;
	}

	@Override
	public int getMajorVersion() {
		LOG.debug("getMajorVersion");
		return 0;
	}

	@Override
	public int getMinorVersion() {
		LOG.debug("getMinorVersion");
		return 0;
	}

	@Override
	public int getEffectiveMajorVersion() {
		LOG.debug("getEffectiveMajorVersion");
		return 0;
	}

	@Override
	public int getEffectiveMinorVersion() {
		LOG.debug("getEffectiveMinorVersion");
		return 0;
	}

	@Override
	public String getMimeType(String file) {
		LOG.debug("getMimeType");
		return null;
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		LOG.debug("getResourcePaths");
		return null;
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		LOG.debug("getResource");
		return null;
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		LOG.debug("getResourceAsStream");
		return null;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		LOG.debug("getRequestDispatcher");
		return null;
	}

	@Override
	public RequestDispatcher getNamedDispatcher(String name) {
		LOG.debug("getNamedDispatcher");
		return null;
	}

	@Override
	public Servlet getServlet(String name) throws ServletException {
		LOG.debug("getServlet");
		return null;
	}

	@Override
	public Enumeration<Servlet> getServlets() {
		LOG.debug("getServlets");
		return null;
	}

	@Override
	public Enumeration<String> getServletNames() {
		LOG.debug("getServletNames");
		return null;
	}

	@Override
	public void log(String msg) {
		LOG.debug("log");
		
	}

	@Override
	public void log(Exception exception, String msg) {
		LOG.debug("log");
		
	}

	@Override
	public void log(String message, Throwable throwable) {
		LOG.debug("log");
		
	}

	@Override
	public String getRealPath(String path) {
		ProtectionDomain protectionDomain = this.getClass().getProtectionDomain();
		CodeSource source = protectionDomain.getCodeSource();

		if (source == null) {
			return null;
		}

		try {
			File root = new File(source.getLocation().toURI());
			return new File(root, path).getAbsolutePath();
		} catch (URISyntaxException e) {
			LOG.fatal(e.getMessage(), e);
			return null;
		}

	}

	@Override
	public String getServerInfo() {
		LOG.debug("getServerInfo");
		return null;
	}

	@Override
	public String getInitParameter(String name) {
		LOG.debug("getInitParameter");
		return null;
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		LOG.debug("getInitParameterNames");
		return null;
	}

	@Override
	public boolean setInitParameter(String name, String value) {
		LOG.debug("setInitParameter");
		return false;
	}

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
	public void setAttribute(String name, Object object) {
		LOG.debug("setAttribute");
		
	}

	@Override
	public void removeAttribute(String name) {
		LOG.debug("removeAttribute");
		
	}

	@Override
	public String getServletContextName() {
		LOG.debug("getServletContextName");
		return null;
	}

	@Override
	public Dynamic addServlet(String servletName, String className) {
		LOG.debug("addServlet");
		return null;
	}

	@Override
	public Dynamic addServlet(String servletName, Servlet servlet) {
		LOG.debug("addServlet");
		return null;
	}

	@Override
	public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
		LOG.debug("addServlet");
		return null;
	}

	@Override
	public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
		LOG.debug("createServlet");
		return null;
	}

	@Override
	public ServletRegistration getServletRegistration(String servletName) {
		LOG.debug("getServletRegistration");
		return null;
	}

	@Override
	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		LOG.debug("getServletRegistrations");
		return null;
	}

	@Override
	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, String className) {
		LOG.debug("addFilter");
		return null;
	}

	@Override
	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
		LOG.debug("addFilter");
		return null;
	}

	@Override
	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
		LOG.debug("addFilter");
		return null;
	}

	@Override
	public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
		LOG.debug("createFilter");
		return null;
	}

	@Override
	public FilterRegistration getFilterRegistration(String filterName) {
		LOG.debug("getFilterRegistration");
		return null;
	}

	@Override
	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		LOG.debug("getFilterRegistrations");
		return null;
	}

	@Override
	public SessionCookieConfig getSessionCookieConfig() {
		LOG.debug("getSessionCookieConfig");
		return null;
	}

	@Override
	public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
		LOG.debug("setSessionTrackingModes");
		
	}

	@Override
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		LOG.debug("getDefaultSessionTrackingModes");
		return null;
	}

	@Override
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		LOG.debug("getEffectiveSessionTrackingModes");
		return null;
	}

	@Override
	public void addListener(String className) {
		LOG.debug("addListener");
		
	}

	@Override
	public <T extends EventListener> void addListener(T t) {
		LOG.debug("addListener");
		
	}

	@Override
	public void addListener(Class<? extends EventListener> listenerClass) {
		LOG.debug("addListener");
		
	}

	@Override
	public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
		LOG.debug("createListener");
		return null;
	}

	@Override
	public JspConfigDescriptor getJspConfigDescriptor() {
		LOG.debug("getJspConfigDescriptor");
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		LOG.debug("getClassLoader");
		return null;
	}

	@Override
	public void declareRoles(String... roleNames) {
		LOG.debug("declareRoles");
		
	}

	@Override
	public String getVirtualServerName() {
		LOG.debug("getVirtualServerName");
		return null;
	}

}
