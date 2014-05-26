<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<div class="window_wraper">
    <div class="window_upper">
        <div class="left">Викладач</div>
        <div class="right">
            <a class="click_text" onclick="closeWindow(TEACHER_INFO_WINDOW_ID);">close</a>
        </div>
        <div style="clear:both;"></div>
    </div>
    <c:if test="${not empty teacher}">
        <div class="window_content">
            <input type="hidden" id="info_window_teacher_id" value="${teacher.id}" />
            <div style="float:left;width:30%;">
                <p>Прізвище<br/>
                    <input type="text" id="teacher_last_name_id" value="${teacher.lastName}"/>
                </p>
                <p>По батькові<br/>
                    <input type="text" id="teacher_middle_name_id" value="${teacher.middleName}"/>
                </p>
                <p>Ім'я<br/>
                    <input type="text" id="teacher_first_name_id" value="${teacher.firstName}"/>
                </p>
                <p>Посада<br/>
                    <input type="text" id="teacher_post_id" value="${teacher.post}"/>
                </p>
            </div>
            <div style="float:left;width:70%;">
                <p>Назначення</p>
                <table id="teacher_solutions_table">
                    <thead>
                        <tr>
                            <th class="c1">Семестр</th>
                            <th class="c2">Спеціальність</th>
                            <th class="c3">Група</th>
                            <th class="c4">Предмет</th>
                            <th class="c5">Тип занять</th>
                            <th class="c5"></th>
                        </tr>
                    </thead>
                    <tbody id="teacher_solutions_block">
                        <%@include file="/WEB-INF/jsp/lesson/ajax/teachers_solutions_list.jsp" %>
                    </tbody>
                </table>
            </div>
            <div style="clear:both;"></div>

        </div>
        <div class="window_footer">
            <button onclick="editTeacher(${teacher.id});">Зберегти</button>&nbsp&nbsp
            <button onclick="deleteTeacher(${teacher.id});">Видалити</button>
            &nbsp&nbsp<button onclick="closeWindow(TEACHER_INFO_WINDOW_ID);">Закрити</button>
        </div>
    </c:if>
</div>
