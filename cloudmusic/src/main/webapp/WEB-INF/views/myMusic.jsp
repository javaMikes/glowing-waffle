<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>我的音乐</title>
	<link href="static/template/css/bootstrap.css" rel="stylesheet">
    <link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="static/template/css/screen.css" rel="stylesheet">
    <link href="static/css/my-music.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>
	<!-- 公共header部分 -->
	<%-- <%@ include file="header.jsp" %> --%>

	<div class="container" id="my_music_container" data-user-id="${userId}">
		<div class="row-fluid">
			<div class="span4" id="my_music_container_left">
				<div class="accordion" id="accordion">
					<div class="accordion-group">
						<div class="accordion-heading">
				    		<a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordion" href="#my_create_list">
				        		创建的歌单（${createPlayList.size()}）<button type="button" id="new_playlist">+新建</button>
				      		</a>
				    	</div>
				   		<div id="my_create_list" class="accordion-body collapse">
				      		<div class="accordion-inner">				      			
				      			<c:forEach items="${createPlayList}" var="createPlayList">
				      				<a class="list_little_box" href="showMyMusicPage?userId=${userInfo.id}&playlistId=${createPlayList.id}" data-playlist-id="${createPlayList.id}">
					        			<img src="showPlaylistImg?playlistId=${createPlayList.id}" alt="" class="list_img" />
					        			<span class="list_name">${createPlayList.listName}</span>
					        			<div class="list_icon">
					        				<i class="icon-pencil" title="修改歌单" data-playlist-id="${createPlayList.id}" data-playlist-name="${createPlayList.listName}"></i>
					        				<i class="icon-trash" title="删除歌单" data-playlist-id="${createPlayList.id}" data-playlist-name="${createPlayList.listName}"></i>
					        			</div>
					        		</a>
				      			</c:forEach>
				      		</div>
				    	</div>
				  	</div>
				  	<div class="accordion-group">
				    	<div class="accordion-heading">
				      		<a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordion" href="#my_collect_list">
				        		收藏的歌单（${collectionPlaylist.size()}）
				      		</a>
				    	</div>
				    	<div id="my_collect_list" class="accordion-body collapse">
				      		<div class="accordion-inner">
				        		<c:forEach items="${collectionPlaylist}" var="collectPlayList">
				      				<a class="list_little_box" href="showMyMusicPage?userId=${userInfo.id}&playlistId=${collectPlayList.id}" data-playlist-id="${collectPlayList.id}">
					        			<img src="showPlaylistImg?playlistId=${collectPlayList.id}" alt="" class="list_img" />
					        			<span class="list_name">${collectPlayList.listName}</span>
					        			<span class="list_user">by ${collectPlayList.userName}</span>
					        		</a>
				      			</c:forEach>
				      		</div>
				    	</div>
				  	</div>
				</div><!-- /.accordion-->
			</div><!-- end of span 4 -->
		
			<div class="span8" id="my_music_container_right">
				<div id="list_detail_wrapper">
					<!-- 公共listIntroduction部分 -->
					<%@ include file="listIntroduction.jsp" %>
					
					<!-- 公共listContain部分 -->
					<%@ include file="listContain.jsp" %>				
					
					<!-- 公共comment部分 -->				
					<%@ include file="comment.jsp" %>	
				</div>
				<div id="update_playlist_wrapper" class="row-fluid" data-playlist-id="">
					<form action="#" id="update_playlist_form" class="contactForm coloredGray">
						<p class="row-fluid">
							<label for="list_name" class="span2">歌单名称<sup>*</sup></label>
							<input type="text" name="list_name" class="span10" id="list_name" placeholder="歌单名称">
							<b class="colored span10 offset2 contact_page_form_result"></b>
						</p>
						<p class="row-fluid">
							<label for="profile" class="span2">歌单介绍<sup></sup></label>
							<textarea name="profile" class="span10" id="profile" rows="3" placeholder="歌单介绍"></textarea>
							<b class="colored span10 offset2 contact_page_form_result"></b>
						</p>
						<p class="row-fluid">
							<label for="" class="span2">标签<sup></sup></label>
							<span class="span10">
								<span id="playlist_tag"></span>
								<a href="javascript:void(0)" id="select_tag">选择标签</a>
							</span>						
						</p>
						<p class="row-fluid">
							<button type="button" class="btn btn-color offset2" id="img_page_redirect">上传封面</button>
							<button type="button" class="btn btn-color" id="update_playlist_btn">完成</button>
						</p>
					</form>
				</div>			
			</div><!-- end of span8 -->
		</div>
	</div>
	
	<!-- 公共footer部分 -->
	<%-- <%@ include file="footer.jsp" %> --%>		

	<!-- 公共player部分 -->
	<%-- <%@ include file="player.jsp" %> --%>
	
