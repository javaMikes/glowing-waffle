<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>克劳德音乐</title>
	<link href="static/template/css/bootstrap.css" rel="stylesheet">
    <link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="static/template/css/screen.css" rel="stylesheet">
    <link href="static/css/my-music.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>
	<!-- 公共header部分 -->
	<%@ include file="WEB-INF/views/header.jsp" %>
	
	<span class="playlistId">${playlistId}</span>	
	
	<div class="container" id="list_container">
		<div class="row-fluid">
			<div class="span4" id="my_music_container_left">
				<div class="accordion" id="accordion">
					<div class="accordion-group">
						<div class="accordion-heading">
				    		<a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordion" href="#my_create_list">
				        		创建的歌单（2）
				      		</a>
				    	</div>
				   		<div id="my_create_list" class="accordion-body collapse">
				      		<div class="accordion-inner">
				        		<a class="list_little_box" href="#">
				        			<img src="static/img/testImg.jpg" alt="" class="list_img" />
				        			<span class="list_name">我喜欢的音乐</span>
				        			<span class="num_user">50首 by 啊华</span>
				        		</a>
				        		<a class="list_little_box" href="#">
				        			<img src="static/img/testImg.jpg" alt="" class="list_img" />
				        			<span class="list_name">我创建的音乐</span>
				        			<span class="num_user">50首 by 啊华</span>
				        		</a>
				      		</div>
				    	</div>
				  	</div>
				  	<div class="accordion-group">
				    	<div class="accordion-heading">
				      		<a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordion" href="#my_collect_list">
				        		收藏的歌单（5）
				      		</a>
				    	</div>
				    	<div id="my_collect_list" class="accordion-body collapse">
				      		<div class="accordion-inner">
				        		Anim pariatur cliche...
				      		</div>
				    	</div>
				  	</div>
				</div><!-- /.accordion-->
			</div><!-- end of span 4 -->
		
			<div class="span8" id="my_music_container_right">
				<!-- 歌单介绍 -->
				<div id="list_introduction" class="row-fluid">
					<img src="static/img/testImg.jpg" alt="" class="img-polaroid span4 list_img"/>
					<div class="span8">
						<p>
							<span class="icn"><i class="icon-tag"></i>歌单</span><span class="icn_title">奥斯卡经典欧美</span>
						</p>						
						<p>
							<img src="static/img/testImg.jpg" alt="" class="found_img"/>&nbsp;&nbsp;&nbsp;
							<span class="found_nickname">昵称</span>&nbsp;&nbsp;&nbsp;
							<span class="found_date">2017.10.01</span>&nbsp;&nbsp;&nbsp;
							<span>创建</span>
						</p>						
						<p>
							<button class="btn"><i class="icon-play icon-white"></i>&nbsp;播放</button>
							<button class="btn" disabled="disabled icon-white"><i class="icon-folder-open"></i>&nbsp;收藏</button>
							<a class="btn" href="#comment"><i class="icon-comment icon-white"></i>&nbsp;评论</a>
						</p>
						<p>
							<span>标签：</span>
							<button class="tag">摇滚</button>
							<button class="tag">乡村</button>
							<button class="tag">非主流</button>
						</p>
						<p>
							<span class="list_intro">介绍：很好听</span>
						</p>	
					</div>
				</div>		
				<!-- 公共listContain部分 -->
				<%@ include file="WEB-INF/views/listContain.jsp" %>				
				
				<!-- 公共comment部分 -->				
				<%@ include file="WEB-INF/views/comment.jsp" %>					
			</div><!-- end of span8 -->
		</div>
	</div>
	
	<!-- 公共footer部分 -->
	<%@ include file="WEB-INF/views/footer.jsp" %>		

	<!-- 公共player部分 -->
	<%@ include file="WEB-INF/views/player.jsp" %>
	
<script src="static/template/js/jquery-ui.js"></script>
<script src="static/template/js/bootstrap.min.js"></script>
<script src="static/template/js/settingsbox.js"></script>
<script src="static/template/js/farbtastic/farbtastic.js"></script>
<script src="static/template/js/jquery.prettyPhoto.js"></script>
<script src="static/template/js/izotope.js"></script>
<script src="static/template/js/option.js"></script>
<script src="static/js/my-music.js"></script>
</body>
</html>