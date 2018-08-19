$(function(){
	//更改导航激活状态
	var $li = $(parent.document).find(".mainNav .nav_li");
	$li.removeClass("active");
	$li.eq(1).addClass("active");
	
	loadSelectedTag();
	
	$("#select_tag").click(function(){
		$("#tag_box").toggle();
	});
	
	$("#tag_box dd a").click(function(){
		$(this).toggleClass("tag_selected");
		if ($("#tag_box dd .tag_selected").length > 3) {
			$(this).toggleClass("tag_selected");
//			alert("最多只能选择3个标签 : (");
			$("#login_msg_box .box_body").html("最多只能选择3个标签");
       	 	$("#login_msg_box").fadeToggle("fast");
			return ;
		}
		var tagStr = "";
		$.each($("#tag_box dd .tag_selected"), function(index, element){
			tagStr += $(this).attr("data-tag") + ",";
		});
		tagStr = tagStr.slice(0, -1);
		window.location.href = "showPlaylist?tagName=" + tagStr;
		//根据复选标签查询歌单
		/*$.ajax({
			url : "showPlaylist",
			data : {
				tagName : tagStr
			},
			success : function(data, status){
				
			}
		});*/
	});
});

function loadSelectedTag(){
	var tagStr = $("#tag_box").attr("data-selected");
	if (tagStr.length > 0) {
		var tagArr = $("#tag_box").attr("data-selected").split(",");
		$.each(tagArr, function(index, element){
			$("#tag_box dd a[data-tag="+element+"]").addClass("tag_selected");
		});
	}
}







