<div id="new_playlist_modal" class="my_modal">
	<div class="modal_head">
		<strong class="head_title">
			新建歌单
		</strong>
		<button type="button" class="head_close">&times;</button>
	</div>
	<div class="modal_body">
		<form action="#" id="new_playlist_form" class="contactForm">
			<p class="row-fluid">
				<label for="list_name" class="span2">歌单名称<sup>*</sup></label>
				<input type="text" name="list_name" class="span10" id="list_name" placeholder="歌单名称">
				<b class="colored span10 offset2 contact_page_form_result"></b>
			</p>
			<p class="row-fluid">
				<button type="button" class="btn btn-color offset2" id="new_playlist_btn">新建</button>
			</p>
		</form>
	</div>
</div>

<div id="select_tag_modal" class="my_modal">
	<div class="modal_head">
		<strong class="head_title">
			选择标签
		</strong>
		<button type="button" class="head_close">&times;</button>
	</div>
	<div class="modal_body">
		<dl>
			<dt>语种</dt>
			<dd>
		   		<a href="javascript:void(0)" data-tag="华语">华语</a>
		   		<a href="javascript:void(0)" data-tag="欧美">欧美</a>
		   		<a href="javascript:void(0)" data-tag="日语">日语</a>
		   		<a href="javascript:void(0)" data-tag="韩语">韩语</a>
		   		<a href="javascript:void(0)" data-tag="粤语">粤语</a>
		   		<a href="javascript:void(0)" data-tag="小语种">小语种</a>
		   	</dd>
		</dl>						
		<dl>
			<dt>风格</dt>
			<dd>
		   		<a href="javascript:void(0)" data-tag="流行">流行</a>
		   		<a href="javascript:void(0)" data-tag="摇滚">摇滚</a>
		   		<a href="javascript:void(0)" data-tag="民谣">民谣</a>
		   		<a href="javascript:void(0)" data-tag="电子">电子</a>
		   		<a href="javascript:void(0)" data-tag="舞曲">舞曲</a>
		   		<a href="javascript:void(0)" data-tag="说唱">说唱</a>
		   		<a href="javascript:void(0)" data-tag="轻音乐">轻音乐</a>
		   		<a href="javascript:void(0)" data-tag="爵士">爵士</a>
		   		<a href="javascript:void(0)" data-tag="乡村">乡村</a>
		   		<a href="javascript:void(0)" data-tag="拉丁">拉丁</a>
		   		<a href="javascript:void(0)" data-tag="古典">古典</a>
		   		<a href="javascript:void(0)" data-tag="民族">民族</a>
		   		<a href="javascript:void(0)" data-tag="英伦">英伦</a>
		   		<a href="javascript:void(0)" data-tag="金属">金属</a>
		   		<a href="javascript:void(0)" data-tag="朋克">朋克</a>
		   		<a href="javascript:void(0)" data-tag="蓝调">蓝调</a>
		   		<a href="javascript:void(0)" data-tag="雷鬼">雷鬼</a>
		   		<a href="javascript:void(0)" data-tag="世界音乐">世界音乐</a>
		   	</dd>
		</dl>						
		<dl>
			<dt>场景</dt>
			<dd>
		   		<a href="javascript:void(0)" data-tag="清晨">清晨</a>
		   		<a href="javascript:void(0)" data-tag="夜晚">夜晚</a>
		   		<a href="javascript:void(0)" data-tag="学习">学习</a>
		   		<a href="javascript:void(0)" data-tag="工作">工作</a>
		   		<a href="javascript:void(0)" data-tag="午休">午休</a>
		   		<a href="javascript:void(0)" data-tag="下午茶">下午茶</a>
		   		<a href="javascript:void(0)" data-tag="地铁">地铁</a>
		   		<a href="javascript:void(0)" data-tag="驾车">驾车</a>
		   		<a href="javascript:void(0)" data-tag="运动">运动</a>
		   		<a href="javascript:void(0)" data-tag="旅行">旅行</a>
		   		<a href="javascript:void(0)" data-tag="散步">散步</a>
		   		<a href="javascript:void(0)" data-tag="酒吧">酒吧</a>
		   	</dd>
		</dl>
		<dl>
			<dt>情感</dt>
			<dd>
		   		<a href="javascript:void(0)" data-tag="怀旧">怀旧</a>
		   		<a href="javascript:void(0)" data-tag="清新">清新</a>
		   		<a href="javascript:void(0)" data-tag="浪漫">浪漫</a>
		   		<a href="javascript:void(0)" data-tag="性感">性感</a>
		   		<a href="javascript:void(0)" data-tag="伤感">伤感</a>
		   		<a href="javascript:void(0)" data-tag="治愈">治愈</a>
		   		<a href="javascript:void(0)" data-tag="放松">放松</a>
		   		<a href="javascript:void(0)" data-tag="孤独">孤独</a>
		   		<a href="javascript:void(0)" data-tag="感动">感动</a>
		   		<a href="javascript:void(0)" data-tag="兴奋">兴奋</a>
		   		<a href="javascript:void(0)" data-tag="快乐">快乐</a>
		   		<a href="javascript:void(0)" data-tag="安静">安静</a>
		   		<a href="javascript:void(0)" data-tag="思念">思念</a>
		   	</dd>
		</dl>
		<dl>
			<dt>主题</dt>
			<dd>
		   		<a href="javascript:void(0)" data-tag="影视原声">影视原声</a>
		   		<a href="javascript:void(0)" data-tag="ACG">ACG</a>
		   		<a href="javascript:void(0)" data-tag="校园">校园</a>
		   		<a href="javascript:void(0)" data-tag="游戏">游戏</a>
		   		<a href="javascript:void(0)" data-tag="70后">70后</a>
		   		<a href="javascript:void(0)" data-tag="80后">80后</a>
		   		<a href="javascript:void(0)" data-tag="90后">90后</a>
		   		<a href="javascript:void(0)" data-tag="网络歌曲">网络歌曲</a>
		   		<a href="javascript:void(0)" data-tag="KTV">KTV</a>
		   		<a href="javascript:void(0)" data-tag="经典">经典</a>
		   		<a href="javascript:void(0)" data-tag="吉他">吉他</a>
		   		<a href="javascript:void(0)" data-tag="钢琴">钢琴</a>
		   		<a href="javascript:void(0)" data-tag="器乐">器乐</a>
		   		<a href="javascript:void(0)" data-tag="儿童">儿童</a>
		   		<a href="javascript:void(0)" data-tag="榜单">榜单</a>
		   		<a href="javascript:void(0)" data-tag="00后">00后</a>
		   	</dd>
		</dl>
	</div>
	<div class="modal_foot">
		<button class="btn" id="save_tag_btn">保存</button>
	</div>
</div>

	
<script src="static/template/js/jquery-ui.js"></script>
<script src="static/template/js/bootstrap.min.js"></script>
<script src="static/template/js/settingsbox.js"></script>
<script src="static/template/js/farbtastic/farbtastic.js"></script>
<script src="static/template/js/jquery.prettyPhoto.js"></script>
<script src="static/template/js/izotope.js"></script>
<script src="static/template/js/option.js"></script>
<script src="static/js/song-func.js"></script>
<script src="static/js/my-music.js"></script>
</body>
</html>