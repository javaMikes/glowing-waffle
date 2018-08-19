package com.cloudmusic.service;

import java.util.List;

import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.TagName;
// import com.cloudmusic.bean.Song;
import com.cloudmusic.util.Msg;

public interface ISongService {

	/**
	 * 上传歌曲
	 *
	 * @param song
	 * @return
	 */
	public Msg uploadSong(Song song);

	/**
	 * 通过用户id查询用户收听过的歌曲
	 *
	 * @param userId
	 * @return Msg["list" : List<Song>收听过的歌曲列表]
	 */
	public Msg selectSongListenedByUserId(long userId);

	/**
	 * 通过用户id查询用户收藏过的歌曲
	 *
	 * @param userId
	 * @return Msg["list":List<Song>:用户歌曲收藏集合]
	 */
	public Msg selectSongCollectedByUserId(long userId);

	/**
	 * 某首歌曲播放次数加1
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "提示信息"]
	 */
	public Msg addOnePlaytimeOfSong(long userId, long songId);

	/**
	 * 某用户收藏某首歌曲
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "提示信息"]
	 */
	public Msg addCollectionOfSong(long userId, long songId);

	/**
	 * 某用户取消收藏某首歌曲
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "提示信息"]
	 */
	public Msg subCollectionOfSong(long userId, long songId);

	/**
	 * 根据歌曲id搜索该歌曲被播放的次数
	 *
	 * @param songId
	 * @return Msg(包含两个键，"msg"：提示信息； "sum" ： 播放次数)
	 */
	public Msg selectTimesPlayOfSong(long songId);

	/**
	 * 根据用户id，歌曲id，判断某个用户是否收听过某个歌曲
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg" : true(收听过)；false(未收听过)]
	 */
	public Msg isListenedTheSong(long userId, long songId);

	/**
	 * 根据用户id，歌曲id，判断某个用户是否收藏某首歌曲
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg" : true(已收藏)；false(未收藏)]
	 */
	public Msg isCollectTheSong(long userId, long songId);

	/**
	 * 根据用户id将其听过的歌曲按次数多少降序排序
	 *
	 * @param userId
	 * @return
	 */
	public Msg rankSongListenTimes(long userId);

	/**
	 * 根据歌手搜索歌曲
	 *
	 * @param singer
	 * @return Msg包含两个键[msg : "提示信息"]; ["list" : List<Song>歌曲列表]
	 */
	public Msg selectSongBySinger(String singer);

	/**
	 * 根据歌名模糊搜索歌曲
	 *
	 * @param songName
	 * @return Msg包含两个键[msg : "提示信息"]; ["list" : List<Song>歌曲列表]
	 */
	public Msg selectSongBySongName(String songName);

	/**
	 * 获取热门歌曲
	 *
	 * @return Msg[list : List<Song>热门歌曲集合]
	 */
	public Msg getHotSongs();

	/**
	 * 获取热门歌手
	 *
	 * @return Msg["list" : List<String> 热门歌手集合]
	 */
	public Msg getHotSingers();

	/**
	 * 根据歌曲ID搜索出一首歌
	 *
	 * @param id
	 * @return 有歌就是Msg.success map["song":Song:歌曲对象]<br/>
	 *         没有歌就是Msg.error
	 */
	public Msg selectSongBySongId(Long id);

	/**
	 * 根据用户id，查找出他可能喜欢的歌曲，并按顺序依次排序下来，返回一个最大size为24的list
	 *
	 * @param userId
	 *            用户id
	 * @return Msg.success map["list":List{Song}:长度为0到24之间的list]
	 */
	public Msg findFavoriteSongByUserId(Long userId);

	/**
	 * 查询歌单，按照播放次数降序排序
	 * 
	 * @return
	 */
	public Msg selectSongDescByPlayTimes();

	/**
	 * 查询歌单，按照收藏次数降序排序
	 * 
	 * @return
	 */
	public Msg selectSongDescByCollection();
	
	/**
	 * 根据歌曲名，模糊搜索出歌曲
	 * @param name
	 * @return success["list":List{Song}:歌曲集合]
	 */
	public Msg selectSongByLikeName(String name);
	
	/**
	 * 根据多个标签，推荐适合这些标签的歌曲
	 * @param list_tag 标签列表
	 * @return Msg["list":List&lt;Song&gt;:歌曲集合]
	 */
	public Msg findNiceSongByTagName(List<TagName> list_tag);
	
	/**
	 * 根据歌手名字，得出该歌手的所有歌曲，并按热度降序排序
	 * @param singerName 歌手名字
	 * @return Msg["list":List{Song}:歌曲集合]
	 */
	public Msg selectSingerSongAndHotSort(String singerName);
	
	/**
	 * 为歌曲设置标签（会覆盖原来设置好的标签）
	 * @param songId 歌曲id
	 * @param list_tag 标签名集合
	 * @return Msg.success or Msg.error
	 */
	public Msg updateSongTagByTagNameList(long songId, List<String> list_tag);
}
