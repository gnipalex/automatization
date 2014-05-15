<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

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