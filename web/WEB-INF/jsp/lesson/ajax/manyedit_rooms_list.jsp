<%@page pageEncoding="UTF-8" contentType="text/html" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<c:if test="${not empty rooms}">
    <c:forEach var="r" items="${rooms}">
        <option value="${r.id}">${r.name}</option>
    </c:forEach>
</c:if>