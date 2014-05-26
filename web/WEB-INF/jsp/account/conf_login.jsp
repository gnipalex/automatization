<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Страница входа</title>
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
                    <div class="login_wraper">
                        <div class="login_form">
                            <c:if test="${not empty error}">
                            <div class="error">
                                ${error}
                            </div>
                            </c:if>
                            <form action="${ctx}/config/config_spring_login" method="post">
                            <table>
                                <tr>
                                    <td>Логин</td>
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
                            </table>
                            </form>
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