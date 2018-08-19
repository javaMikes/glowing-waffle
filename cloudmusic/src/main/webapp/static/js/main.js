$(function(){	
	//更改导航激活状态
	var $li = $(parent.document).find(".mainNav .nav_li");
	$li.removeClass("active");
	$li.eq(0).addClass("active");
	
	loadHotList();
	loadFavouriteList();
	loadSongTopTenByPlayTimes();
	loadSongTopTenByCollection();
	loadSingerTopTen();
	loadNewSinger();
	
	$(".rs_mainslider_items").click(function(){
		var index = $(this).find(".rs_mainslider_items_active").index();
		if (index == 0){
			window.location.href = "showSingerInfoPage?singerName=Guns N Roses";
		}
		else if (index == 1){
			window.location.href = "showSingerInfoPage?singerName=陈奕迅";
		}
		else{
			window.location.href = "showSingerInfoPage?singerName=beyond";
		}
	});
	
	/*
	$("#img1").click(function(){
		console.log($("#img1").attr("src"));
		window.location.href = "showSingerInfoPage?singerName=陈奕迅";
	});
	$("#img2").click(function(){
		console.log($("#img2").attr("src"));
		window.location.href = "showSingerInfoPage?singerName=beyond";
	});
	$("#img3").click(function(){
		console.log($("#img3").attr("src"));
		window.location.href = "showSingerInfoPage?singerName=枪炮与玫瑰";
	});*/
});

//个人设置
function userUpdate(id){
	console.log(id);
	$.ajax({
		 url: "userUpdate",
        type: "POST",
        data:{
        	id:id
        },
        success: function (data) {
        	if(data == 'success'){
        		window.location.href = "showUserUpdate?id=" + id;
        	}
        	else
        		window.location.href = "main.jsp";
        },
        error: function () {
            
        }
	});	 
}

//签到
function signIn(id){
	console.log(id);
	$.ajax({
		 url: "signIn",
        type: "POST",
        data:{
        	userId:id
        },
        success: function (data) {
        	if(data.code == 100){
//        		window.location.href = "main.jsp" ;
        		//将签到按钮设置为禁用
        		$("#signInButton").attr('disabled',"true");
        		$("#signInButton").text("已签到");
        		//积分加2
        		$("#integral").text("积分：" + (parseInt($("#integral").text().substr(3)) + 2));
        	}
        	else{
        		alert(data.msg);
        	}
        },
        error: function () {
            
        }
	});	 
}

//加载热门歌单
function loadHotList(){
	$.ajax({
		url : "findHotPlaylistTop",
		async : false,
		type : "POST",
		dataType : "json",
		success : function(data, status){
			$.each(data.map.hotPlayListTop, function(index, element){
				var $li = $("#hot_list li:nth("+index+")");
				$li.find(".thumbnail").attr("href", "showPlaylistPage?playlistId="+element.id);
				$li.find("img").attr("src", "showPlaylistImg?playlistId="+element.id);
				$li.find("h3 a").attr("href", "showPlaylistPage?playlistId="+element.id).html(element.listName);
				
				$.ajax({
					url : "findUserNameById",
					type : "POST",
					dataType : "json",
					data:{
			        	userId:element.userId
			        },
					success : function(data, status){
						$li.find("p").html("by&nbsp;&nbsp;" + data.map.userName);
					}
				});
			});
		}
	});
}

function loadFavouriteList(){
	$.ajax({
		url : "findFavoritePlaylistByUserId",
		async : false,
		type : "POST",
		dataType : "json",
		data : {
			userId : $("#favourite_list").attr("data-user-id")
		},
		success : function(data, status){
			$.each(data.map.list, function(index, element){
				var $li = $("#favourite_list li:nth("+(parseInt(index)+1)+")");
				$li.find(".thumbnail").attr("href", "showPlaylistPage?playlistId="+element.id);
				$li.find("img").attr("src", "showPlaylistImg?playlistId="+element.id);
				$li.find("h3 a").attr("href", "showPlaylistPage?playlistId="+element.id).html(element.listName);
				
				$.ajax({
					url : "findUserNameById",
					type : "POST",
					dataType : "json",
					data:{
			        	userId:element.userId
			        },
					success : function(data, status){
						$li.find("p").html("by&nbsp;&nbsp;" + data.map.userName);
					}
				});
			});
			//删除多余推荐
			var length = $("#favourite_list li a[href!='#']").length/2;
			for(var i = 0; i < 4-length; ++i){
				var $li = $("#favourite_list li");
				$li.eq($li.length-1).remove();
			}
		}
	});
}

function loadSongTopTenByPlayTimes() {
	$.ajax({
		url : "selectSongTopTenByPlayTimes",
		async : false,
		success : function(data, status){
			var $tr = $("#billboard_table tbody tr");
			$.each($tr, function(index, element){
				var $a = $(element).find("a").eq(0);
				$a.html(data.map.msg[index].songName);
				$a.prop("href", "showSongPage?songId=" + data.map.msg[index].id);
			});
		}
	});
}

function loadSongTopTenByCollection() {
	$.ajax({
		url : "selectSongTopTenByCollection",
		async : false,
		success : function(data, status){
			var $tr = $("#billboard_table tbody tr");
			$.each($tr, function(index, element){
				var $a = $(element).find("a").eq(1);
				$a.html(data.map.msg[index].songName);
				$a.prop("href", "showSongPage?songId=" + data.map.msg[index].id);
			});
		}
	});
}

function loadSingerTopTen(){
	$.ajax({
		url : "selectSingerTopNum",
		async : false,
		data : {
			topNum : "all"
		},
		success : function(data, status){
			var $tr = $("#billboard_table tbody tr");
			$.each($tr, function(index, element){
				var $a = $(element).find("a").eq(2);
				$a.html(data.map.selectSingerTopNum[index]);
				$a.prop("href", "showSingerInfoPage?singerName=" + data.map.selectSingerTopNum[index]);				
			});
		}
	});
}

function loadNewSinger(){
	$.ajax({
		url : "selectNewestSinger",
		async : false,
		success : function(data, status){
			$.each(data.map.list, function(index, element){
			  	var $figure = $('<figure><img src="showSingerImg?singerId='+element.id+'" alt="photo"></figure>');
			  	var $p = $('<p><b>'+element.singerName+'</b><br>'+element.introduction+'</p>');
			  	var $a = $('<a href="showSingerInfoPage?singerName='+element.singerName+'"></a> ');
			  	var $li = $('<li></li>');
			  	$a.append($figure);
			  	$a.append($p);
			  	$li.append($a);
			  	$("#singer_list_container ul").append($li);
			});
		}
	});
}








