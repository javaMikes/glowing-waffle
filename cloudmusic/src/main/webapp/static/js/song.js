$(function(){
	loadSongLrc();
	
	$(".song_img_pic").attr("src", encodeURI($(".song_img_pic").attr("src")));
	$.each($(".song_box .song_photo"), function(index, element){
		$(element).attr("src", encodeURI($(element).attr("src")));
	});
	
	$("#show_lrc").click(function(){		
		$(".lrc").css("height", "auto");
		$(this).css("display", "none");
		$("#hide_lrc").css("display", "inline");
		
		//加高度差h
		var h = parseInt($(".lrc").css("height")) - 300;
		var height = parseInt($(".container").css("height"));
		$(".container").css("height", (height+h)+"px");
		
		function iframeHeight(){
			var ifm= parent.document.getElementById("iframepage");   
			var subWeb = parent.document.frames ? parent.document.frames["iframepage"].document : ifm.contentDocument;  
				//alert("加载前："+ifm.height);
				ifm.height = 0;
			 if(ifm != null && subWeb != null) {
				//alert("scrollHeight="+subWeb.body.scrollHeight);
			   ifm.height = subWeb.body.scrollHeight;
			 // alert("加载后："+ifm.height);
			   ifm.width = subWeb.body.scrollWidth;
			}    
		}		
		setTimeout(function(){iframeHeight()},500);
	});
	$("#hide_lrc").click(function(){
		//减高度差h
		var h = parseInt($(".lrc").css("height")) - 300;
		
		$(".lrc").css("height", "300px");
		$(this).css("display", "none");
		$("#show_lrc").css("display", "inline");	
	
		var height = parseInt($(".container").css("height"));
		$(".container").css("height", (height-h)+"px");
		
		function iframeHeight(){
			var ifm= parent.document.getElementById("iframepage");   
			var subWeb = parent.document.frames ? parent.document.frames["iframepage"].document : ifm.contentDocument;  
				//alert("加载前："+ifm.height);
				ifm.height = 0;
			 if(ifm != null && subWeb != null) {
				//alert("scrollHeight="+subWeb.body.scrollHeight);
			   ifm.height = subWeb.body.scrollHeight;
			 // alert("加载后："+ifm.height);
			   ifm.width = subWeb.body.scrollWidth;
			}    
		}		
		setTimeout(function(){iframeHeight()},500);
	});
	
	$("#song_introduction .play").click(function(){
		var info = getSongById($(".songId").html());
		var id = info.id;
		var name = info.songName;
		var singer = info.singer;
		var img = info.imgPath.replace(/\\/g, "/");
		var src = info.path.replace(/\\/g, "/");
		var time = info.musicTime;
		console.log("musictime:" + time);
		var lrc = info.lyrics.replace(/\\n/g, "\n");
		var oneSong = createSong(id, name, singer, img, src, lrc, time);
		var songList = getSongList();
		saveSong(oneSong, songList, 1);
	});
	
	$("#song_introduction .collect").click(function(){
		var userId = $(".userId").html();
		var songId = $(".songId").html();
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
	});
	
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
})

function loadSongLrc(){
	$.ajax({
		url : "showSongLrc?",
		async : false,
		data : {
			songId : $(".songId").html()
		},
		success : function(data, status) {
			$(".lrc").html(data.map.lrcInfo.replace(/\\n/g, "<br>"));
		}
	});
}