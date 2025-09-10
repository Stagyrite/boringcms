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

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.junit.Test;

import boringcms.mocks.MockHttpServletRequest;
import boringcms.mocks.MockHttpServletResponse;
import boringcms.mocks.MockServletConfig;

/**
 * An XML page generator test.
 * 
 * @author stagyrite
 *
 */
public class XmlServletTest {
	private static XmlServlet xmlServlet;

	@BeforeClass
	public static void initServlet() throws ServletException {
		xmlServlet = new XmlServlet();
		xmlServlet.init(new MockServletConfig());
	}

	@Test
	public void shouldRenderDemoPage() throws ServletException, IOException {
		// The root web path should be overriden with /main.xml.
		MockHttpServletResponse response = doGet("/");
		assertThat(response.getResponse(), containsString("Your IP address is 127.0.0.1"));
		assertThat(response.getResponse(), containsString("Get PDF"));
		assertThat(response.getResponse(), containsString("Content Management System"));
		assertEquals(SC_OK, response.getStatus());
	}

	@Test
	public void shouldRenderPdfDemoPage() throws ServletException, IOException {
		MockHttpServletResponse response = doGet("/main_pdf.xml");
		assertEquals(SC_OK, response.getStatus());
	}

	@Test
	public void shouldRenderNotFoundPage() throws ServletException, IOException {
		MockHttpServletResponse response = doGet("/no-such-page");
		assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
	}

	private MockHttpServletResponse doGet(String requestURI) throws IOException, ServletException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI(requestURI);
		MockHttpServletResponse response = new MockHttpServletResponse();
		xmlServlet.doGet(request, response);
		return response;
	}

}
