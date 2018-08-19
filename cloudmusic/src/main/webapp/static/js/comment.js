$(function(){
	
	
	loadComment(1);
	
	//点击评论框后先判断是否已登录
	$(".my_comment_content").click(function(){
		console.log("userId:" + $(".userId").html());
		if($(".userId").html() == ""){
			alert("请先登录");
		}
	});
	
	$("#comment").on("click", ".comment_reply", function(){
		//点击评论框后先判断是否已登录
		if($(".userId").html() == ""){
			alert("请先登录");
		}
		else{
			$(".comment_box b").html("");
			if ($(this).parent().siblings(".reply_input_box").css("display") == "block") {
				$(this).parent().siblings(".reply_input_box").css("display", "none");
				return ;
			}
			$(".reply_input_box").css("display", "none");
			$(this).parent().siblings(".reply_input_box").css("display", "block");
		}
	});
	$("#comment").on("click", ".comment_report", function(){
		if($(".userId").html() == ""){
			alert("请先登录");
		}
		else {
			var commentId = $(this).parents(".comment_box").eq(0).attr("data-comment-id");
			var url;
			if ($(".songId").html() == undefined){
				url = "reportPlaylistComment";
			}
			else {
				url = "reportSongComment";
			}
			
			$.ajax({
				url : url,
				data : {
					commentId : commentId
				},
				success : function(data, status){
					if (data.code == 100) {
						alert("举报成功");
					}
				}
			});
		}
	});
	
	//添加歌单评论
	$("#comment_sava_btn").click(function(){
		//点击评论后先判断是否已登录
		if($(".userId").html() == ""){
			alert("请先登录");
		}else{
			addComment();
		}
	});
	//添加评论回复
	$("#comment").on("click", ".reply_save_btn", function(){
		addReply(this);
	});
});

//添加歌单/歌曲评论
function addComment(){
	var playlistId = $("#comment").attr("data-playlist-id");
	var songId = $("#comment").attr("data-song-id");
	
	var content = $(".my_comment_content").val();
	if (content.length == 0 || content.length > 200) {
		show_validate_msg($(".my_comment_content"), "评论字数应在0~200字内");
		return ;
	}
	
	//添加歌单的评论
	if (playlistId != "" && playlistId != undefined && playlistId != null){
		$.ajax({
			url : "addPlayListComment",
			data : {
				userId : $(".userId").html(),
				playlistId : playlistId,
				content : content
			},
			success : function(data, status){
				//window.location.reload(true);
				loadComment(1);
			}
		});
	}
	//添加歌曲的评论
	else{
		$.ajax({
			url : "addSongComment",
			data : {
				userId : $(".userId").html(),
				songId : songId,
				content : content
			},
			success : function(data, status){
				//window.location.reload(true);
				loadComment(1);
			}
		});
	}	
}

//添加歌单/歌曲回复
function addReply(replyBtn){
	var playlistId = $("#comment").attr("data-playlist-id");
	var songId = $("#comment").attr("data-song-id");
	
	var content = $(replyBtn).siblings("textarea").eq(0).val();
	if (content.length == 0 || content.length > 200) {
		show_validate_msg($(replyBtn).siblings("textarea"), "回复字数应在0~200字内");
		return ;
	}
	
	//添加歌单的评论
	if (playlistId != "" && playlistId != undefined && playlistId != null){
		$.ajax({
			url : "replyComment",
			data : {
				userId : $(".userId").html(),
				commentId : $(replyBtn).parents(".comment_box").eq(0).attr("data-comment-id"),
				content : content
			},
			success : function(data, status){
				//window.location.reload(true);
				loadComment(1);
			}
		});
	}
	//添加歌曲的回复
	else {
		$.ajax({
			url : "replySongComment",
			data : {
				userId : $(".userId").html(),
				commentId : $(replyBtn).parents(".comment_box").eq(0).attr("data-comment-id"),
				content : content
			},
			success : function(data, status){
				//window.location.reload(true);
				loadComment(1);
			}
		});
	}
}

