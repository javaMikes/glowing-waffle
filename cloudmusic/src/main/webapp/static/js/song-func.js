//创建歌曲对象
function createSong(id, name, singer, img, src, lrc, time) {
	var o = new Object();
	o.id = id;
	o.name = name;
	o.singer = singer;
	o.img = img;
	o.src = src;
	o.lrc = lrc;
	o.time = time;
	return o;
}

//获取localStorage的歌曲信息，并返回一个包含歌曲json对象的数组
function getSongList(){
	var str = localStorage.getItem("song_list");
	if (str == null || str == ""){
		return null;
	}
	else{
		var arr = str.split("---");
		var songList = new Array();
		for (var i = 0; i < arr.length; ++i){
			songList.push(JSON.parse(arr[i]));
		}
		return songList;
	}
}

//保存歌曲信息到localStorage并添加到播放列表，并自动播放该歌曲
function saveSong(oneSong, songList, mode) {
	/*
	 * mode：1，添加并播放
	 * mode：2，添加
	*/
	
	var songStr = '{'+	//键和值都必须用双引号
		'"id":"'+oneSong.id+'",'+
		'"name":"'+oneSong.name+'",'+
		'"singer":"'+oneSong.singer+'",'+
		'"img":"'+oneSong.img+'",'+
		'"src":"'+oneSong.src+'",'+
		'"time":"'+oneSong.time+'"'+
	'}';
	
	if (songList == null){
		localStorage.setItem("song_list", songStr);
		//更新播放列表
		parent.player.addSong(toSongArray(oneSong));
		if (mode == 1) {
			//自动切换歌曲
			parent.player.play(parent.player.getDisplayList(true).songs.length);
		}		
	}
	else{
		if (isSongExist(oneSong.id, songList)){
			alert("歌曲已存在播放列表中");
			return ;
		}
		localStorage.setItem("song_list", localStorage.getItem("song_list")+"---"+songStr);
		//更新播放列表
		parent.player.addSong(toSongArray(oneSong));
		if (mode == 1) {
			//自动切换歌曲
			parent.player.play(parent.player.getDisplayList(true).songs.length);
		}
	}
}

//根据id查询歌曲在列表中是否存在
function isSongExist(songId, songList) {
	for (var i = 0; i < songList.length; ++i) {
		if (songId == songList[i].id){
			return true;
		}
	}
	return false;
}

//将歌转化为数组的形式，以便添加到播放列表
function toSongArray(oneSong){
	var arr = new Array();
	arr.push(oneSong);
	return arr;
}

//获取歌曲信息
function getSongById(id){
	var songInfo;
	$.ajax({
		url : "playMusic",
		type : "POST",
		dataType : "json",
		async : false,
		data : {
			id : id
		},
		success : function(data, status){
			songInfo = data.map.songInfo;
		}
	});
	return songInfo;
}

//加载本地存储的播放列表信息
function loadSong(player){
	var str = localStorage.getItem("song_list");
	if (str == null || str == ""){
		return null;
	}
	else{
		var arr = str.split("---");
		var song;
		var songList = new Array();
		for (var i = 0; i < arr.length; ++i){
			song = JSON.parse(arr[i]);
			//在这里补充lrc,song.lrc = lrc
			var lrc = getSongById(song.id).lyrics.replace(/\\n/g, "\n");
			song.lrc = lrc;
			songList.push(song);
		}
		parent.player.addSong(songList);
	}
}

//根据下标删除localStorage的歌曲并更新
function refreshLocalStorageSongList(index){
	var listStr = "";
	var newList = localStorage.getItem("song_list").split("---");
	newList.splice(index, 1);
	
	$.each(newList, function(index, element){
		listStr += element.toString() + "---";
	});
	localStorage.setItem("song_list", listStr.slice(0, -3));
}

//播放当前页面所有歌曲（歌单/歌手）
function playListSongFromCurrentPage(){
	//讲歌单歌曲信息保存到localStorage
	localStorage.removeItem("song_list");
	var $info = $("#list_detail_table").find(".song_info");
	var songArrSavedToLocalStorage = "";
	$.each($info, function(index, element){
		var id = $(this).attr("data-id");
		var name = $(this).attr("data-name");
		var singer = $(this).attr("data-singer");
		var img = $(this).attr("data-img").replace(/\\/g, "/");
		var src = $(this).attr("data-src").replace(/\\/g, "/");
		var time = $(this).attr("data-time");
		var lrc = getSongById(id).lyrics.replace(/\\n/g, "\n");
		var songStr = '{'+	//键和值都必须用双引号
			'"id":"'+id+'",'+
			'"name":"'+name+'",'+
			'"singer":"'+singer+'",'+
			'"img":"'+img+'",'+
			'"src":"'+src+'",'+
			'"time":"'+time+'"'+
		'}';
		songArrSavedToLocalStorage += songStr + "---";		
	});
	songArrSavedToLocalStorage = songArrSavedToLocalStorage.slice(0, -3);
	localStorage.setItem("song_list", songArrSavedToLocalStorage);

	parent.player.dom.list.html("");
	parent.player.list[0].splice(0, parent.player.list[0].length);
	loadSong(parent.player);
	parent.player.play(0);

	var playlistId = $("#list_introduction").attr("data-playlist-id");
	if (playlistId != "" && playlistId != null && playlistId != undefined){
		$.ajax({
			url : "addPlaytimeOfPlaylist",
			data : {
				playlistId : playlistId
			}
		});
	}	
}
