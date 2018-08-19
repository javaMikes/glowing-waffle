$(function(){		
	$(".login, .login_close").click(function(){
		$("#email_login").val("");
	    $("#password_login").val("");
	    $('#login_box b').html("");
		$('#login_box').fadeToggle("fast");
	});

//	console.log("val:" + $("#userId").val());
	if($("#userId").val() != ""){
		var eventSource = new EventSource("findPlaylistMessageByUserId");
        eventSource.onmessage = function(event) {
            //document.getElementById("foo").innerHTML = event.data;
        	console.log(event.data);
            if(event.data != ''){
            	alert(event.data);
            	$.ajax({
            		url: "readMessage",
            		type: "post",
            		success: function(data){
            			console.log(data);
            		}
            	});
            }
        };
        
	}
	
	$("#login_btn").click(function(){
		//表单验证通过，发送请求
		if (validate_login_form()){
			$.ajax({
				 url: "login",
	             type: "POST",
	             data: {
	            	 email : $("#email_login").val(),
	                 password : $("#password_login").val(),
	             },
	             success: function (data, status) {
	            	 console.log(data);
	                 if (data.code == 100){
//	                	 window.location.href = "sessionHandlerByCacheMap";
	                	 window.location.href = "main.jsp";
	                 }
	                 else{
	                	 $("#login_msg_box .box_body").html("登录失败");
	                	 $("#login_msg_box").fadeToggle("fast");               	 
	                 }
	             },
	             error: function () {
	                 
	             }
			});	 
		}
	});
	$("#email_login, #password_login").keydown(function(event){
		if (event.keyCode == 13){
			$("#login_btn").trigger("click");
		}		
	});

	$(".box_close, #login_confirm").click(function(){
		$("#login_msg_box").fadeOut("fast");
	});

	$("#user_nav_content").mouseover(function(){
		$("#user_nav_list").toggle();
	});
	
	$("#user_nav").mouseout(function(){
		$("#user_nav_list").toggle();
	});

//	$(".user_setting_btn").click(function(){
//		window.location.href = "userUpdate.jsp";
//	});

	/*$(".user_logout_btn").click(function(){
		window.location.href = "";
	});	*/
});

//验证登录表单
function validate_login_form(){
	$("#login_form").find("b").html("");
	var validate = true;
	
	//验证邮箱
	var email = $("#email_login").val();
	var emailReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	if (email.length>20 || !emailReg.test(email)){
		show_validate_msg(document.getElementById("email_login"), "格式不正确");
		validate = false;
	}
	
	//验证密码
	var password = $("#password_login").val();
	var passwordReg = /^[a-zA-Z0-9_]+$/;
	if (password.length>20 || !passwordReg.test(password)){
		show_validate_msg(document.getElementById("password_login"), "格式不正确");
		validate = false;
	}

	return validate;
}

//表单提示消息
function show_validate_msg(element, msg){
	$(element).siblings("b").eq(0).html(msg);
}

//注销
function logout(){
	$.ajax({
		 url: "logout",
        type: "POST",
        success: function (data, status) {
//        	alert("main.jsp");
            if (data.code == 100){
            	window.location.href = "main.jsp";
            }
            else{
           	 $("#login_msg_box .box_body").html("注销失败");
           	 $("#login_msg_box").fadeToggle("fast");               	 
            }
        },
        error: function () {
        	window.location.href = "main.jsp";
        }
	});	 
}

//跳转到我的音乐界面
var myMusic = function(userId){
	console.log("enter success");
	if(userId == undefined){
		console.log("success");
		$("#login_msg_box .box_body").html("请先登录");
      	$("#login_msg_box").fadeToggle("fast");    
	}else{
		content.location.href = "showMyMusicPage?userId=" + userId;
	}
}