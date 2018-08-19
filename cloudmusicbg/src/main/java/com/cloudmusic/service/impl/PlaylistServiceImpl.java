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
	PlaylistMapper playlistMapper; // �赥Dao

	@Autowired
	ListenMapper listenMapper;

	@Autowired
	ReceiveMapper receiveMapper; // ����Dao

	@Autowired
	PlaylistCollectionMapper playlistCollectionMapper; // �赥�ղ�Dao

	@Autowired
	PlaylistTagMapper playlistTagMapper;

	@Autowired
	SqlSessionFactory sqlSessionFactory; // ������

	@Autowired
	ITagService tagService;// ��ǩ������

	@Autowired
	SongMapper songMapper;

	@Autowired
	UserMapper userMapper;

	/**
	 * ����һ���µĸ赥��
	 *
	 * @return Msg��map�У�������������msg��������ʾ��Ϣ����playlist��Ϊ�ղ����playlist������idֵΪ���ݿ������Ľ��
	 */
	public Msg insertPlaylist(Playlist playlist) {
		int num = playlistMapper.insert(playlist);
		if (num > 0) {
			Msg msg = Msg.success();
			msg.add("msg", "�����赥�ɹ�");
			msg.add("playlist", playlist);
			return msg;
		}
		return Msg.error().add("msg", "����赥ʧ��");
	}

	/**
	 * �½�һ���赥������Ϊ����赥���ñ�ǩ
	 *
	 * @param playlist
	 *            �赥
	 * @param tagList
	 *            ��ǩ����
	 * @return Msg.error��ʾ�½��赥ʧ��<br/>
	 *         Msg.success��ʾ�ɹ�.["playlist":Playlist:����һ���ո��½��ɹ���Playlist����]
	 */
	public Msg insertPlaylistWithTagName(Playlist playlist, List<TagName> tagList) {

		if (tagList.size() == 0)
			return Msg.error();

		// �漰���һ���ԵĲ���ʱ��ʹ���������ֶ�
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
					throw new Exception("Ϊ�赥���ñ�ǩ�����г�������ع�");
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
	 * ������ ���� �赥
	 *
	 * @param playlistId
	 *            �赥ID
	 * @param songIds
	 *            Ҫ����ĸ���ID���鼯��
	 * @return Msg������map������1����msg������ʾ��Ϣ
	 */
	public Msg insertPlaylistWithSong(Long playlistId, List<Long> songIds) {
		SqlSession sqlSession = sqlSessionFactory.openSession(false);
		try {

			for (int i = 0; i < songIds.size(); i++) {
				ReceiveKey receive = new ReceiveKey();
				receive.setPlaylistId(playlistId);
				receive.setSongId(songIds.get(i));
				receiveMapper.insert(receive);
				
				//��Ӧ�������ղش���+1
				Song song = songMapper.selectByPrimaryKey(songIds.get(i));
				long times = song.getCollection();
				times++;
				song.setCollection(times);
				songMapper.updateByPrimaryKey(song);
			}

			sqlSession.commit();
			return Msg.success().add("msg", "�ɹ�����" + songIds.size() + "������");
		} catch (Exception e) {
			sqlSession.rollback();
			return Msg.error().add("msg", "����赥Ϊ" + playlistId + "ʱ��������");
		} finally {
			sqlSession.close();
		}

	}

	/**
	 * ����example�����Ҹ赥
	 *
	 * @param example
	 *            PlaylistExample�Ķ���
	 * @return Msg��������,"list" ���赥�ļ���
	 */
	public Msg selectPlaylistByExample(PlaylistExample example) {
		List<Playlist> list = playlistMapper.selectByExample(example);
		if (list == null) {
			return Msg.error();
		}
		return Msg.success().add("list", list);
	}

	/**
	 * Ѱ�����Ÿ赥
	 *
	 * @return Msg["list":���Ÿ赥�ļ���]
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
	 * �������Ӹ赥��ɾ��
	 *
	 * @param id
	 *            �赥ID
	 * @param songIds
	 *            ����ID����
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
			return Msg.success().add("msg", "�ɹ�ɾ��" + songIds.size() + "������");
		} catch (Exception e) {
			sqlSession.rollback();
			return Msg.error().add("msg", "�赥IDΪ" + id + "����ɾ��ĳЩ����ʱ��������");
		} finally {
			sqlSession.close();
		}
	}

	/*
	 * ĳ���赥�Ĳ��Ŵ���+1
	 *
	 * @param id �赥ID
	 *
	 * @return Msg["msg":������Ϣ]
	 */
	public Msg addPlaytimeOfPlaylist(long id) {
		Playlist playlist = playlistMapper.selectByPrimaryKey(id);
		if (playlist == null)
			return Msg.error().add("msg", "û������赥");

		playlist.setPlayTimes(playlist.getPlayTimes() + 1);
		int num = playlistMapper.updateByPrimaryKeySelective(playlist);
		if (num > 0)
			return Msg.success().add("msg", "�ɹ�");

		return Msg.error().add("msg", "���¸赥���ݿ��ʱʧ��");
	}

	/*
	 * ĳ���赥���ղش���+1
	 *
	 * @param id �赥ID
	 *
	 * @return Msg["msg":������Ϣ]
	 */
	public Msg addCollectionOfPlaylist(long id) {
		Playlist playlist = playlistMapper.selectByPrimaryKey(id);
		if (playlist == null)
			return Msg.error().add("msg", "û������赥");

		playlist.setCollection(playlist.getCollection() + 1);
		int num = playlistMapper.updateByPrimaryKeySelective(playlist);
		if (num > 0)
			return Msg.success().add("msg", "�ɹ�");

		return Msg.error().add("msg", "���¸赥���ݿ��ʱʧ��");
	}

	/*
	 * ĳ���赥���ղش���-1
	 *
	 * @param id �赥ID
	 *
	 * @return Msg["msg":������Ϣ]
	 */
	public Msg subCollectionOfPlaylist(long id) {
		Playlist playlist = playlistMapper.selectByPrimaryKey(id);
		if (playlist == null)
			return Msg.error().add("msg", "û������赥");

		playlist.setCollection(playlist.getCollection() - 1);
		int num = playlistMapper.updateByPrimaryKeySelective(playlist);
		if (num > 0)
			return Msg.success().add("msg", "�ɹ�");

		return Msg.error().add("msg", "���¸赥���ݿ��ʱʧ��");
	}

	/**
	 * ���ݸ赥IDɾ���赥
	 *
	 * @param id
	 *            �赥ID
	 * @return Msg[]
	 */
	public Msg deletePlaylistById(long id) {
		return (playlistMapper.deleteByPrimaryKey(id) > 0) ? Msg.success() : Msg.error();
	}

	/**
	 * �û��ղظ赥
	 *
	 * @param userId
	 *            �û�ID
	 * @param playlistId
	 *            �赥ID
	 * @return Msg[]
	 */
	public Msg insertPlaylistCollection(long userId, long playlistId) {

		PlaylistCollectionKey record = new PlaylistCollectionKey();
		record.setUserId(userId);
		record.setPlaylistId(playlistId);
		int num = playlistCollectionMapper.insert(record);
		if (num > 0) {
			// �赥�ղ���+1
			this.addCollectionOfPlaylist(playlistId);
		}
		return (num > 0) ? Msg.success() : Msg.error();
	}

	/**
	 * ����ĳһ���û����ղصĸ赥����
	 *
	 * @param userId
	 *            �û�ID
	 * @return Msg["list":List<Playlist>:���ĸ赥����]
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
	 * �����û�����������ϲ���ĸ赥
	 *
	 * @param userId
	 *            �û�ID
	 * @return Msg["list":List<Playlist>:�赥����]
	 */
	public Msg findFavoritePlaylistByUserId(long userId) {
		Msg msg = tagService.findFavoriteTagByUserId(userId);
		@SuppressWarnings("unchecked")
		List<TagName> list = (List<TagName>) msg.getMap().get("list");
		if (list.size() == 0)
			return Msg.success().add("list", new ArrayList<Playlist>());

		int tagSize = list.size();// ��¼��ǩ����

		// ȡ����һ����ǩ�����и赥
		@SuppressWarnings("unchecked")
		List<Playlist> list2 = (List<Playlist>) tagService.selectPlaylistByTagName(list.get(0).getTagName()).getMap().get("list");
		if (list2.size() == 3)
			return Msg.success().add("list", list2);
		else if (list2.size() > 3) {
			// �赥����3��ʱ���������������û�����3��
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
						// �ø赥���������ǩ
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
			// �赥С��3��ʱ�����ѳ�����Щ��Ϊ�Ƽ��������������������û��ĸ赥
			List<Playlist> listResult = new ArrayList<Playlist>();
			for (int i = 0; i < list2.size(); i++) {
				listResult.add(list2.get(i));
			}
			int remain = 3 - listResult.size();// ����ʣ�»���Ҫ�Ƽ��ĸ�

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
						// ���֮ǰ�Ѵ�������赥ID
						int count = map4.get(playlistId);
						count++;
						map4.put(playlistId, count);
					} else {
						// ��һ�γ�������赥ID
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
			// �Ը赥����ǰ�������Ƽ���ǰremain���赥

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

	// һ����ʱд����,����findFavoritePlaylistByUserId����
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
	 * ����һ���赥�б������ȶ����������򣬵�һ��Ϊ��������
	 *
	 * @param list
	 *            ��Ҫ����ĸ赥����
	 * @return Msg["list":List{Playlist}:�ź���ĸ赥����]
	 */
	public Msg sortPlaylistBasisHot(List<Playlist> list) {
		Collections.sort(list, hotComparator);

		return Msg.success().add("list", list);
	}

	/**
	 * ���ݸ赥ID�ҳ������ɵ����и���
	 *
	 * @param playlistId
	 *            �赥ID
	 * @return Msg.error()��ʾ�ø赥û�����ɸ��� <br/>
	 *         Msg.success() Msg["list":List{Song}:��������]
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
	 * ��ѯ���еĸ赥
	 *
	 * @return Msg[msg:1�����ڸ���������list���ϣ�2�������ڸ赥��������ʾ��Ϣ]
	 */
	public Msg selectPlayList(Integer pn) {

		// ָ��PageHelper��ǰҳ,ÿҳ��ʾ����
		PageHelper.startPage(pn, 18);

		List<Playlist> list = playlistMapper.selectByExample(null);

		// ��ȡÿ���赥�Ĵ������û���
		for (Playlist playlist : list) {
			playlist.setUserName(userMapper.selectByPrimaryKey(playlist.getUserId()).getNickname());
		}

		PageInfo<Playlist> playlistPageInfo = new PageInfo<Playlist>(list, 5);

		if (list.size() == 0 || list == null) {
			return Msg.error().add("msg", "û�и赥");
		}
		return Msg.success().add("msg", playlistPageInfo);
	}

	/**
	 * ���ݸ赥id��ȡ�赥��Ϣ
	 *
	 * @param playlistId
	 * @return Msg["msg" : error:��ʾ��Ϣ��success���赥��Ϣ]
	 */
	public Msg selectPlayListByPlayListId(long playlistId) {
		PlaylistExample example = new PlaylistExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(playlistId);
		List<Playlist> list = playlistMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return Msg.error().add("msg", "�޴˸赥��");
		}
		return Msg.success().add("msg", list.get(0));
	}

	/**
	 * ��ѯ�赥�����ղ��Ŵ�����������
	 *
	 * @return Msg["list" : �����Ŵ�����������ĸ赥�б�]
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
	 * ��ѯ�赥�������ղش�����������
	 *
	 * @return Msg["list" : ���ղش�����������ĸ赥�б�]
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
	 * ���ո��ֵĸ貥�Ŵ������н�������
	 * 
	 * @return Msg["list" : �����ֵĸ������Ŵ������н�������ĸ��ּ���]
	 */
	public Msg selectSingerDescByPlayTimes() {

		List<Song> songlist = songMapper.selectByExample(null);
		if (songlist == null || songlist.size() == 0) {
			return Msg.error();
		}
		// allSinger����ȫ������
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
		// songList���潵������ò������ǵĸ������б����ŵĸ��ּ���
		List<String> singerList = new ArrayList<String>();
		for (int i = 0; i < sortList.size(); i++) {
			singerList.add(sortList.get(i).getKey());
		}
		// ��������֣�����δ������������singerList��
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

		int tagSize = list_tag.size();// ��¼�����ǩ�ĸ���
		Set<Long> playlist_set = new HashSet<Long>();
		Map<Long, Integer> times_map = new HashMap<Long, Integer>();

		// ����ȡ��ÿ����ǩ�����и赥
		for (int i = 0; i < tagSize; i++) {
			// ���ݵ�i����ǩȡ���赥
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
			
			//1.��ɾ���ø赥ԭ�еı�ǩ
			PlaylistTagExample example1 = new PlaylistTagExample();
			com.cloudmusic.bean.PlaylistTagExample.Criteria c1 = example1.createCriteria();
			c1.andPlaylistIdEqualTo(playlistId);
			playlistTagMapper.deleteByExample(example1);
			
			//2.Ȼ��Ϊ�ø赥�����±�ǩ
			//2.1 ���������ǿգ���ֱ�ӷ���
			if(list_tagName.size() <= 0)
				return Msg.success();
			
			
			for(String name : list_tagName)
			{
				Msg msg = tagService.selectByTagName(name);
				TagName tagName = (TagName) msg.getMap().get("tagName");
				
				//�������ݿ�
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
