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
                   <h1>Информация</h1>
                    <p>Приложение для разрешения семестровых планов. Предназначено для руководителей кафедр.</p>
                    <p>Возможности:</p>
                    <ul>
                        <li>Просмотр планов</li>
                        <li>Назначение преподавателя</li>
                        <li>Назначение аудитории</li>
                        <li>Редактирование списка преподавателей кафедры</li>
                    </ul>
                    <p>Автор : студент гр. 545а Гнып А.С., gnip.alex.ag@yandex.ru</p>
                    
                </div>
            </div>
            <div id="footer">
               <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>
