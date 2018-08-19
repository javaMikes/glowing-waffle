$(function(){
	$("#region_register").click(function (e) {
		SelCity(this,e);
	    console.log("inout",$(this).val(),new Date());
	});
	
	$(".box_close").click(function(){
		$("#register_msg_box").fadeOut("fast");
	});
	
	$("#register_confirm").click(function(){
		parent.window.location.href = "main.jsp";
	});

	$("#register_btn").click(function(){
		//表单验证通过，发送请求
		if (validate_register_form()){
			var date = new Date($("#birthday_register").val());
			$.ajax({
				 url: "register",
	             type: "POST",
	             data: {
	            	 email : $("#email_register").val(),
	                 password : $("#password_register").val(),
	                 birthday : date,
	                 sex : $("input[name='sex']:checked").val(),
	                 region : $("#region_register").val(),
	                 nickname : $("#nickname_register").val(),
	                 personSignature : $("#person_signature_register").val()                  
	             },
	             success: function (data, status) {
	                 if (data.code == 100){
	                	 $("#register_confirm").css("display", "inline-block");
	                	 $("#register_msg_box .box_body").html("注册成功，请前往邮箱激活账号（邮件可能会存在于垃圾箱）");
	                	 $("#register_msg_box").fadeIn("fast"); 
	                 }
	                 else{
	                	 $("#register_confirm").css("display", "none");
	                	 $("#register_msg_box .box_body").html("注册失败，邮箱已被占用");
	                	 $("#register_msg_box").fadeIn("fast");               	 
	                 }
	             },
	             error: function () {
	                 
	             }
			});	 
		}
	});
});

//验证注册表单
function validate_register_form(){
	$("#register_form").find("b").html("");
	var validate = true;
	
	//验证邮箱
	var email = $("#email_register").val();
	var emailReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	if (email.length>20 || !emailReg.test(email)){
		show_validate_msg(document.getElementById("email_register"), "只允许由英文字母、数字、下划线、英文句号、中划线以及@组成，长度不大于20");
		validate = false;
	}
	
	//验证密码
	var password = $("#password_register").val();
	var passwordReg = /^[a-zA-Z0-9_]+$/;
	if (password.length>20 || !passwordReg.test(password)){
		show_validate_msg(document.getElementById("password_register"), "只允许由英文字母、数字、下划线组成，长度不大于20");
		validate = false;
	}
	
	//验证确认密码
	var confirm = $("#confirm_register").val();
	if (confirm != password){
		show_validate_msg(document.getElementById("confirm_register"), "两次输入的密码必须一致");
		validate = false;
	}
	
	//验证生日
	var birthday = $("#birthday_register").val();
	var brithdayReg = /(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)/;
	if (birthday.length>10 || !brithdayReg.test(birthday)){
		show_validate_msg(document.getElementById("birthday_register"), "日期格式不正确");
		validate = false;
	}
	
	//验证地区
	var region = $("#region_register").val();
	if (region.length>20){
		show_validate_msg(document.getElementById("region_register"), "长度不大于20");
		validate = false;
	}
	
	//验证昵称
	var nickname = $("#nickname_register").val();
	if (nickname.length>20){
		show_validate_msg(document.getElementById("nickname_register"), "长度不大于20");
		validate = false;
	}
	
	//验证个性签名
	var personSignature = $("#person_signature_register").val();
	if (personSignature>200){
		show_validate_msg(document.getElementById("person_signature_register"), "长度不大于200");
		validate = false;
	}
	
	return validate;
}

//表单提示消息
function show_validate_msg(element, msg){
	$(element).siblings("b").eq(0).html(msg);
}