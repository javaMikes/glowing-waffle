$(function(){
	$(document).on("mouseover", ".mp-list tr", function(){
		$(this).find("i").css("display", "inline-block");
	});
	$(document).on("mouseout", ".mp-list tr", function(){
		$(this).find("i").css("display", "none");
	});
	$(".mp-list").on("click", "i", function(event){
		var index = $(this).parents("tr").index();	//待删除的歌曲tr相对于整个列表的下标
		refreshLocalStorageSongList(index);
		$(this).parents("tr").remove();
		player.list[0].splice(index, 1);
		if (player.getCurrentSong() == index){
			player.play(index);
		}		
		event.stopPropagation();
	});
})