<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<div style="height:30px;"></div>
<div class="header_wraper">
    <div class="left">
        <div class="logo_wraper">
            <a href="${ctx}/" title="На главную"><img class="logo_img" src="${ctx}/images/logo.png"/></a>
        </div>
    </div>
    <div class="right">
        <div class="user">
            <sec:authorize access="isAuthenticated()">
                <div class="user_wrap">
                    Ви ввійшли як <i><sec:authentication property="principal.username" /></i>, 
                    <a href="${ctx}/spring_logout">Вихід</a>
                    <c:if test="${not empty appUser}">
                       <p>Ви авторизовані як ${appUser.lastName} ${appUser.name}, кафедра ${appUser.readingChair.name}</p>
                    </c:if>
                </div>
                <div>
                    
                </div>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <div class="user_wrap">
                    Ви не авторизовані
                </div>
            </sec:authorize>
        </div>
    </div>
    <div style="clear:both;"></div>
    <div id="menu">
        <table cellspacing="10">
            <tr>
                <td><a href="${ctx}/">ГОЛОВНА</a></td>
                <sec:authorize access="isAnonymous()">
                    <td><a href="${ctx}/lesson">НАЗНАЧЕНІ ПЛАНИ</a></td>
                    <td><a href="${ctx}/chair_lessons">ПЛАНИ КАФЕДРИ</a></td>
                    <td><a href="${ctx}/teachers">ВИКЛАДАЧІ</a></td>
                    <td><a href="${ctx}/rooms">АУДИТОРІЇ</a></td> 
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <td><a href="${ctx}/lesson">НАЗНАЧЕНІ ПЛАНИ</a></td>
                    <td><a href="${ctx}/chair_lessons">ПЛАНИ КАФЕДРИ</a></td>
                    <td><a href="${ctx}/teachers">ВИКЛАДАЧІ</a></td>
                    <td><a href="${ctx}/rooms">АУДИТОРІЇ</a></td>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_CONFIG')">
                    <td><a href="${ctx}/config/users">КОНФІГУРАЦІЯ</a></td>
                </sec:authorize>
            </tr>
        </table>
    </div>
</div>

