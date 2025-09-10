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
package boringcms.xmlview;

import static java.util.Locale.ENGLISH;
import static boringcms.util.UtilMethods.appendStringValue;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * An XML request: writes a serialized request in XML format
 * 
 * @author stagyrite
 *
 */
public class XmlRequest implements XmlView {

	private final HttpServletRequest request;

	public XmlRequest(HttpServletRequest request) {
		this.request = request;
	}

	public static Element requestCookiesToNode(HttpServletRequest request, Document document) {
		Element main = document.createElement("request.cookies");
		
		if (request.getCookies() != null) {
			
			for (Cookie cookie : request.getCookies()) {
				appendParam(main, "cookie", cookie.getName(), cookie.getValue());
			}
			
		}
		
		return main;
	}

	public static Element requestHeadersToNode(HttpServletRequest request, Document document) {
		Element main = document.createElement("request.headers");
		Enumeration<String> headers = request.getHeaderNames();
		
		while (headers.hasMoreElements()) {
			String key = headers.nextElement();
			key = key.toLowerCase(ENGLISH);
			String value = request.getHeader(key);
			// The charset is supposed to be UTF-8.
			appendParam(main, "header", key, value);
		}
		
		return main;
	}

	public static Element requestInfoToNode(HttpServletRequest request, Document document) {
		Element parent = document.createElement("request.info");
		appendStringValue("pathInfo", request.getPathInfo(), parent);
		appendStringValue("pathTranslated", request.getPathTranslated(), parent);
		appendStringValue("contextPath", request.getContextPath(), parent);
		appendStringValue("queryString", request.getQueryString(), parent);
		appendStringValue("remoteAddr", request.getRemoteAddr(), parent);
		appendStringValue("remoteHost", request.getRemoteHost(), parent);
		appendStringValue("requestURI", request.getRequestURI(), parent);
		StringBuffer requestUrl = request.getRequestURL();
		appendStringValue("requestURL", requestUrl.toString(), parent);
		return parent;
	}

	public static Element requestMultipleParametersToNode(ServletRequest request, Document document) {
		Element main = document.createElement("request.multipleParameters");
		Enumeration<String> keys = request.getParameterNames();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			if (request.getParameterValues(key) != null) {

				for (String value : request.getParameterValues(key)) {
					appendParam(main, "param", key, value);
				}

			}

		}
		
		return main;
	}

	public static Element requestParametersToNode(ServletRequest request, Document document) {
		Element main = document.createElement("request.parameters");
		Enumeration<String> keys = request.getParameterNames();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			appendParam(main, "param", key, request.getParameter(key));
		}

		return main;
	}

	public static Element requestToNode(ServletRequest request, Document document) {
		Element main = document.createElement("request");
		main.appendChild(requestParametersToNode(request, document));
		main.appendChild(requestMultipleParametersToNode(request, document));

		if (request instanceof HttpServletRequest) {
			main.appendChild(requestCookiesToNode((HttpServletRequest) request, document));
			main.appendChild(requestHeadersToNode((HttpServletRequest) request, document));
			main.appendChild(requestInfoToNode((HttpServletRequest) request, document));
		}

		return main;
	}

	/**
	 * Appends a new element to the document's element main. The newly created
	 * element has the given tag name, the name attribute and the value attribute.
	 * 
	 * @param main    the main document element
	 * @param tagName the tag name
	 * @param name    the name
	 * @param value   the value, may be null
	 */
	private static void appendParam(Element main, String tagName, String name, String value) {
		Document document = main.getOwnerDocument();
		Element param = document.createElement(tagName);
		param.setAttribute("name", name);
		param.setAttribute("value", defaultIfEmpty(value, ""));
		main.appendChild(param);
	}

	@Override
	public Node getXml(String params, Document document) {
		return requestToNode(request, document);
	}

	/*
	 * Here's a sample response with query string 'this=this&this=that'.
	 * 
	 * <?xml version="1.0" encoding="UTF-8"?>
	 * <all>
	 *   <servlet>
	 * 		<?java class = "boringcms.xmlview.XmlRequest"?>
	 * 	 <request>
	 *       <request.parameters>
	 *         <param name="this" value="this"/>
	 *       </request.parameters>
	 *       <request.multipleParameters>
	 *         <param name="this" value="this"/>
	 *         <param name="this" value="that"/>
	 *       </request.multipleParameters>
	 *       <request.cookies/>
	 *       <request.headers>
	 *         <header name="host" value="localhost:8080"/>
	 *         <header name="user-agent" value="Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0"/>
	 *         <header name="accept" value="text/html,application/xhtml+xml,application/xml;q=0.9,*\/*;q=0.8"/>
	 *         <header name="accept-language" value="pl,en-US;q=0.7,en;q=0.3"/>
	 *         <header name="accept-encoding" value="gzip, deflate"/>
	 *         <header name="connection" value="keep-alive"/>
	 *         <header name="upgrade-insecure-requests" value="1"/>
	 *       </request.headers>
	 *       <request.info>
	 *         <pathInfo>
	 *           <empty/>
	 *         </pathInfo>
	 *         <pathTranslated>
	 *           <empty/>
	 *         </pathTranslated>
	 *         <contextPath>
	 *         </contextPath>
	 *         <queryString>this=this&amp;this=that</queryString>
	 *         <remoteAddr>0:0:0:0:0:0:0:1</remoteAddr>
	 *         <remoteHost>0:0:0:0:0:0:0:1</remoteHost>
	 *         <requestURI>/xmlrequest.xml</requestURI>
	 *         <requestURL>http://localhost:8080/xmlrequest.xml</requestURL>
	 *       </request.info>
	 *     </request>
	 * 	</servlet>
	 * </all>
	 */
	
}
