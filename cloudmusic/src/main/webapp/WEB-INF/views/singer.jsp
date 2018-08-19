<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${singerInfo.singerName}</title>
	<link href="static/template/css/bootstrap.css" rel="stylesheet">
    <link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="static/template/css/screen.css" rel="stylesheet">
    <link href="static/css/singer.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>
	<!-- 公共header部分 -->
	<%-- <%@ include file="header.jsp" %> --%>
	
	<div class="container" id="singer_container" data-singer-id="${singerInfo.id}">
		<div class="row-fluid">
			<div class="span8" id="singer_container_left">
				<div id="singer_nav">
					<img src="showSingerImg?singerId=${singerInfo.id}" alt="" id="singer_img" />
					<div id="nav_bar">
						<div class="nav_block nav_selected" data-target="#all_song_content">所有歌曲</div>
						<div class="nav_block" data-target="#self_introduction_content">个人简介</div>
					</div>					
				</div>
				<div id="nav_content_wrapper">
					<div id="all_song_content" class="nav_content">
						<button class="btn" id="play_singer_song_btn">播放TA的所有歌曲</button>
						<!-- 公共listContain部分 -->
						<%@ include file="listContain.jsp" %>
					</div>
					<div id="self_introduction_content" class="nav_content">
						<strong class="intro_title">${singerInfo.singerName}</strong><br>
						<p class="intro_word">${singerInfo.introduction}</p>						
					</div>		
				</div>						
			</div>
			
			<div class="span4" id="singer_container_right">
				<div>
					<strong>TA的热门歌曲</strong><hr>
				</div>
				<div>
					<c:forEach items="${hotSongList}" var="song">
						<a href="showSongPage?songId=${song.id}" class="song_box">
				  			<img src="findLocalImg?imgPath=${song.imgPath}" alt="photo" class="song_photo"><br>
				  			<span class="ellipsis song_name">
				  				${song.songName}
				  			</span>			  				
				  		</a>			
					</c:forEach>
				</div>		
			</div>
		</div>
	</div>
	
	<!-- 公共footer部分 -->
	<%-- <%@ include file="footer.jsp" %> --%>		

	<!-- 公共player部分 -->
	<%-- <%@ include file="player.jsp" %> --%>
	
<script src="static/template/js/jquery-ui.js"></script>	
<script src="static/template/js/bootstrap.min.js"></script>
<script src="static/js/song-func.js"></script>
<script src="static/js/singer.js"></script>
</body>
</html>