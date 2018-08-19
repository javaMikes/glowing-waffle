package com.cloudmusic.service;

import java.util.List;

import com.cloudmusic.util.Msg;

public interface ITagService {

	/**
	 * 查找出用户最喜欢的标签，按顺序排下来，第一个标签是最经常听的标签，最后一个是最少听的标签<br/>
	 * 实现思想：根据用户听过的歌曲，收藏的歌曲，收藏的歌单，3者进行联合。将标签搜索出来进行频率比较
	 * 
	 * @param userId
	 *            用户ID
	 * @return Msg["list":List<TagName>:标签集合]
	 */
	public Msg findFavoriteTagByUserId(long userId);

	/**
	 * 根据歌曲id搜索出它的标签id
	 * 
	 * @param songId
	 *            歌曲ID
	 * @return
	 */
	public List<Long> findTagNameBySongId(long songId);

	/**
	 * 根据歌单id搜索出它的标签id
	 * 
	 * @param playlistId
	 *            歌曲ID
	 * @return
	 */
	public List<Long> findTagNameByPlaylistId(long playlistId);

	/**
	 * 根据标签id搜索出该标签
	 * 
	 * @param id
	 * @return Msg["tagName":TagName:标签]
	 */
	public Msg selectTagNameByTagId(long id);

	/**
	 * 查找出所有标签
	 * 
	 * @return Msg["list":list{TagName}:标签集合]
	 */
	public Msg selectAllTagName();

	/**
	 * 根据标签类型查找出这个类型的所有标签
	 * 
	 * @param type
	 *            标签类型名字
	 * @return Msg.error时["msg":String:错误信息提示] ; <br/>
	 *         Msg.success时表示成功["list":List{TagName}:该类型的标签集合]
	 */
	public Msg selectTagNameType(String type);

	/**
	 * 根据标签名，搜索出包含该标签的所有歌单
	 * 
	 * @param name
	 *            标签名
	 * @return Msg["list":List{Playlist}:歌单集合，无论有无都会返回]
	 */
	public Msg selectPlaylistByTagName(String name);

	/**
	 * 根据标签名列表，分页搜索出包含这些标签的所有歌单（歌单取这些标签的交集）
	 * 
	 * @param list_tagName
	 *            标签名字符串列表
	 * @param pn
	 *            页码
	 * @return Msg["list":List&lt;Playlist&gt;:歌曲集合]
	 */
	public Msg selectPlaylistPageInfoByTagNameList(List<String> list_tagName, Integer pn);

	/**
	 * 根据标签名，分页搜索出包含该标签的所有歌单
	 *
	 * @param name
	 *            标签名
	 * @return Msg["list":List{Playlist}:分页的歌单集合，无论有无都会返回]
	 */
	public Msg selectPlaylistPageInfoByTagName(String name, Integer pn);

	/**
	 * 根据标签名，搜索出包含该标签的所有歌曲
	 * 
	 * @return Msg["list":List{Song}:歌曲集合，无论有无都会返回一个list]
	 */
	public Msg selectSongByTagName(String name);

	/**
	 * 根据标签名获取标签信息
	 * 
	 * @return Msg["tagName":标签信息]
	 */
	public Msg selectByTagName(String name);
}
