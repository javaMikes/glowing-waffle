package com.cloudmusic.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cloudmusic.bean.Listen;
import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.PlaylistComment;
import com.cloudmusic.bean.Singer;
import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.TagName;
import com.cloudmusic.service.IPlaylistCommentService;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ISingerService;
import com.cloudmusic.service.ISongService;
import com.cloudmusic.service.IUserService;
import com.cloudmusic.util.MD5Util;
import com.cloudmusic.util.Msg;
import com.cloudmusic.util.SendMailUtil;
import com.github.pagehelper.PageInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class JUnitTest {

	@Autowired
	ISongService songService;

	@Autowired
	IUserService userService;

	@Autowired
	IPlaylistService playlistService;

	@Autowired
	ISingerService singerService;

	@Autowired
	IPlaylistCommentService playlistCommentService;

	// @Test
	// mybatis�����򹤳�
	public void test() {
		try {
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			File configFile = new File("mbg.xml");
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config;
			config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	// Test�����õ�
	public void test2() {

	}

	@Test
	public void test3() {
		SendMailUtil.send("18676042181@163.com", "��ӭע��������ƽ̨�������������ӽ��м���");
	}

	// md5����
	@Test
	public void md5Test() {
		System.out.println(MD5Util.encode2hex("123456"));
	}

	// ���Ը����û�id��ȡ�������ĸ���
	@Test
	public void testGetSongListenedByUserId() {
		Msg msg = songService.selectSongListenedByUserId(2);
		if (msg.getCode() == 100) {
			List<Song> list = (List<Song>) msg.getMap().get("list");
			if (list != null) {
				for (int i = 0; i < list.size(); i++)
					System.out.println(list.get(i).getSongName());
			} else {
				System.out.println("�������¼");
			}
		}
	}

	// ���Ը����û�id��ȡ���ղصĸ���
	@Test
	public void testGetSongCollectedByUserId() {
		Msg msg = songService.selectSongCollectedByUserId(2);
		System.out.println(msg.getCode());
		if (msg.getCode() == 100) {
			List<Song> list = (List<Song>) msg.getMap().get("list");
			if (list != null) {
				for (int i = 0; i < list.size(); i++)
					System.out.println(list.get(i).getSongName());
			} else {
				System.out.println("���ղأ�");
			}
		}
	}

	// ���Ը����û�id�Լ�����id��ĳ�׸������Ŵ�����1
	@Test
	public void testAddPlaytimeOfSong() {
		Msg msg = songService.addOnePlaytimeOfSong(2, 2);
		if (msg.getCode() == 100) {
			System.out.println("�����ɹ�");
		}
	}

	// ����ĳ�û��ղ�ĳ�׸���
	@Test
	public void testAddCollectionOfSong() {
		Msg msg = songService.addCollectionOfSong(2, 6);
		if (msg.getCode() == 100) {
			System.out.println("�����ɹ���");
		}
	}

	// ����ĳ�û�ȡ���ղ�ĳ�׸���
	@Test
	public void testSubCollectionOfSong() {
		Msg msg = songService.subCollectionOfSong(2, 2);
		System.out.println(msg.getCode());
	}

	// ����ĳ�׸������ŵĴ���
	@Test
	public void testSelectTimesPlayOfSong() {
		Msg msg = songService.selectTimesPlayOfSong(1);
		if (msg.getCode() == 100) {
			System.out.println(msg.getMap().get("sum"));
		}
	}

	// ���Ը����û�id������id���ж�ĳ���û��Ƿ�������ĳ������
	@Test
	public void testIsListenedTheSong() {
		Msg msg = songService.isListenedTheSong(2, 3);
		// if(msg.getCode() == 100){
		System.out.println(msg.getMap().get("msg"));
		// }
	}

	@Test
	public void testIsCollectTheSong() {
		Msg msg = songService.isCollectTheSong(2, 2);
		// if(msg.getCode() == 100){
		System.out.println(msg.getMap().get("msg"));
		// }
	}

	@Test
	public void testRankSongListenTimes() {
		Msg msg = songService.rankSongListenTimes(2);
		System.out.println(msg.getCode());
		if (msg.getCode() == 100) {
			List<Listen> list = (List<Listen>) msg.getMap().get("list");
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getTimes());
			}
		}
	}

	@Test
	public void testSelectSongBySinger() {
		Msg msg = songService.selectSongBySinger("�ܽ���");
		System.out.println(msg.getCode());
		if (msg.getCode() == 100) {
			List<Song> list = (List<Song>) msg.getMap().get("list");
			for (int i = 0; i < list.size(); i++)
				System.out.println(list.get(i).getSongName());
		}
	}

	@Test
	public void testSelectSongBySongName() {
		Msg msg = songService.selectSongBySongName("��");
		if (msg.getCode() == 100) {
			List<Song> list = (List<Song>) msg.getMap().get("list");
			for (int i = 0; i < list.size(); i++)
				System.out.println(list.get(i).getSongName());
		}
	}

	@Test
	public void testSelectPlayListDescByPlayTimes() {
		Msg msg = playlistService.selectPlayListDescByPlayTimes();
		if (msg.getCode() == 100) {
			List<Playlist> list = (List<Playlist>) msg.getMap().get("list");
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getListName());
			}
		}
	}

	@Test
	public void testSelectPlayListDescByCollection() {
		Msg msg = playlistService.selectPlayListDescByCollection();
		if (msg.getCode() == 100) {
			List<Playlist> list = (List<Playlist>) msg.getMap().get("list");
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getListName());
			}
		}
	}

	@Test
	public void testSelectSingerDescByPlatTimes() {
		Msg msg = playlistService.selectSingerDescByPlayTimes();
		if (msg.getCode() == 100) {
			List<String> list = (List<String>) msg.getMap().get("list");
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
	}

	// ���ݸ������Ŵ������н�������
	// @Test
	// public void selectSongDescByPlayTimes() {
	// List<Song> songlist = songService.selectSongDescByPlayTimes();
	// for (Song song : songlist) {
	// System.out.println(song.getId());
	// }
	// }

	@Test
	public void testSelectSingerBySingerName() {
		Msg msg = singerService.selectSingerBySingerName("�ܽ���");
		if (msg.getCode() == 100) {
			Singer singer = (Singer) msg.getMap().get("list");
			System.out.println(singer.getIntroduction());
		}
	}

	// ��ʾ���۲���
	@Test
	public void testSelectCommentByPlaylistId() {
		Msg msg = playlistCommentService.selectCommentByPlaylistId(5, 1);
		if (msg.getCode() == 100) {
			PageInfo<PlaylistComment> pageInfo = (PageInfo<PlaylistComment>) msg.getMap().get("list");
			for (PlaylistComment playlistComment : pageInfo.getList()) {
				System.out.println(playlistComment.getParentId());
			}
		}
	}

	// ��ظ赥�Ƽ�����
	@Test
	public void testFindNicePlaylistByTagName() {
		List<TagName> tagNameList = new ArrayList<TagName>();
		TagName tagName1 = new TagName();
		tagName1.setTagName("����");
		TagName tagName2 = new TagName();
		tagName2.setTagName("����");
		tagNameList.add(tagName1);
		tagNameList.add(tagName2);
		Msg msg = playlistService.findNicePlaylistByTagName(tagNameList);
		if (msg.getCode() == 100) {
			System.out.println(msg.getMap().get("list"));
		}
	}

	/**
	 * �����û�id���������ĸ������������ٽ�������
	 *
	 * @param userId
	 * @return
	 */
	@Test
	public void rankSongListenTimes() {
		Msg msg = songService.rankSongListenTimes(72L);
		@SuppressWarnings("unchecked")
		List<Listen> songList = (List<Listen>) msg.getMap().get("list");
		for (Listen song : songList) {
			System.out.println(song.getTimes());
		}
	}

	// �����û�id����ȡ���û�δ���ĸ赥���ۼ���
	@Test
	public void testSelectInformByUserId() {
		Msg msg = playlistCommentService.selectInformByUserId(72);
		@SuppressWarnings("unchecked")
		List<PlaylistComment> list = (List<PlaylistComment>) msg.getMap().get("list1");
		System.out.println(list.size());
	}

	// �����û�id����ȡ���û�δ���ĸ赥���ۼ���
	@Test
	public void findFavoriteSongByUserId() {
		Msg msg = songService.findFavoriteSongByUserId(72L);
		@SuppressWarnings("unchecked")
		List<Song> list = (List<Song>) msg.getMap().get("list");
		System.out.println(list.size());
	}
}
