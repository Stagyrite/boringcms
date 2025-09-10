<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:lxslt="http://xml.apache.org/xslt">

	<xsl:import href="demo.xsl"/>
	
	<xsl:output method="html" indent="yes" />

	<xsl:template match="/all/servlet/main/ascii-art">
		<xsl:apply-imports/>
		<a href="main_pdf.xml">Get PDF</a>
	</xsl:template>
</xsl:stylesheet>
