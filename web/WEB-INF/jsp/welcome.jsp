<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Приветствие</title>
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
                   <h1>Вітаємо!</h1>
                    <p>Цей додаток представляє собою підсистему для назначення семестрових планів. 
                        Додаток дає можливість користувачам переглядати семестрові плани, 
                        обирати викладача для плана а також аудиторію. Користувач також має можливість завантажити 
                        список планів з файлу MS Excel. 
                        Для користування системою вам необхідно <a href="${ctx}/account/login">ввійти</a></p>
                    <%--<p><a href="${ctx}/lesson">Я хочу посмотреть список занятий</a></p>
                    <p><a href="${ctx}/config/users">Страница пользователей</a></p>
                    --%>
                    <%-- <p>${aut}</p> --%>
                </div>
            </div>
            <div id="footer">
               <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>
