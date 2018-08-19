<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>忘记密码</title>	
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
	<div style="height:20px;"></div>
		<div class="row">		
			<div class="span8 offset2">
				<form action="#" id="forgotPsw_form" class="contactForm coloredGray">
					<p class="row-fluid">
						<label for="email_forgot" class="span2">邮箱<sup>*</sup></label>
						<input type="email" name="email" class="span10" id="email_forgot" placeholder="确认账号">
						<b class="colored span10 offset2 contact_page_form_result"></b>
					</p>


					<p class="row-fluid">
						<label type="button" id="btn1" class="btn span2">获取验证码</label><sup>*</sup>
						<input type="email" name="email" class="span10" id="code" placeholder="安全验证">
						<b class="colored span10 offset2 contact_page_form_result"></b>
					</p>
					<p class="row-fluid">
						<label for="password_forgot" class="span2">新密码<sup>*</sup></label>
						<input type="password" name="newpassword" class="span10" id="password_forgot" placeholder="新密码">
						<b class="colored span10 offset2 contact_page_form_result"></b>
					</p>
					<p class="row-fluid">
						<label for="confirm_fotgot" class="span2">确认密码<sup>*</sup></label>
						<input type="password" name="newconfirm" class="span10" id="confirm_fotgot" placeholder="确认密码">
						<b class="colored span10 offset2 contact_page_form_result"></b>
					</p>				
					<p class="row-fluid">
						<button type="button" class="btn btn-color offset2" id="forgotPsw_btn">重置密码</button>
					</p>
				</form>
			</div>
		</div>
	</div>
	
	<!-- 公共footer部分 -->
	<%-- <%@ include file="WEB-INF/views/footer.jsp" %>	 --%>	
	
	<!-- 公共player部分 -->
	<%-- <%@ include file="WEB-INF/views/player.jsp" %> --%>


<script type="text/javascript">
var second = -1; // 剩余秒数
var e = 0;
$("#btn1").click(function(){
	var email = $("#email_forgot").val();
	var emailReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	if (email.length>20 || !emailReg.test(email)){
		show_validate_msg(document.getElementById("email_forgot"), "格式不正确");
		return false;
	}
	else{
		show_validate_msg(document.getElementById("email_forgot"), "");
	}
	if(e==0){
    $.ajax({
      url : "sendCode",
      type : "POST",
      dateType : "json",
      data : {
    	  email : $("#email_forgot").val(),
      },
      success : function(data, status){
    	  alert("验证码已发送！");
    	  e=1;
    	  second=60;
    	  document.getElementById("email_forgot").readOnly=true;
    	  document.getElementById("btn1").innerHTML = "60秒后过期";
      },
      error : function(){
      }
    });
    }
});

$("#forgotPsw_btn").click(function(){
	if (validate_forgot_form()){
		alert("格式验证成功！");
	    $.ajax({
	        url : "forgotPsw",
	        type : "POST",
	        dateType : "json",
	        data : {
	        	 email : $("#email_forgot").val(),
	        	 password : $("#password_forgot").val(), 
	        	 nickname : $("#code").val(),
	        },
	        success : function(data, status){
	        	alert("修改成功！");
	        },
	        error : function(){
	        	alert("修改失败！");
	        }
	      });
	}
});
//验证登录表单
function validate_forgot_form(){
	
	$("#forgotPsw_form").find("b").html("");
	var validate = true;
	//验证邮箱
	var email = $("#email_forgot").val();
	var emailReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	if (email.length>20 || !emailReg.test(email)){
		show_validate_msg(document.getElementById("email_forgot"), "格式不正确");
		validate = false;
	}
	//验证新密码
	var password = $("#password_forgot").val();
	var passwordReg = /^[a-zA-Z0-9_]+$/;
	if (password.length>20 || !passwordReg.test(password)){
		show_validate_msg(document.getElementById("password_forgot"), "格式不正确");
		validate = false;
	}
	//验证新密码
	var confirm = $("#confirm_fotgot").val();
	var confirmReg = /^[a-zA-Z0-9_]+$/;
	if (confirm.length>20 || !confirmReg.test(confirm)){
		show_validate_msg(document.getElementById("confirm_fotgot"), "格式不正确");
		validate = false;
	}
	else{
		if(password!=confirm){
			show_validate_msg(document.getElementById("confirm_fotgot"), "密码不一致");
		}
	}	
	return validate;
}
//倒计时
window.setInterval(function(){
	if(second>0&&second<61){
		second --;
		document.getElementById("btn1").innerHTML = second+"秒后过期";
	}
	if(second==0){
		second=-1;
	    $.ajax({
	        url : "delete",
	        type : "POST",
	        dateType : "json",
	        data : {
	        },
	        success : function(data, status){
	      	  alert("验证码已消除！");
	      	  document.getElementById("email_forgot").readOnly=false;
	      	  document.getElementById("btn1").innerHTML = "获取验证码";
	      	  e=0;
	        },
	        error : function(){
	        }
	      });
	}
		
	
}, 1000);

</script>

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