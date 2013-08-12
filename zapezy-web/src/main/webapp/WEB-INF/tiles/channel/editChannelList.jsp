<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
    #selectedChannels {
        border: 2px solid rgba(165, 245, 165, 0.54);
        background: rgba(165, 245, 165, 0.15);
        float: right;
    }

    #selectedChannels h2 {
        border-bottom: 2px solid rgba(165, 245, 165, 0.54);
    }

    #unselectedChannels {
        border: 2px solid rgba(245, 126, 118, 0.54);
        background: rgba(245, 126, 118, 0.15);
    }

    #unselectedChannels h2 {
        border-bottom: 2px solid rgba(245, 126, 118, 0.54);
    }

    .channelselector {
        border-radius: 5px;
        width: 48%;
        display: inline-block;
        min-height: 1900px;
        height: 100%;
    }

    .channelselector ul {
        list-style: none;
        margin: 0;
        padding: 0;
    }

    .channelselector li {
        border: 2px solid #c5c5c5;
        border-radius: 5px;
        margin: 3px;
        padding: 2px;
    }

    .channelselector h2 {
        margin: 0;
        padding: 5px;
        font-size: 1.2em;
        color: #333333;
    }

    .unselected, .selected {
        min-height: 1800px;
    }
</style>
<script type="text/javascript">
    $(function () {
        $("ul.unselected").sortable({
            connectWith: "ul",
            receive: function (event, ui) {
                updateChannelList();
            }
        });

        $("ul.selected").sortable({
            connectWith: "ul",
            receive: function (event, ui) {
                updateChannelList();
            },
            update: function (event, ui) {
                updateChannelList();
            }
        });

        $("#sortable1, #sortable2, #sortable3").disableSelection();
    });

    function updateChannelList() {
        var channels = "";
        $.each($("#sortable2 li input"), function () {
            channels += this.getAttribute("value") + ";; ";
        });

        $.ajax({
            url: "${pageContext.servletContext.contextPath}/channel/saveChannelList?channels="+channels
        });
    }
</script>
<%--TODO info dersom ingen distributører er valgt --%>
<%--TODO checkbox for å også vise kanaler fra andre distributører enn de som er valgt --%>

<%--<h1>Dra kanaler over fra h&oslash;yre side for &aring; velge</h1>--%>
<div>
    <div class="channelselector" id="unselectedChannels">
        <h2>Tilgjengelige kanaler</h2>
        <ul id="sortable1" class="unselected">
            <c:forEach items="${editChannelListBean.unselectedChannels}" var="channel">
                <li>
                    <img src="${channel.iconUrl}" alt="${channel.channelName}" height="40px"/>
                    <input type="hidden" value="${channel.channelId}"/>
                    Velg distribut&oslash;r
                </li>
            </c:forEach>
        </ul>
    </div>

    <div class="channelselector" id="selectedChannels">
        <h2>Valgte kanaler</h2>
        <ul id="sortable2" class="selected">
            <c:forEach items="${editChannelListBean.selectedChannels}" var="channel">
                <li>
                    <img src="${channel.iconUrl}" alt="${channel.channelName}" height="40px"/>
                    <input type="hidden" value="${channel.channelId}"/>
                    Velg distribut&oslash;r
                </li>
            </c:forEach>
        </ul>
    </div>
</div>