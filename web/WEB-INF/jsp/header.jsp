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
        <!--<h1>Здесь меню</h1>
        <p><a href="${ctx}/admin">Страница администратора</a></p>
        <p><a href="${ctx}/account/login">Login</a> | 
            <a href="${ctx}/spring_logout">LogOut</a>
        </p>
        -->
        <sec:authorize access="isAuthenticated()">
            <div class="user">
                <div class="user_wrap">
                    Вы вошли как <i><sec:authentication property="principal.username" /></i>, 
                    <a href="${ctx}/spring_logout">Выйти</a>
                </div>
            </div>
            <div class="menu">
                <div class="menu_wrap">
                   <p>Навигация</p>
                   <ul>
                       <li><a href="${ctx}/lesson">Учебные планы</a></li>
                       <li><a href="${ctx}/manage/teachers">Преподаватели</a></li>
                       <li><a>Аудитории</a></li>
                   </ul>
                </div>
            </div>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <div class="user">
                <div class="user_wrap">
                    Вы не авторизованы, ввойдите!
                    <div style="width:300px;">
                        <%@include file="/WEB-INF/jsp/account/login_form.jsp" %>
                    </div>
                </div>
                
            </div>
        </sec:authorize>
    </div>
    <div style="clear:both;"></div>
</div>

