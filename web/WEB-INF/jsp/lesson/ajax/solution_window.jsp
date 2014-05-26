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
            <div class="left">Назначення плану</div>
            <div class="right">
                <a class="click_text" onclick="closeWindow(SOLUTION_WINDOW_ID);">close</a>
            </div>
            <div style="clear:both;"></div>
        </div>
        <div class="window_content">
            <div>
                <p>Викладач<br/>
                    <select class="solution_internal_select" id="_edit_solution_select_teacher">
                        <%@include file="/WEB-INF/jsp/lesson/ajax/teachers_list.jsp" %>
                    </select>
                </p
                <p>Корпус<br/>
                    <select onchange="loadRooms();" class="solution_internal_select" id="_edit_solution_select_building">
                        <option>--не задано--</option>
                        <c:if test="${not empty buildings}">
                            <c:forEach var="b" items="${buildings}">
                                <option value="${b.id}">${b.name}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </p>
                <p>Аудиторія<br/>
                    <select class="solution_internal_select" id="_edit_solution_select_room">
                    </select>
                </p>
                <p>
                    <c:if test="${lesson.hoursLectAll > 0}">
                        <label>
                            <input type="checkbox" id="sol_window_chckb_lect"/>
                            лекція
                        </label>
                    </c:if>
                </p>
                <p>
                    <c:if test="${lesson.hoursLabAll > 0}">
                        <label>
                            <input type="checkbox" id="sol_window_chckb_lab"/>
                            лабораторна робота
                        </label>
                    </c:if>
                </p>
                <p>
                    <c:if test="${lesson.hoursPractAll > 0}">
                        <label>
                            <input type="checkbox" id="sol_window_chckb_pract"/>
                            практика
                        </label>
                    </c:if>
                </p>
            </div>
        </div>
        <div class="window_footer">
            <button onclick="saveSolution();">Зберегти</button>
            &nbsp<button onclick="closeWindow(SOLUTION_WINDOW_ID);">Скасувати</button>
        </div>
    </div>
</c:if>