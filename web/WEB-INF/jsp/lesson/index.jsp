<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Учебные планы</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/plans.css" />
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/filter.css" />
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/lesson_window.css" />
        <script type="text/javascript" src="${ctx}/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="${ctx}/js/lesson.js"></script>
        <script type="text/javascript" src="${ctx}/js/filter.js"></script>
    </head>
    <body>
        <div id="wraper">
            <div id="header">
                <%@include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div id="content">
                <div id="cwrap">
                   <h2>Семестрові плани</h2>
                       <p>
                           <input type="button" value="Редагувати виділені" onclick="showManyEditWindow();"/>
                           <input type="button" value="Виділити всі" onclick="markAll();"/>
                           <input type="button" value="Зняти виділення" onclick="unMarkAll();"/>
                           <input type="button" value="Фільтр" title="Показати/Приховати поле фільтру" onclick="showFilter();"/>
                       </p>
                   
                   <div id="filter_holder" style="display:none;">
                       <%@include file="/WEB-INF/jsp/lesson/filter.jsp" %>
                   </div>
                   <div id="plans_holder">
                        <%@include file="/WEB-INF/jsp/lesson/ajax/lesson_table.jsp" %>
                   </div>
                   <hr/>
                   <p>Відображаються семестрові плани, які були створені іншими кафедрами. 
                       Ви можете обрати викладача та аудиторію. Для цього необхідно виділити необхідні строки в таблиці
                   та натиснути кнопку "Редагувати виділені", або клікніть курсором по колонці "Назначено" окремої строки.
                   </p>
                   
                </div>
            </div>
            <div id="footer">
               <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>


