<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">

<script type="text/javaScript" src="static/js/jquery-3.2.1.min.js"></script>

<script src="static/bootstrap/js/bootstrap.min.js"></script>

<title>登录</title>
</head>
<body>
	<div style="margin-top:100px;padding: 10px 10px 10px;margin-left:auto;margin-right:auto;width:30%" class="text-center">
	 	<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
	            	克勞德音樂後台管理系統
	       		</h3>
				<form action="adminLogin" method="post" class="form-signin">
					<h3 class="form-signin-heading">请登录</h3>
					<label class="sr-only">邮箱</label> 
						<input class="form-control" placeholder="邮箱" name="email" required autofocus> <label
						for="inputPassword" class="sr-only">密码</label> <input
						type="password" id="inputPassword" name="password" class="form-control"
						placeholder="密码" required>
					<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>