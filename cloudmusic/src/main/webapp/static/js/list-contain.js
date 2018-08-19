$(function(){
	loadContainSong();
	bindIconEvent();
	
	$("#choose_create_playlist .head_close").click(function(){
		$("#choose_create_playlist").hide();
	});
	$(document).on("click", ".select_box", function(){
		var songId = $(this).attr("data-song-id");
		var playlistId = $(this).attr("data-playlist-id");
		var songIds = new Array();
		songIds.push(songId);
		
		$.ajax({
			url : "insertPlaylistWithSong",
			traditional : true,
			data : {
				playlistId : playlistId,
				songIds : songIds
			},
			success : function(data, status){
				if (data.code == 100) {
					alert("收藏成功");
					$("#choose_create_playlist").hide();
				}
				else {
					alert("歌单已包含该歌曲");
					$("#choose_create_playlist").hide();
				}
			}
		});
	});
	$(document).on("click", "#list_detail_table .icon-trash", function(){
		var songName = $(this).siblings(".song_info").eq(0).attr("data-name");
		var songId = $(this).siblings(".song_info").eq(0).attr("data-id");
		var playlistId = $("#list_contain").attr("data-playlist-id");
		if (confirm("确认要从歌单中移除歌曲“"+songName+"”？")){
			$.ajax({
				url : "removeSong",
				data : {
					songId : songId,
					playlistId : playlistId
				},
				success : function(data, status){
					window.location.reload(true);
				}
			});
		}		
	});
});

