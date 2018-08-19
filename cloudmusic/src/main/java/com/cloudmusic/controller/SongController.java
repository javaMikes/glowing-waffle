package com.cloudmusic.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.Singer;
import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.TagName;
import com.cloudmusic.bean.User;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ISingerService;
import com.cloudmusic.service.ISongCommentService;
import com.cloudmusic.service.ISongService;
import com.cloudmusic.service.ITagService;
import com.cloudmusic.service.IUserService;
import com.cloudmusic.util.Const;
import com.cloudmusic.util.LrcParser;
import com.cloudmusic.util.Msg;

/**
 * 歌曲控制器
 * 
 * @author Mike
 *
 */
@Controller
public class SongController {

	@Autowired
	ISongService songService;

	@Autowired
	ITagService tagService;

	@Autowired
	IPlaylistService playlistService;

	@Autowired
	ISingerService singerService;

	@Autowired
	ISongCommentService songCommentService;

	@Autowired
	IUserService userService;

	/**
	 * 播放音乐
	 * 
	 * @param 歌曲id
	 * @return
	 */
	@RequestMapping("playMusic")
	@ResponseBody
	public Msg playMusic(@RequestParam(required = true) long id) {
		Msg msg = songService.selectSongBySongId(id);
		if (msg.getCode() == 100) {
			Song song = (Song) msg.getMap().get("song");
			LrcParser lp = new LrcParser();
			// 解析歌词
			try {
				String lyrics = lp.parser(song.getLyrics());
				song.setLyrics(lyrics);
			} catch (Exception e) {
				e.printStackTrace();
				return Msg.error();
			}
			return Msg.success().add("songInfo", song);
		} else {
			return Msg.error().add("msg", "不存在该id的歌曲");
		}
	}

	/**
	 * 读取本地音乐
	 * 
	 * @throws UnsupportedEncodingException
	 */
	// @RequestMapping(value = "findLocalMusic")
	// @ResponseBody
	// public String findLocalMusic(HttpServletRequest request,
	// HttpServletResponse response) throws UnsupportedEncodingException {
	// // response.setContentType("image/*");
	// String musicPath = URLDecoder.decode(request.getParameter("musicPath"),
	// "utf-8");
	// FileInputStream fis = null;
	// OutputStream os = null;
	// try {
	// fis = new FileInputStream(musicPath);
	// os = response.getOutputStream();
	// int count = 0;
	// byte[] buffer = new byte[1024 * 8];
	// while ((count = fis.read(buffer)) != -1) {
	// os.write(buffer, 0, count);
	// os.flush();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// try {
	// fis.close();
	// os.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return "ok";
	// }

	/**
	 * 读取本地音乐图片
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "findLocalImg")
	@ResponseBody
	public String findLocalImg(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// response.setContentType("image/*");
		String imgPath = URLDecoder.decode(request.getParameter("imgPath"), "utf-8");
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(imgPath);
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
	 * 获取歌曲收听榜前十名
	 */
	@RequestMapping(value = "selectSongTopTenByPlayTimes")
	@ResponseBody
	public Msg selectSongTopTenByPlayTimes() {
		Msg msg = songService.selectSongDescByPlayTimes();

		if (msg.getCode() == 200) {
			return Msg.error().add("msg", "获取信息失败");
		}

		// 存取前十条记录
		List<Song> songList = new ArrayList<Song>();
		@SuppressWarnings("unchecked")
		List<Song> songListSelected = (List<Song>) msg.getMap().get("list");

		// 选前十条
		for (int i = 0; i < 10; i++) {
			songList.add(songListSelected.get(i));
		}

		return Msg.success().add("msg", songList);
	}

	/**
	 * 获取歌曲收藏榜前十名
	 */
	@RequestMapping(value = "selectSongTopTenByCollection")
	@ResponseBody
	public Msg selectSongTopTenByCollection() {
		Msg msg = songService.selectSongDescByCollection();

		if (msg.getCode() == 200) {
			return Msg.error().add("msg", "获取信息失败");
		}

		// 存取前十条记录
		List<Song> songList = new ArrayList<Song>();
		@SuppressWarnings("unchecked")
		List<Song> songListSelected = (List<Song>) msg.getMap().get("list");

		// 选前十条
		for (int i = 0; i < 10; i++) {
			songList.add(songListSelected.get(i));
		}

		return Msg.success().add("msg", songList);
	}

