package com.cloudmusic.service;

import java.util.List;

import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.PlaylistExample;
import com.cloudmusic.bean.TagName;
import com.cloudmusic.util.Msg;

public interface IPlaylistService {
	/**
	 * 插入一个新的歌单，
	 * 
	 * @return Msg的map中，有两个键。[“msg”:String:储存提示信息]，[“playlist”:Playlist:为刚插入的playlist对象，其id值为数据库自增的结果]
	 */
	public Msg insertPlaylist(Playlist playlist);

	/**
	 * 新建一个歌单，并且为这个歌单设置标签
	 * 
	 * @param playlist
	 *            歌单
	 * @param tagList
	 *            标签集合
	 * @return Msg.error表示新建歌单失败<br/>
	 *         Msg.success表示成功.["playlist":Playlist:返回一个刚刚新建成功的Playlist对象]
	 */
	public Msg insertPlaylistWithTagName(Playlist playlist, List<TagName> tagList);

	/**
	 * 将歌曲 加入 歌单
	 * 
	 * @param playlistId
	 *            歌单ID
	 * @param songIds
	 *            要加入的歌曲ID数组集合
	 * @return Msg包含的map键。（1）[“msg”：String:提示信息 ]
	 */
	public Msg insertPlaylistWithSong(Long playlistId, List<Long> songIds);

	/**
	 * 根据example来查找歌单
	 * 
	 * @param example
	 *            PlaylistExample的对象
	 * @return Msg包含键名,["list":List<Playlist> 含歌单的集合]
	 */
	public Msg selectPlaylistByExample(PlaylistExample example);

	/**
	 * 寻找热门歌单
	 * 
	 * @return Msg["list":List<Playlist>热门歌单的集合]
	 * 
	 */
	public Msg findHotPlaylist();

	/**
	 * 将歌曲从歌单中删除
	 * 
	 * @param id
	 *            歌单ID
	 * @param songIds
	 *            歌曲ID集合
	 * @return Msg
	 */
	public Msg removeSongFromPlaylistById(long id, List<Long> songIds);

	/*
	 * 某个歌单的播放次数+1
	 */
	public Msg addPlaytimeOfPlaylist(long id);

	/*
	 * 某个歌单的收藏次数+1
	 */
	public Msg addCollectionOfPlaylist(long id);

	/*
	 * 某个歌单的收藏次数-1
	 */
	public Msg subCollectionOfPlaylist(long id);

	/**
	 * 根据歌单ID删除歌单
	 * 
	 * @param id
	 *            歌单ID
	 * @return Msg[]
	 */
	public Msg deletePlaylistById(long id);

	/**
	 * 用户收藏歌单
	 * 
	 * @param userId
	 *            用户ID
	 * @param playlistId
	 *            歌单ID
	 * @return Msg[]
	 */
	public Msg insertPlaylistCollection(long userId, long playlistId);

	/**
	 * 查找某一个用户所收藏的歌单集合
	 * 
	 * @param userId
	 *            用户ID
	 * @return Msg["list":List<Playlist>:他的歌单集合]
	 */
	public Msg selectPlaylistCollectionByUserId(long userId);

	/**
	 * 根据用户查找他可能喜欢的歌单
	 * 
	 * @param userId
	 *            用户ID
	 * @return Msg["list":List<Playlist>:歌单集合]
	 */
	public Msg findFavoritePlaylistByUserId(long userId);

	/**
	 * 给定一个歌单列表，根据热度来进行排序，第一个为最近最火热
	 * 
	 * @param list
	 *            需要排序的歌单集合
	 * @return Msg["list":List{Playlist}:排好序的歌单集合]
	 */
	public Msg sortPlaylistBasisHot(List<Playlist> list);

	/**
	 * 根据歌单ID找出它收纳的所有歌曲
	 * 
	 * @param playlistId
	 *            歌单ID
	 * @return Msg["list":List{Song}:歌曲集合]
	 */
	public Msg selectSongByPlaylist(long playlistId);

	/**
	 * 查询所有的歌单
	 * 
	 * @return Msg[msg:1：存在歌曲，返回list集合；2：不存在歌单，返回提示信息]
	 */
	public Msg selectPlayList(Integer pn);

	/**
	 * 根据歌单id获取歌单信息
	 * 
	 * @param playlistId
	 * @return Msg["msg" : error:提示信息；success：歌单信息]
	 */
	public Msg selectPlayListByPlayListId(long playlistId);

	/**
	 * 查询歌单，按照播放次数降序排序
	 * 
	 * @return
	 */
	public Msg selectPlayListDescByPlayTimes();

	/**
	 * 查询歌单，按照收藏次数降序排序
	 * 
	 * @return
	 */
	public Msg selectPlayListDescByCollection();

	/**
	 * 按照歌手的歌播放次数进行降序排序
	 * 
	 * @return Msg["list" : 按歌手的歌曲播放次数进行降序排序的歌手集合]
	 */
	public Msg selectSingerDescByPlayTimes();

	/**
	 * 根据多个标签，推荐适合这些标签的歌单,歌单是降序排列的，第一个是最符合标签的歌单，以此类推
	 * 
	 * @param list_tag
	 *            标签列表
	 * @return Msg["list":List&lt;Playlist&gt;:歌单集合]
	 */
	public Msg findNicePlaylistByTagName(List<TagName> list_tag);

	/**
	 * 根据用户id查找出该用户创建的所有歌单
	 * 
	 * @param userId
	 * @return Msg.success["list":List&lt;Playlist&gt;:歌单集合]
	 */
	public Msg selectPlaylistByUserId(long userId);

	/**
	 * 根据歌单名字模糊搜索出歌单
	 * 
	 * @param name
	 *            歌单名字
	 * @return success["list":List{Playlist}:歌单集合]
	 */
	public Msg selectPlaylistByLikeName(String name);

	/**
	 * 用户取消收藏歌单
	 * 
	 * @param userId
	 *            用户id
	 * @param playlistId
	 *            歌单id
	 * @return 成功success<br/>
	 * 		失败error
	 */
	public Msg undoPlaylistCollection(long userId, long playlistId);

	/**
	 * 修改歌单的图片路径
	 * 
	 * @param playlistId
	 * @param img_path
	 * @return 成功success,map["playlist":Playlist:刚修改成功的歌单对象]<br/>
	 *         失败error
	 */
	public Msg updatePlaylistImgPath(long playlistId, String img_path);

	/**
	 * 更新歌单信息，根据playlist对象里的主键,有选择的改
	 * 
	 * @param playlist
	 *            需要修改的歌单对象
	 * @return 成功sucess，map["playlist":Playlist:刚修改成功的歌单对象]<br/>
	 *         失败error
	 */
	public Msg updatePlaylistByPrimaryKey(Playlist playlist);

	/**
	 * 修改歌单的标签（会把原有已经设置的标签覆盖）
	 * 
	 * @param playlistId
	 *            歌单id
	 * @param list_tagName
	 *            标签名
	 * @return 成功success，失败error
	 */
	public Msg updatePlaylistTagName(long playlistId, List<String> list_tagName);
}
