package com.cloudmusic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudmusic.bean.Listen;
import com.cloudmusic.bean.ListenExample;
import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.PlaylistExample;
import com.cloudmusic.bean.PlaylistTagExample;
import com.cloudmusic.bean.PlaylistTagKey;
import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.SongCollectionExample;
import com.cloudmusic.bean.SongCollectionKey;
import com.cloudmusic.bean.SongExample;
import com.cloudmusic.bean.SongTagExample;
import com.cloudmusic.bean.SongTagKey;
import com.cloudmusic.bean.TagName;
import com.cloudmusic.bean.TagNameExample;
import com.cloudmusic.bean.TagNameExample.Criteria;
import com.cloudmusic.mapper.ListenMapper;
import com.cloudmusic.mapper.PlaylistCollectionMapper;
import com.cloudmusic.mapper.PlaylistMapper;
import com.cloudmusic.mapper.PlaylistTagMapper;
import com.cloudmusic.mapper.SongCollectionMapper;
import com.cloudmusic.mapper.SongMapper;
import com.cloudmusic.mapper.SongTagMapper;
import com.cloudmusic.mapper.TagNameMapper;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ITagService;
import com.cloudmusic.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TagServiceImpl implements ITagService {

	@Autowired
	TagNameMapper tagNameMapper;

	@Autowired
	SongTagMapper songTagMapper;

	@Autowired
	PlaylistTagMapper playlistTagMapper;

	@Autowired
	ListenMapper listenMapper;

	@Autowired
	PlaylistCollectionMapper playlistCollectionMapper;

	@Autowired
	SongCollectionMapper songCollectionMapper;

	@Autowired
	PlaylistMapper playlistMapper;

	@Autowired
	IPlaylistService iplaylistService;

	@Autowired
	SongMapper songMapper;

	/**
	 * 查找出用户最喜欢的标签，按顺序排下来，第一个标签是最经常听的标签，最后一个是最少听的标签
	 * 实现思想：根据用户听过的歌曲，收藏的歌曲，收藏的歌单，3者进行联合。将标签搜索出来进行频率比较
	 *
	 * @param userId
	 *            用户ID
	 * @return Msg["list":List{TagName}:标签集合]
	 */
	public Msg findFavoriteTagByUserId(long userId) {
		Set<Long> tagId_set = new LinkedHashSet<Long>(); // 存放用户听过的标签，用Set集合是为了不让标签重复
		Map<Long, Integer> tagId_times = new HashMap<Long, Integer>(); // 记录每个标签的频度

		// (1)首先查找该用户收听的歌曲
		ListenExample example1 = new ListenExample();
		com.cloudmusic.bean.ListenExample.Criteria c1 = example1.createCriteria();
		c1.andUserIdEqualTo(userId);
		List<Listen> list1 = listenMapper.selectByExample(example1);
		// 查看这些歌曲的标签
		for (int i = 0; i < list1.size(); i++) {
			long times = list1.get(i).getTimes();

			List<Long> listNow = this.findTagNameBySongId(list1.get(i).getSongId());
			for (int v = 0; v < listNow.size(); v++) {

				long o = listNow.get(v);
				if (tagId_set.contains(o)) {
					// 标签已存在，直接加频率
					int time = tagId_times.get(o);// 取出这标签之前的频度
					time += times;
					tagId_times.put(o, time);
				} else {
					// 标签不存在
					tagId_set.add(o);
					tagId_times.put(o, (int) times);
				}
			}
		}

		// (2)查找该用户收藏的歌
		SongCollectionExample example2 = new SongCollectionExample();
		com.cloudmusic.bean.SongCollectionExample.Criteria c2 = example2.createCriteria();
		c2.andUserIdEqualTo(userId);
		List<SongCollectionKey> list2 = songCollectionMapper.selectByExample(example2);
		// 查看这些歌曲的标签

		for (int i = 0; i < list2.size(); i++) {
			List<Long> listNow = this.findTagNameBySongId(list2.get(i).getSongId());
			for (int v = 0; v < listNow.size(); v++) {
				long o = listNow.get(v);
				if (tagId_set.contains(o)) {
					// 标签已存在，直接加频率
					int time = tagId_times.get(o);// 取出这标签之前的频度
					time++;
					tagId_times.put(o, time);
				} else {
					// 标签不存在
					tagId_set.add(o);
					tagId_times.put(o, 1);
				}
			}
		}

		// （3）查找用户收藏的歌单

		Msg msg = iplaylistService.selectPlaylistCollectionByUserId(userId);

		@SuppressWarnings("unchecked")
		List<Playlist> list3 = (List<Playlist>) msg.getMap().get("list");

		for (int i = 0; i < list3.size(); i++) {
			List<Long> listNow = this.findTagNameByPlaylistId(list3.get(i).getId());
			for (int v = 0; v < listNow.size(); v++) {
				long o = listNow.get(v);
				if (tagId_set.contains(o)) {
					// 标签已存在，直接加频率
					int time = tagId_times.get(o);// 取出这标签之前的频度
					time++;
					tagId_times.put(o, time);
				} else {
					// 标签不存在
					tagId_set.add(o);
					tagId_times.put(o, 1);
				}
			}
		}

		// (4)最后进行标签频度排序
		List<TagName> listResult = new ArrayList<TagName>();
		while (!tagId_set.isEmpty()) {
			Long bestTagId = -1L;
			int bestTimes = -1;

			Iterator<Long> it = tagId_set.iterator();
			while (it.hasNext()) {
				Long tagId = it.next();
				int times = tagId_times.get(tagId);

				if (bestTimes == -1) {
					bestTagId = tagId;
					bestTimes = times;
					continue;
				}
				if (times >= bestTimes) {
					// 当前更好
					bestTagId = tagId;
					bestTimes = times;
					continue;
				}

			}
			// 找到最好的，把最好的插入结果
			Msg msg2 = this.selectTagNameByTagId(bestTagId);
			TagName tagName2 = (TagName) msg2.getMap().get("tagName");
			if (tagName2 == null)
				return Msg.error();

			listResult.add(tagName2);
			// 将该最好的数据删去
			tagId_set.remove(bestTagId);
		}

		return Msg.success().add("list", listResult);
	}

	/**
	 * 根据歌曲id搜索出它的标签id
	 *
	 * @param songId
	 *            歌曲ID
	 * @return
	 */
	public List<Long> findTagNameBySongId(long songId) {
		SongTagExample example = new SongTagExample();
		com.cloudmusic.bean.SongTagExample.Criteria c2 = example.createCriteria();
		c2.andSongIdEqualTo(songId);
		List<SongTagKey> list = songTagMapper.selectByExample(example);

		List<Long> listResult = new ArrayList<Long>();
		for (int i = 0; i < list.size(); i++) {
			listResult.add(list.get(i).getTagId());
		}
		return listResult;
	}

	/**
	 * 根据歌单id搜索出它的标签id
	 *
	 * @param playlistId
	 *            歌曲ID
	 * @return
	 */
	public List<Long> findTagNameByPlaylistId(long playlistId) {
		PlaylistTagExample example = new PlaylistTagExample();
		com.cloudmusic.bean.PlaylistTagExample.Criteria c2 = example.createCriteria();
		c2.andPlaylistIdEqualTo(playlistId);
		List<PlaylistTagKey> list = playlistTagMapper.selectByExample(example);

		List<Long> listResult = new ArrayList<Long>();
		for (int i = 0; i < list.size(); i++) {
			listResult.add(list.get(i).getTagId());
		}
		return listResult;
	}

	/**
	 * 根据标签id搜索出该标签
	 *
	 * @param id
	 * @return 失败error，成功success
	 *
	 *         Msg["tagName":TagName:标签]
	 */
	public Msg selectTagNameByTagId(long id) {
		TagName tagName = tagNameMapper.selectByPrimaryKey(id);
		if (tagName == null)
			Msg.error();

		return Msg.success().add("tagName", tagName);
	}

	/**
	 * 查找出所有标签
	 *
	 * @return Msg["list":list{TagName}:标签集合]
	 */
	public Msg selectAllTagName() {
		List<TagName> list = tagNameMapper.selectByExample(null);
		if (list == null)
			return Msg.error();

		return Msg.success().add("list", list);
	}

	/**
	 * 根据标签类型查找出这个类型的所有标签
	 *
	 * @param type
	 *            标签类型名字
	 * @return Msg.error时["msg":String:错误信息提示] ; <br/>
	 *         Msg.success时表示成功["list":List{TagName}:该类型的标签集合]
	 *
	 */
	public Msg selectTagNameType(String type) {
		TagNameExample example1 = new TagNameExample();
		com.cloudmusic.bean.TagNameExample.Criteria c1 = example1.createCriteria();
		c1.andTypeEqualTo(type);
		List<TagName> tag1 = tagNameMapper.selectByExample(example1);
		if (tag1 == null || tag1.size() == 0)
			return Msg.error().add("msg", "标签类型为" + type + "的类型不存在");

		return Msg.success().add("list", tag1);
	}

	/**
	 * 根据标签名，搜索出包含该标签的所有歌单
	 *
	 * @param name
	 *            标签名
	 * @return Msg["list":List{Playlist}:歌单集合，无论有无都会返回]
	 */
	public Msg selectPlaylistByTagName(String name) {
		TagNameExample example = new TagNameExample();
		Criteria c = example.createCriteria();
		c.andTagNameEqualTo(name);
		List<TagName> list = tagNameMapper.selectByExample(example);
		if (list.size() == 0)
			return Msg.success().add("list", new ArrayList<Playlist>());

		long tagId = list.get(0).getId();

		PlaylistTagExample example2 = new PlaylistTagExample();
		com.cloudmusic.bean.PlaylistTagExample.Criteria c2 = example2.createCriteria();
		c2.andTagIdEqualTo(tagId);
		List<PlaylistTagKey> list2 = playlistTagMapper.selectByExample(example2);
		if (list2.size() == 0)
			return Msg.success().add("list", new ArrayList<Playlist>());

		List<Long> values = new ArrayList<Long>();
		for (PlaylistTagKey i : list2) {
			values.add(i.getPlaylistId());
		}

		PlaylistExample example3 = new PlaylistExample();
		com.cloudmusic.bean.PlaylistExample.Criteria c3 = example3.createCriteria();
		c3.andIdIn(values);

		List<Playlist> listResult = playlistMapper.selectByExample(example3);

		return Msg.success().add("list", listResult);
	}

	/**
	 * 根据标签名，分页搜索出包含该标签的所有歌单
	 *
	 * @param name
	 *            标签名
	 * @return Msg["list":List{Playlist}:分页的歌单集合，无论有无都会返回]
	 */
	public Msg selectPlaylistPageInfoByTagName(String name, Integer pn) {
		TagNameExample example = new TagNameExample();
		Criteria c = example.createCriteria();
		c.andTagNameEqualTo(name);
		List<TagName> list = tagNameMapper.selectByExample(example);
		if (list.size() == 0)
			return Msg.success().add("list", new PageInfo<Playlist>());

		long tagId = list.get(0).getId();

		PlaylistTagExample example2 = new PlaylistTagExample();
		com.cloudmusic.bean.PlaylistTagExample.Criteria c2 = example2.createCriteria();
		c2.andTagIdEqualTo(tagId);
		List<PlaylistTagKey> list2 = playlistTagMapper.selectByExample(example2);
		if (list2.size() == 0)
			return Msg.success().add("list", new PageInfo<Playlist>());

		List<Long> values = new ArrayList<Long>();
		for (PlaylistTagKey i : list2) {
			values.add(i.getPlaylistId());
		}

		PlaylistExample example3 = new PlaylistExample();
		com.cloudmusic.bean.PlaylistExample.Criteria c3 = example3.createCriteria();
		c3.andIdIn(values);

		// 指定PageHelper当前页,每页显示长度
		PageHelper.startPage(pn, 18);

		List<Playlist> listResult = playlistMapper.selectByExample(example3);

		// 分页显示歌单
		PageInfo<Playlist> playlistPageInfo = new PageInfo<Playlist>(listResult, 5);

		return Msg.success().add("list", playlistPageInfo);
	}

	public Msg selectSongByTagName(String name) {
		TagNameExample example = new TagNameExample();
		Criteria c = example.createCriteria();
		c.andTagNameEqualTo(name);
		List<TagName> list = tagNameMapper.selectByExample(example);
		if (list.size() == 0)
			return Msg.success().add("list", new ArrayList<Song>());

		long tagId = list.get(0).getId();

		SongTagExample example2 = new SongTagExample();
		com.cloudmusic.bean.SongTagExample.Criteria c2 = example2.createCriteria();
		c2.andTagIdEqualTo(tagId);
		List<SongTagKey> list2 = songTagMapper.selectByExample(example2);
		if (list2.size() == 0)
			return Msg.success().add("list", new ArrayList<Song>());

		List<Long> values = new ArrayList<Long>();
		for (SongTagKey i : list2) {
			values.add(i.getSongId());
		}

		SongExample example3 = new SongExample();
		com.cloudmusic.bean.SongExample.Criteria c3 = example3.createCriteria();
		c3.andIdIn(values);
		List<Song> listResult = songMapper.selectByExample(example3);
		return Msg.success().add("list", listResult);
	}

	public Msg selectPlaylistPageInfoByTagNameList(List<String> list_tagName, Integer pn) {
		// 取出第一个标签的所有歌单
		Msg msg = this.selectPlaylistByTagName(list_tagName.get(0));
		@SuppressWarnings("unchecked")
		List<Playlist> list = (List<Playlist>) msg.getMap().get("list");
		if (list.size() <= 0)
			return Msg.success().add("list", new PageInfo<Playlist>(new ArrayList<Playlist>(), 5));

		// 把传入的标签，从数据库中取出它们对应的ID
		TagNameExample example1 = new TagNameExample();
		Criteria c1 = example1.createCriteria();
		c1.andTagNameIn(list_tagName);
		List<TagName> list2 = tagNameMapper.selectByExample(example1);

		// result集用来存储结果
		List<Playlist> result = new ArrayList<Playlist>();
		for (int i = 0; i < list.size(); i++) {
			long playlistId = list.get(i).getId();
			boolean canAdd = true;
			for (int j = 0; j < list2.size(); j++) {
				long tagId = list2.get(j).getId();

				PlaylistTagExample example2 = new PlaylistTagExample();
				com.cloudmusic.bean.PlaylistTagExample.Criteria c2 = example2.createCriteria();
				c2.andPlaylistIdEqualTo(playlistId);
				c2.andTagIdEqualTo(tagId);
				if (playlistTagMapper.selectByExample(example2).size() <= 0) {
					// 说明这首歌没包含tagId的标签
					canAdd = false;
					break;
				}

			}
			if (canAdd) {
				result.add(list.get(i));
			}
		}
		// 指定PageHelper当前页,每页显示长度
		PageHelper.startPage(pn, 18);

		// 分页显示歌单
		PageInfo<Playlist> playlistPageInfo = new PageInfo<Playlist>(result, 5);

		return Msg.success().add("list", playlistPageInfo);
	}

	public Msg selectByTagName(String name) {
		TagNameExample example = new TagNameExample();
		com.cloudmusic.bean.TagNameExample.Criteria criteria = example.createCriteria();
		criteria.andTagNameEqualTo(name);
		TagName tagName = tagNameMapper.selectByExample(example).get(0);
		if (null == name)
			return Msg.error().add("msg", "获取标签失败");
		return Msg.success().add("tagName", tagName);
	}
}
