<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="/error.jsp" %>
<%@include file="/WEB-INF/jsp/include_tags.jsp" %>

<html>
    <head>
        <title>Тестовая страница</title>
        <script type="text/javascript" src="${ctx}/js/jquery-1.11.0.js"></script>
        <script type="text/javascript">
            function getJson(){
                $.ajax({
                    url:'test/getJson',
                    type: 'POST'
                }).done(function(resp){
                    alert(resp.message);
                }).fail(function(){
                    alert('oups(');
                });
            }
        </script>
    </head>
    <body>
        <h2>Тест</h2>
        <p>Нажми для получения JSON от сервера<input type="button" value="click" onclick="getJson();"/>
        </p>
    </body>
</html>