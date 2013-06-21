<!DOCTYPE HTML>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>zapezy.com - webtv done easy</title>
    <link rel="icon" type="image/gif" href="resources/favicon.ico">
    <link rel="stylesheet" href="<spring:theme code="css"/>" type="text/css" />
    <tiles:insertAttribute name="meta"/>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
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
    <c:if test="${infoMessage != null}">
        <span class="infoMessage">${infoMessage}</span>
    </c:if>
    <c:if test="${errorMessage != null}">
        <span class="errorMessage">${errorMessage}</span>
    </c:if>
    <tiles:insertAttribute name="body"/>
</div>
<div id="footer">
    <h3><tiles:insertAttribute name="footer"/></h3>
</div>
</body>
</html>