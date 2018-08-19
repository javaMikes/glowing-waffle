package com.cloudmusic.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudmusic.bean.Singer;
import com.cloudmusic.bean.Song;
import com.cloudmusic.service.ISingerService;
import com.cloudmusic.service.ISongService;
import com.cloudmusic.util.Msg;

@Controller
public class SingerController {

	@Autowired
	ISingerService singerService;

	@Autowired
	ISongService songService;

	/**
	 * 根据歌手名字获取歌手信息
	 * 
	 * @param singerName
	 * @param model
	 * @return ["singerInfo" 歌手信息,"hotSongList",热门歌单]
	 */
	@RequestMapping("showSingerInfoPage")
	public String showSingerInfoPage(@RequestParam(required = true) String singerName, Model model) {

		Msg msg = singerService.selectSingerBySingerName(singerName);

		if (msg.getCode() == 200) {
			model.addAttribute("message", "查询歌手信息出错");
			return "error";
		}
		Singer singer = (Singer) msg.getMap().get("list");

		// 根据歌手的名字获取该歌手的热门歌曲
		Msg songMsg = songService.selectSingerSongAndHotSort(singerName);

		if (songMsg.getCode() == 200) {
			model.addAttribute("message", "查询热门歌曲信息出错");
			return "error";
		}

		@SuppressWarnings("unchecked")
		List<Song> allSongList = (List<Song>) songMsg.getMap().get("list");
		// 存储前六名热门歌曲
		List<Song> songList = new ArrayList<Song>();

		for (int i = 0; i < allSongList.size() && i < 6; i++) {
			songList.add(allSongList.get(i));
		}

		// 返回歌手信息
		model.addAttribute("singerInfo", singer);
		// 返回热门歌曲
		model.addAttribute("hotSongList", songList);
		return "singer";
	}

	/**
	 * 根据歌手id获取歌手的歌曲
	 * 
	 * @param singerId
	 * @return Msg包含两个键[msg : "提示信息"]; ["list" : List<Song>歌曲列表]
	 */
	@RequestMapping("findSongBySingerId")
	@ResponseBody
	public Msg getSongBySingerId(@RequestParam(required = true) long singerId) {
		Msg msg = singerService.selectSingerById(singerId);
		if (msg.getCode() == 200)
			return msg;

		// 根据歌手id获取歌手信息
		Msg singerMsg = singerService.selectSingerById(singerId);
		Singer singer = (Singer) singerMsg.getMap().get("singer");

		// 根据歌手的名字获取该歌手的歌曲
		Msg songMsg = songService.selectSongBySinger(singer.getSingerName());

		if (songMsg.getCode() == 200)
			return songMsg;

		return songMsg;
	}

	/**
	 * 根据歌手id获取歌手的热门歌曲
	 * 
	 * @param singerId
	 * @return ["hotSongList",前六位热门歌曲集合]
	 */
	@RequestMapping("findHotSongBySingerId")
	@ResponseBody
	public Msg getHotSongBySingerId(@RequestParam(required = true) long singerId) {
		Msg msg = singerService.selectSingerById(singerId);
		if (msg.getCode() == 200)
			return msg;

		// 根据歌手id获取歌手信息
		Msg singerMsg = singerService.selectSingerById(singerId);
		Singer singer = (Singer) singerMsg.getMap().get("singer");

		// 根据歌手的名字获取该歌手的热门歌曲
		Msg songMsg = songService.selectSingerSongAndHotSort(singer.getSingerName());

		if (songMsg.getCode() == 200)
			return songMsg;

		@SuppressWarnings("unchecked")
		List<Song> allSongList = (List<Song>) songMsg.getMap().get("list");
		// 存储前六名热门歌曲
		List<Song> songList = new ArrayList<Song>();

		for (int i = 0; i < allSongList.size() && i < 6; i++) {
			songList.add(allSongList.get(i));
		}

		return Msg.success().add("hotSongList", songList);
	}

	/**
	 * 根据歌手id显示歌手图像
	 * 
	 * @param singerId
	 * @return ["hotSongList",前六位热门歌曲集合]
	 */
	@RequestMapping(value = "showSingerImg")
	@ResponseBody
	public String showSingerImg(@RequestParam(required = true) long singerId, HttpServletResponse response) {
		// response.setContentType("image/*");
		// 获取歌单的信息
		Msg msg = singerService.selectSingerById(singerId);
		Singer singer = new Singer();
		if (msg.getCode() == 100) {
			singer = (Singer) msg.getMap().get("singer");
		} else {
			return Msg.error().getMsg();
		}
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(singer.getImgPath());
			os = response.getOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((count = fis.read(buffer)) != -1) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			fis.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "ok";
	}

	/**
	 * 获取新晋歌手，最新的3个
	 * 
	 * @return Msg["list":List{Singer}:歌手集合]
	 */
	@RequestMapping(value = "selectNewestSinger")
	@ResponseBody
	public Msg selectNewestSinger() {
		return singerService.selectNewestSinger();
	}

}