//加载歌单/歌曲评论
function loadComment(pn){
	var playlistId = $("#comment").attr("data-playlist-id");
	var songId = $("#comment").attr("data-song-id");
	
	//加载歌单的评论
	if (playlistId != "" && playlistId != undefined && playlistId != null){
		$.ajax({
			url : "showCommentByPlaylistId",
			async : false,
			data : {
				playlistId : playlistId,
				pn : pn
			},
			success : function(data, status){
				//清空table
				$(".comment_box").empty();
				$(".commnet_counts").html(data.map.playlistCommentPageInfo.total);
				//获取评论列表
				var commentList = data.map.playlistCommentPageInfo.list;
				//获取回复列表
				var replyList = data.map.playlistReplyCommentTotalList;
				//显示评论内容
				$.each(commentList, function(index, element){
					var time = new Date(element.commentTime);
					var hours = (""+time.getHours()).length==1 ? "0"+time.getHours() : time.getHours();
					var minutes = (""+time.getMinutes()).length==1 ? "0"+time.getMinutes() : time.getMinutes();
					var $commentHead = $('<div class="comment_head"></div>').append('<img src="seekExperts?userId='+element.userId+'" class="img-rounded">');
					var $commentContentBox = $('<div class="comment_content_box"></div>').append('<a href="showPersonPage?userId='+element.userId+'" class="comment_user user">'+element.userName+'</a>：<span class="comment_content">'+element.content+'</span>');
					var $commentTimeBox = $('<div class="comment_time_box"></div>').append('<span class="comment_time">'+(parseInt(time.getYear())+1900)+'-'+(parseInt(time.getMonth())+1)+'-'+time.getDate()+'&nbsp;'+hours+':'+minutes+'</span><a href="javascript:void(0)" class="comment_reply">回复</a>');
					var $replyInputBox = $('<div class="reply_input_box"></div>').append('<textarea class="reply_input" placeholder="回复"></textarea><b class="colored span10 contact_page_form_result"></b><button type="button" class="btn btn-color reply_save_btn">回复</button>');
					var $commentDetail = $('<div class="comment_detail"></div>');
					if (element.isReported != 2) {
						$commentTimeBox.append('<a href="javascript:void(0)" class="comment_report">举报</a>');
					}
					$commentDetail.append($commentContentBox);
					$commentDetail.append($commentTimeBox);
					$commentDetail.append($replyInputBox);
					var $commentBox = $('<div class="comment_box" data-comment-id="'+element.id+'"></div>');
					$commentBox.append($commentHead);
					$commentBox.append($commentDetail);
					$commentBox.append('<hr>');
					$(".comment_pagination").before($commentBox);
				});
				//显示回复内容
				$.each(replyList, function(index, element){
					var $replyContentBox = $('<div class="reply_content_box"></div>').append('<a href="showPersonPage?userId='+element.userId+'" class="reply_user user">'+element.userName+'</a>：<span class="reply_content">'+element.content+'</span>');
					$(".comment_box[data-comment-id="+element.parentId+"]").find(".comment_content_box").eq(0).after($replyContentBox);
				});		
				//解析并显示分页条信息,若没有记录,则不显示
				if(data.map.playlistCommentPageInfo.total > 0){
					build_comment_nav(data.map.playlistCommentPageInfo);
				}
				
				//增加页面高度，解决回复框被挡住的问题
				var height = $(".container").css("height");
				var val = height.slice(0, -2);
				$(".container").css("height", (parseInt(val)+250)+"px");
			}
		});
	}
	//加载歌曲的评论
	else {
		$.ajax({
			url : "showCommentBySongId",
			data : {
				songId : songId,
				pn : pn
			},
			async : false,
			success : function(data, status){
				//清空table
				$(".comment_box").empty();
				$(".commnet_counts").html(data.map.songtCommentPageInfo.total);
				//获取评论列表
				var commentList = data.map.songtCommentPageInfo.list;
				//获取回复列表
				var replyList = data.map.songReplyCommentTotalList;
				//显示评论内容
				$.each(commentList, function(index, element){
					var time = new Date(element.commentTime);
					var hours = (""+time.getHours()).length==1 ? "0"+time.getHours() : time.getHours();
					var minutes = (""+time.getMinutes()).length==1 ? "0"+time.getMinutes() : time.getMinutes();
					var $commentHead = $('<div class="comment_head"></div>').append('<img src="seekExperts?userId='+element.userId+'" class="img-rounded">');
					var $commentContentBox = $('<div class="comment_content_box"></div>').append('<a href="showPersonPage?userId='+element.userId+'" class="comment_user user">'+element.userName+'</a>：<span class="comment_content">'+element.content+'</span>');
					var $commentTimeBox = $('<div class="comment_time_box"></div>').append('<span class="comment_time">'+(parseInt(time.getYear())+1900)+'-'+(parseInt(time.getMonth())+1)+'-'+time.getDate()+'&nbsp;'+hours+':'+minutes+'</span><a href="javascript:void(0)" class="comment_reply">回复</a>');
					var $replyInputBox = $('<div class="reply_input_box"></div>').append('<textarea class="reply_input" placeholder="回复"></textarea><b class="colored span10 contact_page_form_result"></b><button type="button" class="btn btn-color reply_save_btn">回复</button>');
					var $commentDetail = $('<div class="comment_detail"></div>');
					if (element.isReported != 2) {
						$commentTimeBox.append('<a href="javascript:void(0)" class="comment_report">举报</a>');
					}
					$commentDetail.append($commentContentBox);
					$commentDetail.append($commentTimeBox);
					$commentDetail.append($replyInputBox);
					var $commentBox = $('<div class="comment_box" data-comment-id="'+element.id+'"></div>');
					$commentBox.append($commentHead);
					$commentBox.append($commentDetail);
					$commentBox.append('<hr>');
					$(".comment_pagination").before($commentBox);
				});
				//显示回复内容
				$.each(replyList, function(index, element){
					var $replyContentBox = $('<div class="reply_content_box"></div>').append('<a href="showPersonPage?userId='+element.userId+'" class="reply_user user">'+element.userName+'</a>：<span class="reply_content">'+element.content+'</span>');
					$(".comment_box[data-comment-id="+element.parentId+"]").find(".comment_content_box").eq(0).after($replyContentBox);
				});		
				//解析并显示分页条信息,若没有记录,则不显示
				if(data.map.songtCommentPageInfo.total > 0){
					build_comment_nav(data.map.songtCommentPageInfo);
				}
				
				//增加页面高度，解决回复框被挡住的问题
				var height = $(".container").css("height");
				var val = height.slice(0, -2);
				$(".container").css("height", (parseInt(val)+250)+"px");
			}
		});
	}	
}

