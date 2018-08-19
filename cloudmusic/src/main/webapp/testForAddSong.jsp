<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>

<p>不同的按钮是一首不同的歌</p>
<p>重复摁会提示“歌曲已存在”，因为摁下第一次会把信息保存在本地存储</p>
<p>查看本地存储：开发者工具->Application->Local Storage</p>
<p>第一次加载列表为空，因为本地存储也为空</p>
<p>第二次加载能播，但是会没歌词，因为还没去访问controller</p>

<button class="add" type="button" data-id="123" data-name="一睁眼" data-singer="沈玮琦" 
	data-img="http://imgcache.qq.com/music/photo/album_500/51/500_albumpic_1796451_0.jpg" 
	data-src="http://ws.stream.qqmusic.qq.com/200254836.m4a?fromtag=46"
	data-lrc="[ti:一睁眼]\n[ar:沈玮琦]\n[al:一睁眼]\n[by:]\n[offset:0]\n[00:02.65]一睁眼&#32;&#45;&#32;沈玮琦\n[00:03.36]词：许嵩\n[00:03.49]曲：许嵩\n[00:08.13]一睁眼\n[00:08.99]新的一天\n[00:10.84]跳出了多梦的一夜\n[00:13.71]可能旅店的枕垫\n[00:15.78]太软了一些\n[00:19.26]洗好脸走到窗边\n[00:22.08]有风轻轻掀动纱帘\n[00:24.90]心也\n[00:25.90]心也软了一些\n[00:30.58]一睁眼\n[00:31.56]新的一天\n[00:33.36]计划太多会添纠结\n[00:36.24]要不要试着接受\n[00:38.41]快乐一时是一时的哲学\n[00:41.92]没有你在我身边\n[00:44.72]我的幽默渐渐不见\n[00:47.56]雨斜斜&#32;歌绵绵\n[00:50.42]闭关修炼\n[00:52.95]昨晚的梦里面\n[00:55.63]楼台杏花琴弦\n[00:58.47]场面有些古典\n[01:01.02]谁飞扬了裙边\n[01:03.84]你抱着我转圈\n[01:06.40]在南方的雨天\n[01:09.44]怎么雨水都甜\n[01:12.19]怎么回忆都咸\n[01:15.43]昨晚的梦里面\n[01:18.29]时光倒回从前\n[01:21.07]心动还能重演\n[01:23.62]是爱情在身边\n[01:26.44]你送我的项链\n[01:29.23]戴上叫做想念\n[01:31.97]怎么没说再见\n[01:34.92]还没好好告别\n[01:36.95]已睁眼\n[02:06.76]一睁眼\n[02:07.61]新的一天\n[02:09.46]跳出了多梦的一夜\n[02:12.23]可能旅店的枕垫\n[02:14.31]太软了一些\n[02:17.89]洗好脸走到窗边\n[02:20.69]有风轻轻掀动纱帘\n[02:23.43]心也\n[02:24.44]心也软了一些\n[02:29.19]一睁眼\n[02:30.23]新的一天\n[02:32.08]计划太多会添纠结\n[02:34.81]要不要试着接受\n[02:36.96]快乐一时是一时的哲学\n[02:40.49]没有你在我身边\n[02:43.28]我的幽默渐渐不见\n[02:46.16]雨斜斜&#32;歌绵绵\n[02:48.92]闭关修炼\n[02:51.46]昨晚的梦里面\n[02:54.20]楼台杏花琴弦\n[02:57.01]场面有些古典\n[02:59.78]谁飞扬了裙边\n[03:02.38]你抱着我转圈\n[03:05.19]在南方的雨天\n[03:08.07]怎么雨水都甜\n[03:10.84]怎么回忆都咸\n[03:13.98]昨晚的梦里面\n[03:16.76]时光倒回从前\n[03:19.59]心动还能重演\n[03:22.09]是爱情在身边\n[03:24.94]你送我的项链\n[03:27.60]戴上叫做想念\n[03:30.60]怎么没说再见\n[03:33.38]还没好好告别\n[03:35.48]已睁眼\n[03:39.94]一睁眼\n[03:51.23]一睁眼\n[03:57.34]新的一天"
	data-time="05:05">
	add1
