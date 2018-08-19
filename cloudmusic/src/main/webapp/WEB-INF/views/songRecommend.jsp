<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${singerInfo.singerName}</title>
	<link href="static/template/css/bootstrap.css" rel="stylesheet">
    <link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="static/template/css/screen.css" rel="stylesheet">
    <link href="static/css/song-recommend.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>
	<%-- <!-- 公共header部分 -->
	<%@ include file="header.jsp" %> --%>
	
	<div class="container" id="song_recommend_container" data-singer-id="${singerInfo.id}">
		<div class="row-fluid">
			<div class="span8" id="song_recommend_container_left">
				<img src="static/img/recommend.jpg" alt="" id="recommend_img" />
				<button class="btn" id="play_all_recommend_btn">播放所有推荐歌曲</button>
				<!-- 公共listContain部分 -->
				<%@ include file="listContain.jsp" %>					
			</div>			
		</div>
	</div>
	
	<%-- <!-- 公共footer部分 -->
	<%@ include file="footer.jsp" %>		

	<!-- 公共player部分 -->
	<%@ include file="player.jsp" %> --%>
	
<script src="static/template/js/jquery-ui.js"></script>	
<script src="static/template/js/bootstrap.min.js"></script>
<script src="static/js/song-func.js"></script>
<script src="static/js/song-recommend.js"></script>
</body>
</html>