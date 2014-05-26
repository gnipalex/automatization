<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Аудиторії</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/rooms.css" />
        <script type="text/javascript" src="${ctx}/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="${ctx}/js/rooms.js"></script>
    </head>
    <body>
        <div id="wraper">
            <div id="header">
                <%@include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div id="content">
                <div id="cwrap">
                    <h2>Аудиторії</h2>
                    <div>
                        <div style="width:300px;float:left;">
                            <p>Корпус<br/>
                                <select onchange="onSelectLoadRooms();" id="building_select_id">
                                    <option></option>
                                    <c:if test="${not empty buildings}">
                                        <c:forEach var="b" items="${buildings}">
                                            <option value="${b.id}">${b.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </p>
                            <table id="rooms_table_id">
                                <tbody id="rooms_block_id">

                                </tbody>
                            </table>
                        </div>
                        <div style="width:400px;float:left;">
                            <p>Добавити нову аудиторію<br/>
                                <input type="text" id="room_input_id"/>&nbsp
                                <input type="button" value="Зберегти" onclick="saveRoomToBuilding();" />
                            </p>
                            <p>Добавити новий корпус<br/>
                                <input type="text" id="building_input_id"/>&nbsp
                                <input type="button" value="Зберегти" onclick="saveNewBuilding();" />
                            </p>
                        </div>
                        <div style="clear:both;"></div>
                    </div>
                </div>
            </div>
            <div id="footer">
                <%@include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>
