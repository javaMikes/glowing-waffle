<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${songInfo.songName}</title>
	<link href="static/template/css/bootstrap.css" rel="stylesheet">
    <link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="static/template/css/screen.css" rel="stylesheet">
    <link href="static/css/song.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>
	<!-- 公共header部分 -->
	<%-- <%@ include file="header.jsp" %> --%>
	
	<span class="songId">${songInfo.id}</span>	
	<span class="userId">${userInfo.id}</span>
	<div class="container" id="song_container">
		<div class="row-fluid">
			<div class="span8" id="song_container_left">
				<!-- 歌曲介绍 -->
				<div id="song_introduction" class="row-fluid">
					<div class="span4 song_img">
						<img src="findLocalImg?imgPath=${songInfo.imgPath}" alt="" class="song_img_pic"/>
						<img src="static/img/cover.png" alt="" class="song_img_cover" />
					</div>
					<div class="span8">
						<p>
							<span class="icn"><i class="icon-tag"></i>单曲</span><span class="icn_title">${songInfo.songName}</span>
						</p>						
						<p>
							歌手：<a href="showSingerInfoPage?singerName=${songInfo.singer}" class="user">${songInfo.singer}</a>
						</p>						
						<p>
							<a class="btn play" href="javascript:void(0)"><i class="icon-play icon-white"></i>&nbsp;播放</a>
							<a class="btn collect" href="javascript:void(0)"><i class="icon-folder-open icon-white icon-white"></i>&nbsp;收藏</a>
							<a class="btn" href="#comment"><i class="icon-comment icon-white"></i>&nbsp;评论</a>
						</p>
						<p>
							<span class="lrc"></span>
							<a href="javascript:void(0)" id="show_lrc">展开 ∨</a>
							<a href="javascript:void(0)" id="hide_lrc">收起 ∧</a>
						</p>	
					</div>
				</div>		
				<!-- 公共comment部分 -->				
				<%@ include file="comment.jsp" %>	
			</div><!-- end of span8 -->
			
			<div class="span4" id="song_container_right">
				<div>
					<strong>相关歌曲推荐</strong><hr>
				</div>
				<div>
					<c:forEach items="${niceSongList}" var="song">
						<a href="showSongPage?songId=${song.id}" class="song_box">
					  		<figure>
					  			<img src="findLocalImg?imgPath=${song.imgPath}" alt="photo" class="song_photo">
					  		</figure>
					  		<p>
				  				<b class="song_name">${song.songName}</b><br>
				  				<span class="little_word">歌手</span>&nbsp;
				  				<span class="song_singer">${song.singer}</span>
					  		</p>
				  		</a><br>
					</c:forEach>
				</div>		
			</div><!-- end of span 4 -->
		</div>
	</div>

<div id="choose_create_playlist" class="my_modal">
	<div class="modal_head">
		<strong class="head_title">
			选择歌单
		</strong>
		<button type="button" class="head_close">&times;</button>
	</div>
	<div class="modal_body">
		<!-- <div href="#" class="select_box">
			<img src="" alt="" />
			<span></span>
		</div> -->
	</div>
</div>

	<!-- 公共footer部分 -->
	<%-- <%@ include file="footer.jsp" %> --%>		

	<!-- 公共player部分 -->
	<%-- <%@ include file="player.jsp" %> --%>
	
<script src="static/template/js/jquery-ui.js"></script>
<script src="static/template/js/bootstrap.min.js"></script>
<script src="static/template/js/settingsbox.js"></script>
<script src="static/template/js/farbtastic/farbtastic.js"></script>
<script src="static/template/js/jquery.prettyPhoto.js"></script>
<script src="static/template/js/izotope.js"></script>
<script src="static/template/js/option.js"></script>
<script src="static/js/song-func.js"></script>
<script src="static/js/song.js"></script>
</body>
</html>