</button>

<button class="add" type="button" data-id="456" data-name="江湖" data-singer="不知道" 
	data-img="http://imgcache.qq.com/music/photo/album_500/65/500_albumpic_1588165_0.jpg" 
	data-src="http://ws.stream.qqmusic.qq.com/108344251.m4a?fromtag=46"
	data-lrc="[ti:江湖（网剧《画江湖之不良人》主题曲）][ar:许嵩][al:江湖][by:许嵩][offset:0][00:00.08]江湖 （《画江湖之不良人》超级网剧主题曲） - 许嵩[00:10.00]词：许嵩[00:11.00]曲：许嵩[00:43.55]今夕是何夕[00:46.76]晚风过花庭[00:49.10]飘零 予人乐后飘零[00:56.41]故地是何地[00:59.44]死生不复回[01:02.12]热血 风干在旧恨里[01:08.16]衣锦夜行[01:10.20]当一生尘埃落定[01:14.19]飞鸽来急[01:16.56]那落款沾染血迹[01:21.07]夜半嘱小徒复信[01:24.99]言师已故去[01:27.88]星云沉默江湖里[01:34.83]孤雁飞去 红颜来相许[01:40.24]待到酒清醒[01:42.61]她无影 原来是梦里[01:47.52]恩怨散去[01:50.65]刀剑已归隐[01:53.07]敬属江上雨[01:55.45]寒舟里 我独饮[02:24.99]衣锦夜行[02:27.06]当一生尘埃落定[02:31.03]飞鸽来急[02:33.43]那落款沾染血迹[02:37.81]夜半嘱小徒复信[02:41.81]言师已故去[02:44.65]星云沉默江湖里[02:51.64]孤雁飞去 红颜来相许[02:57.11]待到酒清醒[02:59.35]她无影 原来是梦里[03:04.28]恩怨散去[03:07.48]刀剑已归隐[03:09.81]敬属江上雨[03:12.27]寒舟里 我独饮[03:36.42]孤雁飞去 红颜来相许[03:41.71]待到酒清醒[03:44.16]她无影 原来是梦里[03:48.96]恩怨散去[03:52.22]刀剑已归隐[03:54.51]敬属江上雨[03:56.97]寒舟里 我独饮[04:04.73]我独饮"
	data-time="03:25">
	add2
</button>

<button class="add" type="button" data-id="789" data-name="宇宙之大" data-singer="许嵩" 
	data-img="http://imgcache.qq.com/music/photo/album_500/35/500_albumpic_1779435_0.jpg" 
	data-src="http://ws.stream.qqmusic.qq.com/7214467.m4a?fromtag=46"
	data-lrc="[ar:许嵩]\n[ti:平行宇宙]\n[00:00.45]作曲 : 许嵩\n[00:01.76]作词 : 许嵩\n[00:03.58]我梦见我轻盈自由的腾空\n[00:09.70]随后画面切到我背着你遨游\n[00:15.89]你眼睛开始闪烁点点星光\n[00:22.60]可能是美梦来的太突然了吧\n[00:28.11]那时候在一起的时间很多\n[00:34.26]只不过珍惜的意义还没搞懂\n[00:40.55]虽然说 如今已经分开很久\n[00:46.92]有时候 还是不经意想你的笑容\n[00:55.78]深爱过 所以没有再联络\n[01:02.05]不回头 因为勉强的笑很难受\n[01:08.01]深爱过 真心感谢你陪我度过\n[01:14.50]那几年 苦中有甜的生活\n[01:29.62]床头柜 躺着一本老旧相册\n[01:35.79]也就是 闲极的时候才翻一翻\n[01:41.91]还养着你走时留下的小狗\n[01:48.54]长大后 它心事重重不太活泼\n[01:54.21]这条路我们没能走到最后\n[02:00.42]朋友说 是个意外的意料之中\n[02:06.48]有时相信在某个平行的宇宙\n[02:13.08]你的爱还一如既往陪在我左右\n[02:21.86]深爱过 所以没有再联络\n[02:28.03]不回头 因为勉强的笑很难受\n[02:34.24]深爱过 真心感谢你陪我度过那几年\n[02:43.60]深爱过 所以没有再联络\n[02:49.66]不回头 因为勉强的笑很难受\n[02:55.68]深爱过 真心感谢你陪我度过\n[03:01.90]那几年 苦中有甜的生活\n[03:08.18]多年后再想起你\n[03:12.28]镜子里一副流泪的笑容"
	data-time="03:25">
	add3
