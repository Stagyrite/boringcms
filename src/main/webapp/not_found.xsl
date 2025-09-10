<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:lxslt="http://xml.apache.org/xslt" xmlns:boringcms="http://boringcms"
	extension-element-prefixes="boringcms">

	<lxslt:component prefix="boringcms" elements="java">
		<lxslt:script lang="javaclass" src="xalan://boringcms.servlets.XslExtensionsBase"/>
	</lxslt:component>

	<xsl:template match="/">
		<boringcms:java class="boringcms.xmlview.XmlRequest"/>
		<html lang="en">
			<head>
				<meta charset="utf-8"></meta>
				<meta name="viewport"
					content="width=device-width, initial-scale=1.0"></meta>
				<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
			</head>
			<body>
				<h2>Not found</h2>
				The requested page <xsl:value-of select="/all/request/request.info/requestURI" /> is not found.
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>
