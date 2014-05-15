<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<div class="login_form">
    <c:if test="${not empty error}">
    <div class="error">
        ${error}
    </div>
    </c:if>
    <form action="${ctx}/spring_login" method="post">
    <table>
        <tr>
            <td>Логин(почта)</td>
            <td><input type="text" class="text_field" name="login"/></td>
        </tr>
        <tr>
            <td>Пароль</td>
            <td><input type="password" class="text_field" name="password"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Вход" class="button"/></td>
        </tr>
        <tr>
            <td colspan="2">
                Пройти <a href="${ctx}/account/register">регистрацию</a>
            </td>
        </tr>
    </table>
    </form>
</div>