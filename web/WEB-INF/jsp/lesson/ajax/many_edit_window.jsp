<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<%-- если данных нет, то не нужно генерированть разметку --%>
<c:if test="${not empty lessons}">   
    <div class="window_wraper">
        <div class="window_upper">
            <div class="left">Редактирование списка планов</div>
            <div class="right">
                <a class="click_text" onclick="closeWindow(MANY_EDIT_WINDOW_ID);">close</a>
            </div>
            <div style="clear:both;"></div>
        </div>
        <div class="window_content">
            <div id="many_edit_table_holder">
                <table class="many_edit_table">
                    <thead>
                        <tr>
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
                    </tr></thead>
                    <tbody class="many_edit_table_scrolable">
                        <c:forEach var="item" items="${lessons}">
                            <tr>
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
                                    <td><div class="hour">
                                    ${item.hoursLectAll}
                                    <c:if test="${item.hoursLectAll > 0}">
                                        <input type="checkbox" name="many_edit_lection_chckb" value="${item.id}"/> 
                                    </c:if>
                                    </div>           
                                </td>
                                <td><div class="hour">
                                    ${item.hoursPractAll}
                                    <c:if test="${item.hoursPractAll > 0}">
                                        <input type="checkbox" name="many_edit_practice_chckb" value="${item.id}"/> 
                                    </c:if>
                                    </div>   
                                </td>
                                <td><div class="hour">
                                    ${item.hoursLabAll}
                                    <c:if test="${item.hoursLabAll > 0}">
                                        <input type="checkbox" name="many_edit_lab_chckb" value="${item.id}"/> 
                                    </c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div>
                <div style="float:left;width:50%;">
                    <p>Выбор преподавателя</p>
                    <select id="teacher_select_id">
                        <option value="">--не выбрано--</option>
                        <c:if test="${not empty teachers}">
                            <c:forEach var="t" items="${teachers}">
                                <c:if test="${not empty teacher_id && t.id == teacher_id}">
                                    <option selected value="${t.id}">${t.chair.name} - ${t.post} ${t.getShortFIO()}</option>
                                </c:if>
                                <c:if test="${empty teacher_id || t.id != teacher_id}">
                                    <option value="${t.id}">${t.chair.name} - ${t.post} ${t.getShortFIO()}</option>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
                <div style="float:left;width:50%;">
                    <p>Выбор корпуса и аудитории</p>
                    <select onchange="loadRoomsForManyEdit();" id="many_edit_building_select_id">
                        <option value="">--не выбрано--</option>
                        <c:if test="${not empty buildings}">
                            <c:forEach var="item" items="${buildings}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </c:if>   
                    </select>
                    <select id="many_edit_rooms_select_id">
                    </select>
                </div>
                <div style="clear:both;"></div>
            </div>
        </div> 
        <div class="window_footer">
            <input type="button" value="Зберегти" onclick="saveManySolution();"/>
            <input type="button" value="Скасувати" onclick="closeWindow(MANY_EDIT_WINDOW_ID);"/>
        </div>
    </div>
</c:if>