	/**
	 * 获取歌曲收听榜所有歌曲
	 */
	@RequestMapping(value = "selectSongDesByPlayTimes")
	@ResponseBody
	public Msg selectSongDesByPlayTimes() {
		Msg msg = songService.selectSongDescByPlayTimes();

		if (msg.getCode() == 200) {
			return Msg.error().add("msg", "获取信息失败");
		}

		@SuppressWarnings("unchecked")
		List<Song> songList = (List<Song>) msg.getMap().get("list");

		return Msg.success().add("msg", songList);
	}

	/**
	 * 获取歌曲收藏榜所有歌曲
	 */
	@RequestMapping(value = "selectSongDesByCollection")
	@ResponseBody
	public Msg selectSongDesByCollection() {
		Msg msg = songService.selectSongDescByCollection();

		if (msg.getCode() == 200) {
			return Msg.error().add("msg", "获取信息失败");
		}
		@SuppressWarnings("unchecked")
		List<Song> songList = (List<Song>) msg.getMap().get("list");

		return Msg.success().add("msg", songList);
	}

	/**
	 * 根据歌曲Id跳转到歌曲页面
	 * 
	 * @param songId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "showSongPage")
	public String showSongPage(@RequestParam(required = true) long songId, Model model) throws Exception {
		Msg msg = songService.selectSongBySongId(songId);
		if (msg.getCode() == 200) {
			model.addAttribute("message", "查询出错");
			return "error";
		}
		Song songInfo = (Song) msg.getMap().get("song");

		// 解析歌词
		LrcParser lp = new LrcParser();
		songInfo.setLyrics(lp.parserWithoutTime(songInfo.getLyrics()));

		// 根据歌曲id获取歌曲的所有标签
		List<Long> tagIdList = tagService.findTagNameBySongId(songId);
		List<TagName> tagNameList = new ArrayList<TagName>();

		for (long tagId : tagIdList) {
			tagNameList.add((TagName) tagService.selectTagNameByTagId(tagId).getMap().get("tagName"));
		}

		// 根据多个标签，推荐适合这些标签的歌曲
		Msg niceSongMsg = songService.findNiceSongByTagName(tagNameList);
		if (niceSongMsg.getCode() == 200) {
			model.addAttribute("message", "鏌ヨ鍑洪敊");
			return "error";
		}

		// 存取歌曲信息
		model.addAttribute("songInfo", songInfo);

		// 获取相关歌曲
		@SuppressWarnings("unchecked")
		List<Song> songlistSelected = (List<Song>) niceSongMsg.getMap().get("list");

		// 获取前五条
		List<Song> songlist = new ArrayList<Song>();

		// 若相关歌曲大于五条，则选择前五条
		for (int i = 0; i < songlistSelected.size() && i < 5; i++) {
			songlist.add(songlistSelected.get(i));
		}

		// 相关歌曲推荐
		model.addAttribute("niceSongList", songlist);

		return "song";
	}

	/**
	 * 使用ajax获取歌词信息
	 */
	@RequestMapping(value = "showSongLrc")
	@ResponseBody
	public Msg showSongLrc(@RequestParam(required = true) long songId) throws Exception {
		Msg msg = songService.selectSongBySongId(songId);
		if (msg.getCode() == 200) {
			return msg;
		}
		Song songInfo = (Song) msg.getMap().get("song");

		// 解析歌词
		LrcParser lp = new LrcParser();
		return Msg.success().add("lrcInfo", lp.parserWithoutTime(songInfo.getLyrics()));
	}

	/**
	 * 某首歌曲播放次数加1
	 *
	 * @param songId
	 * @return Msg["msg": "提示信息"]
	 */
	@RequestMapping(value = "addOnePlaytimeOfSong")
	@ResponseBody
	public Msg addOnePlaytimeOfSong(@RequestParam(required = true) long songId, HttpSession session) throws Exception {

		long userId = -1;
		// 判断用户是否登录
		User user = (User) session.getAttribute("userInfo");
		if (user != null) {
			userId = user.getId();
		}

		// 歌曲播放次数+1
		return songService.addOnePlaytimeOfSong(userId, songId);
	}

