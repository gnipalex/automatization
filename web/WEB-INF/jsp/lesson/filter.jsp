<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<div class="filter_items_wraper">
    <div class="filter_item">По специальности<br/>
        <select id="filter_speciality" class="filter_list">
            <option value="">--не выбрано--</option>
            <c:forEach var="spec" items="${specialities}">
                <option value="${spec.id}">${spec.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="filter_item">По семестру<br/>
        <select id="filter_semester" class="filter_list">
            <option value="">--не задано--</option>
            <c:forEach var="sem" items="${semesters}">
                <option value="${sem.id}">${sem.name} - ${sem.course} - ${sem.getSeson()}</option>
            </c:forEach>
        </select>
    </div>
    <div class="filter_item">По выпускающей кафедре<br/>
        <select id="filter_pchair" class="filter_list">
            <option value="">--не задано--</option>
            <c:forEach var="ch" items="${chairs}">
                <option value="${ch.id}">${ch.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="filter_item">По предмету<br/>
        <select id="filter_disc" class="filter_list">
            <option value="">--не задано--</option>
            <c:forEach var="d" items="${disciplines}">
                <option value="${d.id}">${d.name}</option>
            </c:forEach>
        </select>
    </div>
    <div style="clear:both;"></div>
    <div style=""> 
        <label><input id="filter_zachet" type="checkbox">залік</label>
        <label><input id="filter_exam" type="checkbox"/>екзамен</label>
        <label><input id="filter_div_zachet" type="checkbox"/>диф.залік</label>
        <label><input id="filter_cp" type="checkbox"/>КП</label>
        <label><input id="filter_cw" type="checkbox"/>КР</label>
    </div>
</div>
<div class="filter_buttons_block">
    <input type="button" onclick="applyFilter();" value="Применить"/>
    <input type="button" onclick="resetFilter();" value="Сбросить"/>
</div>
