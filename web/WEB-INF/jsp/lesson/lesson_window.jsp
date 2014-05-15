<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<!-- если данных нет, то не нужно генерированть разметку -->
<c:if test="${not empty has_data}">
    <input type="hidden" id="_lesson_id_lesson_window_hidden" value="${lesson.id}" />
<div class="window_wraper">
    <div class="window_upper">
        <div class="left">Учебный план</div>
        <div class="right">
            <a class="click_text" onclick="closeWindow(LESSON_WINDOW_ID);">close</a>
        </div>
        <div style="clear:both;"></div>
    </div>
    <div class="window_content">
        <table class="info_table">
            <tr>
                <th class="c1">Семестр</th>
                <td class="c2">${lesson.semester.toString()}</td>
            </tr>
            <tr>
                <th class="c1">Кафедра</th>
                <td class="c2">${lesson.producedChair.name}</td>
            </tr>
            <tr>
                <th class="c1">Специальность</th>
                <td class="c2">${lesson.speciality.name}</td>
            </tr>
            <tr>
                <th class="c1">Группы</th>
                <td class="c2">
                    <c:forEach var="g" items="${lesson.groups}">
                        ${g.name};
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <th class="c1">Дисциплина</th>
                <td class="c2">${lesson.discipline.name}</td>
            </tr>
            <tr>
                <th class="c1">Тип</th>
                <td class="c2">${lesson.discType.name}</td>
            </tr>
            <tr>
                <th class="c1">Контроль</th>
                <td class="c2">${lesson.controlType.name}</td>
            </tr>
            <tr>
                <th class="c1">Выделено часов</th>
                <td class="c2">${lesson.hours}</td>
            </tr> 
        </table>
        <p>Решения:</p>
        <div id="_solutions_block">
            <%@include file="/WEB-INF/jsp/lesson/solutions_list.jsp" %>
        </div>
    </div> 
</div>
</c:if>