<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    $(function () {
        $("#email").focus();
    });
</script>

<form id="login_form" name="f" action="<c:url value='j_spring_security_check' />" method='POST'>
    <table>
        <tr>
            <td><label for="email"><spring:message code="label.email"/></label></td>
            <td><input id="email" type="text" name="j_username" value=""/></td>
        </tr>
        <tr>
            <td><label for="password"><spring:message code="label.password"/></label></td>
            <td><input id="password" type="password" name="j_password"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input name="submit" type="submit" value="<spring:message code="label.sign_in"/>"/>
            </td>
        </tr>
    </table>
    <a href="signUp">Register new user</a>
    <a href="forgotPassword">Forgotten password</a>
</form>