<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

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
        </c:if><!-- viewLesson(${item.id}); -->
        <tr onclick="" class="${item_style_class}">
            <td>
                <div class="chkb">
                <input type="checkbox" name="plan_to_edit" value="${item.id}"/>
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
            <td onclick="viewLesson(${item.id});"><div class="prepod">
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