function loadContainSong(){
	var playlistId = $("#list_contain").attr("data-playlist-id");
	var singerId = $("#list_contain").attr("data-singer-id");
	var userId = $("#list_contain").attr("data-user-id");
	var recommendId = $("#list_contain").attr("data-recommend-id");
	
	//加载歌单歌曲列表
	if (playlistId != "" && playlistId != undefined && playlistId != null){
		$.ajax({
			url : "findSongByPlayListId",
			async : false,
			type: "POST",
			dataType : "json",
			data : {
				playlistId : playlistId
			},
			success : function(data, status){
				$("#list_contain h2").html("歌曲列表");
				$("#list_contain thead").append('<tr><th></th><th>歌曲标题</th><th>时长</th><th>歌手</th></tr>');
				$.each(data.map.songList, function(index, element){
					var $indexTd = $('<td>'+parseInt(index+1)+'</td>');
					var $songNameTd = $('<td><div class="td_text"><a href="showSongPage?songId='+element.id+'">'+element.songName+'</a></div></td>');
					var $timeTd = $('<td><div class="td_time">'+element.musicTime+'</div></td>');
					var $btnTd = $('<div class="td_btn"><i class="icon-play icon" title="播放"></i>&nbsp;<i class="icon-plus icon" title="添加到播放列表"></i>&nbsp;<i class="icon-folder-open icon" title="收藏"></i>&nbsp;<i class="icon-arrow-down icon" title="下载"></i>&nbsp;</div>');
					var $songHiddenInfo = $('<i class="song_info icon" data-id="'+element.id+'" data-name="'+element.songName+'" data-singer="'+element.singer+'" data-img="'+element.imgPath+'" data-src="'+element.path+'" data-time="'+element.musicTime+'"></i>');
					var $singerTd = $('<td><div class="td_text"><a href="showSingerInfoPage?singerName='+element.singer+'">'+element.singer+'</a></div></td>');
					var $tr = $('<tr></tr>');
					$songHiddenInfo.appendTo($btnTd);
					$btnTd.appendTo($timeTd);
					$tr.append($indexTd);
					$tr.append($songNameTd);
					$tr.append($timeTd);
					$tr.append($singerTd);
					$tr.appendTo("#list_detail_table tbody");
				});
				if (window.location.href.indexOf("showMyMusicPage?") != -1 && $("#list_introduction .collect").length == 0){
					var $tdBtn = $("#list_detail_table .td_btn");
					$tdBtn.append('<i class="icon-trash icon" title="从歌单中移除"></i>&nbsp;');
				}
			},
			error : function(){
				
			}
		});
	}
	//加载歌手歌曲列表
	else if(singerId != "" && singerId != undefined && singerId != null){
		$.ajax({
			url : "findSongBySingerId",
			async : false,
			type: "POST",
			dataType : "json",
			data : {
				singerId : singerId
			},
			success : function(data, status){
				if (data.code == 100) {
					$("#list_contain h2").remove();
					$("#list_contain thead").remove();
					$.each(data.map.list, function(index, element){
						var $indexTd = $('<td>'+parseInt(index+1)+'</td>');
						var $songNameTd = $('<td><a href="showSongPage?songId='+element.id+'">'+element.songName+'</a></td>');
						var $timeTd = $('<td><div class="td_time">'+element.musicTime+'</div></td>');
						var $btnTd = $('<div class="td_btn"><i class="icon-play icon" title="播放"></i>&nbsp;<i class="icon-plus icon" title="添加到播放列表"></i>&nbsp;<i class="icon-folder-open icon" title="收藏"></i>&nbsp;<i class="icon-arrow-down icon" title="下载"></i>&nbsp;</div>');
						var $songHiddenInfo = $('<i class="song_info icon" data-id="'+element.id+'" data-name="'+element.songName+'" data-singer="'+element.singer+'" data-img="'+element.imgPath+'" data-src="'+element.path+'" data-time="'+element.musicTime+'"></i>');
						var $tr = $('<tr></tr>');
						$songHiddenInfo.appendTo($btnTd);
						$btnTd.appendTo($timeTd);
						$tr.append($indexTd);
						$tr.append($songNameTd);
						$tr.append($timeTd);
						$tr.appendTo("#list_detail_table tbody");
					});
				}
			},
			error : function(){
				
			}
		});
	}
	//加载个人歌曲排行列表
	else if(userId != "" && userId != undefined && userId != null){
		$.ajax({
			url : "findRankSong",
			async : false,
			type: "POST",
			dataType : "json",
			data : {
				userId : userId
			},
			success : function(data, status){
				$("#list_contain h2").html("听歌排行");
				$("#list_contain thead").remove();
				$.each(data.map.rankSonglist, function(index, element){
					var $indexTd = $('<td>'+parseInt(index+1)+'</td>');
					var $songNameTd = $('<td><a href="showSongPage?songId='+element.id+'">'+element.songName+'</a></td>');
					var $timesTd = $('<td>'+element.playTimes+'</td>');
					var $btnTd = $('<div class="td_btn"><i class="icon-play icon" title="播放"></i>&nbsp;<i class="icon-plus icon" title="添加到播放列表"></i>&nbsp;<i class="icon-folder-open icon" title="收藏"></i>&nbsp;<i class="icon-arrow-down icon" title="下载"></i>&nbsp;</div>');
					var $songHiddenInfo = $('<i class="song_info icon" data-id="'+element.id+'" data-name="'+element.songName+'" data-singer="'+element.singer+'" data-img="'+element.imgPath+'" data-src="'+element.path+'" data-time="'+element.musicTime+'"></i>');
					var $tr = $('<tr></tr>');
					$songHiddenInfo.appendTo($btnTd);
					$btnTd.appendTo($songNameTd);
					$tr.append($indexTd);
					$tr.append($songNameTd);
					$tr.append($timesTd);
					$tr.appendTo("#list_detail_table tbody");
				});
			},
			error : function(){
				
			}
		});
	}
	//加载登录用户推荐歌曲列表（必须放最后判断）
	else{
		$.ajax({
			url : "findFavoriteSongByUserId",
			async : false,
			type: "POST",
			dataType : "json",
			data : {
				userId : recommendId
			},
			success : function(data, status){
				$("#list_contain h2").html("推荐歌曲列表");
				$("#list_contain thead").append('<tr><th></th><th>歌曲标题</th><th>时长</th><th>歌手</th></tr>');
				$.each(data.map.list, function(index, element){
					var $indexTd = $('<td>'+parseInt(index+1)+'</td>');
					var $songNameTd = $('<td><div class="td_text"><a href="showSongPage?songId='+element.id+'">'+element.songName+'</a></div></td>');
					var $timeTd = $('<td><div class="td_time">'+element.musicTime+'</div></td>');
					var $btnTd = $('<div class="td_btn"><i class="icon-play icon" title="播放"></i>&nbsp;<i class="icon-plus icon" title="添加到播放列表"></i>&nbsp;<i class="icon-folder-open icon" title="收藏"></i>&nbsp;<i class="icon-arrow-down icon" title="下载"></i>&nbsp;</div>');
					var $songHiddenInfo = $('<i class="song_info icon" data-id="'+element.id+'" data-name="'+element.songName+'" data-singer="'+element.singer+'" data-img="'+element.imgPath+'" data-src="'+element.path+'" data-time="'+element.musicTime+'"></i>');
					var $singerTd = $('<td><div class="td_text"><a href="showSingerInfoPage?singerName='+element.singer+'">'+element.singer+'</a></div></td>');
					var $tr = $('<tr></tr>');
					$songHiddenInfo.appendTo($btnTd);
					$btnTd.appendTo($timeTd);
					$tr.append($indexTd);
					$tr.append($songNameTd);
					$tr.append($timeTd);
					$tr.append($singerTd);
					$tr.appendTo("#list_detail_table tbody");
				});
			},
			error : function(){
				
			}
		});
	}
}

