<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<div class="filter_label">Фильтр</div>
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
   <div class="filter_item">По типу занятий<br/>
       <select id="filter_dtype" class="filter_list">
           <option value="">--не задано--</option>
           <c:forEach var="dt" items="${dtypes}">
               <option value="${dt.id}">${dt.name}</option>
           </c:forEach>
       </select>
   </div>
   <div style="clear:both;"></div>
   <div>
       <button title="Применить" onclick="applyFilter();">Применить</button>
       <button title="Сбросить" onclick="resetFilter();">Сбросить</button>
   </div>
</div>
