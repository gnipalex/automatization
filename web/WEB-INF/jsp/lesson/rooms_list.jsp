<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<option value="">--не выбрано--</option>
<c:if test="${not empty rooms}">
    <c:forEach var="r" items="${rooms}">
        <c:if test="${not empty room_id && r.id == room_id}">
            <option selected value="${r.id}">${r.name}</option>
        </c:if>
        <c:if test="${empty room_id || r.id != room_id}">
            <option value="${r.id}">${r.name}</option>
        </c:if>
    </c:forEach>
</c:if>