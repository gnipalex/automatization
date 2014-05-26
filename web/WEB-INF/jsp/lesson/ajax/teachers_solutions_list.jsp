<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<c:if test="${not empty teacher}">
    <c:if test="${not empty teacher.solutionPlans}">
        <c:forEach var="s" items="${teacher.solutionPlans}">
            <tr>
                <td class="c1">${s.lessonPlan.semester.name}</td>
                <td class="c2">${s.lessonPlan.speciality.name}</td>
                <td class="c3">${s.lessonPlan.group.name}</td>
                <td class="c4">${s.lessonPlan.discipline.name}</td>
                <td class="c5">${s.getLessonTypes()}</td>
                <td class="c5">
                    <a class="click_text" onclick="deleteTeacherSolution(${s.id});" title="удалить">X</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</c:if>