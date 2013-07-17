<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
    $(function () {
        $("#datepicker").datepicker({
            maxDate: 0,
            changeMonth: true,
            changeYear: true,
            firstDay: 1,
            shortYearCutoff: 50,
            dateFormat: "dd. M yy",
            defaultDate: "01. Jan 1980"

        });
        $("#firstname").focus();
    });
</script>

<form:form method="post" commandName="userDetailBean" action="_changeUserDetails.html" cssClass="eform">
    <h1><spring:message code="user.edit_user_details"/></h1>
    <table>
        <tr>
            <td><form:label path="firstname"><spring:message code="label.firstname"/></form:label></td>
            <td>
                <form:input path="firstname"/>
                <form:errors path="firstname"/>
            </td>
        </tr>
        <tr>
            <td><form:label path="lastname"><spring:message code="label.lastname"/></form:label></td>
            <td>
                <form:input path="lastname"/>
                <form:errors path="lastname"/>
            </td>
        </tr>
        <tr>
            <td><form:label path="gender"><spring:message code="label.gender"/></form:label></td>
            <td>
                <form:select path="gender">
                    <form:option value="">--- <spring:message code="label.select_gender"/> ---</form:option>
                    <form:options items="${genders}"/>
                </form:select>
                <form:errors path="gender"/>
            </td>
        </tr>
        <tr>
            <td><form:label path="birthdayMonth"><spring:message code="label.birthday"/></form:label></td>
            <td>
                <form:select path="birthdayDay">
                    <form:option value="">--- <spring:message code="label.select_day"/> ---</form:option>
                    <form:options items="${days}"/>
                </form:select>
                <form:errors path="birthdayDay"/>
                <form:select path="birthdayMonth">
                    <form:option value="">--- <spring:message code="label.select_month"/> ---</form:option>
                    <form:options items="${months}"/>
                </form:select>
                <form:errors path="birthdayMonth"/>
                <form:select path="birthdayYear">
                    <form:option value="">--- <spring:message code="label.select_year"/> ---</form:option>
                    <form:options items="${years}"/>
                </form:select>
                <form:errors path="birthdayYear"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="<spring:message code="label.save"/>"/>
            </td>
        </tr>
    </table>

</form:form>