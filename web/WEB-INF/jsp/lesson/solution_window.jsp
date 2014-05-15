<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<c:if test="${not empty lesson_id}">
    <input type="hidden" id="_solution_lesson_id_hidden" value="${lesson_id}" />
    <c:if test="${not empty solution}">
        <input type="hidden" id="_solution_solution_id_hidden" value="${solution.id}"/>
        <c:set var="teacher_id" value="${solution.teacher.id}"></c:set>
        <c:set var="room_id" value="${solution.room.id}"></c:set>
    </c:if>
    <div class="window_wraper">
        <div class="window_upper">
            <div class="left">Разрешение плана</div>
            <div class="right">
                <a class="click_text" onclick="closeWindow(SOLUTION_WINDOW_ID);">close</a>
            </div>
            <div style="clear:both;"></div>
        </div>
        <div class="window_content">
            <div>
                <table class="info_table">
                    <tr>
                        <th class="c1">Специальность</th>
                        <td class="c2">${lesson.speciality.name}</td>
                    </tr>
                    <tr>
                        <th class="c1">Группа</th>
                        <td class="c2">
                            <c:forEach var="g" items="${lesson.groups}">${g.name};</c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <th class="c1">Дисциплина</th>
                        <td class="c2">${lesson.discipline.name}</td>
                    </tr>
                    <tr>
                        <th class="c1">Тип занятий</th>
                        <td class="c2">${lesson.discType.name}</td>
                    </tr>
                </table>
            </div>
            <div>
                <p>Преподаватель<br/>
                    <select class="solution_internal_select" id="_edit_solution_select_teacher">
                        <%@include file="/WEB-INF/jsp/lesson/teachers_list.jsp" %>
                    </select>
                    <a class="click_text" onclick="viewAddTeacher();">+</a>
                </p>
                <p>Аудитория<br/>
                    <select class="solution_internal_select" id="_edit_solution_select_room">
                        <%@include file="/WEB-INF/jsp/lesson/rooms_list.jsp" %>
                    </select>
                    <a class="click_text" onclick="viewAddRoom();">+</a>
                </p>
            </div>
        </div>
        <div class="window_footer">
            <button onclick="saveSolution();">Сохр</button>
            &nbsp<button onclick="closeWindow(SOLUTION_WINDOW_ID);">Отмена</button>
        </div>
    </div>
</c:if>