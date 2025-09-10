<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:lxslt="http://xml.apache.org/xslt">

	<xsl:template match="/all/servlet/main">
		<html>
			<body>
				<xsl:apply-templates select="ascii-art"/>
				<hr/>
				Your IP address is <xsl:value-of select="remoteAddr"/>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="/all/servlet/main/ascii-art">
		<h2>boringcms Demo Page</h2>
		<pre>
			<xsl:value-of select="."/>
		</pre>
	</xsl:template>
</xsl:stylesheet>
