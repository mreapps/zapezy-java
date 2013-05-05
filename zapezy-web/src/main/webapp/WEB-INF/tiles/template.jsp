<!DOCTYPE HTML>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>zapezy.com - webtv done easy</title>
    <link rel="icon" type="image/gif" href="resources/favicon.ico">
    <link rel="stylesheet" href="<spring:theme code="css"/>" type="text/css" />
    <tiles:insertAttribute name="meta"/>
</head>
<body>

<div id="header">
    <h1><tiles:insertAttribute name="header" defaultValue=""/></h1>
    <span style="float: right">
    <a href="?lang=en">en</a>
    |
    <a href="?lang=no">no</a>

    <span style="float: left">
    <a href="?theme=default">default</a>
    |
    <a href="?theme=dark">dark</a>
</span>
</span>
</div>
<div id="body">
    <tiles:insertAttribute name="body"/>
</div>
<div id="footer">
    <h3><tiles:insertAttribute name="footer"/></h3>
</div>
</body>
</html>