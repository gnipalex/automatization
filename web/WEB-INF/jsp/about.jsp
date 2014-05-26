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
                   <h1>Информація</h1>
                    <p>Додаток для назначення семестрових планів.</p>
                    <p>Можливості:</p>
                    <ul>
                        <li>перегляд планів</li>
                        <li>створення викладача</li>
                        <li>назначення викладача</li>
                        <li>назначення аудиторії</li>
                        <li>редагування списку викладачів кафедри</li>
                    </ul>
                    <p>Автор : студент гр. 545а Гнып А.С., gnip.alex.ag@yandex.ru</p>
                    
                    <br />
                    <hr />
                    <p><a href="${ctx}/config/users">Сторінка конфігурації</a></p>
                </div>
            </div>
            <div id="footer">
               <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>
