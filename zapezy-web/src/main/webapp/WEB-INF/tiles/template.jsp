<!DOCTYPE HTML>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<html>
<head>
    <title>zapezy.com - webtv done easy</title>
    <link rel="icon" type="image/gif" href="resources/favicon.ico">
    <tiles:insertAttribute name="meta" />
</head>
<body>
<div id="header">
    <h1><tiles:insertAttribute name="header" defaultValue=""/></h1>
</div>
<div id="body">
    <tiles:insertAttribute name="body" />
</div>
<div id="footer">
    <h3><tiles:insertAttribute name="footer" /></h3>
</div>
</body>
</html>