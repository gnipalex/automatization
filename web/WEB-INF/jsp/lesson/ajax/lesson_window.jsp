<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<!-- если данных нет, то не нужно генерированть разметку -->
<c:if test="${not empty has_data}">
    <input type="hidden" id="_lesson_id_lesson_window_hidden" value="${lesson.id}" />
    <div class="window_wraper">
        <div class="window_upper">
            <div class="left">Семестровий план</div>
            <div class="right">
                <a class="click_text" onclick="closeWindow(LESSON_WINDOW_ID);">close</a>
            </div>
            <div style="clear:both;"></div>
        </div>
        <div class="window_content">
            <div class="info_table_wraper">
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
                        <th class="c1">Спеціальність</th>
                        <td class="c2">${lesson.speciality.name}</td>
                    </tr>
                    <tr>
                        <th class="c1">Група</th>
                        <td class="c2">${lesson.group.name}</td>
                    </tr>
                    <tr>
                        <th class="c1">Дисципліна</th>
                        <td class="c2">${lesson.discipline.name}</td>
                    </tr>
                    <tr>
                        <th class="c1">Контроль</th>
                        <td class="c2">
                            <c:if test="${lesson.exam}">екзамен</c:if>&nbsp
                            <c:if test="${lesson.zachet}">залік</c:if>&nbsp
                            <c:if test="${lesson.div_zachet}">диф.залік</c:if>&nbsp
                            </td>
                        </tr>
                        <tr>
                            <th class="c1">Семестрова робота</th>
                            <td class="c2">
                            <c:if test="${lesson.rgr}">РГР</c:if>&nbsp
                            <c:if test="${lesson.rr}">РР</c:if>&nbsp
                            <c:if test="${lesson.rk}">РК</c:if>&nbsp
                            <c:if test="${lesson.cp}">КП</c:if>&nbsp
                            <c:if test="${lesson.cw}">КР</c:if>&nbsp
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="info_table_wraper">
                    <table class="info_table">
                        <tr>
                            <th class="c1">Лекційні години 1/2</th>
                            <td class="c2">${lesson.hoursLect1Half}/${lesson.hoursLect2Half}</td>
                        </tr>
                        <tr>
                            <th class="c1">Практичні години 1/2</th>
                            <td class="c2">${lesson.hoursPract1Half}/${lesson.hoursPract2Half}</td>
                        </tr>
                        <tr>
                            <th class="c1">Лабораторні години 1/2</th>
                            <td class="c2">${lesson.hoursLab1Half}/${lesson.hoursLab2Half}</td>
                        </tr>
                        <tr>
                            <th class="c1">Всього годин</th>
                            <td class="c2">${lesson.hoursAll}</td>
                        </tr>
                        <tr>
                            <th class="c1">Годин самостійної роботи</th>
                            <td class="c2">${lesson.hoursOwn}</td>
                        </tr>
                </table>
            </div>
            <div style="clear:both;"></div>
            <p>Назначення:</p>
            <p><input type="button" value="Добавити" onclick="viewSolution();"/></p>
            <div id="_solutions_wraper">
                <table class="solutions_table">
                    <thead>
                        <tr>
                            <th class="st1">Викладач</th>
                            <th class="st2">Вид занять</th>
                            <th class="st3">Аудиторія</th>
                            <th class="st4"></th>
                        </tr>
                    </thead>
                    <tbody id="_solutions_block">
                        <%@include file="/WEB-INF/jsp/lesson/ajax/solutions_list.jsp"%>
                    </tbody>
                </table>
            </div>
        </div> 
    </div>
</c:if>