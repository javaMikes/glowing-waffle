<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${playlistInfo.listName}</title>
	<link href="static/template/css/bootstrap.css" rel="stylesheet">
    <link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="static/template/css/screen.css" rel="stylesheet">
    <link href="static/css/list-detail.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>
	<!-- 公共header部分 -->
	<%-- <%@ include file="header.jsp" %> --%>
	
	<div class="container" id="list_container">
		<div class="row-fluid">
			<div class="span8" id="list_container_left">
				<!-- 公共listIntro部分 -->
				<%@ include file="listIntroduction.jsp" %>
					
				<!-- 公共listContain部分 -->
				<%@ include file="listContain.jsp" %>			
				
				<!-- 公共comment部分 -->
				<%@ include file="comment.jsp" %>					
			</div><!-- end of span8 -->
			
			<div class="span4" id="list_container_right">
				<div>
					<strong>相关歌单推荐</strong><hr>
				</div>
				<div>
					<c:forEach items="${nicePlaylist}" var="playlist">
						<a href="showPlaylistPage?playlistId=${playlist.id}" class="list_box">
					  		<figure>
					  			<img src="showPlaylistImg?playlistId=${playlist.id}" alt="photo" class="list_photo">
					  		</figure>
					  		<p>
				  				<b class="list_name">${playlist.listName}</b><br>
				  				<span class="little_word">by</span>&nbsp;
				  				<span class="list_user">${playlist.userName}</span>
					  		</p>
				  		</a><br>
					</c:forEach>
				</div>		
			</div><!-- end of span 4 -->
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
<script src="static/js/list-detail.js"></script>

<script src="static/mplayer-master/js/mplayer.js"></script>
<script src="static/mplayer-master/js/mplayer-list.js"></script>
<script src="static/mplayer-master/js/mplayer-functions.js"></script>
<script src="static/mplayer-master/js/jquery.nstSlider.js"></script>
<script src="static/js/song-func.js"></script>
<script src="static/js/player.js"></script>

<script>

	/* alert(document.body.scrollHeight); */
//parent.document.all("iframepage").style.height=document.body.scrollHeight;   
//parent.document.all("iframepage").style.width=document.body.scrollWidth; 
	
</script>
</body>
</html>