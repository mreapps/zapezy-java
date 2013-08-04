<!DOCTYPE HTML>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
    String language = response.getLocale().getLanguage();
%>
<html>
<head>
    <title>zapezy.com - <spring:message code="slogan"/></title>
    <link rel="icon" type="image/gif" href="${pageContext.servletContext.contextPath}/resources/favicon.ico"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/form.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/stylized.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/<spring:theme code="css"/>" type="text/css"/>
    <%--<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/redmond/jquery-ui.min.css" type="text/css"/>--%>
    <%--<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/pepper-grinder/jquery-ui.min.css" type="text/css"/>--%>
    <link rel="stylesheet" href="http://cdn.wijmo.com/themes/cobalt/jquery-wijmo.css" type="text/css"/>
    <tiles:insertAttribute name="meta"/>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
</head>
<body>
<div id="wrapper">
    <div id="header">
        <img src="${pageContext.servletContext.contextPath}/resources/image/tv.png"/>

        <h1>zapezy.com</h1>

        <h2>::<spring:message code="slogan"/></h2>

        <div id="menu">
            <ul>
                <li class="selected"><a href="${pageContext.servletContext.contextPath}/home">Home</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/tvscreen">TV-screen</a></li>
                <sec:authorize access="isAnonymous()">
                    <li><a href="${pageContext.servletContext.contextPath}/user/signIn"><spring:message code="label.sign_in"/> </a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li><a href="<c:url value="/j_spring_security_logout" />"><spring:message code="label.sign_out"/></a></li>
                    <li><a href="${pageContext.servletContext.contextPath}/user/changeUserDetails"><spring:message code="label.user_details"/></a></li>
                </sec:authorize>
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
    <div id="footer">
        zapezy.com :: &copy;mreapps.com
    </div>
</div>
</body>
</html>