<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Приветствие</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
    </head>
    <body>
        <div id="wraper">
            <div id="header">
                <%@include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div id="content">
                <div id="cwrap">
                    <h1>Пользователи системы</h1>
                    <table>
                        <tr>
                            <th>Почта</th>
                            <th>ФИО</th>
                            <th>Кафедра</th>
                            <th>Активность</th>
                        </tr>
                        <c:forEach var="item" items="${users}">
                            <tr>
                                <td>${item.email}</td>
                                <td>${item.lastName} ${item.name}</td>
                                <td>${item.readingChair.name}</td>
                                <td>
                                    <c:if test="${item.isActive}">
                                        <a href="${ctx}/admin/activate?id=${item.id}&activate=false">Выключить</a>
                                    </c:if>
                                    <c:if test="${!item.isActive}">
                                        <a href="${ctx}/admin/activate?id=${item.id}&activate=true">Включить</a>
                                    </c:if>
                                </td>
                            </tr>       
                        </c:forEach>
                    </table>
                </div>
            </div>
            <div id="footer">
               <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>
