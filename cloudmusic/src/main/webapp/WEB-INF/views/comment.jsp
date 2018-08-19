<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="static/css/comment.css" rel="stylesheet">
<div class="row-fluid" id="comment" data-playlist-id="${playlistId}" data-song-id="${songInfo.id}">
	<span class="userId">${userInfo.id}</span>
	<h2>评论</h2>	
	<div class="my_comment">
		<div class="comment_head">
			<img src="seekExperts?userId=${userInfo.id}" class="img-rounded">
		</div>					
		<textarea class="my_comment_content" placeholder="评论"></textarea>
		<b class="colored span10 contact_page_form_result"></b>
		<button type="button" class="btn btn-color" id="comment_sava_btn">提交</button>
	</div>
	<strong>精彩评论</strong>&nbsp;<span class="little_word">共<span class="commnet_counts">0</span>条评论</span>
	<hr>
	<%-- <div class="comment_box" data-comment-id="">
		<div class="comment_head">
			<img src="seekExperts?userId=${userInfo.id}" class="img-rounded">
		</div>
		<div class="comment_detail">
			<div class="comment_content_box">
				<a href="#" class="comment_user user">评论人</a>：<span class="comment_content">评论内容</span>
			</div>							
			<div class="reply_content_box">
				<a href="#" class="reply_user user">回复人</a>：<span class="reply_content">回复内容</span>
			</div>
			<div class="comment_time_box">
				<span class="comment_time">2017年10月10号</span><a href="javascript:void(0)" class="comment_reply">回复</a>
			</div>							
			<div class="reply_input_box">
				<textarea class="reply_input" placeholder="回复"></textarea>
				<b class="colored span10 contact_page_form_result"></b>
				<button type="button" class="btn btn-color reply_save_btn">回复</button>								
			</div>			
		</div>												
	</div>
	<hr> --%>
	<!-- <div class="pagination_box comment_pagination">
		<div class="pagination">
			<ul>
				<li><a href="#">&laquo;</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">&raquo;</a></li>
			</ul>
		</div>
	</div> -->
	<!-- 页码导航栏 -->
	<div class="pagination_box comment_pagination">
		<div class="pagination" id="page_nav">
		  <ul>

		  </ul>
		</div>
	</div>
</div>	
<script src="static/js/comment.js"></script>