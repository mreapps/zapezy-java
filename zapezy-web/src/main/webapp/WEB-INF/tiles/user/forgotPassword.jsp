<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="type_email_address_to_reset_password"/>
<form:form method="post" commandName="passwordBean" action="sendResetPasswordToken.html">
    <table>
        <tr>
            <td><form:label path="email"><spring:message code="label.email"/></form:label></td>
            <td><form:input path="email" /></td>
            <td><form:errors path="email" /></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="<spring:message code="label.send.password.token"/>"/>
            </td>
        </tr>
    </table>

</form:form>