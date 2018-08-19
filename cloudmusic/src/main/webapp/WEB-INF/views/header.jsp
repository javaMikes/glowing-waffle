<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="static/css/header-footer.css" rel="stylesheet">
<header>
	<div class="navTop">
		<div class="container">
			<div class="row">
				<nav class="span12">
					<ul class="clearfix unstyled pull-right">
					<input type="hidden" id="userId" value="${userInfo.id}"/>
						<c:choose>
							<c:when test="${userInfo.nickname==null}">
								<a href="javascript:void(0)"><li class="login"><span>登录</span></li></a>
								<a href="register.jsp" target="content"><li class="register"><span>注册</span></li></a>
							</c:when>
							<c:otherwise>	
								<div id="user_nav">
									<div id="user_nav_content">${userInfo.nickname}，你好！
										<img src="seekExperts?userId=${userInfo.id}" class="img-circle" id="headImg">
									</div>	
									<div id="user_nav_list">
							    		<a href="showPersonPage?userId=${userInfo.id}" class="user_page_btn" target="content">个人主页</a>
								    	<a href="showUserUpdate?id=${userInfo.id}" class="user_setting_btn"target="content">个人设置</a>
							    		<a href="javascript:logout()" class="user_logout_btn" target="content">注销</a>
								    </div>
								</div>	
							</c:otherwise>
						</c:choose>	
					</ul>
				</nav><!-- /nav -->
			</div><!-- /.row [TopNavigation] -->
		</div><!-- /.container -->
	</div><!-- /.navTop -->

	<div class="mainNav">
		<div class="container">
			<div class="row">
				<div class="span4">
					<a href="main_content.jsp" target="content" class="brand"></a>
				</div>
				<nav class="span8 clearfix navbar-inner">
					<a class="btn btn-color menuMobile" data-toggle="collapse" data-target=".nav-collapse">menu</a>
				    <div class="nav-collapse collapse">
						<ul class="nav inline pull-right menu">
							<li class="nav_li active">
								<a href="main_content.jsp" target="content">发现音乐</a>
								<!-- <ul class="unstyled">
									<li><a href="#">推荐</a></li>
									<li><a href="#">排行榜</a></li>
									<li><a href="showPlaylist">歌单</a></li>
									<li><a href="#">主播电台</a></li>
									<li><a href="#">歌手</a></li>
									<li><a href="#">新碟上架</a></li>
								</ul> -->
							</li>
							<li class="nav_li">
								<a href="showPlaylist" target="content">歌单</a>
								<!-- <ul class="unstyled">
									<li><a href="#">推荐</a></li>
									<li><a href="#">排行榜</a></li>
									<li><a href="showPlaylist">歌单</a></li>
									<li><a href="#">主播电台</a></li>
									<li><a href="#">歌手</a></li>
									<li><a href="#">新碟上架</a></li>
								</ul> -->
							</li>
							<li class="nav_li">
								<a href="javascript:myMusic(${userInfo.id})" target="content">我的音乐</a>
								<!-- <ul class="unstyled">
									<li><a href="about_us.html">About us</a></li>
									<li><a href="service.html">Services</a></li>
									<li><a href="faq.html">Faq</a></li>
									<li><a href="features.html">Features</a></li>
									<li><a href="error_404.html">error 404</a></li>
									<li><a href="full_width.html">Full width</a></li>
									<li><a href="right_sidebar.html">Right sidebar</a></li>
									<li><a href="left_sidebar.html">Left sidebar</a></li>
								</ul> -->
							</li>
							<!--<li>
								<a href="#">朋友</a>
								 <ul class="unstyled">
									<li><a href="gallery_2.html">Gallery 2 columns</a></li>
									<li><a href="gallery_3.html">Gallery 3 columns</a></li>
									<li><a href="gallery_4.html">Gallery 4 columns</a></li>
								</ul> 
							</li>-->
							<!-- <li>
								<a href="#">PORTFOLIO</a>
								<ul class="unstyled">
									<li><a href="portfolio_2.html">Portfolio 2 columns</a></li>
									<li><a href="portfolio_3.html">Portfolio 3 columns</a></li>
									<li><a href="portfolio_4.html">Portfolio 4 columns</a></li>
								</ul>
							</li> -->
							<!--<li>
								<a href="#">音乐人</a>
								 <ul class="unstyled">
									<li><a href="blog_right_sidebar.html">Right sidebar</a></li>
									<li><a href="blog_left_sidebar.html">Left sidebar</a></li>
									<li><a href="blog.html">Full width</a></li>
									<li><a href="blog_single_post.html">Single post</a></li>
								</ul> 
							</li>
							<li><a href="#">关于我们</a></li>-->
							<li >
									<input placeholder="搜索 音乐/歌单/歌手" id="dysearchBtn" style="border-radius:30px" type="text" class="search" required x-webkit-speech="x-webkit-speech">
									<div class="dySearchDiv" id="dySearchDiv" style="display:none">
										<div style="width:220px;height:auto" id="dy1" class="dyBorderBottom">
											<div style="width:30%;position:relative;left:0;height:100%" >
												<i class="icon-user"></i>用户
											</div>
											<div  class="dyBorderLeft dyInfoDiv">
												
											</div>
										</div>
										<div style="width:220px;height:auto" id="dy2" class="dyBorderBottom">
											<div style="width:30%;position:relative;left:0;height:100%" >
												<i class="icon-music"></i>歌曲
											</div>
											<div  class="dyBorderLeft dyInfoDiv">
												
											</div>
										</div>
										<div style="width:220px;height:auto;background: #F9F8F8;" id="dy3" class="dyBorderBottom">
											<div style="width:30%;position:relative;height:100%" >
												<i class="icon-headphones"></i>歌单
											</div>
											<div   class="dyBorderLeft dyInfoDiv">
												
											</div>
										</div>
										<div style="width:220px;height:auto" id="dy4" class="">
											<div style="width:30%;position:relative;height:100%" >
												<i class="icon-star"></i>歌手
											</div>
											<div  class="dyBorderLeft dyInfoDiv">
												
											</div>
										</div>
									</div>
							</li>
						</ul>
					</div>
				</nav><!-- /nav.span6 [MainNavigation] -->
			</div><!-- /.row [Logo, MainNavigation] -->
		</div><!-- /.container -->
	</div><!-- /.mainNav -->
