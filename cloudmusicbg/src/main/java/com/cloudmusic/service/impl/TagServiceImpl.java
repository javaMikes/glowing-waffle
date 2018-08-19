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
	 * ���ҳ��û���ϲ���ı�ǩ����˳������������һ����ǩ��������ı�ǩ�����һ�����������ı�ǩ
	 * ʵ��˼�룺�����û������ĸ������ղصĸ������ղصĸ赥��3�߽������ϡ�����ǩ������������Ƶ�ʱȽ�
	 *
	 * @param userId
	 *            �û�ID
	 * @return Msg["list":List{TagName}:��ǩ����]
	 */
	public Msg findFavoriteTagByUserId(long userId) {
		Set<Long> tagId_set = new LinkedHashSet<Long>(); // ����û������ı�ǩ����Set������Ϊ�˲��ñ�ǩ�ظ�
		Map<Long, Integer> tagId_times = new HashMap<Long, Integer>(); // ��¼ÿ����ǩ��Ƶ��

		// (1)���Ȳ��Ҹ��û������ĸ���
		ListenExample example1 = new ListenExample();
		com.cloudmusic.bean.ListenExample.Criteria c1 = example1.createCriteria();
		c1.andUserIdEqualTo(userId);
		List<Listen> list1 = listenMapper.selectByExample(example1);
		// �鿴��Щ�����ı�ǩ
		for (int i = 0; i < list1.size(); i++) {
			long times = list1.get(i).getTimes();

			List<Long> listNow = this.findTagNameBySongId(list1.get(i).getSongId());
			for (int v = 0; v < listNow.size(); v++) {

				long o = listNow.get(v);
				if (tagId_set.contains(o)) {
					// ��ǩ�Ѵ��ڣ�ֱ�Ӽ�Ƶ��
					int time = tagId_times.get(o);// ȡ�����ǩ֮ǰ��Ƶ��
					time += times;
					tagId_times.put(o, time);
				} else {
					// ��ǩ������
					tagId_set.add(o);
					tagId_times.put(o, (int) times);
				}
			}
		}

		// (2)���Ҹ��û��ղصĸ�
		SongCollectionExample example2 = new SongCollectionExample();
		com.cloudmusic.bean.SongCollectionExample.Criteria c2 = example2.createCriteria();
		c2.andUserIdEqualTo(userId);
		List<SongCollectionKey> list2 = songCollectionMapper.selectByExample(example2);
		// �鿴��Щ�����ı�ǩ

		for (int i = 0; i < list2.size(); i++) {
			List<Long> listNow = this.findTagNameBySongId(list2.get(i).getSongId());
			for (int v = 0; v < listNow.size(); v++) {
				long o = listNow.get(v);
				if (tagId_set.contains(o)) {
					// ��ǩ�Ѵ��ڣ�ֱ�Ӽ�Ƶ��
					int time = tagId_times.get(o);// ȡ�����ǩ֮ǰ��Ƶ��
					time++;
					tagId_times.put(o, time);
				} else {
					// ��ǩ������
					tagId_set.add(o);
					tagId_times.put(o, 1);
				}
			}
		}

		// ��3�������û��ղصĸ赥

		Msg msg = iplaylistService.selectPlaylistCollectionByUserId(userId);

		@SuppressWarnings("unchecked")
		List<Playlist> list3 = (List<Playlist>) msg.getMap().get("list");

		for (int i = 0; i < list3.size(); i++) {
			List<Long> listNow = this.findTagNameByPlaylistId(list3.get(i).getId());
			for (int v = 0; v < listNow.size(); v++) {
				long o = listNow.get(v);
				if (tagId_set.contains(o)) {
					// ��ǩ�Ѵ��ڣ�ֱ�Ӽ�Ƶ��
					int time = tagId_times.get(o);// ȡ�����ǩ֮ǰ��Ƶ��
					time++;
					tagId_times.put(o, time);
				} else {
					// ��ǩ������
					tagId_set.add(o);
					tagId_times.put(o, 1);
				}
			}
		}

		// (4)�����б�ǩƵ������
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
					// ��ǰ����
					bestTagId = tagId;
					bestTimes = times;
					continue;
				}

			}
			// �ҵ���õģ�����õĲ�����
			Msg msg2 = this.selectTagNameByTagId(bestTagId);
			TagName tagName2 = (TagName) msg2.getMap().get("tagName");
			if (tagName2 == null)
				return Msg.error();

			listResult.add(tagName2);
			// ������õ�����ɾȥ
			tagId_set.remove(bestTagId);
		}

		return Msg.success().add("list", listResult);
	}

	/**
	 * ���ݸ���id���������ı�ǩid
	 *
	 * @param songId
	 *            ����ID
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
	 * ���ݸ赥id���������ı�ǩid
	 *
	 * @param playlistId
	 *            ����ID
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
	 * ���ݱ�ǩid�������ñ�ǩ
	 *
	 * @param id
	 * @return ʧ��error���ɹ�success
	 *
	 *         Msg["tagName":TagName:��ǩ]
	 */
	public Msg selectTagNameByTagId(long id) {
		TagName tagName = tagNameMapper.selectByPrimaryKey(id);
		if (tagName == null)
			Msg.error();

		return Msg.success().add("tagName", tagName);
	}

	/**
	 * ���ҳ����б�ǩ
	 *
	 * @return Msg["list":list{TagName}:��ǩ����]
	 */
	public Msg selectAllTagName() {
		List<TagName> list = tagNameMapper.selectByExample(null);
		if (list == null)
			return Msg.error();

		return Msg.success().add("list", list);
	}

	/**
	 * ���ݱ�ǩ���Ͳ��ҳ�������͵����б�ǩ
	 *
	 * @param type
	 *            ��ǩ��������
	 * @return Msg.errorʱ["msg":String:������Ϣ��ʾ] ; <br/>
	 *         Msg.successʱ��ʾ�ɹ�["list":List{TagName}:�����͵ı�ǩ����]
	 *
	 */
	public Msg selectTagNameType(String type) {
		TagNameExample example1 = new TagNameExample();
		com.cloudmusic.bean.TagNameExample.Criteria c1 = example1.createCriteria();
		c1.andTypeEqualTo(type);
		List<TagName> tag1 = tagNameMapper.selectByExample(example1);
		if (tag1 == null || tag1.size() == 0)
			return Msg.error().add("msg", "��ǩ����Ϊ" + type + "�����Ͳ�����");

		return Msg.success().add("list", tag1);
	}

	/**
	 * ���ݱ�ǩ���������������ñ�ǩ�����и赥
	 *
	 * @param name
	 *            ��ǩ��
	 * @return Msg["list":List{Playlist}:�赥���ϣ��������޶��᷵��]
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
	 * ���ݱ�ǩ������ҳ�����������ñ�ǩ�����и赥
	 *
	 * @param name
	 *            ��ǩ��
	 * @return Msg["list":List{Playlist}:��ҳ�ĸ赥���ϣ��������޶��᷵��]
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

		// ָ��PageHelper��ǰҳ,ÿҳ��ʾ����
		PageHelper.startPage(pn, 18);

		List<Playlist> listResult = playlistMapper.selectByExample(example3);

		// ��ҳ��ʾ�赥
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
		// ȡ����һ����ǩ�����и赥
		Msg msg = this.selectPlaylistByTagName(list_tagName.get(0));
		@SuppressWarnings("unchecked")
		List<Playlist> list = (List<Playlist>) msg.getMap().get("list");
		if (list.size() <= 0)
			return Msg.success().add("list", new PageInfo<Playlist>(new ArrayList<Playlist>(), 5));

		// �Ѵ���ı�ǩ�������ݿ���ȡ�����Ƕ�Ӧ��ID
		TagNameExample example1 = new TagNameExample();
		Criteria c1 = example1.createCriteria();
		c1.andTagNameIn(list_tagName);
		List<TagName> list2 = tagNameMapper.selectByExample(example1);

		// result�������洢���
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
					// ˵�����׸�û����tagId�ı�ǩ
					canAdd = false;
					break;
				}

			}
			if (canAdd) {
				result.add(list.get(i));
			}
		}
		// ָ��PageHelper��ǰҳ,ÿҳ��ʾ����
		PageHelper.startPage(pn, 18);

		// ��ҳ��ʾ�赥
		PageInfo<Playlist> playlistPageInfo = new PageInfo<Playlist>(result, 5);

		return Msg.success().add("list", playlistPageInfo);
	}

	public Msg selectByTagName(String name) {
		TagNameExample example = new TagNameExample();
		com.cloudmusic.bean.TagNameExample.Criteria criteria = example.createCriteria();
		criteria.andTagNameEqualTo(name);
		TagName tagName = tagNameMapper.selectByExample(example).get(0);
		if (null == name)
			return Msg.error().add("msg", "��ȡ��ǩʧ��");
		return Msg.success().add("tagName", tagName);
	}
}
