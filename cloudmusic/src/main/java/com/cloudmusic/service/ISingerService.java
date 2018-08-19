package com.cloudmusic.service;

import com.cloudmusic.util.Msg;

public interface ISingerService {

	/**
	 * 根据歌手姓名获取歌手信息
	 * @param singer_name
	 * @return Msg["list" : 歌手信息]
	 */
	public Msg selectSingerBySingerName(String singer_name);
	
	/**
	 * 根据歌手名字模糊搜索出歌手
	 * @param name
	 * @return Msg["list":List{Singer}:歌手集合]
	 */
	public Msg selectSingerByLikeName(String name);
	
	/**
	 * 根据歌手id获取歌手信息
	 * @param id 歌手ID
	 * @return Msg["singer":Singer:歌手信息]
	 */
	public Msg selectSingerById(long id);
	
	/**
	 * 获取新晋歌手，最新的3个
	 * @return Msg["list":List{Singer}:歌手集合]
	 */
	public Msg selectNewestSinger();
}
