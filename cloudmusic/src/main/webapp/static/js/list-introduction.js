$(function(){
	changeCollectBtn();
	
	$(".play").click(function(){
		playListSongFromCurrentPage();
	});
	$(".collect").click(function(){
		var userId = $(".userId").html();
		var playlistId = $("#list_introduction").attr("data-playlist-id");
		if (userId == "" || userId == null || userId == undefined){
			alert("请先登录");
		}
		else {
			if ($(".collect").attr("data-collected") == 100){
				$.ajax({
					url : "collectPlaylist",
					data : {
						userId : userId,
						playlistId : playlistId
					},
					success : function(data, status){
						if (data.code == 100){
							window.location.reload(true);
						}
					}
				});
			}
			else {
				$.ajax({
					url : "undoPlaylistCollection", 
					data : {
						playlistId : playlistId
					}, 
					success : function(data, status){
						if (data.code == 100){
							window.location.reload(true);
						}
					}
				});
			}
		}		
	});
});

function changeCollectBtn(){
	var playlistId = $("#list_introduction").attr("data-playlist-id");
	$.ajax({
		url : "isCollection",
		async : false,
		data : {
			playlistId : playlistId
		},
		success : function(data, status){
			//没收藏：100；收藏过：200
			if (data.code == 200){
				$(".collect").html('<i class="icon-folder-open icon-white"></i>&nbsp;取消收藏');
				$(".collect").attr("data-collected", "200");
			}
		}
	});
}