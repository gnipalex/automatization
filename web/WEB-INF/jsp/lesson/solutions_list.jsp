<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<c:if test="${not empty lesson}">
    <c:if test="${not empty lesson.solutions}">
    <c:forEach var="s" items="${lesson.solutions}">
        <div class="solution_item" onclick="">
            <div class="left" onclick="viewSolution(${s.id});"> 
                ${s.teacher.getShortFIO()}, аудитория ${s.room.name} 
            </div>
            <div class="right">
                <a class="click_text" onclick="deleteSolution(${s.id});">X</a>
            </div>
            <div style="clear:both;"></div>
        </div>
    </c:forEach>
    </c:if>
    <div class="solution_item" onclick="viewSolution();">+</div>
</c:if>