</header><!-- /header -->

<div id="login_box">
	<div class="login_head">
		<button type="button" class="login_close">&times;</button>
	</div>	
	<div class="login_body">
		<div class="row-fluid">
			<div class="span7">
				<form action="#" id="login_form" class="contactForm coloredGray">
					<p class="row-fluid">
						<label for="email_login" class="span2">邮箱</label>
						<input type="email" name="email" class="span10" id="email_login" placeholder="邮箱">
						<b class="colored span10 contact_page_form_result"></b>
					</p>
					<p class="row-fluid">
						<label for="password_login" class="span2">密码</label>
						<input type="password" name="password" class="span10" id="password_login" placeholder="密码">
						<b class="colored span10 contact_page_form_result"></b>
					</p>
					<p class="row-fluid">		
						<button type="button" class="btn btn-color " id="login_btn">&nbsp;登录&nbsp;</button>
						<a href="forgotPsw.jsp"  class="btn btn-color" target="content" id="forgetPwdBtn">忘记密码？</a>
					</p>
				</form>
			</div>
			<div class="span4">
				<img src="static/img/l.png" alt="" class="login_box_img"/>
			</div>		
		</div>
		
	</div>
	<div class="login_foot">
	
	</div>
</div>

<div id="login_msg_box">
	<div class="box_head">
		<button type="button" class="box_close">&times;</button>
	</div>	
	<div class="box_body"></div>
	<div class="box_foot">
		<button type="button" class="btn btn-color" id="login_confirm">确认</button>
	</div>
