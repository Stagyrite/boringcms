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

import static org.apache.commons.logging.LogFactory.getLog;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.xalan.extensions.XSLProcessorContext;
import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemExtensionCall;
import org.apache.xalan.transformer.TransformerImpl;

/**
 * An XSL extensions base:
 * 
 * @author stagyrite
 */
public class XslExtensionsBase {
	private static final Log LOG = getLog(XslExtensionsBase.class);

	private static String getParams(XSLProcessorContext context, ElemExtensionCall extElem)
			throws TransformerException {
		String params = "";
		@SuppressWarnings("unchecked")
		Iterator<AVT> en = extElem.enumerateLiteralResultAttributes();

		while (en.hasNext()) {
			AVT avt = en.next();

			if (avt != null && "params".equals(avt.getRawName())) {
				TransformerImpl transformer = context.getTransformer();
				params = extElem.getAttribute(avt.getRawName(), context.getContextNode(), transformer);
			}

		}

		return params;
	}

	/**
	 * Creates an XML node with an XML produces defined in the processing
	 * instructions.
	 * 
	 * @param context
	 * @param extElem
	 * @throws TransformerException thrown on transformation process error
	 */
	public void java(XSLProcessorContext context, ElemExtensionCall extElem) throws TransformerException {
		String className = extElem.getAttribute("class");

		if (StringUtils.isBlank(className)) {
			LOG.error("No class attribute.");
		} else {
			TransformerImpl transformer = context.getTransformer();
			HttpServletRequest request = (HttpServletRequest) transformer.getParameter("REQUEST");
			XmlExpander xmlexpander = new XmlExpander(request);
			String params = getParams(context, extElem);
			xmlexpander.expandPItoXML(context.getSourceTree(), className, params);
		}

	}

}
