<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="password.reset.type.new.password"/>
<form:form method="post" commandName="passwordBean" action="_resetPassword.html">
    <form:hidden path="token" />
    <table>
        <tr>
            <td><form:label path="password1"><spring:message code="label.password"/></form:label></td>
            <td><form:password path="password1" /></td>
            <td><form:errors path="password1" /></td>
        </tr>
        <tr>
            <td><form:label path="password2"><spring:message code="label.retype_password"/></form:label></td>
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