<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Адміністратор</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
    </head>
    <body>
        <div id="wraper">
            <div id="header">
                <%@include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div id="content">
                <div id="cwrap">
                    <h2>Завантаження планів</h2>
                    <p>Виберіть файл формату Microsoft Office Excel з семестровим планами</p>
                    <p>
                    <form>
                        <input type="file" />
                        <input type="submit" value="Завантажити" /> 
                    </form>
                    </p>
                    <h2>Додатково</h2>
                    <p>Створити нову групу. Оберіть семестр та спеціальність</p>
                    <p>
                        
                    </p>
                </div>
            </div>
            <div id="footer">
               <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>
