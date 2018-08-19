<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
        function start() {
            var eventSource = new EventSource("messageTest");
            eventSource.onmessage = function(event) {
                //document.getElementById("foo").innerHTML = event.data;
                console.log(event.data);
                if(event.data == 'false')
                	alert("用户未签到");
            };
        }
    </script>
</head>
<body>
    Time: <span id="foo"></span>
    
    <br><br>
    <button onclick="start()">Start</button>

</body>
</html>