<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<c:if test="${not empty lessons}">   
    <div class="window_wraper">
        <div class="window_upper">
            <div class="left">Створення групи</div>
            <div class="right">
                <a class="click_text" onclick="closeWindow();">close</a>
            </div>
            <div style="clear:both;"></div>
        </div>
        <div class="window_content">
            <div id="many_edit_table_holder">
                <p>Оберіть плани, які будуть скопійовані для нової групи</p>
                <table class="many_edit_table">
                    <thead>
                        <tr>
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
                    </tr></thead>
                    <tbody class="many_edit_table_scrolable">
                        <c:forEach var="item" items="${lessons}">
                            <tr>
                                <td><div class="chkb">
                                        <input type="checkbox" name="produced_lesson_id_chckb" value="${item.id}"/>
                                    </div></td>
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
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div> 
        <div class="window_footer">
            <input type="button" value="Зберегти" onclick=""/>
            <input type="button" value="Скасувати" onclick="closeWindow();"/>
        </div>
    </div>
</c:if>