</div>
<script src="static/js/header-footer.js"></script>
<script>
	$("#dysearchBtn").click(function(e){
		var str = $("#dysearchBtn").val();
		if(str=="")
		{
			return false;
		}

		e.stopPropagation();
		$("#dySearchDiv").fadeIn("slow");
	});

	
	$("html body").click(function(){
		$("#dySearchDiv").fadeOut("1000");
	});

	$("#forgetPwdBtn").click(function(){
		$(".login_close").click();
	});
	
	$("#dysearchBtn").bind("input propertychange", function(e){
		var str = $("#dysearchBtn").val();
		if(str=="")
		{
			$("#dySearchDiv").fadeOut("1000");
			return false;
		}

		e.stopPropagation();
		$("#dySearchDiv").fadeIn("slow");
		//向后台请求数据
		 $.ajax({

                url:"searchAll",
                data:{
                    name : str
                },
                type:"post",
                success: function (msg) {  
                  
                  //拼接用户，最多2个
                  var opUser = $("#dy1").children("div:eq(1)");
                  opUser.empty();

                  var listUser = msg.map.listUser;
                  if(listUser == "")
                  {
                  	
                  	$("#dy1").fadeOut(10);
                  }
                  else
                  {
                  	$("#dy1").fadeIn(10);
	                  $.each(listUser, function(index, item) {

							if(index <= 1)
							{
								var str_length = str.length;

								var str_ = item.nickname;
								var length = str_.length;

								var strongIndex = str_.indexOf(str);//得到开始变色的下标

								var str_1 = str_.substring(0,strongIndex);
								var str_2 = str_.substring(strongIndex,strongIndex+str_length);
								var str_3 = str_.substring(strongIndex+str_length,length);



								var op = $("<p value='"+ item.id +"' style='margin-bottom:5px;cursor:pointer'></p>").append(str_1).append($("<span style='color:red'></span>").append(str_2)).append(str_3);
								var op1 = $("#dy1").children("div:eq(1)");
								op.appendTo(op1);
							}

							
					  });
	              }

                  //拼接歌曲，最多3个
                  var opSong = $("#dy2").children("div:eq(1)");
                  opSong.empty();

                  var listSong = msg.map.listSong;
                  if(listSong == "")
                  {
                  	
                  	$("#dy2").fadeOut(10);
                  }
                  else
                  {
                  	$("#dy2").fadeIn(10);
	                  $.each(listSong, function(index, item) {

							if(index <= 2)
							{
								var str_length = str.length;

								var str_ = item.songName;
								var length = str_.length;

								var strongIndex = str_.indexOf(str);//得到开始变色的下标

								var str_1 = str_.substring(0,strongIndex);
								var str_2 = str_.substring(strongIndex,strongIndex+str_length);
								var str_3 = str_.substring(strongIndex+str_length,length);



								var op = $("<p value='"+ item.id +"' style='margin-bottom:5px;cursor:pointer'></p>").append(str_1).append($("<span style='color:red'></span>").append(str_2)).append(str_3);
								var op1 = $("#dy2").children("div:eq(1)");
								op.appendTo(op1);
							}

							
					  });
	              }

                  //拼接歌单
                  var opPlaylist = $("#dy3").children("div:eq(1)");
                  opPlaylist.empty();

                  var listPlaylist = msg.map.listPlaylist;

                  if(listPlaylist == "")
                  {
                  	$("#dy3").fadeOut(10);
                  }
                  else
                  {
                  	$("#dy3").fadeIn(10);
  	                  $.each(listPlaylist, function(index, item) {
  	
  							if(index <= 2)
  							{
  								var str_length = str.length;
  	
  								var str_ = item.listName;
  								var length = str_.length;
  	
  								var strongIndex = str_.indexOf(str);//得到开始变色的下标
  	
  								var str_1 = str_.substring(0,strongIndex);
  								var str_2 = str_.substring(strongIndex,strongIndex+str_length);
  								var str_3 = str_.substring(strongIndex+str_length,length);
  	
  	
  								var op = $("<p value='"+ item.id +"' style='margin-bottom:5px;cursor:pointer'></p>").append(str_1).append($("<span style='color:red'></span>").append(str_2)).append(str_3);
  								
  								op.appendTo(opPlaylist);
  							}
  	
  							
  					  });
                  	}
				  //拼接歌手
				  var opSinger = $("#dy4").children("div:eq(1)");
                  opSinger.empty();

                  var listSinger = msg.map.listSinger;

                  if(listSinger == "")
                  {
                  	$("#dy4").fadeOut(10);
                  }
                  else
                  {
                  	$("#dy4").fadeIn(10);
  	                  $.each(listSinger, function(index, item) {
  	
  							if(index <= 2)
  							{
  								var str_length = str.length;
  	
  								var str_ = item.singerName;
  								var length = str_.length;
  	
  								var strongIndex = str_.indexOf(str);//得到开始变色的下标
  	
  								var str_1 = str_.substring(0,strongIndex);
  								var str_2 = str_.substring(strongIndex,strongIndex+str_length);
  								var str_3 = str_.substring(strongIndex+str_length,length);
  	
  								var op = $("<p value='"+ item.singerName +"' style='margin-bottom:5px;cursor:pointer'></p>").append(str_1).append($("<span style='color:red'></span>").append(str_2)).append(str_3);
  								
  								op.appendTo(opSinger);
  							}
  	
  							
  					  });
                  	}

              },  
              error: function (msg) {  
                  alert("error");  
              }  
        });
	});
//关于用户的后发事件
	$(document).on("click","#dy1 p",function(){
		var str = $(this).attr("value");
		
		content.location.href="showPersonPage?userId=" + str;
	});

	$(document).on("mouseover","#dy2 p",function(){
		$(this).css("background","#FAEBD7");
	});

	$(document).on("mouseout","#dy2 p",function(){
		$(this).css("background","");
	});

//关于歌曲的后发事件
	$(document).on("click","#dy2 p",function(){
		var str = $(this).attr("value");
		
		content.location.href="showSongPage?songId="+str;
	});

	$(document).on("mouseover","#dy2 p",function(){
		$(this).css("background","#FAEBD7");
	});

	$(document).on("mouseout","#dy2 p",function(){
		$(this).css("background","");
	});


//关于歌单的后发事件
	$(document).on("click","#dy3 p",function(){
		var str = $(this).attr("value");
		content.location.href="showPlaylistPage?playlistId="+str;
	});

	$(document).on("mouseover","#dy3 p",function(){
		$(this).css("background","#FAEBD7");
	});

	$(document).on("mouseout","#dy3 p",function(){
		$(this).css("background","");
	});

//关于歌手的后发事件
	$(document).on("click","#dy4 p",function(){
		var str = $(this).attr("value");
		content.location.href = "showSingerInfoPage?singerName="+str;
	});

	$(document).on("mouseover","#dy4 p",function(){
		$(this).css("background","#FAEBD7");
	});

	$(document).on("mouseout","#dy4 p",function(){
		$(this).css("background","");
	});

</script>
