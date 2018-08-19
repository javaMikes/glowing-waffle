<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="static/css/list-contain.css" rel="stylesheet">
<div id="list_contain" class="row-fluid" data-playlist-id="${playlistId}" data-singer-id="${singerInfo.id}" data-user-id="${userInfoSelected.id}" data-recommend-id="${userInfo.id}">
	<span class="userId">${userInfo.id}</span>
	<h2><!-- 歌曲列表 --></h2>
	<table class="table" id="list_detail_table">
		<thead>
			<!-- <tr>
				<th></th>
				<th>歌曲标题</th>
				<th>时长</th>
				<th>歌手</th>
			</tr>	 -->						
		</thead>
		<tbody>
			<!-- <tr>
				<td>1</td>
				<td><div class="td_text"><a href="javascript:void(0)">ET</a></div></td>
				<td>
					<div class="td_time">5:00</div>
					<div class="td_btn">
						<i class="icon-play" title="播放"></i>
						<i class="icon-plus" title="添加到播放列表"></i>
						<i class="icon-folder-open" title="收藏"></i>
					</div>
				</td>
				<td><div class="td_text"><a href="javascript:void(0)">Ketty Parry</a></div></td>
			</tr> -->
		</tbody>
	</table>
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
<script src="static/js/list-contain.js"></script>