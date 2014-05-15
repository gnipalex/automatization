<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<c:if test="${empty lessonPlans}">
    <p>-- Планы не найдены --</p>
</c:if> 
<c:if test="${not empty lessonPlans}">
    <table class="plans">
        <tr class="tr_top">
           <th class="sem">Семестр</th>
           <th class="spec">Специальность</th>
           <th class="chair">Кафедра</th>
           <th class="group">Группа</th>
           <th class="disc">Дисциплина</th>
           <th class="type">Тип</th>
           <th class="hour">Часов</th>
           <th class="prepod">Решения</th>
       </tr>
       <c:forEach var="item" items="${lessonPlans}">
           <c:if test="${empty item.solutions}">
               <c:set var="item_style_class" value="tr_red"></c:set>
           </c:if>
           <c:if test="${not empty item.solutions}">
               <c:set var="item_style_class" value="tr_green"></c:set>
           </c:if>
           <tr onclick="viewLesson(${item.id});" class="${item_style_class}">
               <td class="sem">${item.semester.name}</td>
               <td class="spec">${item.speciality.name}</td>
               <td class="chair">${item.producedChair.name}</td>
               <td class="group">
                   <c:forEach var="gr" items="${item.groups}">
                       ${gr.name};
                   </c:forEach>
               </td>
               <td class="disc">${item.discipline.name}</td>
               <td class="type">${item.discType.name}</td>
               <td class="hour">${item.hours}</td>
               <td class="prepod">
                   <c:forEach var="sol" items="${item.solutions}">
                       *<c:if test="${not empty sol.teacher}">${sol.teacher.post} ${sol.teacher.getShortFIO()}</c:if>
                       <c:if test="${not empty sol.room}"> аудитория ${sol.room.name}</c:if>
                       <br/>
                   </c:forEach>
               </td>
           </tr>
       </c:forEach>
    </table>
</c:if> 

