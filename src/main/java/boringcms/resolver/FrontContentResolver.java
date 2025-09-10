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
package boringcms.resolver;

import static boringcms.dao.EntityManagerHolder.createEntityManager;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import boringcms.domain.Content;
import boringcms.domain.ContentFolder;
import boringcms.domain.ContentStylesheet;
import boringcms.domain.Stylesheet;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.ProcessingInstruction;

import boringcms.dao.ContentDao;
import boringcms.dao.ContentFolderDao;

public class FrontContentResolver implements XmlResolver {
	/**
	 * the logger
	 */
	private static final Log LOG = LogFactory.getLog(FrontContentResolver.class);

	private static Document buildDocument(Content content, DocumentBuilder db) {
		Document document = db.newDocument();
		document.appendChild(content.toXml(document));

		for (ContentStylesheet stylesheet : content.getStylesheets()) {
			String data = toProcessingInstructionData(stylesheet);
			ProcessingInstruction pi = document.createProcessingInstruction("xml-stylesheet", data);
			document.appendChild(pi);
		}

		return document;
	}

	private static Content getOnlyContent(String groupName, EntityManager em) {
		ContentFolder contentFolder = ContentFolderDao.findByPath(groupName, em);
		Content content = null;

		if (contentFolder != null) {
			List<Content> contents = contentFolder.getContents();

			if (contents.size() == 1) {
				// There's only one content item in the folder.
				content = contents.get(0);
			}

		}

		return content;
	}

	private static String toProcessingInstructionData(ContentStylesheet stylesheet) {
		String data = "type=\"text/dxsl\" href=\"";

		if (stylesheet.getSystemStylesheet() == null) {
			data += stylesheet.getHref() + "\"";

			if (isNotBlank(stylesheet.getMedia())) {
				data += " media=\"" + stylesheet.getMedia() + "\"";
			}

		} else {
			Stylesheet systemStylesheet = stylesheet.getSystemStylesheet();
			data += systemStylesheet.getPath() + "\"";
		}

		return data;
	}

	@Override
	public Document getXML(String query, HttpServletRequest request) {
		// Remove web directory name.
		String documentName = StringUtils.substringAfterLast(query, "/");
		// Chomp the XML file suffix.
		documentName = StringUtils.removeEnd(documentName, ".xml");
		long documentId = NumberUtils.toLong(documentName, 0);
		Content content;
		EntityManager em = createEntityManager();

		if (documentId > 0) {
			content = em.find(Content.class, documentId);
		} else {
			// Underscore is the HREF deliminator.
			String path = substringBefore(documentName, "_");
			String href = substringAfter(documentName, "_");
			content = ContentDao.findByPath(path, href, em);

			if (content == null) {
				content = getOnlyContent(documentName, em);
			}

		}

		Document document = null;

		if (content != null) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			try {
				document = buildDocument(content, dbf.newDocumentBuilder());
			} catch (ParserConfigurationException e) {
				LOG.fatal(e.getMessage(), e);
			}

		}

		return document;
	}

}
