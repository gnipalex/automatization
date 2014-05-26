<%@page pageEncoding="UTF-8" contentType="text/html" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<div class="window_wraper">
    <div class="window_upper">
        <div class="left">Ролі користувача ${user.email}</div>
        <div class="right">
            <a class="click_text" onclick="closeWindow(USER_ROLES_WINDOW_ID);">close</a>
        </div>
        <div style="clear:both;"></div>
    </div>
    <div class="window_content">
        <div id="roles_list_id">
            <%@include file="/WEB-INF/jsp/admin/ajax/roles_list.jsp" %>
        </div>
        <p>Добавити роль<br/>
            <select id="user_roles_roles_select">
                <c:if test="${not empty user}">
                    <c:forEach var="rol" items="${roles}">
                        <option value="${rol.id}">${rol.name}</option>
                    </c:forEach>
                </c:if>
            </select>
            <c:if test="${not empty user}">
                &nbsp <input type="button" onclick="addRoleToUser(${user.id});" value="Добавити"/>
            </c:if>
        </p>
    </div>
    <div class="window_footer">
        <button onclick="">Сохр</button>&nbsp<button onclick="closeWindow(USER_ROLES_WINDOW_ID);">Отмена</button>
    </div>
</div>