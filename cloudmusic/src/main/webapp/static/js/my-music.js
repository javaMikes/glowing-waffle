$(function(){
	//更改导航激活状态
	var $li = $(parent.document).find(".mainNav .nav_li");
	$li.removeClass("active");
	$li.eq(2).addClass("active");
	
	//调整iframe高度
	$(".accordion-heading").click(function(){
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
	
	//歌单选择效果
	$(".list_little_box").mouseover(function(){
		$(this).find(".list_icon").css("display", "block");
		if (!$(this).hasClass("selected_list")) {
			$(this).css("background-color", "#f5f5f5");
		}	
	});
	$(".list_little_box").mouseout(function(){
		$(this).find(".list_icon").css("display", "none");
		if (!$(this).hasClass("selected_list")) {
			$(this).css("background-color", "white");
		}	
	});
	$(".list_little_box").click(function(){
		$(this).parents("#my_music_container_left .list_little_box").removeClass("selected_list");
		$(this).addClass("selected_list");
	});

	//新建歌单
	$("#new_playlist, #new_playlist_modal .modal_head .head_close").click(function(){
		$("#new_playlist_form b").html("");	
		$("#new_playlist_modal").toggle();		
		return false;
	});
	$("#new_playlist_btn").click(function(){
		var userId = $("#my_music_container").attr("data-user-id");
		var playlistName = $("#new_playlist_modal #list_name").val();
		if (playlistName == "" || playlistName == null || playlistName.length > 20) {
			show_validate_msg($("#new_playlist_modal #list_name"), "歌单名称应在0~10字之间");
		}
		else {
			$.ajax({
				url : "createPlaylist",
				data : {
					userId : userId,
					playlistName : playlistName
				},
				success : function(data, status) {
					window.location.href = "showMyMusicPage?userId=" + userId + "&playlistId=" + data.map.playlist.id;
				},
				error: function() {
					alert();
				}
			});
		}
	});
	
	//修改歌单
	$(".list_little_box .icon-pencil").click(function(){
		$("#list_detail_wrapper").css("display", "none");
		$("#update_playlist_wrapper").css("display", "block");
		$("#update_playlist_wrapper").attr("data-playlist-id", $(this).attr("data-playlist-id"));
		var playlistId = $("#update_playlist_wrapper").attr("data-playlist-id");
		
		$.ajax({
			url : "findPlaylistById",
			data : {
				playlistId : playlistId
			},
			success : function(data, status){
				$("#update_playlist_form #list_name").val(data.map.msg.listName);
				$("#update_playlist_form #profile").val(data.map.msg.profile);
				$.each(data.map.tagList, function(index, element){
					$("#playlist_tag").append('<a class="tag" href="javascript:void(0)" data-tag='+element.tagName+'>'+element.tagName+'</a>');
					$("#select_tag_modal .modal_body a[data-tag="+element.tagName+"]").addClass("tag_selected");
				});
			}
		});
		
		return false;
	});
	$("#select_tag, #select_tag_modal .modal_head .head_close, #save_tag_btn").click(function(){
		$("#select_tag_modal").toggle();
		var $tags = $("#select_tag_modal .modal_body .tag_selected");
		$("#update_playlist_form #playlist_tag").empty();
		$.each($tags, function(index, element){
			var $tag = $('<a class="tag" href="javascript:void(0)" data-tag='+$(element).attr("data-tag")+'>'+$(element).attr("data-tag")+'</a>');
			$("#update_playlist_form #playlist_tag").append($tag);
		});
	});
	$("#select_tag_modal a").click(function(){
		$(this).toggleClass("tag_selected");
		var count = $(this).parents("#select_tag_modal .modal_body").eq(0).find(".tag_selected").length;
		if (count > 3) {
			alert("标签选择不能超过3个");
			$(this).toggleClass("tag_selected");
		}
	});
	$("#update_playlist_wrapper #img_page_redirect").click(function(){
		var playlistId = $("#update_playlist_wrapper").attr("data-playlist-id");
		window.location.href = "showUploadImgPage?playlistId=" + playlistId;
	});
	$("#update_playlist_btn").click(function(){
		$("#update_playlist_form b").html("");
		var listName = $("#update_playlist_form #list_name").val();
		var profile = $("#update_playlist_form #profile").val();
		var tagName = "";
		$.each($("#update_playlist_form #playlist_tag .tag"), function(index, element){
			if (index != 0) {
				tagName += ",";
			}
			tagName += $(element).attr("data-tag");
		});
		
		if (listName == "" || listName == null || listName.length > 10) {
			show_validate_msg($("#update_playlist_form #list_name"), "歌单名称应在0~10字之间");
			return ;
		}
		if (profile.length > 200) {
			show_validate_msg($("#update_playlist_form #profile"), "歌单介绍应在200字内");		
			return ;
		}
		$.ajax({
			url : "updatePlaylist",
			data : {
				id : $("#update_playlist_wrapper").attr("data-playlist-id"),
				listName : listName,
				profile : profile,
				tagName : tagName
			},
			success : function(data, status){
				if (data.code == 100) {
					window.location.href = "showMyMusicPage?userId=" + $("#my_music_container").attr("data-user-id");
				}
			}
		});
		
	});
	
	//删除歌单
	$(".list_little_box .icon-trash").click(function(){
		var userId = $("#my_music_container").attr("data-user-id");
		var playlistId = $(this).attr("data-playlist-id");
		var playlistName = $(this).attr("data-playlist-name");
		if (confirm("确定要删除歌单“"+playlistName+"”吗？")) {
			$.ajax({
				url : "deleteCreatePlaylist",
				data : {
					playlistId : playlistId,
				},
				success : function(data, status){
					if (data.code == 100) {
						window.location.href = "showMyMusicPage?userId=" + userId;
					}
				}
			});
		}
		
		return false;
	});
	
	triggerClick();
});

function triggerClick() {
	var href = window.location.href;
	var index = href.indexOf("&playlistId=");
	if (index != -1) {
		var playlistId = href.substr(index+12);
		var $playlist = $("#accordion").find(".list_little_box[data-playlist-id="+playlistId+"]").eq(0);
		$playlist.parents(".accordion-body").eq(0).siblings(".accordion-heading").eq(0).addClass("accordion-heading-active");
		$playlist.parents(".accordion-body").eq(0).addClass("in");
		$playlist.trigger("click");		
	}
	else {
		var $playlist = $("#accordion").find(".list_little_box").eq(0);
		$playlist.parents(".accordion-body").eq(0).siblings(".accordion-heading").eq(0).addClass("accordion-heading-active");
		$playlist.parents(".accordion-body").eq(0).addClass("in");
		$playlist.trigger("click");
	}
}

//表单提示消息
function show_validate_msg(element, msg){
	$(element).siblings("b").eq(0).html(msg);
}
