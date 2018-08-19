<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自动跳转</title>
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">

<style>

.container {
	margin-top: 100px;
	text-align: center;
}

</style>

</head>
<body> 
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<img src="static/img/title_brand.png" alt="克劳德音乐" />
				<h1>${message} : )</h1>
				<h2>页面将在&nbsp;<span id="mes">5</span>&nbsp;秒后跳转</h2>
				<h3>（若浏览器无反应，请点<a href="main.jsp">这里</a>）</h3>
			</div>
		</div>
	</div>
<script src="static/js/jquery-3.2.1.min.js"></script>
<script language="javascript" type="text/javascript"> 
	var i = 5; 
	var intervalid; 
	intervalid = setInterval("fun()", 1000); 
	function fun() { 
		if (i == 0) { 
		window.location.href = "main.jsp"; 
		clearInterval(intervalid); 
	} 
	document.getElementById("mes").innerHTML = i; 
	i--; 
} 
</script> 
</body> 
</html>