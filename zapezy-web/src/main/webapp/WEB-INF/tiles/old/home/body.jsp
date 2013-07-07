<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
Home sweet home...

<script type="text/javascript">
    $("a.writeJsonLink").click(function() {
        var link = $(this);
        $.ajax({ url: this.href,
            beforeSend: function(req) {
                if (!this.url.match(/\.json$/)) {
                    req.setRequestHeader("Accept", "application/json");
                }
            },
            success: function(json) {
                MvcUtil.showSuccessResponse(JSON.stringify(json), link);
            },
            error: function(xhr) {
                MvcUtil.showErrorResponse(xhr.responseText, link);
            }});
        return false;
    });
</script>

<h3>Message : ${message}</h3>
<h3>Username : ${username}</h3>

<a id="byProducesJsonExt" class="writeJsonLink" href="<c:url value="/channels.json" />">By produces via ".json"</a>

<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>