//表单提示消息
function show_validate_msg(element, msg){
	$(element).siblings("b").eq(0).html(msg);
}

// 歌单/歌曲 页码导航栏
function build_comment_nav(msg){
	//清空nav
	$("#page_nav").empty();
	
	//ul
	var ul = $("<ul></ul>");
	
	//首页
	var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","javascript:void(0)"));
	//上一页
	var prePagelLi = $("<li></li>").append($("<a></a>").append("&laquo;").attr("href","javascript:void(0)"));
	
	//判断是否为首页
	if(!msg.hasPreviousPage){
		//禁用li
		firstPageLi.addClass("disable");
		prePagelLi.addClass("disable");
	}else{
		firstPageLi.click(function(){
			loadComment(1);
		});
		prePagelLi.click(function(){
			loadComment(msg.pageNum - 1);
		});
	}
	
	ul.append(firstPageLi);
	ul.append(prePagelLi);
	
	//页码
	$.each(msg.navigatepageNums,function(index,item){
		var numLi = $("<li></li>").append($("<a></a>").append(item).attr("href","javascript:void(0)"));
		if(msg.pageNum == item){
			numLi.addClass("active");
		}
		numLi.click(function(){
			loadComment(item);
		});
		ul.append(numLi);
	});
	
	//下一页
	var nextPagelLi = $("<li></li>").append($("<a></a>").append("&raquo;").attr("href","javascript:void(0)"));
	//末页
	var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","javascript:void(0)"));
	
	//判断是否为末页
	if(!msg.hasNextPage){//禁用li
		firstPageLi.addClass("disable");
		nextPagelLi.addClass("disable");
	}else{
		nextPagelLi.click(function(){
			loadComment(msg.pageNum + 1);
		});
		lastPageLi.click(function(){
			loadComment(msg.pages);
		});
	}
	
	ul.append(nextPagelLi);
	ul.append(lastPageLi);
	ul.appendTo("#page_nav");
}















