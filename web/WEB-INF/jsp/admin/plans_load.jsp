<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Список пользователей</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
    </head>
    <body>
        <div id="wraper">
            <div id="header">
                <%@include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div id="content">
                <div id="cwrap">
                    <h1>Завантаження планів</h1>
                    <p>Виберіть файл формату Microsoft Office Excel з семестровим планами</p>
                    <p>
                    <form method="post" action="" enctype="multipart/form-data">
                        <input type="file" name="file" />
                        <label><input type="checkbox" name="overwrite"/>Перезаписати співпадаючі існуючі плани</label>
                        <input type="submit" value="Завантажити" />
                        <p>
                            Строки таблиці будуть збережені в базі даних. Ви можете зберегти тільки плани
                            зв'язані з вашою кафедрою.
                        </p>
                    </form>
                    </p>
                </div>
            </div>
            <div id="footer">
               <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>
