$(function(){
	$("#region_update").click(function (e) {
		SelCity(this,e);
	    console.log("inout",$(this).val(),new Date())
	});
	
	$("#change_image_btn").click(function(){
		$(this).siblings("li").removeClass("active");
		$(this).addClass("active");
		$(".setting_content").removeClass("active");
		$("#change_image_content").addClass("active");
	});
	
	$("#basic_setting_btn").click(function(){
		$(this).siblings("li").removeClass("active");
		$(this).addClass("active");
		$(".setting_content").removeClass("active");
		$("#basic_setting_content").addClass("active");
	});
	
	$("#privacy_setting_btn").click(function(){
		$(this).siblings("li").removeClass("active");
		$(this).addClass("active");
		$(".setting_content").removeClass("active");
		$("#privacy_setting_content").addClass("active");
	});
	
	$("#user_update_btn").click(function(){
		//表单验证通过，发送请求
		if (validate_update_form()){
			var date = new Date($("#birthday_update").val());
			$.ajax({
				 url: "updateUserByUser",
	             type: "POST",
	             data: {
	                 birthday : date,
	                 sex : $("input[name='sex']:checked").val(),
	                 region : $("#region_update").val(),
	                 nickname : $("#nickname_update").val(),
	                 personSignature : $("#person_signature_update").val()                  
	             },
	             success: function (data, status) {
	                 if (data.code == 100){
	                	/*  $("#register_confirm").css("display", "inline-block");
	                	 $("#register_msg_box .box_body").html("注册成功，请前往邮箱激活账号");
	                	 $("#register_msg_box").fadeToggle();  */
	                	 $("#register_confirm").css("display", "inline-block");
	                	 $("#register_msg_box .box_body").html("修改成功");
	                	 $("#register_msg_box").fadeIn("fast");
	                 }
	                 else{
	                	/*  $("#register_confirm").css("display", "none");
	                	 $("#register_msg_box .box_body").html("注册失败，邮箱已被占用");
	                	 $("#register_msg_box").fadeToggle();               	 */ 
	                 }
	             },
	             error: function () {
	                 
	             }
			});	 
		}
	});
	
});

//验证更新表单
function validate_update_form(){
	$("#user_update_form").find("b").html("");
	var validate = true;
	
	//验证生日
	var birthday = $("#birthday_update").val();
	var brithdayReg = /(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)/;
	if (birthday.length>10 || !brithdayReg.test(birthday)){
		show_validate_msg(document.getElementById("birthday_update"), "日期格式不正确");
		validate = false;
	}
	
	//验证地区
	var region = $("#region_update").val();
	if (region.length>20){
		show_validate_msg(document.getElementById("region_update"), "长度不大于20");
		validate = false;
	}
	
	//验证昵称
	var nickname = $("#nickname_update").val();
	if (nickname.length>20){
		show_validate_msg(document.getElementById("nickname_update"), "长度不大于20");
		validate = false;
	}
	
	//验证个性签名
	var personSignature = $("#person_signature_update").val();
	if (personSignature>200){
		show_validate_msg(document.getElementById("person_signature_update"), "长度不大于200");
		validate = false;
	}
	
	return validate;
}

//表单提示消息
function show_validate_msg(element, msg){
	$(element).siblings("b").eq(0).html(msg);
}

