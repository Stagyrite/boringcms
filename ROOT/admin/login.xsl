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
				<script type="text/javascript">
					function showMessages() {
					
						<xsl:for-each select="/all/servlet/view/message">
							alert('<xsl:value-of select="."/>');
						</xsl:for-each>
					
					}
				</script>
			</head>
			<body onLoad="showMessages()">
				<h2>Login</h2>
				<form method="POST" action="/j_admin_login">
					<table>
						<tr>
						<td>User:</td><td><input type="text" name="username"/></td>
						</tr>
						<tr>
						<td>Password:</td><td><input type="password" name="password"/></td>
						</tr>
						<tr>
							<td colspan="2" align="right"><input type="submit" value="Login"/></td>
						</tr>
					</table>
				</form>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>
