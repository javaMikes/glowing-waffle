<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全部歌单</title>
	<link href="static/template/css/bootstrap.css" rel="stylesheet">
    <link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="static/template/css/screen.css" rel="stylesheet">
    <link href="static/css/main.css" rel="stylesheet">
    <link href="static/css/showPlaylist.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>
	<!-- 公共header部分 -->
	<%-- <%@ include file="../header.jsp" %> --%>
		
		<div class="container-fluid topImage">
			<!-- <div class="container">
				<div class="row">
					<div class="span12">
						<h1>Portfolio 4 columns</h1>
					</div>
				</div>
				<div class="row">
					<div class="span12">
						<ul class="breadcrumb">
						  <li><a href="#">Home</a> <span class="divider">/</span></li>
						  <li class="active">Portfolio 4 columns</li>
						</ul>
					</div>
				</div>
			</div> --><!-- /.container -->
		</div><!-- /.container-fluid [topImage] -->

		<div class="container" id="list_container">
			<c:if test="${message != null}" >
				<p class="no_data">暂无数据</p>
			</c:if>	
			<div class="row">
				<div class="span12">
					<!-- <ul id="filters" class="galleryNav inline">
						<li>Sort by:</li>
						<li><a href="#" class="btn current" data-filter="*">All</a></li>
						<li><a href="#" class="btn" data-filter=".design">Design</a></li>
						<li><a href="#" class="btn" data-filter=".art">Art</a></li>
						<li><a href="#" class="btn" data-filter=".photography">Photography</a></li>
						<li><a href="#" class="btn" data-filter=".illustration">Illustration</a></li>
					</ul> -->
				</div>
					<div class="span12 h_box">
					<c:if test="${tagName == null}">
						<h2>全部</h2>
					</c:if>
					<c:if test="${tagName != null}">
						<h2>${tagName}</h2>
					</c:if>
					<button class="btn" id="select_tag">选择分类 ∨</button>		
					<div id="tag_box" data-selected="${tagName}">
						<h3>
							<a href="showPlaylist" class="btn">全部风格</a>
						</h3>
						<dl>
							<dt>语种</dt>
							<dd>
						   		<a href="javascript:void(0)" data-tag="华语">华语</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="欧美">欧美</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="日语">日语</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="韩语">韩语</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="粤语">粤语</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="小语种">小语种</a>
						   		<span>|</span>
						   	</dd>
						</dl>						
						<dl>
							<dt>风格</dt>
							<dd>
						   		<a href="javascript:void(0)" data-tag="流行">流行</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="摇滚">摇滚</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="民谣">民谣</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="电子">电子</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="舞曲">舞曲</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="说唱">说唱</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="轻音乐">轻音乐</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="爵士">爵士</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="乡村">乡村</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="拉丁">拉丁</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="古典">古典</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="民族">民族</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="英伦">英伦</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="金属">金属</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="朋克">朋克</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="蓝调">蓝调</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="雷鬼">雷鬼</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="世界音乐">世界音乐</a>
						   		<span>|</span>
						   	</dd>
						</dl>						
						<dl>
							<dt>场景</dt>
							<dd>
						   		<a href="javascript:void(0)" data-tag="清晨">清晨</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="夜晚">夜晚</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="学习">学习</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="工作">工作</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="午休">午休</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="下午茶">下午茶</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="地铁">地铁</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="驾车">驾车</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="运动">运动</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="旅行">旅行</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="散步">散步</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="酒吧">酒吧</a>
						   		<span>|</span>
						   	</dd>
						</dl>
						<dl>
							<dt>情感</dt>
							<dd>
						   		<a href="javascript:void(0)" data-tag="怀旧">怀旧</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="清新">清新</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="浪漫">浪漫</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="性感">性感</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="伤感">伤感</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="治愈">治愈</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="放松">放松</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="孤独">孤独</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="感动">感动</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="兴奋">兴奋</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="快乐">快乐</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="安静">安静</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="思念">思念</a>
						   		<span>|</span>
						   	</dd>
						</dl>
						<dl>
							<dt>主题</dt>
							<dd>
						   		<a href="javascript:void(0)" data-tag="影视原声">影视原声</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="ACG">ACG</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="校园">校园</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="游戏">游戏</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="70后">70后</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="80后">80后</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="90后">90后</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="网络歌曲">网络歌曲</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="KTV">KTV</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="经典">经典</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="吉他">吉他</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="钢琴">钢琴</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="器乐">器乐</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="儿童">儿童</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="榜单">榜单</a>
						   		<span>|</span>
						   		<a href="javascript:void(0)" data-tag="00后">00后</a>
						   		<span>|</span>
						   	</dd>
						</dl>
					</div>	
				</div>
			</div> <!--/.row [galleryNav]-->
			<ul class="playlistBlock row portfolioItems" style="margin-top:50px">
					<c:forEach items="${playlistPageInfo.list}" var="playlist">
						<li class="span2">
							<figure>
								<a href="showPlaylistPage?playlistId=${playlist.id}" class="thumbnail">
									<img src="showPlaylistImg?playlistId=${playlist.id}" alt="project">
								</a>
								<figcaption>
									<h3><a href="showPlaylistPage?playlistId=${playlist.id}">${playlist.listName}</a></h3>
									<p>
										by&nbsp;&nbsp;${playlist.userName}
									</p>
								</figcaption>
							</figure>
						</li>
					</c:forEach>
			</ul><!-- /.row [portfolioItems]-->	
		</div><!-- /.container -->			
		
		<!-- 页码导航栏 -->
		<div class="row" style="margin-left:550px;">
			<%-- <div class="col-md-6">
				当前${playlistPageInfo.pageNum}页，一共有${playlistPageInfo.pages}页，总共有${playlistPageInfo.total}条数据
			</div> --%>
			<div class="pagination">
			  <ul>
			  	<c:if test="${tagName == null}">
			  		<li><a href="showPlaylist?pn=1" style="color:black">首页</a></li>
			  	</c:if>
			  	<c:if test="${tagName != null}">
			  		<li><a href="showPlaylist?pn=1&tagName=${tagName}" style="color:black">首页</a></li>
			  	</c:if>
			    <c:if test="${playlistPageInfo.hasPreviousPage}">
					  		<li>
					  			<c:if test="${tagName == null}">
						  			<a href="showPlaylist?pn=${playlistPageInfo.pageNum-1}" style="color:black" aria-label="Previous">
						  				<span aria-hidden="true">&laquo;</span>
						  			</a>
						  		</c:if>
						  		<c:if test="${tagName != null}">
						  			<a href="showPlaylist?pn=${playlistPageInfo.pageNum-1}&tagName=${tagName}" style="color:black" aria-label="Previous">
						  				<span aria-hidden="true">&laquo;</span>
						  			</a>
						  		</c:if>
					  		</li>
					  	</c:if>
					  	<c:forEach var="pageNum" items="${playlistPageInfo.navigatepageNums}">
							
							<c:if test="${pageNum==playlistPageInfo.pageNum}">
								<c:if test="${tagName == null}">
									<li class="active"><a href="showPlaylist?pn=${pageNum}" style="color:black">${pageNum}</a></li>
								</c:if>
								<c:if test="${tagName != null}">
									<li class="active"><a href="showPlaylist?pn=${pageNum}&tagName=${tagName}" style="color:black">${pageNum}</a></li>
								</c:if>
							</c:if>
	
							<c:if test="${pageNum!=playlistPageInfo.pageNum}">
								<c:if test="${tagName == null}">
									<li><a href="showPlaylist?pn=${pageNum}" style="color:black">${pageNum}</a></li>
								</c:if>
								<c:if test="${tagName != null}">
									<li><a href="showPlaylist?pn=${pageNum}&tagName=${tagName}" style="color:black">${pageNum}</a></li>
								</c:if>
							</c:if>
						</c:forEach>

						<c:if test="${playlistPageInfo.hasNextPage}">
							<c:if test="${tagName == null}">
								<li><a href="showPlaylist?pn=${playlistPageInfo.pageNum+1}"
									aria-label="Next" style="color:black"> <span aria-hidden="true">&raquo;</span>
								</a></li>
							</c:if>
							<c:if test="${tagName != null}">
								<li><a href="showPlaylist?pn=${playlistPageInfo.pageNum+1}&tagName=${tagName}"
									aria-label="Next" style="color:black"> <span aria-hidden="true">&raquo;</span>
								</a></li>
							</c:if>
						</c:if>
						<c:if test="${tagName == null}">
							<li><a href="showPlaylist?pn=${playlistPageInfo.pages}" style="color:black">末页</a></li>
						</c:if>
						<c:if test="${tagName != null}">
							<li><a href="showPlaylist?pn=${playlistPageInfo.pages}&tagName=${tagName}" style="color:black">末页</a></li>
						</c:if>
			  </ul>
			</div>
		</div>

		<!-- 公共footer部分 -->
	<%-- <%@ include file="../footer.jsp" %>	 --%>	

	<!-- 公共player部分 -->
	<%-- <%@ include file="../player.jsp" %> --%>
	
	<!-- Scripts -->
	<script src="static/template/js/jquery-ui.js"></script>
    <script src="static/template/js/bootstrap.min.js"></script>
	<script src="static/template/js/settingsbox.js"></script>
	<script src="static/template/js/farbtastic/farbtastic.js"></script>
	<script src="static/template/js/jquery.prettyPhoto.js"></script>
	<script src="static/template/js/izotope.js"></script>
	<script src="static/template/js/option.js"></script>
	<script src="static/js/playlist/showPlaylist.js"></script>
</body>
</html>