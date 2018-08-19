<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>这里写歌单名字</title>
	<link href="static/template/css/bootstrap.css" rel="stylesheet">
    <link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="static/template/css/screen.css" rel="stylesheet">
    <link href="static/css/user-page.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>
	<!-- 公共header部分 -->
	<%-- <%@ include file="header.jsp" %> --%>
	
	<div class="container" id="user_page_container">
		<div class="row-fluid" id="user_page_info">
			<div class="span3">
				<img src="seekExperts?userId=${userInfoSelected.id}" alt="" class="img-polaroid user_img"/>
			</div>
			<div class="span9 row-fluid">		
				<span class="user">${userInfoSelected.nickname}</span>
				<c:if test="${userInfo.id == userInfoSelected.id}">
					<a href="showUserUpdate?id=${ userInfoSelected.id}" class="btn edit_info_btn">编辑个人资料</a>
				</c:if>
				<hr />
				<%-- <div class="box_concern">
					<div class="num_concern">${concernNum}</div>
					<div class="word_concern">关注</div>
				</div> --%>
				<div>
					<div class="word_fans">${userInfo.region}</div>
					<!-- <div class="word_fans">粉丝</div> -->
				</div>				
			</div>
		</div>
		
		<!-- 公共listContain部分 -->
		<%@ include file="listContain.jsp" %>
		<%-- <h2>听歌排行</h2>
		<table class="table" id="rank_song_table">
			<tbody>
				<tr>
					<td>1</td>
					<td>歌曲名字</td>
					<td>
						几次
						<span class="td_btn">
							<i class="icon-play" title="播放"></i>
							<i class="icon-plus" title="添加到播放列表"></i>
							<i class="icon-folder-open" title="收藏"></i>
							<i class="song_info" data-id="${song.id}" data-name="${song.songName}" data-singer="${song.singer}" data-img="${song.imgPath}" data-src="${song.path}" data-time="${song.musicTime}"></i>
						</span>
					</td>
				</tr>
			</tbody>
		</table> --%>
		
		
		<h2>创建的歌单</h2>
		<ul class="playlistBlock row portfolioItems" style="margin-top:30px">
			<c:forEach items="${createPlayList}" var="playlist">
				<li class="span2">
					<figure>
						<a href="showPlaylistPage?playlistId=${playlist.id}" class="thumbnail">
							<img src="showPlaylistImg?playlistId=${playlist.id}" alt="project">
						</a>
						<figcaption>
							<h3><a href="showPlaylistPage?playlistId=${playlist.id}">${playlist.listName}</a></h3>
							<p>
								
							</p>
						</figcaption>
					</figure>
				</li>
			</c:forEach>
		</ul><!-- /.row [portfolioItems]-->	
		<h2>收藏的歌单</h2>
		<ul class="playlistBlock row portfolioItems" style="margin-top:30px">
			<c:forEach items="${collectionPlaylist}" var="playlist">
				<li class="span2">
					<figure>
						<a href="showPlaylistPage?playlistId=${playlist.id}" class="thumbnail">
							<img src="showPlaylistImg?playlistId=${playlist.id}" alt="project">
						</a>
						<figcaption>
							<h3><a href="showPlaylistPage?playlistId=${playlist.id}">${playlist.listName}</a></h3>
							<p>
								
							</p>
						</figcaption>
					</figure>
				</li>
			</c:forEach>
		</ul><!-- /.row [portfolioItems]-->	
	</div>
	
	<!-- 公共footer部分 -->
	<%-- <%@ include file="footer.jsp" %>	 --%>	

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
<script src="static/js/user-page.js"></script>
</body>
</html>