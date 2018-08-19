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
	 * �ϴ�����
	 *
	 * @param song
	 * @return Msg ����map(����"msg": ֵ ������ʾ��Ϣ�� )
	 */
	public Msg uploadSong(Song song) {
		int num = songMapper.insert(song);
		if (num > 0) {
			return Msg.success().add("msg", "�����ϴ��ɹ���");
		}
		return Msg.error().add("msg", "�����ϴ�ʧ�ܣ�");
	}

	/**
	 * ͨ���û�id��ѯ�û��������ĸ���
	 *
	 * @param userid
	 * @return Msg["list" : List<Song>�������ĸ����б�]
	 */
	public Msg selectSongListenedByUserId(long userId) {
		ListenExample example = new ListenExample();
		com.cloudmusic.bean.ListenExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);

		List<Listen> list = listenMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return Msg.error().add("msg", "���û�û���������κθ���Ŷ��");
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
	 * ͨ���û�id��ѯ�û��ղع��ĸ���
	 *
	 * @param userId
	 * @return Msg["list":List<Song>:�û������ղؼ���]
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
	 * �����û�id�Լ�����id��ĳ�׸������Ŵ�����1
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "��ʾ��Ϣ"]
	 */
	public Msg addOnePlaytimeOfSong(long userId, long songId) {

		Song song = songMapper.selectByPrimaryKey(songId);
		song.setPlayTimes(song.getPlayTimes() + 1);
		int nums = songMapper.updateByPrimaryKey(song);
		if (nums > 0) {
			if (userId > 0) {// �û����ڵ�¼״̬
				ListenExample example = new ListenExample();
				com.cloudmusic.bean.ListenExample.Criteria criteria = example.createCriteria();
				criteria.andUserIdEqualTo(userId);
				criteria.andSongIdEqualTo(songId);
				List<Listen> list = listenMapper.selectByExample(example);
				if (list.size() == 0) {
					// �û�֮ǰ��δ�����ø���������listen��������һ����¼����������������Ϊ1
					Listen listen = new Listen();
					listen.setUserId(userId);
					listen.setSongId(songId);
					listen.setTimes(1L);
					int num = listenMapper.insert(listen);
					if (num > 0) {
						return Msg.success().add("msg", "�������ݿ�ɹ���");
					}
					return Msg.error().add("msg", "�������ݿ�ʧ�ܣ�");
				}
				Listen listen = list.get(0);// ȡ����һ����¼
				listen.setTimes(list.get(0).getTimes() + 1);
				int num = listenMapper.updateByPrimaryKey(listen);
				if (num > 0)
					return Msg.success().add("msg", "�������ݿ�ɹ���");
				return Msg.error().add("msg", "�������ݿ�ʧ�ܣ�");
			}
			return Msg.success().add("msg", "�������ݿ�ɹ���");
		} else
			return Msg.error().add("msg", "�������ݿ�ʧ�ܣ�");

	}

	/**
	 * ĳ�û��ղ�ĳ�׸���
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "��ʾ��Ϣ"]
	 */
	public Msg addCollectionOfSong(long userId, long songId) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
		
			SongCollectionKey songCollectionKey = new SongCollectionKey();
			songCollectionKey.setUserId(userId);
			songCollectionKey.setSongId(songId);
	
			int num = songCollectionMapper.insert(songCollectionKey);
			
			if (num <= 0)
				throw new Exception("����ʧ�ܣ���Ϊ�û����ղ��˸ø���");
			
			//�޸ĸ����ղ���+1
			Song song = songMapper.selectByPrimaryKey(songId);
			long times = song.getCollection();
			times++;
			song.setCollection(times);
			
			if(songMapper.updateByPrimaryKey(song) <= 0)
				throw new Exception("�ղ�ʧ�ܣ����ֱ��޸�ʧ��");
			
		}catch(Exception e)
		{
			sqlSession.rollback();
			return Msg.error().add("msg", e.getMessage());
		}finally {
			sqlSession.close();
		}
		
		return Msg.success().add("msg", "�ղسɹ�");
	}

	/**
	 * ĳ�û�ȡ���ղ�ĳ�׸���
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "��ʾ��Ϣ"]
	 */
	public Msg subCollectionOfSong(long userId, long songId) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
		
			SongCollectionKey songCollectionKey = new SongCollectionKey();
			songCollectionKey.setSongId(songId);
			songCollectionKey.setUserId(userId);
	
			int num = songCollectionMapper.deleteByPrimaryKey(songCollectionKey);
			if (num <= 0) {
				throw new Exception("ȡ���ղ�ʧ�ܣ���Ϊ���û�δ�ղظø���");
			}
			
			//�����ղش�����1
			Song song = songMapper.selectByPrimaryKey(songId);
			long times = song.getCollection();
			times--;
			song.setCollection(times);
			if(songMapper.updateByPrimaryKey(song) <= 0)
				throw new Exception("ȡ���ղ�ʧ�ܣ��������ֱ�ʱʧ��");
		
		}catch(Exception e)
		{
			sqlSession.rollback();
			return Msg.error().add("msg", e.getMessage());
		}finally {
			sqlSession.close();
		}
		return Msg.success().add("msg", "ȡ���ղسɹ�");
	}

	/**
	 * ���ݸ���id�����ø��������ŵĴ���
	 *
	 * @param songId
	 * @return Msg(������������"msg"����ʾ��Ϣ�� "sum" �� ���Ŵ���)
	 */
	public Msg selectTimesPlayOfSong(long songId) {
		ListenExample example = new ListenExample();
		com.cloudmusic.bean.ListenExample.Criteria criteria = example.createCriteria();
		criteria.andSongIdEqualTo(songId);
		List<Listen> list = listenMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return Msg.error().add("msg", "�ø���δ����������");
		}
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i).getTimes();
		}
		return Msg.success().add("sum", sum);
	}

	/**
	 * �����û�id������id���ж�ĳ���û��Ƿ�������ĳ������
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg" : true(������)��false(δ������)]
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
	 * �����û�id������id���ж�ĳ���û��Ƿ��ղ�ĳ�׸���
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg" : true(���ղ�)��false(δ�ղ�)]
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
	 * �����û�id���������ĸ������������ٽ�������
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
			return Msg.error().add("msg", "���û�δ����������");
		// ʹ�ÿ��������㷨��������
		quickSort(list, 0, list.size() - 1);
		return Msg.success().add("list", list);

	}

	// ʹ�ÿ������򰴸�������������������
	public void quickSort(List<Listen> list, int high, int low) {
		int i, j;
		long index, index2;
		i = high;// �߶��±�
		j = low;// �Ͷ��±�
		index = list.get(i).getTimes(); // ���ӱ�ĵ�һ����¼����׼
		index2 = list.get(i).getSongId();
		while (i < j) { // �ݹ����:low>=high
			while (i < j && list.get(j).getTimes() < index)// ��˱�indexС�����Ͻ��򣬲�������low�±�ǰ��
				j--;// while���ָ��temp����Ǹ�
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
		} // while�꣬����һ������
		list.get(i).setSongId(index2);
		list.get(i).setTimes(index);// ��indexֵ�ŵ������ڵ�λ�á�
		if (high < i)
			quickSort(list, high, i - 1); // �����������ݹ�
		if (i < low)
			quickSort(list, i + 1, low); // ���Ҷ�������ݹ�
	}

	/**
	 * ���ݸ�����������
	 *
	 * @param singer
	 * @return Msg����������[msg : "��ʾ��Ϣ"]; ["list" : List<Song>�����б�]
	 */
	public Msg selectSongBySinger(String singer) {
		SongExample example = new SongExample();
		com.cloudmusic.bean.SongExample.Criteria criteria = example.createCriteria();
		criteria.andSingerEqualTo(singer);
		List<Song> list = songMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return Msg.error().add("msg", "δ�ҵ��ø��ֵĸ���");
		return Msg.success().add("list", list);
	}

	/**
	 * ���ݸ���ģ����������
	 *
	 * @param songName
	 * @return Msg����������[msg : "��ʾ��Ϣ"]; ["list" : List<Song>�����б�]
	 */
	public Msg selectSongBySongName(String songName) {
		SongExample example = new SongExample();
		com.cloudmusic.bean.SongExample.Criteria criteria = example.createCriteria();
		criteria.andSongNameLike("%" + songName + "%");
		List<Song> list = songMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return Msg.error().add("msg", "δƥ�䵽����");
		return Msg.success().add("list", list);
	}

	// �Ƚ�����
	private Comparator hotComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			Song s1 = (Song) o1;
			Song s2 = (Song) o2;

			long songId1 = s1.getId();// ����id
			long songId2 = s2.getId();

			ListenExample example = new ListenExample();
			com.cloudmusic.bean.ListenExample.Criteria criteria = example.createCriteria();
			criteria.andSongIdEqualTo(songId1);
			List<Listen> list = listenMapper.selectByExample(example);
			long listenTimesSum = 0;// ����s1����������
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
			long listenTimesSum2 = 0L;// ����s2����������
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

			long num1 = listenTimesSum + collectTimesSum; // ��ȡ����s1���Ŵ������ղ���֮��
			long num2 = listenTimesSum2 + collectTimesSum2; // ��ȡ����s2���Ŵ������ղ���֮��

			if (num1 < num2)
				return 1;
			else if (num1 == num2)
				return 0;
			return -1;
		}
	};

	/**
	 * ��ȡ���Ÿ���
	 *
	 * @return Msg[list : List<Song>���Ÿ�������]
	 */
	public Msg getHotSongs() {
		List<Song> list = songMapper.selectByExample(null);
		if (list == null || list.size() == 0)
			return Msg.error().add("msg", "δ�ҵ�������");

		Collections.sort(list, hotComparator);

		return Msg.success().add("list", list);
	}

	/**
	 * ��ȡ���Ÿ���
	 *
	 * @return Msg["list" : List<String> ���Ÿ��ּ���]
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
	 * �����û�id���Ի��Ƽ�����
	 */
	public Msg findFavoriteSongByUserId(Long userId) {
		if (userId == null)
			return Msg.success().add("list", new ArrayList<Song>());
		Msg msg = tagService.findFavoriteTagByUserId(userId);
		List<TagName> list = (List<TagName>) msg.getMap().get("list");
		if (list.size() == 0)
			return Msg.success().add("list", new ArrayList<Song>());

		Set<Long> set = new LinkedHashSet<Long>();// ��¼������id
		Map<Long, Integer> map = new HashMap<Long, Integer>();// ��¼ĳһ��id�ĸ裬�ĸ��Ի��ȼ�
		int totalSize = 0;// ��¼�Ѿ��Ƽ����ĸ�������

		// ѭ������ϲ����ǩȡ�������������ﵽ24��ʱֹͣ
		for (int i = 0; i < list.size(); i++) {
			List<Song> songList = (List<Song>) tagService.selectSongByTagName(list.get(i).getTagName()).getMap().get("list");
			for (int j = 0; j < songList.size(); j++) {

				if (set.contains(songList.get(j).getId())) {
					// ������׸��Ѿ�������Set��
					int times = map.get(songList.get(j).getId());
					times++;
					map.put(songList.get(j).getId(), times);
				} else {
					if (totalSize < 24) {
						// ����µĽ���
						int times = list.size() - i;// �������Ի��Ƽ����ȼ���Խ��ǰ�ı�ǩ�����ȼ�Խ�ߣ�
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
			// �����ŵĽ����ӵ����ؼ�
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

		// ����ȡ��ÿ����ǩ�����и���
		for (int i = 0; i < tagSize; i++) {
			// ���ݵ�i����ǩȡ������
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
			return Msg.error().add("msg", "δ�ҵ��ø��ֵĸ���");
		return Msg.success().add("list", list);
	}

	public Msg updateSongTagByTagNameList(long songId, List<String> list_tag) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			
			//1.��ɾ���ø���ԭ�еı�ǩ
			SongTagExample example1 = new SongTagExample();
			com.cloudmusic.bean.SongTagExample.Criteria c1 = example1.createCriteria();
			c1.andSongIdEqualTo(songId);
			songTagMapper.deleteByExample(example1);
			
			//2.Ȼ��Ϊ�ø��������±�ǩ
			//2.1 ���������ǿգ���ֱ�ӷ���
			if(list_tag.size() <= 0)
				return Msg.success();
			
			
			for(String name : list_tag)
			{
				Msg msg = tagService.selectByTagName(name);
				TagName tagName = (TagName) msg.getMap().get("tagName");
				
				//�������ݿ�
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
	// *����
	// */
	// public List<Song> selectSongDescByPlayTimes() {
	// // ��ȡ���е��û�������¼
	// List<Listen> listenList = listenMapper.selectByExample(new
	// ListenExample());
	// // �洢�����ĸ�����Ϣ
	// List<Song> songList = new ArrayList<Song>();
	//
	// // ��ȡ������¼������и���id(���ظ�)
	// Set<Long> listenSet = new HashSet<Long>();
	// for (Listen listen : listenList) {
	// listenSet.add(listen.getSongId());
	// }
	//
	// // �������и����Ĳ����ܴ�����������map��
	// Map<Long, Long> countMap = new HashMap<Long, Long>();
	// for (Long songId : listenSet) {
	// System.out.println(songId);
	// Long sum = 0L;
	// ListenExample listenExample = new ListenExample();
	// com.cloudmusic.bean.ListenExample.Criteria criteria =
	// listenExample.createCriteria();
	// criteria.andSongIdEqualTo(songId);
	// // ����songId��ȡlisten��¼
	// List<Listen> listenSelected =
	// listenMapper.selectByExample(listenExample);
	// for (Listen listen : listenSelected) {
	// sum += listen.getTimes();
	// }
	// countMap.put(sum, songId);
	// }
	//
	// // ����
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
