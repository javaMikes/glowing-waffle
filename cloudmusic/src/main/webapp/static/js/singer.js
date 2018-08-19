$(function(){	
	$.each($(".song_box .song_photo"), function(index, element){
		$(element).attr("src", encodeURI($(element).attr("src")));
	});
	
	$(".nav_block").click(function(){
		var target = $(this).attr("data-target");
		$(target).siblings(".nav_content").css("display", "none");
		$(target).css("display", "block");
		$(this).siblings(".nav_block").removeClass("nav_selected");
		$(this).addClass("nav_selected");
	});	
	
	$("#play_singer_song_btn").click(function(){
		playListSongFromCurrentPage();
	});
});