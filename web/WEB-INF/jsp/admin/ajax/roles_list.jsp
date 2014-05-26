<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<p>Ролі : </p>
<c:if test="${not empty user}">
    <c:forEach var="r" items="${user.roles}">
        ${r.name} <a class="click_text" onclick="removeRole(${r.id},${user.id});">X</a>;
    </c:forEach>
</c:if>

