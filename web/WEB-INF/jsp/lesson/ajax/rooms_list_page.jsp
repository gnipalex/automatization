<%@page pageEncoding="UTF-8" contentType="text/html" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<c:if test="${not empty building}">
    <c:forEach var="r" items="${building.rooms}">
        <tr>
            <td>${r.name}</td>
        </tr>
    </c:forEach>
</c:if>