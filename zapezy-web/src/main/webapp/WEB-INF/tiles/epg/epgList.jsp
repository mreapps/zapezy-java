<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    $(function () {
        $("#tabs-min").tabs();
    });
    //    function showDescription(description) {
    //        $(".programmedetails").text(description);
    //    }
</script>

<style type="text/css">
    #epglist {
        border: 1px solid #dddddd;
        border-radius: 5px;
        width: 49%;
        display: inline-block;
        /*float: left;*/
    }

    #epglist a {
        display: inline-block;
        text-decoration: none;
        height: 100%;
        width: 100%;
    }

    #epgChannelDetail {
        border: 1px solid #dddddd;
        border-radius: 5px;
        width: 49%;
        display: inline-block;
        float: right;

        min-height: 200px;
    }

    #epglist ul {
        list-style: none;
        margin: 0;
        padding: 0;
    }

    .channel {
        display: inline-block;
        width: 90%;
        padding: 0;
        margin: 0;
    }

    .icon {
        display: inline-block;
        padding-left: 10px;
        width: 70px;
        margin: auto;
    }

    .programmeInfo {
        margin-top: 5px;
        margin-bottom: 5px;
        display: inline-block;
    }

    .watch_tv {
        display: inline-block;
        position: relative;
        top: -5px;
    }

    #epglist li:nth-child(even) {
        background: #ffffff;
    }

    #epglist li:nth-child(odd) {
        background-color: #f5f5f5;
    }

    .current {
        font-size: 1.2em;
        margin-bottom: 2px;
        color: #000000;
    }

    .next {
        margin-top: 2px;
        color: #888888;
    }

    #epglist ul li:hover {
        background: #fdfdbe;
    }

    #selectedChannel {
        background: #fdfdbe;
    }

    #channeldetails {
        padding: 5px 0 5px 10px;
        border-bottom: 1px solid #cccccc;
        background: #f5f5f5;
    }

    #channelIcon {
        vertical-align: middle;
        height: 50px;
    }

    #channelName {
        vertical-align: middle;
        padding-left: 10px;
        font-size: 1.5em;
    }

    #channeldetails a {
        vertical-align: middle;
        margin-left: 50px;
        color: #555555;
        text-decoration: none;
    }

    #channeldetails a:hover {
        color: #333333;
        text-decoration: underline;
    }

    .programmes ul {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .programmes li {
        margin-bottom: 3px;
    }

    .programmes .finished {
        color: #bbbbbb;
    }

    .programmes .currentProgramme {
        border-top: 1px solid #000000;
        border-bottom: 1px solid #000000;
        color: #000000;
        padding-top: 3px;
        padding-bottom: 3px;
        background: #f5f5f5;

    }

    .programmes .future {
        color: #333333;
    }

</style>

<div id="epgAll">
    <div id="epglist">
        <ul>
            <c:forEach items="${channels}" var="channel" varStatus="count">
                <c:choose>
                    <c:when test="${channel.selected}">
                        <c:set var="li_id" scope="request" value="selectedChannel"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="li_id" scope="request" value="s"/>
                    </c:otherwise>
                </c:choose>

                <li>
                    <a href="?channel=${count.index}" id="${li_id}">
                        <div class="channel">
                            <div class="icon">
                                <img src="${channel.iconUrl}" alt="${channel.channelName}" style="height: 35px"/>
                            </div>
                            <div class="programmeInfo">
                                <div class="current">${channel.currentStartAndTitle}</div>
                                <div class="next">${channel.nextStartAndTitle}</div>
                            </div>
                        </div>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div id="epgChannelDetail">
        <div id="channeldetails">
            <img id="channelIcon" src="${selectedChannel.iconUrl}"/>
            <span id="channelName">${selectedChannel.channelName}</span>
            <a href="${pageContext.servletContext.contextPath}/tvscreen?channelId=${selectedChannel.channelId}"><spring:message key="watch_channel" /></a>
        </div>
        <div id="tabs-min">
            <ul>
                <c:forEach items="${days}" var="day">
                    <li><a href="#tabs-${day.dayResourceKey}"><spring:message key="${day.dayResourceKey}"/></a></li>
                </c:forEach>
            </ul>
            <c:forEach items="${days}" var="day">
                <div id="tabs-${day.dayResourceKey}">
                    <div class="programmes">
                        <div class="programmelist">
                            <ul>
                                <c:forEach items="${programmesPerDay[day]}" var="programme">

                                    <c:choose>
                                        <c:when test="${programme.finished}">
                                            <c:set var="class" scope="request" value="finished"/>
                                        </c:when>
                                        <c:when test="${programme.current}">
                                            <c:set var="class" scope="request" value="currentProgramme"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="class" scope="request" value="future"/>
                                        </c:otherwise>
                                    </c:choose>

                                    <li class="${class}">
                                        <span style="width: 100px; display: inline-block;">${programme.start}</span>
                                        <span>${programme.title}</span>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>