function bindIconEvent(){
	//绑定tr鼠标悬停事件	
	$(document).on("mouseover", "#list_detail_table tr", function(){
		$(this).find(".td_time").css("visibility", "hidden");
		$(this).find(".td_btn").css("display", "inline-block");
	});
	
	$(document).on("mouseout", "#list_detail_table tr", function(){
		$(this).find(".td_time").css("visibility", "visible");
		$(this).find(".td_btn").css("display", "none");
	});
	
	//绑定icon点击事件	
	$(document).on("click", "#list_detail_table .icon-play", function(){
		var $info = $(this).siblings(".song_info").eq(0);
		var id = $info.attr("data-id");
		var name = $info.attr("data-name");
		var singer = $info.attr("data-singer");
		var img = $info.attr("data-img").replace(/\\/g, "/");
		var src = $info.attr("data-src").replace(/\\/g, "/");
		var time = $info.attr("data-time");
		var lrc = getSongById(id).lyrics.replace(/\\n/g, "\n");
		var oneSong = createSong(id, name, singer, img, src, lrc, time);
		var songList = getSongList();
		saveSong(oneSong, songList, 1);
	});
	
	$(document).on("click", "#list_detail_table .icon-plus", function(){
		var $info = $(this).siblings(".song_info").eq(0);
		var id = $info.attr("data-id");
		var name = $info.attr("data-name");
		var singer = $info.attr("data-singer");
		var img = $info.attr("data-img").replace(/\\/g, "/");
		var src = $info.attr("data-src").replace(/\\/g, "/");
		var time = $info.attr("data-time");
		var lrc = getSongById(id).lyrics.replace(/\\n/g, "\n");
		var oneSong = createSong(id, name, singer, img, src, lrc, time);
		var songList = getSongList();
		saveSong(oneSong, songList, 2);
	});
	
	$(document).on("click", "#list_detail_table .icon-folder-open", function(){
		var userId = $(".userId").html();
		var songId = $(this).siblings(".song_info").eq(0).attr("data-id");
		if (userId == "" || userId == null || userId == undefined){
			alert("请先登录");
		}
		else{
			$("#choose_create_playlist .modal_body").empty();
			$.ajax({
				url : "getCreatePlaylist",
				data : {
					userId : userId
				},
				success : function(data, status){				
					$.each(data.map.list, function(index, element){
						$("#choose_create_playlist .modal_body").append('<div href="javascript:void(0)" class="select_box" data-song-id="'+songId+'" data-playlist-id="'+element.id+'"><img src="showPlaylistImg?playlistId='+element.id+'" alt="" />&nbsp;<span>'+element.listName+'</span></div><hr>');
					});
					$("#choose_create_playlist").show();
				}
			});
		}		
	});
	
	$(document).on("click", "#list_detail_table .icon-arrow-down", function(){
		var $info = $(this).siblings(".song_info").eq(0);
		console.log($(".userId").html());
		if($(".userId").html() == undefined || $(".userId").html() == "" || $(".userId").html() == null){
			alert("请先登录");
		}
		else{
			//获取该用户的积分
			$.ajax({
				url : "getIntegralByUserId",
				data : {
					userId : $(".userId").html()
				},
				success : function(data){
					//获取该歌曲的积分
					$.ajax({
						url : "selectSongBySongId",
						data : {
							songId : $info.attr("data-id")
						},
						success : function(songData, status){	
							
							if(data.map.integral < songData.map.song.integral){
								alert("您的积分不足") 
							}else{
								if (confirm("将扣除" + songData.map.song.integral + "积分，确认下载？")){
									window.location.href = "download?songId=" + $info.attr("data-id");
								}	
							}
						}
					});
				}
			});
		}		 
	});
}














