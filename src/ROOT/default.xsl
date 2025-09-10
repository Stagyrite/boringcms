<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<html lang="en">
			<head>
				<meta charset="utf-8"></meta>
				<meta name="viewport"
					content="width=device-width, initial-scale=1.0"></meta>
				<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
			</head>
			<body>
				<h1>BoringCMS</h1>
				<h2>It works!</h2>
				<xsl:value-of select="/all/document/body" disable-output-escaping="yes" />
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>
