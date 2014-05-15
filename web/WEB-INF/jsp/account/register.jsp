<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Страница регистрации</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/login.css" />
    </head>
    <body>
        <div id="wraper">
            <div id="header">
                <%@include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div id="content">
                <div id="cwrap">
                    <c:if test="${not empty error}">
                        <div class="error">
                            ${error}
                        </div>
                    </c:if>
                    <div class="register_wraper">
                        <div class="login_form">
                            <form:form modelAttribute="registerForm" method="POST" action="${ctx}/account/register">
                                <table>
                                    <tr>
                                        <td class="label">Логин(почта)</td>
                                        <td><form:input path="email" cssClass="text_field"/>
                                            <br />
                                            <form:errors path="email" cssClass="error"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label">Фамилия</td>
                                        <td><form:input path="lastname" cssClass="text_field"/>
                                            <br />
                                            <form:errors path="lastname" cssClass="error"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label">Имя</td>
                                        <td><form:input path="name" cssClass="text_field"/>
                                            <br />
                                            <form:errors path="name" cssClass="error"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label">Кафедра</td>
                                        <td><form:select path="chair" cssClass="text_field">
                                                <form:option label="-не выбрано-" value="" />
                                                <c:if test="${not empty chairs}">
                                                    <c:forEach var="item" items="${chairs}">
                                                        <form:option label="${item.name}" value="${item.name}"/>
                                                    </c:forEach>
                                                </c:if>
                                            </form:select>
                                            <br />
                                            <form:errors path="chair" cssClass="error"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label">Пароль</td>
                                        <td><form:password path="password" cssClass="text_field"/>
                                            <br />
                                            <form:errors path="password" cssClass="error"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label">Пароль еще разок</td>
                                        <td><form:password path="password1" cssClass="text_field"/></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td><input type="submit" value="Регистрация" class="button"/></td>
                                    </tr>
                                </table>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
            <div id="footer">
               <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>

