<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:lxslt="http://xml.apache.org/xslt">

	<xsl:output method="html" indent="yes" />

	<xsl:template match="/">
		<html>
			<head>
				<script type="text/javascript" src="https://cdn.ckeditor.com/ckeditor5/11.1.1/classic/ckeditor.js"></script>
				<script type="text/javascript">
					function createEditor() {
						ClassicEditor
						    .create( document.querySelector( '#editor' ) )
						    .then( editor => {
						        console.log( editor );
						    } )
						    .catch( error => {
						        console.error( error );
						    } );
					}
				</script>
			</head>
			<body onLoad="createEditor()">
				<a href="/j_admin_logout">Logout</a>
				<textarea name="body" id="editor"/>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
