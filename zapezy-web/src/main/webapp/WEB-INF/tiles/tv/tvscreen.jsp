<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="label.watch_tv"/></title>
    <link rel="icon" type="image/gif" href="resources/favicon.ico"/>
    <link rel="stylesheet" href="resources/css/tvscreen.css"/>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#channel_list").hide();
            updateChannelList(true);

            $("#channel_list_mouselistener").mouseenter(function() {
                showChannelList();
            });

            $("#channel_list").mouseleave(function() {
                hideChannelList();
            });
        });

        function updateChannelList(zap) {
            $.getJSON('channels.json', function (data) {
                var items = [];

                $.each(data, function (key, val) {
                    var html = '';
                    html += '<a href="#" onclick="zap(\'' + val.channelId + '\', \'' + val.webtvUrl + '\')">';
                    html += '<img src="' + val.iconUrl + '" width="40px" />';
                    html += '<div class="crop">';
                    html += '<span class="currStart">' + val.currentProgrammeStartTime + '</span>'
                    html += '<span class="currTitle">' + val.currentProgrammeTitle + '</span>'
                    html += '</div>';
                    html += '<div class="crop">';
                    html += '<span class="nextStart">' + val.nextProgrammeStartTime + '</span>'
                    html += '<span class="nextTitle">' + val.nextProgrammeTitle + '</span>'
                    html += '</div>';
                    html += '</a>';
                    items.push('<li id="' + val.channelId + '">' + html + '</li>');
                });

                $('<ul/>', {
                    'class':'my-new-list',
                    html:items.join('')
                }).appendTo('#channel_list');

                if(zap) {
                    $("li:first-child").find('a').click();
                }
            });
        }

        function zap(channelId, webtvUrl) {
            $("li").each(function () {
                if ($(this).attr("id") == channelId) {
                    $("#tvframe").attr("src", webtvUrl);
                    $(this).attr("class", "selected");
                }
                else {
                    $(this).attr("class", "");
                }
            });
        }

        function hideChannelList() {
            $("#channel_list").hide('slide', {direction: 'left'}, 300);
        }

        function showChannelList() {
            $("#channel_list").show('slide', {direction: 'left'}, 300);
        }
    </script>
</head>
<body>
<iframe id="tvframe" src=""></iframe>
<div id="channel_list"></div>
<div id="channel_list_mouselistener"></div>

</body>
</html>