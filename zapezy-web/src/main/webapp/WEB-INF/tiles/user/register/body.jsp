<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form:form method="post" action="registerUser.html">

    <table>
        <tr>
            <td><form:label path="email">Email address</form:label></td>
            <td><form:input path="email" /></td>
        </tr>
        <tr>
            <td><form:label path="password1">Password</form:label></td>
            <td><form:password path="password1" /></td>
        </tr>
        <tr>
            <td><form:label path="password2">Re-type password</form:label></td>
            <td><form:password path="password2" /></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Register"/>
            </td>
        </tr>
    </table>

</form:form>