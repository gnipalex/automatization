<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Список викладачів</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/login.css" />
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/teachers.css" />
        <script type="text/javascript" src="${ctx}/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="${ctx}/js/teachers.js"></script>
    </head>
    <body>
        <div id="wraper">
            <div id="header">
                <%@include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div id="content">
                <div id="cwrap">
                    <h2>Викладачі кафедри ${chair.name}</h2>
                    <p>
                        <input type="button" onclick="newTeacher();" value="Добавить"/>
                    </p>
                    <div id="teachers_table_holder">
                            <table class="teachers_table">
                                <thead>
                                    <tr class="tr_h" onclick="">
                                        <th class="ln">Прізвище</th>
                                        <th class="mn">По батькові</th>
                                        <th class="fn">Им'я</th>
                                        <th class="post">Посада</th>
                                        <th class="sol">Кількість назначень</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:if test="${not empty teachers}">
                                <c:forEach var="t" items="${teachers}">
                                    <tr class="tr_" onclick="viewTeacherInfoWindow(${t.id});">
                                        <td class="ln">${t.lastName}</td>
                                        <td class="mn">${t.middleName}</td>
                                        <td class="fn">${t.firstName}</td>
                                        <td class="post">${t.post}</td>
                                        <td class="sol">
                                            <c:if test="${not empty t.solutionPlans}">
                                               ${t.solutionPlans.size()} 
                                            </c:if>                                   
                                        </td>
                                    </tr>
                                </c:forEach>   
                                </c:if>
                                </tbody>
                            </table>    
                    </div>
                    <hr/>
                    <p>На цій сторінці відображаються викладачі кафедри ${chair.name}. Ви можете добавити нового 
                    викладача чи редагувати існуючого, для цього клікніть по відповідній строці таблиці.</p>
                </div>
            </div>
            <div id="footer">
               <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>


