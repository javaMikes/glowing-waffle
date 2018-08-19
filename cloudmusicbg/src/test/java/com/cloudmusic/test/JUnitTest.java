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
	// mybatis的逆向工程
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
	// Test东西用的
	public void test2() {

	}

	@Test
	public void test3() {
		SendMailUtil.send("18676042181@163.com", "欢迎注册云音乐平台，请点击以下链接进行激活");
	}

	// md5测试
	@Test
	public void md5Test() {
		System.out.println(MD5Util.encode2hex("123456"));
	}

	// 测试根据用户id获取其收听的歌曲
	@Test
	public void testGetSongListenedByUserId() {
		Msg msg = songService.selectSongListenedByUserId(2);
		if (msg.getCode() == 100) {
			List<Song> list = (List<Song>) msg.getMap().get("list");
			if (list != null) {
				for (int i = 0; i < list.size(); i++)
					System.out.println(list.get(i).getSongName());
			} else {
				System.out.println("无听歌记录");
			}
		}
	}

	// 测试根据用户id获取其收藏的歌曲
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
				System.out.println("无收藏！");
			}
		}
	}

	// 测试根据用户id以及歌曲id将某首歌曲播放次数加1
	@Test
	public void testAddPlaytimeOfSong() {
		Msg msg = songService.addOnePlaytimeOfSong(2, 2);
		if (msg.getCode() == 100) {
			System.out.println("操作成功");
		}
	}

	// 测试某用户收藏某首歌曲
	@Test
	public void testAddCollectionOfSong() {
		Msg msg = songService.addCollectionOfSong(2, 6);
		if (msg.getCode() == 100) {
			System.out.println("操作成功！");
		}
	}

	// 测试某用户取消收藏某首歌曲
	@Test
	public void testSubCollectionOfSong() {
		Msg msg = songService.subCollectionOfSong(2, 2);
		System.out.println(msg.getCode());
	}

	// 测试某首歌曲播放的次数
	@Test
	public void testSelectTimesPlayOfSong() {
		Msg msg = songService.selectTimesPlayOfSong(1);
		if (msg.getCode() == 100) {
			System.out.println(msg.getMap().get("sum"));
		}
	}

	// 测试根据用户id，歌曲id，判断某个用户是否收听过某个歌曲
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
		Msg msg = songService.selectSongBySinger("周杰伦");
		System.out.println(msg.getCode());
		if (msg.getCode() == 100) {
			List<Song> list = (List<Song>) msg.getMap().get("list");
			for (int i = 0; i < list.size(); i++)
				System.out.println(list.get(i).getSongName());
		}
	}

	@Test
	public void testSelectSongBySongName() {
		Msg msg = songService.selectSongBySongName("七");
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

	// 根据歌曲播放次数进行降序排序
	// @Test
	// public void selectSongDescByPlayTimes() {
	// List<Song> songlist = songService.selectSongDescByPlayTimes();
	// for (Song song : songlist) {
	// System.out.println(song.getId());
	// }
	// }

	@Test
	public void testSelectSingerBySingerName() {
		Msg msg = singerService.selectSingerBySingerName("周杰伦");
		if (msg.getCode() == 100) {
			Singer singer = (Singer) msg.getMap().get("list");
			System.out.println(singer.getIntroduction());
		}
	}

	// 显示评论测试
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

	// 相关歌单推荐测试
	@Test
	public void testFindNicePlaylistByTagName() {
		List<TagName> tagNameList = new ArrayList<TagName>();
		TagName tagName1 = new TagName();
		tagName1.setTagName("华语");
		TagName tagName2 = new TagName();
		tagName2.setTagName("流行");
		tagNameList.add(tagName1);
		tagNameList.add(tagName2);
		Msg msg = playlistService.findNicePlaylistByTagName(tagNameList);
		if (msg.getCode() == 100) {
			System.out.println(msg.getMap().get("list"));
		}
	}

	/**
	 * 根据用户id将其听过的歌曲按次数多少降序排序
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

	// 根据用户id，读取该用户未读的歌单评论集合
	@Test
	public void testSelectInformByUserId() {
		Msg msg = playlistCommentService.selectInformByUserId(72);
		@SuppressWarnings("unchecked")
		List<PlaylistComment> list = (List<PlaylistComment>) msg.getMap().get("list1");
		System.out.println(list.size());
	}

	// 根据用户id，读取该用户未读的歌单评论集合
	@Test
	public void findFavoriteSongByUserId() {
		Msg msg = songService.findFavoriteSongByUserId(72L);
		@SuppressWarnings("unchecked")
		List<Song> list = (List<Song>) msg.getMap().get("list");
		System.out.println(list.size());
	}
}
