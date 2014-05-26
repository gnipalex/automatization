<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Учебные планы</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/plans.css" />
        <%--<link rel="stylesheet" type="text/css" href="${ctx}/styles/group.css" />
        <script type="text/javascript" src="${ctx}/js/group.js"></script>
        --%>
        <script type="text/javascript" src="${ctx}/js/jquery-1.11.0.js"></script>
        
    </head>
    <body>
        <div id="wraper">
            <div id="header">
                <%@include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div id="content">
                <div id="cwrap">
                    <h2>Плани кафедри</h2>
                    <p>Список планів та назначень вирішених читаючими кафедрами</p>
                    <div id="plans_holder">
                        <c:if test="${empty lessonPlans}">
                            <p>-- Планы не найдены --</p>
                        </c:if> 
                        <c:if test="${not empty lessonPlans}">
                            <table class="plans">
                                <thead>
                                    <tr class="tr_top">
                                        <th><div class="chkb">*</div></th>
                                <th><div class="sem">Семестр</div></th>
                                <th><div class="spec">Спеціальність</div></th>
                                <th><div class="chair">Кафедра</div></th>
                                <th><div class="group">Група</div></th>
                                <th><div class="disc">Дисципліна</div></th>
                                <th title="Курсовий проект"><div class="bool">КП</div></th>
                                <th title="Курсова робота"><div class="bool">КР</div></th>
                                <th title="Екзамен"><div class="bool">Е</div></th>
                                <th title="Залік"><div class="bool">З</div></th>
                                <th title="Диференціований залік"><div class="bool">ДЗ</div></th>
                                <th title="Лекційні години"><div class="hour">ЛкГ</div></th>
                                <th title="Практичні години"><div class="hour">ПрГ</div></th>
                                <th title="Лабораторні години"><div class="hour">ЛабГ</div></th>
                                <th><div class="prepod">Назначено</div></th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${lessonPlans}">
                                        <c:if test="${empty item.solutions}">
                                            <c:set var="item_style_class" value="tr_red"></c:set>
                                        </c:if>
                                        <c:if test="${not empty item.solutions}">
                                            <c:set var="item_style_class" value="tr_green"></c:set>
                                        </c:if>
                                        <tr onclick="" class="${item_style_class}">
                                            <td>
                                                <div class="chkb">
                                                </div>
                                            </td>
                                            <td><div class="sem">${item.semester.name}</div></td>
                                            <td><div class="spec">${item.speciality.name}</div></td>
                                            <td><div class="chair">${item.producedChair.name}</div></td>
                                            <td><div class="group">${item.group.name}</div></td> 
                                            <td><div class="disc">${item.discipline.name}</div></td>
                                            <td><div class="bool">
                                                    <c:if test="${item.cp}">+</c:if>
                                                    </div>
                                                </td>
                                                <td><div class="bool">
                                                    <c:if test="${item.cw}">+</c:if></div>
                                                </td>
                                                <td><div class="bool">
                                                    <c:if test="${item.zachet}">+</c:if></div>
                                                </td>
                                                <td><div class="bool">
                                                    <c:if test="${item.exam}">+</c:if></div>
                                                </td>
                                                <td><div class="bool">
                                                    <c:if test="${item.div_zachet}">+</c:if></div>
                                                </td>
                                                <td><div class="hour">${item.hoursLectAll}</div></td>
                                            <td><div class="hour">${item.hoursPractAll}</div></td>
                                            <td><div class="hour">${item.hoursLabAll}</div></td>
                                            <td><div class="prepod">
                                                    <c:forEach var="sol" items="${item.solutions}">
                                                        *<c:if test="${not empty sol.teacher}">${sol.teacher.post} ${sol.teacher.getShortFIO()}</c:if>
                                                        <c:if test="${not empty sol.room}"> аудитория ${sol.room.name}</c:if>
                                                            <br/>
                                                    </c:forEach>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if> 
                    </div>
                    <%-- <div>
                        <p>Добавити групу. Оберіть спеціальність<br />
                            <select id="produced_speciality_select_id">
                                <option>--не вибрано--</option>
                                <c:if test="${not empty specialities}">
                                    <c:forEach var="s" items="${specialities}">
                                        <option value="${s.id}">${s.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select> 
                        </p>
                        <p>Оберіть семестр<br />
                            <select id="produced_semester_select_id">
                                <option>--не вибрано--</option>
                                <c:if test="${not empty semesters}">
                                    <c:forEach var="sem" items="${semesters}">
                                        <option value="${sem.id}">${sem.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </p>
                        <p>Введіть назву групи<br />
                            <input type="text" /> <input type="button" value="Створити групу"/>
                        </p>
                    </div>
                    --%>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <h2>Завантаження планів</h2>
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
                    </sec:authorize>
                </div>
            </div>
            <div id="footer">
                <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>


