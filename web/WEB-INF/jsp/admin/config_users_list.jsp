<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Настройка системи</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/config.css" />
        <script type="text/javascript" src="${ctx}/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="${ctx}/js/config.js"></script>
    </head>
    <body>
        <div id="wraper">
            <div id="header">
                <%@include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div id="content">
                <div id="cwrap">
                    <h2>Користувачі системи</h2>
                    <table id="users_table">
                        <thead>
                            <tr>
                                <th class="c1">Пошта</th>
                                <th class="c2">ПІБ</th>
                                <th class="c3">Кафедра</th>
                                <th class="c4">Активний</th>
                                <th class="c5">Ролі</th>
                                <th class="c6"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${users}">
                                <tr>
                                    <td class="c1">${item.email}</td>
                                    <td class="c2">${item.lastName} ${item.name}</td>
                                    <td class="c3">${item.readingChair.name}</td>
                                    <td class="c4">
                                        <c:if test="${item.isActive}">
                                            <a href="${ctx}/config/activate?id=${item.id}&activate=false">Выключить</a>
                                        </c:if>
                                        <c:if test="${!item.isActive}">
                                            <a href="${ctx}/config/activate?id=${item.id}&activate=true">Включить</a>
                                        </c:if>
                                    </td>
                                    <td class="c5" onclick="showUserRolesWindow(${item.id});">
                                        <c:if test="${not empty item.roles}">
                                            <c:forEach var="r" items="${item.roles}">
                                                ${r.name};
                                            </c:forEach>
                                        </c:if>
                                    </td>
                                    <td class="c6">
                                        <a class="click_text" onclick="deleteUser(${item.id});">X</a>
                                    </td>
                                </tr>       
                            </c:forEach>
                        </tbody>
                    </table>
                    <h2>Завантаження планів</h2>
                    <p>Виберіть файл формату Microsoft Office Excel з семестровим планами</p>
                    <p>
                    <form method="post" action="${ctx}/config/plansLoad" enctype="multipart/form-data">
                        <input type="file" name="file" />
                        <label><input type="checkbox" name="overwrite"/>Перезаписати співпадаючі існуючі плани</label>
                        <p>Починаючи з номеру строки 
                        <input type="text" name="start"/> кількість записів <input type="text" name="count"/>
                        </p>
                        <input type="submit" value="Завантажити" /> 
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
