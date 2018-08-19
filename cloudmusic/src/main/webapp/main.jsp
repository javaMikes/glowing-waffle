<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<style>
::-webkit-scrollbar {
    width: 10px;
    height: 10px
}

::-webkit-scrollbar-button:vertical {
    display: none
}

::-webkit-scrollbar-corner,::-webkit-scrollbar-track {
    background-color: #e2e2e2
}

::-webkit-scrollbar-thumb {
    border-radius: 0;
    background-color: rgba(0,0,0,.3)
}

::-webkit-scrollbar-thumb:vertical:hover {
    background-color: rgba(0,0,0,.35)
}

::-webkit-scrollbar-thumb:vertical:active {
    background-color: rgba(0,0,0,.38)
}

body {
	max-width: 1440px;
	margin: auto !important;
}
</style>

<head>
    <meta charset="utf-8">
    <title>克劳德音乐</title>
    <link href="static/template/css/bootstrap.css" rel="stylesheet">
    <link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="static/template/css/screen.css" rel="stylesheet">
    <link href="static/css/main.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>
	<!-- 公共header部分 -->
	<%@ include file="WEB-INF/views/header.jsp" %>
	
	<iframe src="main_content.jsp" id="iframepage" name="content" frameborder="0" scrolling="no" marginheight="0" width="100%" height="" onLoad="iFrameHeight()"></iframe>
	
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
<!-- <script src="static/js/main.js"></script> -->
<script type="text/javascript" > 
    
	function iFrameHeight() {   
	var ifm= document.getElementById("iframepage");   
	var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;  
		//alert("加载前："+ifm.height);
		ifm.height = 0;
	 if(ifm != null && subWeb != null) {
		//alert("scrollHeight="+subWeb.body.scrollHeight);
	   ifm.height = subWeb.body.scrollHeight;
	 // alert("加载后："+ifm.height);
	   ifm.width = subWeb.body.scrollWidth;
	}    
}   
</script>
</body>
</html>