package com.cloudmusic.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudmusic.bean.Listen;
import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.PlaylistCollectionExample;
import com.cloudmusic.bean.PlaylistCollectionKey;
import com.cloudmusic.bean.PlaylistExample;
import com.cloudmusic.bean.PlaylistExample.Criteria;
import com.cloudmusic.bean.PlaylistTagExample;
import com.cloudmusic.bean.PlaylistTagKey;
import com.cloudmusic.bean.ReceiveExample;
import com.cloudmusic.bean.ReceiveKey;
import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.SongExample;
import com.cloudmusic.bean.TagName;
import com.cloudmusic.mapper.ListenMapper;
import com.cloudmusic.mapper.PlaylistCollectionMapper;
import com.cloudmusic.mapper.PlaylistMapper;
import com.cloudmusic.mapper.PlaylistTagMapper;
import com.cloudmusic.mapper.ReceiveMapper;
import com.cloudmusic.mapper.SongMapper;
import com.cloudmusic.mapper.UserMapper;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ITagService;
import com.cloudmusic.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class PlaylistServiceImpl implements IPlaylistService {
	@Autowired
	PlaylistMapper playlistMapper; // 歌单Dao

	@Autowired
	ListenMapper listenMapper;

	@Autowired
	ReceiveMapper receiveMapper; // 收纳Dao

	@Autowired
	PlaylistCollectionMapper playlistCollectionMapper; // 歌单收藏Dao

	@Autowired
	PlaylistTagMapper playlistTagMapper;

	@Autowired
	SqlSessionFactory sqlSessionFactory; // 事务处理

	@Autowired
	ITagService tagService;// 标签服务类

	@Autowired
	SongMapper songMapper;

	@Autowired
	UserMapper userMapper;

	/**
	 * 插入一个新的歌单，
	 *
	 * @return Msg的map中，有两个键。“msg”储存提示信息，“playlist”为刚插入的playlist对象，其id值为数据库自增的结果
	 */
	public Msg insertPlaylist(Playlist playlist) {
		int num = playlistMapper.insert(playlist);
		if (num > 0) {
			Msg msg = Msg.success();
			msg.add("msg", "新增歌单成功");
			msg.add("playlist", playlist);
			return msg;
		}
		return Msg.error().add("msg", "插入歌单失败");
	}

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
	public Msg insertPlaylistWithTagName(Playlist playlist, List<TagName> tagList) {

		if (tagList.size() == 0)
			return Msg.error();

		// 涉及多表一致性的操作时，使用事务处理手段
		SqlSession sqlSession = sqlSessionFactory.openSession(false);
		try {
			// int num = playlistMapper.insert(playlist);
			// if (num <= 0) {
			// return Msg.error();
			// }

			long id = playlist.getId();
			for (int i = 0; i < tagList.size(); i++) {
				PlaylistTagKey key = new PlaylistTagKey();
				key.setPlaylistId(id);
				key.setTagId(tagList.get(i).getId());
				int num2 = playlistTagMapper.insert(key);
				if (num2 <= 0)
					throw new Exception("为歌单设置标签过程中出错，事务回滚");
			}
			return Msg.success().add("playlist", playlist);

		} catch (Exception e) {
			sqlSession.rollback();

		} finally {
			sqlSession.close();
		}

		return Msg.error();
	}

	/**
	 * 将歌曲 加入 歌单
	 *
	 * @param playlistId
	 *            歌单ID
	 * @param songIds
	 *            要加入的歌曲ID数组集合
	 * @return Msg包含的map键。（1）“msg”：提示信息
	 */
	public Msg insertPlaylistWithSong(Long playlistId, List<Long> songIds) {
		SqlSession sqlSession = sqlSessionFactory.openSession(false);
		try {

			for (int i = 0; i < songIds.size(); i++) {
				ReceiveKey receive = new ReceiveKey();
				receive.setPlaylistId(playlistId);
				receive.setSongId(songIds.get(i));
				receiveMapper.insert(receive);
				
				//对应歌曲的收藏次数+1
				Song song = songMapper.selectByPrimaryKey(songIds.get(i));
				long times = song.getCollection();
				times++;
				song.setCollection(times);
				songMapper.updateByPrimaryKey(song);
			}

			sqlSession.commit();
			return Msg.success().add("msg", "成功插入" + songIds.size() + "条数据");
		} catch (Exception e) {
			sqlSession.rollback();
			return Msg.error().add("msg", "插入歌单为" + playlistId + "时发生错误");
		} finally {
			sqlSession.close();
		}

	}

	/**
	 * 根据example来查找歌单
	 *
	 * @param example
	 *            PlaylistExample的对象
	 * @return Msg包含键名,"list" 含歌单的集合
	 */
	public Msg selectPlaylistByExample(PlaylistExample example) {
		List<Playlist> list = playlistMapper.selectByExample(example);
		if (list == null) {
			return Msg.error();
		}
		return Msg.success().add("list", list);
	}

	/**
	 * 寻找热门歌单
	 *
	 * @return Msg["list":热门歌单的集合]
	 *
	 */
	public Msg findHotPlaylist() {

		List<Playlist> list = playlistMapper.selectByExample(null);

		Collections.sort(list, hotComparator);

		return Msg.success().add("list", list);
	}

	private Comparator hotComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			Playlist p1 = (Playlist) o1;
			Playlist p2 = (Playlist) o2;
			long num1 = p1.getPlayTimes() + p1.getCollection();
			long num2 = p2.getPlayTimes() + p2.getCollection();

			if (num1 < num2)
				return 1;
			else if (num1 == num2)
				return 0;
			return -1;
		}
	};

	/**
	 * 将歌曲从歌单中删除
	 *
	 * @param id
	 *            歌单ID
	 * @param songIds
	 *            歌曲ID集合
	 * @return Msg
	 */
	public Msg removeSongFromPlaylistById(long id, List<Long> songIds) {
		SqlSession sqlSession = sqlSessionFactory.openSession(false);
		try {

			for (int i = 0; i < songIds.size(); i++) {
				ReceiveKey receive = new ReceiveKey();
				receive.setPlaylistId(id);
				receive.setSongId(songIds.get(i));
				receiveMapper.deleteByPrimaryKey(receive);
				
				Song song = songMapper.selectByPrimaryKey(songIds.get(i));
				long times = song.getCollection();
				if(times > 0)
					times--;
				song.setCollection(times);
				songMapper.updateByPrimaryKey(song);
			}

			sqlSession.commit();
			return Msg.success().add("msg", "成功删除" + songIds.size() + "条数据");
		} catch (Exception e) {
			sqlSession.rollback();
			return Msg.error().add("msg", "歌单ID为" + id + "，在删除某些歌曲时发生错误");
		} finally {
			sqlSession.close();
		}
	}

	/*
	 * 某个歌单的播放次数+1
	 *
	 * @param id 歌单ID
	 *
	 * @return Msg["msg":操作信息]
	 */
	public Msg addPlaytimeOfPlaylist(long id) {
		Playlist playlist = playlistMapper.selectByPrimaryKey(id);
		if (playlist == null)
			return Msg.error().add("msg", "没有这个歌单");

		playlist.setPlayTimes(playlist.getPlayTimes() + 1);
		int num = playlistMapper.updateByPrimaryKeySelective(playlist);
		if (num > 0)
			return Msg.success().add("msg", "成功");

		return Msg.error().add("msg", "更新歌单数据库表时失败");
	}

	/*
	 * 某个歌单的收藏次数+1
	 *
	 * @param id 歌单ID
	 *
	 * @return Msg["msg":操作信息]
	 */
	public Msg addCollectionOfPlaylist(long id) {
		Playlist playlist = playlistMapper.selectByPrimaryKey(id);
		if (playlist == null)
			return Msg.error().add("msg", "没有这个歌单");

		playlist.setCollection(playlist.getCollection() + 1);
		int num = playlistMapper.updateByPrimaryKeySelective(playlist);
		if (num > 0)
			return Msg.success().add("msg", "成功");

		return Msg.error().add("msg", "更新歌单数据库表时失败");
	}

	/*
	 * 某个歌单的收藏次数-1
	 *
	 * @param id 歌单ID
	 *
	 * @return Msg["msg":操作信息]
	 */
	public Msg subCollectionOfPlaylist(long id) {
		Playlist playlist = playlistMapper.selectByPrimaryKey(id);
		if (playlist == null)
			return Msg.error().add("msg", "没有这个歌单");

		playlist.setCollection(playlist.getCollection() - 1);
		int num = playlistMapper.updateByPrimaryKeySelective(playlist);
		if (num > 0)
			return Msg.success().add("msg", "成功");

		return Msg.error().add("msg", "更新歌单数据库表时失败");
	}

	/**
	 * 根据歌单ID删除歌单
	 *
	 * @param id
	 *            歌单ID
	 * @return Msg[]
	 */
	public Msg deletePlaylistById(long id) {
		return (playlistMapper.deleteByPrimaryKey(id) > 0) ? Msg.success() : Msg.error();
	}

	/**
	 * 用户收藏歌单
	 *
	 * @param userId
	 *            用户ID
	 * @param playlistId
	 *            歌单ID
	 * @return Msg[]
	 */
	public Msg insertPlaylistCollection(long userId, long playlistId) {

		PlaylistCollectionKey record = new PlaylistCollectionKey();
		record.setUserId(userId);
		record.setPlaylistId(playlistId);
		int num = playlistCollectionMapper.insert(record);
		if (num > 0) {
			// 歌单收藏数+1
			this.addCollectionOfPlaylist(playlistId);
		}
		return (num > 0) ? Msg.success() : Msg.error();
	}

	/**
	 * 查找某一个用户所收藏的歌单集合
	 *
	 * @param userId
	 *            用户ID
	 * @return Msg["list":List<Playlist>:他的歌单集合]
	 */
	public Msg selectPlaylistCollectionByUserId(long userId) {
		PlaylistCollectionExample example = new PlaylistCollectionExample();
		com.cloudmusic.bean.PlaylistCollectionExample.Criteria c = example.createCriteria();
		c.andUserIdEqualTo(userId);
		List<PlaylistCollectionKey> list = playlistCollectionMapper.selectByExample(example);

		if (list.size() == 0) {
			return Msg.success().add("list", new ArrayList<Playlist>());
		}

		List<Long> listPlaylistId = new ArrayList<Long>();
		for (int i = 0; i < list.size(); i++) {

			listPlaylistId.add(list.get(i).getPlaylistId());
		}

		PlaylistExample example2 = new PlaylistExample();
		Criteria c2 = example2.createCriteria();
		c2.andIdIn(listPlaylistId);

		List<Playlist> listResult = playlistMapper.selectByExample(example2);
		if (listResult == null)
			return Msg.error();

		return Msg.success().add("list", listResult);
	}

	/**
	 * 根据用户查找他可能喜欢的歌单
	 *
	 * @param userId
	 *            用户ID
	 * @return Msg["list":List<Playlist>:歌单集合]
	 */
	public Msg findFavoritePlaylistByUserId(long userId) {
		Msg msg = tagService.findFavoriteTagByUserId(userId);
		@SuppressWarnings("unchecked")
		List<TagName> list = (List<TagName>) msg.getMap().get("list");
		if (list.size() == 0)
			return Msg.success().add("list", new ArrayList<Playlist>());

		int tagSize = list.size();// 记录标签个数

		// 取出第一个标签的所有歌单
		@SuppressWarnings("unchecked")
		List<Playlist> list2 = (List<Playlist>) tagService.selectPlaylistByTagName(list.get(0).getTagName()).getMap().get("list");
		if (list2.size() == 3)
			return Msg.success().add("list", list2);
		else if (list2.size() > 3) {
			// 歌单大于3个时，搜索出更符合用户的那3个
			List<PlaylistStruct> listSet = new ArrayList<PlaylistStruct>();
			for (int i = 0; i < list2.size(); i++) {
				long nowPlaylistId = list2.get(i).getId();
				int times = 0;
				for (int j = 1; j < tagSize; j++) {
					long nowTagId = list.get(j).getId();
					PlaylistTagExample example = new PlaylistTagExample();
					com.cloudmusic.bean.PlaylistTagExample.Criteria c = example.createCriteria();
					c.andPlaylistIdEqualTo(nowPlaylistId);
					c.andTagIdEqualTo(nowTagId);
					List<PlaylistTagKey> list3 = playlistTagMapper.selectByExample(example);
					if (list3.size() > 0) {
						// 该歌单存在这个标签
						times++;
					}
				}
				PlaylistStruct playlistStruct = new PlaylistStruct();
				playlistStruct.setPlaylistId(nowPlaylistId);
				playlistStruct.setTimes(times);
				listSet.add(playlistStruct);
			}
			Collections.sort(listSet, new Comparator<Object>() {

				public int compare(Object o1, Object o2) {
					PlaylistStruct p1 = (PlaylistStruct) o1;
					PlaylistStruct p2 = (PlaylistStruct) o2;
					int num1 = p1.getTimes();
					int num2 = p2.getTimes();

					if (num1 < num2)
						return 1;
					else if (num1 == num2)
						return 0;
					return -1;
				}

			});
			List<Playlist> listResult = new ArrayList<Playlist>();
			for (int i = 0; i < 3; i++) {
				Playlist p = playlistMapper.selectByPrimaryKey(listSet.get(i).getPlaylistId());
				listResult.add(p);
			}
			return Msg.success().add("list", listResult);
		} else {
			// 歌单小于3个时，已搜出的那些作为推荐。再搜索出其他复合用户的歌单
			List<Playlist> listResult = new ArrayList<Playlist>();
			for (int i = 0; i < list2.size(); i++) {
				listResult.add(list2.get(i));
			}
			int remain = 3 - listResult.size();// 查找剩下还需要推荐的歌

			Set<Long> set4 = new LinkedHashSet<Long>();
			Map<Long, Integer> map4 = new HashMap<Long, Integer>();
			for (int i = 1; i < tagSize; i++) {
				PlaylistTagExample example4 = new PlaylistTagExample();
				com.cloudmusic.bean.PlaylistTagExample.Criteria c4 = example4.createCriteria();
				c4.andTagIdEqualTo(list.get(i).getId());
				List<PlaylistTagKey> list_key = playlistTagMapper.selectByExample(example4);

				for (int j = 0; j < list_key.size(); j++) {
					Long playlistId = list_key.get(j).getPlaylistId();
					if (set4.contains(playlistId)) {
						// 如果之前已存在这个歌单ID
						int count = map4.get(playlistId);
						count++;
						map4.put(playlistId, count);
					} else {
						// 第一次出现这个歌单ID
						map4.put(playlistId, 0);
					}
				}
			}
			if (set4.size() <= remain) {
				Iterator<Long> it = set4.iterator();
				while (it.hasNext()) {
					Playlist p = playlistMapper.selectByPrimaryKey(it.next());
					listResult.add(p);
				}
				return Msg.success().add("list", listResult);
			}
			// 对歌单进行前后排序，推荐出前remain个歌单

			while (!set4.isEmpty()) {
				Iterator<Long> it = set4.iterator();
				Long nowBestId = null;
				int nowBestTimes = 0;
				while (it.hasNext()) {
					Long id = it.next();
					int timess = map4.get(id);
					if (nowBestId == null) {
						nowBestId = id;
						nowBestTimes = timess;
					}
					if (nowBestTimes < timess) {
						nowBestId = id;
						nowBestTimes = timess;
					}
				}
				Playlist playlistt = playlistMapper.selectByPrimaryKey(nowBestId);
				listResult.add(playlistt);
				remain--;
				if (remain <= 0)
					break;
			}
			return Msg.success().add("list", listResult);
		}
	}

	// 一个临时写的类,用于findFavoritePlaylistByUserId方法
	protected class PlaylistStruct implements Serializable {
		long playlistId;
		int times;

		public long getPlaylistId() {
			return playlistId;
		}

		public void setPlaylistId(long playlistId) {
			this.playlistId = playlistId;
		}

		public void setTimes(int times) {
			this.times = times;
		}

		int getTimes() {
			return this.times;
		}
	}

	/**
	 * 给定一个歌单列表，根据热度来进行排序，第一个为最近最火热
	 *
	 * @param list
	 *            需要排序的歌单集合
	 * @return Msg["list":List{Playlist}:排好序的歌单集合]
	 */
	public Msg sortPlaylistBasisHot(List<Playlist> list) {
		Collections.sort(list, hotComparator);

		return Msg.success().add("list", list);
	}

	/**
	 * 根据歌单ID找出它收纳的所有歌曲
	 *
	 * @param playlistId
	 *            歌单ID
	 * @return Msg.error()表示该歌单没有收纳歌曲 <br/>
	 *         Msg.success() Msg["list":List{Song}:歌曲集合]
	 */
	public Msg selectSongByPlaylist(long playlistId) {
		ReceiveExample example1 = new ReceiveExample();
		com.cloudmusic.bean.ReceiveExample.Criteria c1 = example1.createCriteria();
		c1.andPlaylistIdEqualTo(playlistId);
		List<ReceiveKey> list1 = receiveMapper.selectByExample(example1);

		if (list1 == null || list1.size() == 0)
			return Msg.error();

		List<Long> listSongId = new ArrayList<Long>();
		for (ReceiveKey i : list1) {
			listSongId.add(i.getSongId());
		}

		SongExample example2 = new SongExample();
		com.cloudmusic.bean.SongExample.Criteria c2 = example2.createCriteria();
		c2.andIdIn(listSongId);

		List<Song> listResult = songMapper.selectByExample(example2);
		return Msg.success().add("list", listResult);
	}

	/**
	 * 查询所有的歌单
	 *
	 * @return Msg[msg:1：存在歌曲，返回list集合；2：不存在歌单，返回提示信息]
	 */
	public Msg selectPlayList(Integer pn) {

		// 指定PageHelper当前页,每页显示长度
		PageHelper.startPage(pn, 18);

		List<Playlist> list = playlistMapper.selectByExample(null);

		// 获取每个歌单的创建者用户名
		for (Playlist playlist : list) {
			playlist.setUserName(userMapper.selectByPrimaryKey(playlist.getUserId()).getNickname());
		}

		PageInfo<Playlist> playlistPageInfo = new PageInfo<Playlist>(list, 5);

		if (list.size() == 0 || list == null) {
			return Msg.error().add("msg", "没有歌单");
		}
		return Msg.success().add("msg", playlistPageInfo);
	}

	/**
	 * 根据歌单id获取歌单信息
	 *
	 * @param playlistId
	 * @return Msg["msg" : error:提示信息；success：歌单信息]
	 */
	public Msg selectPlayListByPlayListId(long playlistId) {
		PlaylistExample example = new PlaylistExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(playlistId);
		List<Playlist> list = playlistMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return Msg.error().add("msg", "无此歌单！");
		}
		return Msg.success().add("msg", list.get(0));
	}

	/**
	 * 查询歌单，按照播放次数降序排序
	 *
	 * @return Msg["list" : 按播放次数降序排序的歌单列表]
	 */
	public Msg selectPlayListDescByPlayTimes() {
		List<Playlist> list = playlistMapper.selectByExample(null);

		if (list == null || list.size() == 0)
			return Msg.error();
		Collections.sort(list, playTimesComparator);
		return Msg.success().add("list", list);
	}

	private Comparator playTimesComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			Playlist p1 = (Playlist) o1;
			Playlist p2 = (Playlist) o2;
			long num1 = p1.getPlayTimes();
			long num2 = p2.getPlayTimes();

			if (num1 < num2)
				return 1;
			else if (num1 == num2)
				return 0;
			return -1;
		}
	};

	/**
	 * 查询歌单，按照收藏次数降序排序
	 *
	 * @return Msg["list" : 按收藏次数降序排序的歌单列表]
	 */
	public Msg selectPlayListDescByCollection() {
		List<Playlist> list = playlistMapper.selectByExample(null);

		if (list == null || list.size() == 0)
			return Msg.error();
		Collections.sort(list, collectionComparator);
		return Msg.success().add("list", list);
	}

	private Comparator collectionComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			Playlist p1 = (Playlist) o1;
			Playlist p2 = (Playlist) o2;
			long num1 = p1.getCollection();
			long num2 = p2.getCollection();

			if (num1 < num2)
				return 1;
			else if (num1 == num2)
				return 0;
			return -1;
		}
	};

	/**
	 * 按照歌手的歌播放次数进行降序排序
	 * 
	 * @return Msg["list" : 按歌手的歌曲播放次数进行降序排序的歌手集合]
	 */
	public Msg selectSingerDescByPlayTimes() {

		List<Song> songlist = songMapper.selectByExample(null);
		if (songlist == null || songlist.size() == 0) {
			return Msg.error();
		}
		// allSinger保存全部歌手
		List<String> allSinger = new ArrayList<String>();
		for (int i = 0; i < songlist.size(); i++) {
			if (!allSinger.contains(songlist.get(i).getSinger()))
				allSinger.add(songlist.get(i).getSinger());
		}

		List<Listen> list = listenMapper.selectByExample(null);
		if (list == null || list.size() == 0) {
			return Msg.error();
		}
		HashMap<Long, Long> map = new HashMap<Long, Long>();

		for (int i = 0; i < list.size(); i++) {
			long songId = list.get(i).getSongId();
			long playTimes = list.get(i).getTimes();
			if (map.containsKey(songId)) {
				long times = map.get(songId);
				map.put(songId, playTimes + times);
			} else {
				map.put(songId, playTimes);
			}
		}
		HashMap<String, Long> singerMap = new HashMap<String, Long>();
		Iterator keyValuePairs = map.entrySet().iterator();
		for (int j = 0; j < map.size(); j++) {
			Map.Entry entry = (Entry) keyValuePairs.next();
			long key = (Long) entry.getKey();
			long value = (Long) entry.getValue();

			SongExample example = new SongExample();
			com.cloudmusic.bean.SongExample.Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(key);
			List<Song> song = songMapper.selectByExample(example);
			String singerName = song.get(0).getSinger();

			if (singerMap.containsKey("singerName")) {
				long times = map.get("singerName");
				singerMap.put("singerName", value + times);
			} else {
				singerMap.put(singerName, value);
			}
		}

		List<Entry<String, Long>> sortList = new ArrayList<Entry<String, Long>>(singerMap.entrySet());

		Collections.sort(sortList, singerComparator);
		// songList保存降序排序好并且他们的歌曲都有被播放的歌手集合
		List<String> singerList = new ArrayList<String>();
		for (int i = 0; i < sortList.size(); i++) {
			singerList.add(sortList.get(i).getKey());
		}
		// 将其余歌手（歌曲未被收听）加入singerList中
		for (int i = 0; i < allSinger.size(); i++) {
			if (!singerList.contains(allSinger.get(i))) {
				singerList.add(allSinger.get(i));
			}
		}
		return Msg.success().add("list", singerList);

	}

	private Comparator singerComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			Entry<String, Long> p1 = (Entry<String, Long>) o1;
			Entry<String, Long> p2 = (Entry<String, Long>) o2;
			long num1 = p1.getValue();
			long num2 = p2.getValue();

			if (num1 < num2)
				return 1;
			else if (num1 == num2)
				return 0;
			return -1;
		}
	};

	public Msg findNicePlaylistByTagName(List<TagName> list_tag) {

		int tagSize = list_tag.size();// 记录传入标签的个数
		Set<Long> playlist_set = new HashSet<Long>();
		Map<Long, Integer> times_map = new HashMap<Long, Integer>();

		// 依次取出每个标签的所有歌单
		for (int i = 0; i < tagSize; i++) {
			// 根据第i个标签取出歌单
			String tagName = list_tag.get(i).getTagName();
			List<Playlist> list1 = (List<Playlist>) tagService.selectPlaylistByTagName(tagName).getMap().get("list");

			for (Playlist playlist : list1) {
				Long id = playlist.getId();
				if (playlist_set.contains(id)) {
					int times = times_map.get(id);
					times++;
					times_map.put(id, times);
				} else {
					playlist_set.add(id);
					times_map.put(id, 1);
				}
			}
		}

		List<Playlist> listResult = new ArrayList<Playlist>();
		while (!playlist_set.isEmpty()) {
			Iterator<Long> it = playlist_set.iterator();
			Long nowBestId = null;
			int nowBestTimes = 0;
			while (it.hasNext()) {
				Long id = it.next();
				int times = times_map.get(id);

				if (nowBestId == null) {
					nowBestId = id;
					nowBestTimes = times;
				} else if (nowBestTimes < times) {
					nowBestId = id;
					nowBestTimes = times;
				}
			}
			Playlist playlist = playlistMapper.selectByPrimaryKey(nowBestId);
			listResult.add(playlist);
			playlist_set.remove(nowBestId);
		}
		return Msg.success().add("list", listResult);
	}

	public Msg selectPlaylistByUserId(long userId) {
		PlaylistExample example = new PlaylistExample();
		Criteria c = example.createCriteria();
		c.andUserIdEqualTo(userId);
		List<Playlist> list = playlistMapper.selectByExample(example);

		return Msg.success().add("list", list);
	}

	public Msg selectPlaylistByLikeName(String name) {
		PlaylistExample example = new PlaylistExample();
		Criteria c = example.createCriteria();
		c.andListNameLike("%" + name + "%");
		List<Playlist> list = playlistMapper.selectByExample(example);
		return Msg.success().add("list", list);
	}

	public Msg undoPlaylistCollection(long userId, long playlistId) {
		PlaylistCollectionKey key = new PlaylistCollectionKey();
		key.setUserId(userId);
		key.setPlaylistId(playlistId);
		int num = playlistCollectionMapper.deleteByPrimaryKey(key);
		return (num > 0) ? Msg.success() : Msg.error();
	}

	public Msg updatePlaylistImgPath(long playlistId, String img_path) {
		Playlist playlist = playlistMapper.selectByPrimaryKey(playlistId);
		if (playlist == null)
			return Msg.error();

		playlist.setImgPath(img_path);
		int count = playlistMapper.updateByPrimaryKeySelective(playlist);
		return (count > 0) ? Msg.success().add("playlist", playlist) : Msg.error();
	}

	public Msg updatePlaylistByPrimaryKey(Playlist playlist) {
		int count = playlistMapper.updateByPrimaryKeySelective(playlist);
		return (count > 0) ? Msg.success().add("playlist", playlist) : Msg.error();
	}

	public Msg updatePlaylistTagName(long playlistId, List<String> list_tagName) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			
			//1.先删除该歌单原有的标签
			PlaylistTagExample example1 = new PlaylistTagExample();
			com.cloudmusic.bean.PlaylistTagExample.Criteria c1 = example1.createCriteria();
			c1.andPlaylistIdEqualTo(playlistId);
			playlistTagMapper.deleteByExample(example1);
			
			//2.然后为该歌单设置新标签
			//2.1 如果传入的是空，则直接返回
			if(list_tagName.size() <= 0)
				return Msg.success();
			
			
			for(String name : list_tagName)
			{
				Msg msg = tagService.selectByTagName(name);
				TagName tagName = (TagName) msg.getMap().get("tagName");
				
				//插入数据库
				PlaylistTagKey key = new PlaylistTagKey();
				key.setPlaylistId(playlistId);
				key.setTagId(tagName.getId());
				playlistTagMapper.insert(key);
			}
			
		}catch(Exception e)
		{
			sqlSession.rollback();
			return Msg.error();
		}finally {
			sqlSession.close();
		}
		return Msg.success();
	}

}
