<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    $(function () {
        $("#email").focus();
    });
</script>

<%--suppress XmlPathReference --%>
<form id="login_form" name="f" action="<c:url value='j_spring_security_check' />" method='POST' class="eform">
    <h1><spring:message code="label.sign_in"/></h1>
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
                <label for="j_remember">Remember Me</label>
                <input id="j_remember" name="_spring_security_remember_me" type="checkbox"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input name="submit" type="submit" value="<spring:message code="label.sign_in"/>"/>
            </td>
        </tr>
    </table>
    <div>
        <a href="signUp"><spring:message code="label.register_user"/></a>
        <a href="forgotPassword"><spring:message code="label.forgot_password"/></a>
    </div>
    <a href="social/facebook/signin" id="facebooksignin">
        <img src="resources/image/facebook2.png" alt=""/>
        <span><spring:message code="label.sign_in_with_facebook"/></span>
    </a>
</form>