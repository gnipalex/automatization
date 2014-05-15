<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<div class="window_wraper">
    <div class="window_upper">
        <div class="left">Новая аудитория</div>
        <div class="right">
            <a class="click_text" onclick="closeWindow(NEW_ROOM_WINDOW_ID);">close</a>
        </div>
        <div style="clear:both;"></div>
    </div>
    <div class="window_content">
        <p>Аудитория<br/><input id="_roomname" type="text" /></p>
    </div>
    <div class="window_footer">
        <button onclick="saveRoom();">Сохр</button>&nbsp<button onclick="closeWindow(NEW_ROOM_WINDOW_ID);">Отмена</button>
    </div>
</div>