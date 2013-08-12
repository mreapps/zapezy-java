<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
    h1 {
        font-family: verdana, serif;
        font-size: 1.5em;
        color: #555555;
        margin-bottom: 3px;
    }

    #distributorlist {
        list-style-type: none;
        margin: 0;
        padding: 0;
    }

    #distributorlist li {
        display: inline-block;
        margin-bottom: 5px;
        padding: 10px;
        border-radius: 10px;
        border: 2px solid #a0a0a0;
        background: #d5d5d5;
        width: 290px;
    }

    #distributorlist li:hover {
        cursor: pointer;
        background: #e5e5e5;
    }

    .visible {
        display: inline-block;
    }

    .invisible {
        display: none;
    }
</style>

<script type="text/javascript">
    function toggle(element, id) {
        $.ajax({
            url: "${pageContext.servletContext.contextPath}/user/toggleDistributor?id="+id
        }).done(function () {
                    var src = $(element).find(".check").attr("src");

                    if (src == '${pageContext.servletContext.contextPath}/resources/image/check.png') {
                        src = '${pageContext.servletContext.contextPath}/resources/image/reject.png';
                    }
                    else {
                        src = '${pageContext.servletContext.contextPath}/resources/image/check.png';
                    }

                    $(element).find(".check").attr("src", src);
                });
    }
</script>

<h1><spring:message code="click_to_select_distributor"/></h1>
<ul id="distributorlist">
    <c:forEach items="${distributors}" var="distributor">
        <li class="distributor" onclick="toggle(this, ${distributor.id})">
            <img class="check"
                 src="${pageContext.servletContext.contextPath}/resources/image/<c:choose><c:when test="${distributor.selected}">check</c:when><c:otherwise>reject</c:otherwise></c:choose>.png"
                 alt="Checked" height="40px" style="margin-right: 3px"/>
            <img src="${distributor.iconUrl}" alt="${distributor.name}" height="40px"/>
        </li>
    </c:forEach>
</ul>