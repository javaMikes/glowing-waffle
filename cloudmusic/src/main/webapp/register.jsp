<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>注册</title>	
    <link href="static/template/css/bootstrap.css" rel="stylesheet"> <!-- 2.0版本 -->
    <link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="static/template/css/screen.css" rel="stylesheet">	    
    <link href="static/css/city.css" rel="stylesheet"> 
	<link href="static/css/register.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
	
</head>
<body>
	<!-- 公共header部分1234 -->
	<%-- <%@ include file="WEB-INF/views/header.jsp" %> --%>

	<div class="container">
		<div class="row">
			<div class="span8 offset2">
				<form action="#" id="register_form" class="contactForm coloredGray">
					<p class="row-fluid">
						<label for="email_register" class="span2">邮箱<sup>*</sup></label>
						<input type="email" name="email" class="span10" id="email_register" placeholder="邮箱">
						<b class="colored span10 offset2 contact_page_form_result"></b>
					</p>
					<p class="row-fluid">
						<label for="password_register" class="span2">密码<sup>*</sup></label>
						<input type="password" name="password" class="span10" id="password_register" placeholder="密码">
						<b class="colored span10 offset2 contact_page_form_result"></b>
					</p>
					<p class="row-fluid">
						<label for="confirm_register" class="span2">确认密码<sup>*</sup></label>
						<input type="password" name="confirm" class="span10" id="confirm_register" placeholder="确认密码">
						<b class="colored span10 offset2 contact_page_form_result"></b>
					</p>
					<p class="row-fluid">
						<label for="birthday_register" class="span2">生日<sup>*</sup></label>
						<input type="date" name="birthday" class="span10" id="birthday_register">
						<b class="colored span10 offset2 contact_page_form_result"></b>
					</p>
					<p class="row-fluid">
						<label for="" class="span2">性别<sup>*</sup></label>
						<span class="span10">
							<label class="checkbox-inline"> <input type="radio" name="sex" id="male_register" value="0" checked="checked">&nbsp;&nbsp;&nbsp;男 </label>
							&nbsp;&nbsp;&nbsp; 
							<label class="checkbox-inline"> <input type="radio" name="sex" id="female_register" value="1">&nbsp;&nbsp;&nbsp;女 </label>
						</span>							
					</p>
					<p class="row-fluid">
						<label for="region_register" class="span2">地区<sup></sup></label>
						<input type="text" name="region" class="span10" id="region_register" placeholder="地区">
						<input type="hidden" name="harea" data-id="440607" id="harea" disabled="disabled">
						<input type="hidden" name="hproper" data-id="440600" id="hproper" disabled="disabled">
						<input type="hidden" name="hcity" data-id="440000" id="hcity" disabled="disabled">
						<b class="colored span10 offset2 contact_page_form_result"></b>
					</p>
					<p class="row-fluid">
						<label for="nickname_register" class="span2">昵称<sup></sup></label>
						<input type="text" name="nickname" class="span10" id="nickname_register" placeholder="昵称">
						<b class="colored span10 offset2 contact_page_form_result"></b>
					</p>
					<p class="row-fluid">
						<label for="person_signature_register" class="span2">个性签名<sup></sup></label>
						<textarea name="personSignature" class="span10" id="person_signature_register" rows="3" placeholder="个性签名"></textarea>
						<b class="colored span10 offset2 contact_page_form_result"></b>
					</p>
					<p class="row-fluid">
						<button type="button" class="btn btn-color offset2" id="register_btn">注册</button>
					</p>
				</form>
			</div>
		</div>
	</div>
	
	<!-- 公共footer部分 -->
	<%-- <%@ include file="WEB-INF/views/footer.jsp" %> --%>		
	
	<!-- 公共player部分 -->
	<%-- <%@ include file="WEB-INF/views/player.jsp" %> --%>

<div id="register_msg_box">
	<div class="box_head">
		<button type="button" class="box_close">&times;</button>
	</div>	
	<div class="box_body"></div>
	<div class="box_foot">
		<button type="button" class="btn btn-color" id="register_confirm">确认</button>
	</div>
</div>

<script src="static/template/js/jquery-ui.js"></script>
<script src="static/template/js/bootstrap.min.js"></script>
<script src="static/template/js/settingsbox.js"></script>
<script src="static/template/js/farbtastic/farbtastic.js"></script>
<script src="static/template/js/jquery.prettyPhoto.js"></script>
<script src="static/template/js/izotope.js"></script>
<script src="static/template/js/option.js"></script>
<script src="static/js/city.js"></script>
<script src="static/js/cityJson.js"></script>	
<script src="static/js/register.js"></script>
</body>
</html>