<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="static/css/list-introduction.css" rel="stylesheet">
<span class="userId">${userInfo.id}</span>
<div id="list_introduction" class="row-fluid" data-playlist-id="${playlistId}">
	<img src="showPlaylistImg?playlistId=${playlistInfo.id}" alt="" class="img-polaroid span4 list_img"/>
	<div class="span8">
		<p>
			<span class="icn"><i class="icon-tag"></i>歌单</span><span class="icn_title">${playlistInfo.listName}</span>
		</p>						
		<p>
			<img src="seekExperts?userId=${userId}" alt="" class="found_img"/>&nbsp;&nbsp;&nbsp;
			<a href="showPersonPage?userId=${userId}" class="found_nickname">${userNickname}</a>&nbsp;&nbsp;&nbsp;
			<span class="found_date"><fmt:formatDate value="${playlistInfo.createTime}" pattern="yyyy-MM-dd" /></span>&nbsp;&nbsp;&nbsp;
			<span>创建</span>
		</p>						
		<p>
			<a class="btn play" href="javascript:void(0)"><i class="icon-play icon-white"></i>&nbsp;播放</a>
			<c:if test="${userId != userInfo.id}">
				<a class="btn collect" href="javascript:void(0)" data-collected="100"><i class="icon-folder-open icon-white"></i>&nbsp;收藏</a>
			</c:if>
			<a class="btn" href="#comment"><i class="icon-comment icon-white"></i>&nbsp;评论</a>
		</p>
		<p>
			<span>标签：</span>
			<c:forEach var="playlistTag" items="${playTaglist}">
				<a class="tag" href="showPlaylist?tagName=${playlistTag.tagName}">${playlistTag.tagName}</a>
			</c:forEach>
		</p>
		<p>
			<span class="list_intro">介绍：${playlistInfo.profile}</span>
		</p>	
	</div>
</div>		
<script src="static/js/list-introduction.js"></script>