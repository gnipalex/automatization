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
    </head>
    <body>
        <div id="wraper">
            <div id="header">
                <%@include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div id="content">
                <div id="cwrap">
                   <h2>Учебные планы</h2>
                   <c:if test="${not empty appUser}">
                       <p>Вы авторизованы как ${appUser.lastName} ${appUser.name}, кафедра ${appUser.readingChair.name}</p>
                   </c:if>
                   <div id="filter_holder">
                       <%@include file="/WEB-INF/jsp/lesson/filter.jsp" %>
                   </div>
                   <div id="plans_holder">
                        <%@include file="/WEB-INF/jsp/lesson/lesson_table.jsp" %>
                   </div>
                </div>
            </div>
            <div id="footer">
               <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>


