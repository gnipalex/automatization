<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<div class="window_wraper">
    <div class="window_upper">
        <div class="left">Новий викладач</div>
        <div class="right">
            <a class="click_text" onclick="closeWindow(NEW_TEACHER_WINDOW_ID);">close</a>
        </div>
        <div style="clear:both;"></div>
    </div>
    <div class="window_content">
        <p>Прізвище<br/><input class="window_input_field" id="teacher_lastname" type="text" /></p>
        <p>Им'я<br/><input class="window_input_field" id="teacher_firstname" type="text" /></p>
        <p>По батькові<br/><input class="window_input_field" id="teacher_middlename" type="text" /></p>
        <p>Посада<br/><input class="window_input_field" id="teacher_post" type="text" /></p>
    </div>
    <div class="window_footer">
        <button onclick="saveTeacher();">Зберегти</button>&nbsp<button onclick="closeWindow(NEW_TEACHER_WINDOW_ID);">Скасувати</button>
    </div>
    
</div>