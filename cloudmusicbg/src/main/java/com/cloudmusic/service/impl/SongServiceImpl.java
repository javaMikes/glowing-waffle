package com.cloudmusic.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudmusic.bean.Listen;
import com.cloudmusic.bean.ListenExample;
import com.cloudmusic.bean.ListenKey;
import com.cloudmusic.bean.PlaylistTagExample;
import com.cloudmusic.bean.PlaylistTagKey;
import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.SongCollectionExample;
import com.cloudmusic.bean.SongCollectionKey;
import com.cloudmusic.bean.SongExample;
import com.cloudmusic.bean.SongExample.Criteria;
import com.cloudmusic.bean.SongTagExample;
import com.cloudmusic.bean.SongTagKey;
import com.cloudmusic.bean.TagName;
import com.cloudmusic.mapper.ListenMapper;
import com.cloudmusic.mapper.SongCollectionMapper;
import com.cloudmusic.mapper.SongMapper;
import com.cloudmusic.mapper.SongTagMapper;
import com.cloudmusic.service.ISongService;
import com.cloudmusic.service.ITagService;
import com.cloudmusic.util.Msg;

@Service
public class SongServiceImpl implements ISongService {

	@Autowired
	SongMapper songMapper;

	@Autowired
	ListenMapper listenMapper;

	@Autowired
	SongCollectionMapper songCollectionMapper;

	@Autowired
	ITagService tagService;

	@Autowired
	SongTagMapper songTagMapper;

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 上传歌曲
	 *
	 * @param song
	 * @return Msg 包含map(键："msg": 值 ：“提示信息” )
	 */
	public Msg uploadSong(Song song) {
		int num = songMapper.insert(song);
		if (num > 0) {
			return Msg.success().add("msg", "歌曲上传成功！");
		}
		return Msg.error().add("msg", "歌曲上传失败！");
	}

	/**
	 * 通过用户id查询用户收听过的歌曲
	 *
	 * @param userid
	 * @return Msg["list" : List<Song>收听过的歌曲列表]
	 */
	public Msg selectSongListenedByUserId(long userId) {
		ListenExample example = new ListenExample();
		com.cloudmusic.bean.ListenExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);

		List<Listen> list = listenMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return Msg.error().add("msg", "该用户没有收听过任何歌曲哦！");
		List<Long> songIds = new ArrayList<Long>();
		long songId;
		for (int i = 0; i < list.size(); i++) {
			songId = list.get(i).getSongId();
			songIds.add(songId);
		}
		SongExample example2 = new SongExample();
		com.cloudmusic.bean.SongExample.Criteria criteria2 = example2.createCriteria();
		criteria2.andIdIn(songIds);
		songMapper.selectByExample(example2);
		List<Song> SongListResult = songMapper.selectByExample(example2);
		if (SongListResult == null)
			return Msg.error();