</button>

<button class="add" type="button" data-id="111" data-name="不语" data-singer="怎么又是许嵩" 
	data-img="http://imgcache.qq.com/music/photo/album_500/28/500_albumpic_1289528_0.jpg" 
	data-src="http://ws.stream.qqmusic.qq.com/105637173.m4a?fromtag=46"
	data-lrc="[ti:不语（《不速之客》电影主题曲）][ar:许嵩][al:不语][by:][offset:0][00:00.09]不语 - 许嵩[00:00.77]（不速之客》电影主题曲）[00:01.25]词：火星电台[00:01.47]曲：火星电台[00:02.42]编曲：火星电台[00:22.08]冬夜 多漫长[00:31.26]迷路的孩子[00:34.78]可记得家的模样[00:40.97]有雪 有挂念[00:49.48]落幕的时候 都喝醉[00:58.65]开心 不语[01:03.66]伤心 不语[01:08.49]晴朗 不语[01:14.76]阴天 不语[01:18.08]坚强 不语[01:22.72]脆弱 不语[01:27.51]此时不语 更来不及[01:39.93]我在哪里[01:44.43]我在哪里[01:49.23]我在哪里[01:54.03]我怎么能回去[01:58.88]我在哪里[02:03.46]我在哪里[02:08.35]这是哪里[02:13.08]我怎么能回去[02:19.32]我想 还是选择沉默不语[02:46.90]我在哪里[02:52.00]我在哪里[02:56.73]我在哪里[03:01.61]我怎么能回去[03:06.58]我在哪里[03:11.55]我在哪里[03:16.46]这是哪里[03:21.41]我怎么能回去[03:27.37]我想 还是选择沉默不语"
	data-time="03:25">
	add3
</button>

<p class="info"></p>





<!-- 公共player部分hi -->
<%@ include file="WEB-INF/views/player.jsp" %>

<script src="static/js/main.js"></script>
<script type="text/javascript">

$(function(){
	$(".add").click(function(){
		var id = $(this).attr("data-id");
		var name = $(this).attr("data-name");
		var singer = $(this).attr("data-singer");
		var img = $(this).attr("data-img");
		var src = $(this).attr("data-src");
		var lrc = $(this).attr("data-lrc").replace(/\\n/g, "\n");
		var time = $(this).attr("data-time");
		var oneSong = createSong(id, name, singer, img, src, lrc, time);
		var songList = getSongList();
		saveSong(oneSong, songList);
	});
});

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
		var arr = str.split("-");
		var songList = new Array();
		for (var i = 0; i < arr.length; ++i){
			songList.push(JSON.parse(arr[i]));
		}
		return songList;
	}
}

//保存歌曲信息到localStorage并添加到播放列表，并自动播放该歌曲
function saveSong(oneSong, songList) {
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
		player.addSong(toSongArray(oneSong));
		//自动切换歌曲
		player.play(player.getDisplayList(true).songs.length);
	}
	else{
		if (isSongExist(oneSong.id, songList)){
			alert("歌曲已存在");
			return ;
		}
		localStorage.setItem("song_list", localStorage.getItem("song_list")+"-"+songStr);
		//更新播放列表
		player.addSong(toSongArray(oneSong));
		//自动切换歌曲
		player.play(player.getDisplayList(true).songs.length);
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

</script>
</body>
</html>