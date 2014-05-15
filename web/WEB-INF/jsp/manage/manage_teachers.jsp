<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Список преподавателей</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/login.css" />
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/manage.css" />
        <script type="text/javascript" src="${ctx}/js/jquery-1.11.0.js"></script>
    </head>
    <body>
        <div id="wraper">
            <div id="header">
                <%@include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div id="content">
                <div id="cwrap">
                    <h2>Преподаватели кафедры ${chair.name}</h2>
                    <p>Информация о преподавателях кафедры и учебных планах с которыми они связаны</p>
                    <div id="teachers_table_holder">
                        <c:if test="${not empty teachers}">
                            <table class="teachers_table">
                                <tr class="tr_h" onclick="">
                                    <th class="fn">Фамилия</th>
                                    <th class="mn">Отчество</th>
                                    <th class="ln">Имя</th>
                                    <th class="post">Должность</th>
                                    <th class="sol">Кол-во решений</th>
                                </tr>
                                <c:forEach var="t" items="${teachers}">
                                    <tr class="tr_">
                                        <td class="fn">${t.firstName}</td>
                                        <td class="mn">${t.middleName}</td>
                                        <td class="ln">${t.lastName}</td>
                                        <td class="post">${t.post}</td>
                                        <td class="sol">
                                            <c:if test="${not empty t.solutionPlans}">
                                               ${t.solutionPlans.size()} 
                                            </c:if>                                   
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                </div>
            </div>
            <div id="footer">
               <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>


