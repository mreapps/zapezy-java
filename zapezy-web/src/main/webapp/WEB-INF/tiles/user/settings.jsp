<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<style type="text/css">
    .menuitem {
        border: 2px solid #c0c0c0;
        border-radius: 10px;
        float: left;
        margin-top: 10px;
        margin-right: 20px;
        width: 120px;
        height: 120px;
        background: #f5f5f5;
    }

    .menuitem a {
        height: 100%;
        width: 100%;
        display: inline-block;
        color: #000000;
        text-decoration: none;
    }

    .menuitem:hover {
        background: #e5e5e5;
    }

    .menuitem img {
        padding-top: 20px;
        display: block;
        vertical-align: middle;
        margin: auto;
    }

    .menuitem span {
        display: block;
        text-align: center;
        margin: auto;
    }
</style>

<div class="menuitem">
    <a href="${pageContext.servletContext.contextPath}/user/changeUserDetails">
        <img src="${pageContext.servletContext.contextPath}/resources/image/addressbook.png" style="height: 50px"
             alt="<spring:message code="label.user_details" />"/>
        <span><spring:message code="label.user_details"/></span>
    </a>
</div>

<div class="menuitem">
    <a href="${pageContext.servletContext.contextPath}/user/editDistributors">
        <img src="${pageContext.servletContext.contextPath}/resources/image/stack.png" style="height: 50px"
             alt="<spring:message code="label.select_distributors" />"/>
        <span><spring:message code="label.select_distributors"/></span>
    </a>
</div>

<div class="menuitem">
    <a href="${pageContext.servletContext.contextPath}/channel/editChannelList">
        <img src="${pageContext.servletContext.contextPath}/resources/image/stack.png" style="height: 50px"
             alt="<spring:message code="label.edit_channel_list" />"/>
        <span><spring:message code="label.edit_channel_list"/></span>
    </a>
</div>