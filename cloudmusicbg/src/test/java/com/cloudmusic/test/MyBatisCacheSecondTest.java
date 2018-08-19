package com.cloudmusic.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cloudmusic.bean.Playlist;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.util.Msg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MyBatisCacheSecondTest {
	private static final Logger logger = LoggerFactory.getLogger(MyBatisCacheSecondTest.class);

	@Autowired
	private IPlaylistService playlistService;

	/*
	 * redis二级缓存测试
	 */
	@Test
	public void testCache2() {

		// Playlist playlist = new Playlist();
		//
		// playlist.setId(1L);
		//
		// playlist.setListName("无法追忆的青春2");
		//
		// playlistService.updatePlaylistByPrimaryKey(playlist);

		Msg playlistMsg1 = playlistService.selectPlayListByPlayListId(1);
		Playlist playlist1 = (Playlist) playlistMsg1.getMap().get("msg");
		logger.info(playlist1.getListName());

		// PageInfo<Site> page2 = service.querySite("", 2, 2, "", "");
		// logger.info(page2.getList().get(0).getName());
		//
		// PageInfo<Site> page3 = service.querySite("", 1, 2, "", "");
		// logger.info(page3.getList().get(0).getName());
	}

}