		return Msg.success().add("list", SongListResult);
	}

	/**
	 * 通过用户id查询用户收藏过的歌曲
	 *
	 * @param userId
	 * @return Msg["list":List<Song>:用户歌曲收藏集合]
	 */
	public Msg selectSongCollectedByUserId(long userId) {
		SongCollectionExample example = new SongCollectionExample();
		com.cloudmusic.bean.SongCollectionExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);

		List<SongCollectionKey> list = songCollectionMapper.selectByExample(example);
		if (list.size() == 0)
			return Msg.success().add("list", new ArrayList<Song>());

		List<Long> listSongId = new ArrayList<Long>();
		for (int i = 0; i < list.size(); i++) {
			listSongId.add(list.get(i).getSongId());
		}

		SongExample example2 = new SongExample();
		com.cloudmusic.bean.SongExample.Criteria criteria2 = example2.createCriteria();
		criteria2.andIdIn(listSongId);
		songMapper.selectByExample(example2);
		List<Song> SongListResult = songMapper.selectByExample(example2);
		if (SongListResult == null)
			return Msg.error();

		return Msg.success().add("list", SongListResult);
	}

	/**
	 * 根据用户id以及歌曲id将某首歌曲播放次数加1
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "提示信息"]
	 */
	public Msg addOnePlaytimeOfSong(long userId, long songId) {

		Song song = songMapper.selectByPrimaryKey(songId);
		song.setPlayTimes(song.getPlayTimes() + 1);
		int nums = songMapper.updateByPrimaryKey(song);
		if (nums > 0) {
			if (userId > 0) {// 用户处于登录状态
				ListenExample example = new ListenExample();
				com.cloudmusic.bean.ListenExample.Criteria criteria = example.createCriteria();
				criteria.andUserIdEqualTo(userId);
				criteria.andSongIdEqualTo(songId);
				List<Listen> list = listenMapper.selectByExample(example);
				if (list.size() == 0) {
					// 用户之前尚未听过该歌曲，则在listen表中增加一条记录，并设置收听次数为1
					Listen listen = new Listen();
					listen.setUserId(userId);
					listen.setSongId(songId);
					listen.setTimes(1L);
					int num = listenMapper.insert(listen);
					if (num > 0) {
						return Msg.success().add("msg", "更改数据库成功！");
					}
					return Msg.error().add("msg", "更改数据库失败！");
				}
				Listen listen = list.get(0);// 取出第一条记录
				listen.setTimes(list.get(0).getTimes() + 1);
				int num = listenMapper.updateByPrimaryKey(listen);
				if (num > 0)
					return Msg.success().add("msg", "更改数据库成功！");
				return Msg.error().add("msg", "更改数据库失败！");
			}
			return Msg.success().add("msg", "更改数据库成功！");
		} else
			return Msg.error().add("msg", "更改数据库失败！");

	}

	/**
	 * 某用户收藏某首歌曲
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "提示信息"]
	 */
	public Msg addCollectionOfSong(long userId, long songId) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
		
			SongCollectionKey songCollectionKey = new SongCollectionKey();
			songCollectionKey.setUserId(userId);
			songCollectionKey.setSongId(songId);
	
			int num = songCollectionMapper.insert(songCollectionKey);
			
			if (num <= 0)
				throw new Exception("操作失败，因为用户已收藏了该歌曲");
			
			//修改歌曲收藏数+1
			Song song = songMapper.selectByPrimaryKey(songId);
			long times = song.getCollection();
			times++;
			song.setCollection(times);
			
			if(songMapper.updateByPrimaryKey(song) <= 0)
				throw new Exception("收藏失败，音乐表修改失败");
			
		}catch(Exception e)
		{
			sqlSession.rollback();
			return Msg.error().add("msg", e.getMessage());
		}finally {
			sqlSession.close();
		}
		
		return Msg.success().add("msg", "收藏成功");
	}

	/**
	 * 某用户取消收藏某首歌曲
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "提示信息"]
	 */
	public Msg subCollectionOfSong(long userId, long songId) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
		
			SongCollectionKey songCollectionKey = new SongCollectionKey();
			songCollectionKey.setSongId(songId);
			songCollectionKey.setUserId(userId);
	
			int num = songCollectionMapper.deleteByPrimaryKey(songCollectionKey);
			if (num <= 0) {
				throw new Exception("取消收藏失败，因为该用户未收藏该歌曲");
			}
			
			//音乐收藏次数降1
			Song song = songMapper.selectByPrimaryKey(songId);
			long times = song.getCollection();
			times--;
			song.setCollection(times);
			if(songMapper.updateByPrimaryKey(song) <= 0)
				throw new Exception("取消收藏失败，操作音乐表时失败");
		
		}catch(Exception e)
		{
			sqlSession.rollback();
			return Msg.error().add("msg", e.getMessage());
		}finally {
			sqlSession.close();
		}
		return Msg.success().add("msg", "取消收藏成功");
	}

	/**
	 * 根据歌曲id搜索该歌曲被播放的次数
	 *
	 * @param songId
	 * @return Msg(包含两个键，"msg"：提示信息； "sum" ： 播放次数)
	 */
	public Msg selectTimesPlayOfSong(long songId) {
		ListenExample example = new ListenExample();
		com.cloudmusic.bean.ListenExample.Criteria criteria = example.createCriteria();
		criteria.andSongIdEqualTo(songId);
		List<Listen> list = listenMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return Msg.error().add("msg", "该歌曲未被收听过！");
		}
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i).getTimes();
		}
		return Msg.success().add("sum", sum);
	}

	/**
	 * 根据用户id，歌曲id，判断某个用户是否收听过某个歌曲
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg" : true(收听过)；false(未收听过)]
	 */
	public Msg isListenedTheSong(long userId, long songId) {

		ListenKey key = new ListenKey();
		key.setUserId(userId);
		key.setSongId(songId);
		Listen listen = listenMapper.selectByPrimaryKey(key);
		if (listen == null)
			return Msg.error().add("msg", false);
		return Msg.success().add("msg", true);
	}

	/**
	 * 根据用户id，歌曲id，判断某个用户是否收藏某首歌曲
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg" : true(已收藏)；false(未收藏)]
	 */
	public Msg isCollectTheSong(long userId, long songId) {
		SongCollectionExample example = new SongCollectionExample();
		com.cloudmusic.bean.SongCollectionExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andSongIdEqualTo(songId);
		List<SongCollectionKey> list = songCollectionMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return Msg.error().add("msg", false);

		return Msg.success().add("msg", true);
	}

	/**
	 * 根据用户id将其听过的歌曲按次数多少降序排序
	 *
	 * @param userId
	 * @return
	 */
	public Msg rankSongListenTimes(long userId) {
		ListenExample example = new ListenExample();
		com.cloudmusic.bean.ListenExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<Listen> list = listenMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return Msg.error().add("msg", "该用户未收听过歌曲");
		// 使用快速排序算法进行排序
		quickSort(list, 0, list.size() - 1);
		return Msg.success().add("list", list);

	}

	// 使用快速排序按歌曲收听次数降序排序
	public void quickSort(List<Listen> list, int high, int low) {
		int i, j;
		long index, index2;
		i = high;// 高端下标
		j = low;// 低端下标
		index = list.get(i).getTimes(); // 用子表的第一个记录做基准
		index2 = list.get(i).getSongId();
		while (i < j) { // 递归出口:low>=high
			while (i < j && list.get(j).getTimes() < index)// 后端比index小，符合降序，不管它，low下标前移
				j--;// while完后指比temp大的那个
			if (i < j) {
				list.get(i).setSongId(list.get(j).getSongId());
				list.get(i).setTimes(list.get(j).getTimes());
				i++;
			}
			while (i < j && list.get(i).getTimes() > index)
				i++;
			if (i < j) {
				list.get(j).setSongId(list.get(i).getSongId());
				list.get(j).setTimes(list.get(i).getTimes());
				j--;
			}
		} // while完，即第一盘排序
		list.get(i).setSongId(index2);
		list.get(i).setTimes(index);// 把index值放到它该在的位置。
		if (high < i)
			quickSort(list, high, i - 1); // 对左端子数组递归
		if (i < low)
			quickSort(list, i + 1, low); // 对右端子数组递归
	}

	/**
	 * 根据歌手搜索歌曲
	 *
	 * @param singer
	 * @return Msg包含两个键[msg : "提示信息"]; ["list" : List<Song>歌曲列表]
	 */
	public Msg selectSongBySinger(String singer) {
		SongExample example = new SongExample();
		com.cloudmusic.bean.SongExample.Criteria criteria = example.createCriteria();
		criteria.andSingerEqualTo(singer);
		List<Song> list = songMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return Msg.error().add("msg", "未找到该歌手的歌曲");
		return Msg.success().add("list", list);
	}

	/**
	 * 根据歌名模糊搜索歌曲
	 *
	 * @param songName
	 * @return Msg包含两个键[msg : "提示信息"]; ["list" : List<Song>歌曲列表]
	 */
	public Msg selectSongBySongName(String songName) {
		SongExample example = new SongExample();
		com.cloudmusic.bean.SongExample.Criteria criteria = example.createCriteria();
		criteria.andSongNameLike("%" + songName + "%");
		List<Song> list = songMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return Msg.error().add("msg", "未匹配到歌曲");
		return Msg.success().add("list", list);
	}

	// 比较排序
	private Comparator hotComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			Song s1 = (Song) o1;
			Song s2 = (Song) o2;

			long songId1 = s1.getId();// 歌曲id
			long songId2 = s2.getId();

			ListenExample example = new ListenExample();
			com.cloudmusic.bean.ListenExample.Criteria criteria = example.createCriteria();
			criteria.andSongIdEqualTo(songId1);
			List<Listen> list = listenMapper.selectByExample(example);
			long listenTimesSum = 0;// 歌曲s1的收听次数
			if (list == null || list.size() == 0)
				listenTimesSum = 0;
			else {
				for (int i = 0; i < list.size(); i++) {
					listenTimesSum += list.get(i).getTimes();
				}
			}

			ListenExample example2 = new ListenExample();
			com.cloudmusic.bean.ListenExample.Criteria criteria2 = example2.createCriteria();
			criteria2.andSongIdEqualTo(songId2);
			List<Listen> list2 = listenMapper.selectByExample(example2);
			long listenTimesSum2 = 0L;// 歌曲s2的收听次数
			if (list2 == null || list2.size() == 0)
				listenTimesSum2 = 0;
			else {
				for (int i = 0; i < list2.size(); i++) {
					listenTimesSum2 += list2.get(i).getTimes();
				}
			}

			SongCollectionExample example3 = new SongCollectionExample();
			com.cloudmusic.bean.SongCollectionExample.Criteria criteria3 = example3.createCriteria();
			criteria3.andSongIdEqualTo(songId1);
			List<SongCollectionKey> list3 = songCollectionMapper.selectByExample(example3);

			int collectTimesSum = list3.size();

			SongCollectionExample example4 = new SongCollectionExample();
			com.cloudmusic.bean.SongCollectionExample.Criteria criteria4 = example4.createCriteria();
			criteria4.andSongIdEqualTo(songId2);
			List<SongCollectionKey> list4 = songCollectionMapper.selectByExample(example4);

			long collectTimesSum2 = list4.size();

			long num1 = listenTimesSum + collectTimesSum; // 获取歌曲s1播放次数及收藏数之和
			long num2 = listenTimesSum2 + collectTimesSum2; // 获取歌曲s2播放次数及收藏数之和

			if (num1 < num2)
				return 1;
			else if (num1 == num2)
				return 0;
			return -1;
		}
	};

	/**
	 * 获取热门歌曲
	 *
	 * @return Msg[list : List<Song>热门歌曲集合]
	 */
	public Msg getHotSongs() {
		List<Song> list = songMapper.selectByExample(null);
		if (list == null || list.size() == 0)
			return Msg.error().add("msg", "未找到歌曲！");

		Collections.sort(list, hotComparator);

		return Msg.success().add("list", list);
	}

	/**
	 * 获取热门歌手
	 *
	 * @return Msg["list" : List<String> 热门歌手集合]
	 */
	public Msg getHotSingers() {
		List<Song> list = songMapper.selectByExample(null);

		Collections.sort(list, hotComparator);

		List<String> singerList = new ArrayList<String>();

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (!singerList.contains(list.get(i).getSinger()))
					singerList.add(list.get(i).getSinger());
			}
		}
		return Msg.success().add("list", singerList);
	}

	public Msg selectSongBySongId(Long id) {
		if (id == null)
			return Msg.error();

		Song song = songMapper.selectByPrimaryKey(id);
		if (song == null)
			return Msg.error();

		return Msg.success().add("song", song);
	}

	/**
	 * 根据用户id个性化推荐歌曲
	 */
	public Msg findFavoriteSongByUserId(Long userId) {
		if (userId == null)
			return Msg.success().add("list", new ArrayList<Song>());
		Msg msg = tagService.findFavoriteTagByUserId(userId);
		List<TagName> list = (List<TagName>) msg.getMap().get("list");
		if (list.size() == 0)
			return Msg.success().add("list", new ArrayList<Song>());

		Set<Long> set = new LinkedHashSet<Long>();// 记录歌曲的id
		Map<Long, Integer> map = new HashMap<Long, Integer>();// 记录某一个id的歌，的个性化等级
		int totalSize = 0;// 记录已经推荐出的歌曲个数

		// 循环根据喜爱标签取出歌曲，歌曲达到24个时停止
		for (int i = 0; i < list.size(); i++) {
			List<Song> songList = (List<Song>) tagService.selectSongByTagName(list.get(i).getTagName()).getMap().get("list");
			for (int j = 0; j < songList.size(); j++) {

				if (set.contains(songList.get(j).getId())) {
					// 如果这首歌已经包含在Set中
					int times = map.get(songList.get(j).getId());
					times++;
					map.put(songList.get(j).getId(), times);
				} else {
					if (totalSize < 24) {
						// 添加新的进来
						int times = list.size() - i;// 基础个性化推荐优先级（越靠前的标签，优先级越高）
						map.put(songList.get(j).getId(), times);
						set.add(songList.get(j).getId());
						totalSize++;
					}
				}
			}
			if (totalSize >= 24)
				break;
		}

		List<Song> listResult = new ArrayList<Song>();

		while (!set.isEmpty()) {
			Iterator<Long> it = set.iterator();
			Long nowBestId = null;
			int nowBestTimes = 0;
			while (it.hasNext()) {
				Long nowId = it.next();
				int times = map.get(nowId);
				if (nowBestId == null) {
					nowBestId = nowId;
					nowBestTimes = times;
				}

				if (times > nowBestTimes) {
					nowBestId = nowId;
					nowBestTimes = times;
				}
			}
			// 将最优的结果添加到返回集
			Song song = songMapper.selectByPrimaryKey(nowBestId);
			listResult.add(song);
			set.remove(nowBestId);
		}

		return Msg.success().add("list", listResult);
	}

	public Msg selectSongDescByPlayTimes() {
		List<Song> list = songMapper.selectByExample(null);

		if (list == null || list.size() == 0)
			return Msg.error();
		Collections.sort(list, playTimesComparator);
		return Msg.success().add("list", list);
	}

	public Msg selectSongDescByCollection() {
		List<Song> list = songMapper.selectByExample(null);

		if (list == null || list.size() == 0)
			return Msg.error();
		Collections.sort(list, collectionComparator);
		return Msg.success().add("list", list);
	}

	private Comparator playTimesComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			Song p1 = (Song) o1;
			Song p2 = (Song) o2;
			long num1 = p1.getPlayTimes();
			long num2 = p2.getPlayTimes();

			if (num1 < num2)
				return 1;
			else if (num1 == num2)
				return 0;
			return -1;
		}
	};

	private Comparator collectionComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			Song p1 = (Song) o1;
			Song p2 = (Song) o2;
			long num1 = p1.getCollection();
			long num2 = p2.getCollection();

			if (num1 < num2)
				return 1;
			else if (num1 == num2)
				return 0;
			return -1;
		}
	};

	public Msg selectSongByLikeName(String name) {
		SongExample example = new SongExample();
		Criteria c = example.createCriteria();
		c.andSongNameLike("%" + name + "%");
		List<Song> list = songMapper.selectByExample(example);

		return Msg.success().add("list", list);
	}

	public Msg findNiceSongByTagName(List<TagName> list_tag) {
		int tagSize = list_tag.size();
		Set<Long> song_set = new HashSet<Long>();
		Map<Long, Integer> times_map = new HashMap<Long, Integer>();

		// 依次取出每个标签的所有歌曲
		for (int i = 0; i < tagSize; i++) {
			// 根据第i个标签取出歌曲
			String tagName = list_tag.get(i).getTagName();
			List<Song> list1 = (List<Song>) tagService.selectSongByTagName(tagName).getMap().get("list");

			for (Song song : list1) {
				Long id = song.getId();
				if (song_set.contains(id)) {
					int times = times_map.get(id);
					times++;
					times_map.put(id, times);
				} else {
					song_set.add(id);
					times_map.put(id, 1);
				}
			}
		}

		List<Song> listResult = new ArrayList<Song>();
		while (!song_set.isEmpty()) {
			Iterator<Long> it = song_set.iterator();
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
			Song song = songMapper.selectByPrimaryKey(nowBestId);
			listResult.add(song);
			song_set.remove(nowBestId);
		}
		return Msg.success().add("list", listResult);
	}

	public Msg selectSingerSongAndHotSort(String singerName) {
		SongExample example = new SongExample();
		Criteria c = example.createCriteria();
		c.andSingerEqualTo(singerName);
		example.setOrderByClause("play_times DESC");
		List<Song> list = songMapper.selectByExample(example);
		if (list == null)
			return Msg.error().add("msg", "未找到该歌手的歌曲");
		return Msg.success().add("list", list);
	}

	public Msg updateSongTagByTagNameList(long songId, List<String> list_tag) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			
			//1.先删除该歌曲原有的标签
			SongTagExample example1 = new SongTagExample();
			com.cloudmusic.bean.SongTagExample.Criteria c1 = example1.createCriteria();
			c1.andSongIdEqualTo(songId);
			songTagMapper.deleteByExample(example1);
			
			//2.然后为该歌曲设置新标签
			//2.1 如果传入的是空，则直接返回
			if(list_tag.size() <= 0)
				return Msg.success();
			
			
			for(String name : list_tag)
			{
				Msg msg = tagService.selectByTagName(name);
				TagName tagName = (TagName) msg.getMap().get("tagName");
				
				//插入数据库
				SongTagKey key = new SongTagKey();
				key.setSongId(songId);
				key.setTagId(tagName.getId());
				songTagMapper.insert(key);
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

	// /**
	// *测试
	// */
	// public List<Song> selectSongDescByPlayTimes() {
	// // 获取所有的用户收听记录
	// List<Listen> listenList = listenMapper.selectByExample(new
	// ListenExample());
	// // 存储排序后的歌曲信息
	// List<Song> songList = new ArrayList<Song>();
	//
	// // 获取收听记录里的所有歌曲id(不重复)
	// Set<Long> listenSet = new HashSet<Long>();
	// for (Listen listen : listenList) {
	// listenSet.add(listen.getSongId());
	// }
	//
	// // 计算所有歌曲的播放总次数，并存在map中
	// Map<Long, Long> countMap = new HashMap<Long, Long>();
	// for (Long songId : listenSet) {
	// System.out.println(songId);
	// Long sum = 0L;
	// ListenExample listenExample = new ListenExample();
	// com.cloudmusic.bean.ListenExample.Criteria criteria =
	// listenExample.createCriteria();
	// criteria.andSongIdEqualTo(songId);
	// // 根据songId获取listen记录
	// List<Listen> listenSelected =
	// listenMapper.selectByExample(listenExample);
	// for (Listen listen : listenSelected) {
	// sum += listen.getTimes();
	// }
	// countMap.put(sum, songId);
	// }
	//
	// // 排序
	// while (countMap.size() != 0) {
	// long max = 0L;
	// Long maxKey = 0L;
	// for (Long sum : countMap.keySet()) {
	// if (max < sum) {
	// max = sum;
	// }
	// }
	// countMap.remove(max);
	// songList.add(songMapper.selectByPrimaryKey(max));
	// }
	//
	// return songList;
	// }

}
