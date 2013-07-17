<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
    $(function () {
        $("#oldPassword").focus();
    });
</script>

<spring:message code="password.change.type.new.password"/>
<form:form method="post" commandName="changePasswordBean" action="_changePassword.html">
    <form:hidden path="email" />
    <table>
        <tr>
            <td><form:label path="oldPassword"><spring:message code="label.old_password"/></form:label></td>
            <td><form:password path="oldPassword" /></td>
            <td><form:errors path="oldPassword" /></td>
        </tr>
        <tr>
            <td><form:label path="password1"><spring:message code="label.new_password"/></form:label></td>
            <td><form:password path="password1" /></td>
            <td><form:errors path="password1" /></td>
        </tr>
        <tr>
            <td><form:label path="password2"><spring:message code="label.retype_new_password"/></form:label></td>
            <td><form:password path="password2" /></td>
            <td><form:errors path="password2" /></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="<spring:message code="label.save"/>"/>
            </td>
        </tr>
    </table>

</form:form>