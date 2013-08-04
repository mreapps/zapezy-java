<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    $(function () {
        $("#tabs-min").tabs();
    });
    function showDescription(description) {
        $(".programmedetails").text(description);

    }
</script>
<style type="text/css">
    .programmes {
        height: 600px;
        width: 700px;
    }

    .programmes ul {
        list-style-type: none;
        font-family: tahoma, Times, Arial, Helvetica, serif;
        font-size: 1.2em;
        line-height: 1.3em;
        margin: 0;
        padding: 0;
    }

    .programmes li {
        border-top: 1px solid #ffffff;
        border-bottom: 1px solid #ffffff;
    }

    .programmes li:hover {
        background: #eeeeee;
        border-top: 1px solid #cccccc;
        border-bottom: 1px solid #cccccc;
    }

    .programmelist {
        width: 298px;
        height: 100%;
        float: left;
    }

    .programmedetails {
        background: #eeeeee;
        border: 1px solid #cccccc;
        width: 394px;
        height: 100%;
        float: right;
        padding: 3px;
    }

    .finished {
        color: #888888;
    }
    .current {
        font-weight: bold;
    }
    .future {

    }

</style>
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
                                    <c:set var="class" scope="request" value="current"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="class" scope="request" value="future"/>
                                </c:otherwise>
                            </c:choose>

                            <li onmouseover="showDescription('${programme.description}')" class="${class}">
                                <span style="width: 50px; display: inline-block;">${programme.start}</span>
                                <span>${programme.title}</span>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="programmedetails">
                    <p>
                        tester, tester...
                    </p>
                </div>

            </div>
        </div>
    </c:forEach>
</div>