<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>

<%
    String language = response.getLocale().getLanguage();
%>
<html>
<head>
    <title>zapezy.com - <spring:message code="slogan"/></title>
    <link rel="icon" type="image/gif" href="${pageContext.servletContext.contextPath}/resources/favicon.ico"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/tabs.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/message.css" type="text/css"/>


    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/form.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/stylized.css" type="text/css"/>
    <%--<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/<spring:theme code="css"/>" type="text/css"/>--%>
    <%--<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/redmond/jquery-ui.min.css" type="text/css"/>--%>
    <%--<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/pepper-grinder/jquery-ui.min.css" type="text/css"/>--%>
    <link rel="stylesheet" href="http://cdn.wijmo.com/themes/cobalt/jquery-wijmo.css" type="text/css"/>
    <tiles:insertAttribute name="meta"/>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
</head>
<body>
<div id="header">
    <div>
        <a href="${pageContext.servletContext.contextPath}/">
            <img src="${pageContext.servletContext.contextPath}/resources/image/tv_hvit.png" style="height: 50px"/>
            <div>
                <h1>zapezy.com</h1>
                <h2>::<spring:message code="slogan" /></h2>
            </div>
        </a>
    </div>
    <div id="menu">
        <ul>
            <c:forEach items="${menu}" var="item">
                <li><a href="${item.ref}">${item.label}</a></li>
            </c:forEach>
        </ul>
    </div>
    <div id="language_selection">
        <ul>
            <%
                if (language != null && language.equals("en"))
                {
            %>
            <li><a href="?lang=no"><img class="flag" src="${pageContext.servletContext.contextPath}/resources/image/flag_norway_bw.png"/></a></li>
            <li><a href="?lang=en"><img class="flag" src="${pageContext.servletContext.contextPath}/resources/image/flag_united_kingdom.png"/></a></li>
            <%
            } else
            {
            %>
            <li><a href="?lang=no"><img class="flag" src="${pageContext.servletContext.contextPath}/resources/image/flag_norway.png"/></a></li>
            <li><a href="?lang=en"><img class="flag" src="${pageContext.servletContext.contextPath}/resources/image/flag_united_kingdom_bw.png"/></a></li>
            <%
                }
            %>
        </ul>
    </div>
</div>
<div id="content">
    <c:if test="${STATUS_MESSAGE != null}">
        <div class="${STATUS_MESSAGE.type.name().toLowerCase()}">
                ${STATUS_MESSAGE.message}
            <c:if test="${STATUS_MESSAGE.url != null}">
                <a href="${STATUS_MESSAGE.url}">${STATUS_MESSAGE.urlText}</a>
            </c:if>
        </div>
    </c:if>
    <tiles:insertAttribute name="body"/>
</div>
</body>
</html>