	/**
	 * 内容搜索
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("searchAll")
	@ResponseBody
	public Msg searchAll(String name) {
		// 获取歌曲
		Msg msg = songService.selectSongByLikeName(name);
		@SuppressWarnings("unchecked")
		List<Song> listSong = (List<Song>) msg.getMap().get("list");

		// 获取歌单
		msg = playlistService.selectPlaylistByLikeName(name);
		@SuppressWarnings("unchecked")
		List<Playlist> listPlaylist = (List<Playlist>) msg.getMap().get("list");

		// 获取歌手
		msg = singerService.selectSingerByLikeName(name);
		@SuppressWarnings("unchecked")
		List<Singer> listSinger = (List<Singer>) msg.getMap().get("list");

		// 获取用户相关
		msg = userService.selectUserByLikeName(name);
		@SuppressWarnings("unchecked")
		List<User> listUser = (List<User>) msg.getMap().get("list");

		return Msg.success().add("listSong", listSong).add("listPlaylist", listPlaylist).add("listSinger", listSinger).add("listUser", listUser);
	}

	/**
	 * 下载歌曲
	 * 
	 * @param songId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/download")
	@ResponseBody
	public ResponseEntity<InputStreamResource> downloadSong(@RequestParam(required = true) long songId, HttpSession session) throws IOException {

		// 根据歌曲id获取歌曲信息
		Msg msg = songService.selectSongBySongId(songId);
		Song song = (Song) msg.getMap().get("song");

		// 扣除用户相应的积分
		User user = (User) session.getAttribute("userInfo");
		userService.subUserIntegral(user.getId(), song.getIntegral());

		// 更新session的用户信息
		User updateUser = (User) userService.selectUserById(user.getId()).getMap().get("user");
		session.setAttribute("userInfo", updateUser);

		String filePath = Const.SONG_PATH + song.getSongName() + ".mp3";
		FileSystemResource file = new FileSystemResource(filePath);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Content-Disposition",
				String.format("attachment; filename=\"%s\"", new String(file.getFilename().getBytes("utf-8"), "iso-8859-1")));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(file.getInputStream()));
	}

	/**
	 * 根据用户id，查找出他可能喜欢的歌曲，并按顺序依次排序下来，返回一个最大size为24的list
	 * 
	 * @param userId
	 *            用户id
	 * @return Msg.success map["list":List{Song}:长度为0到24之间的list]
	 */
	@RequestMapping(value = "/findFavoriteSongByUserId")
	@ResponseBody
	public Msg findFavoriteSongByUserId(@RequestParam(required = true) Long userId) {
		return songService.findFavoriteSongByUserId(userId);
	}

	/**
	 * 判断用户是否收藏过该歌曲
	 * 
	 * @param songId
	 * @param playlistId
	 * @return Msg.success() 已收藏过 Msg.error() 未收藏过
	 */
	@RequestMapping(value = "/isCollectionSong")
	@ResponseBody
	public Msg selectSongCollectedByUserId(@RequestParam(required = true) long songId, @RequestParam(required = true) long playlistId) {
		// 根据歌单id获取收纳的歌曲
		Msg playMsg = playlistService.selectSongByPlaylist(playlistId);
		@SuppressWarnings("unchecked")
		List<Song> songlist = (List<Song>) playMsg.getMap().get("list");

		for (Song song : songlist) {
			if (song.getId() == songId)
				return Msg.success();
		}
		return Msg.error();
	}

	/**
	 * 移除收藏的歌曲
	 * 
	 * @param songId
	 * @param playlistId
	 * @return Msg.success() 已收藏过 Msg.error() 未收藏过
	 */
	@RequestMapping(value = "/removeSong")
	@ResponseBody
	public Msg removeSongFromPlaylistById(@RequestParam(required = true) long songId, @RequestParam(required = true) long playlistId) {

		List<Long> songIds = new ArrayList<Long>();

		songIds.add(songId);

		// 根据歌单id移除歌曲
		return playlistService.removeSongFromPlaylistById(playlistId, songIds);
	}

	/**
	 * 跳转到个性推荐页面
	 */
	@RequestMapping(value = "/showRecommendation")
	public String showRecommendation() {
		return "songRecommend";
	}

	/**
	 * 根据歌曲ID获取歌曲信息
	 * 
	 * @param id
	 * @return 有歌就是Msg.success map["song":Song:歌曲对象]<br/>
	 *         没有歌就是Msg.error
	 */
	@RequestMapping(value = "/selectSongBySongId")
	@ResponseBody
	public Msg selectSongBySongId(Long songId) {
		return songService.selectSongBySongId(songId);
	}

}
