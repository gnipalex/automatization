<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<c:if test="${not empty lesson}">
<c:if test="${not empty lesson.solutions}">
    <c:forEach var="s" items="${lesson.solutions}">
        <tr>
            <td class="st1">${s.teacher.getShortFIO()}</td>
            <td class="st2">${s.getLessonTypes()}</td>
            <td class="st3">
                <c:if test="${not empty s.room}">
                    ${s.room.name}
                    <c:if test="${not empty s.room.building}"> 
                        ${s.room.building.name}
                    </c:if>
                </c:if>
            </td>
            <td class="st4"><a class="click_text" onclick="deleteSolution(${s.id});">X</a></td>
        </tr>
    </c:forEach>
</c:if>
</c:if>