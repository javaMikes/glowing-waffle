package com.cloudmusic.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.TagName;
import com.cloudmusic.bean.User;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ITagService;
import com.cloudmusic.service.IUserService;
import com.cloudmusic.util.Const;
import com.cloudmusic.util.Msg;
import com.github.pagehelper.PageInfo;

import sun.misc.BASE64Decoder;

/**
 * 歌单列表控制器
 * 
 * @author Mike
 *
 */
@Controller
public class PlayListController {

	@Autowired
	IPlaylistService playlistService;

	@Autowired
	ITagService tagService;

	@Autowired
	IUserService userService;

	/**
	 * 返回热门推荐前8条
	 */
	@RequestMapping("findHotPlaylistTop")
	@ResponseBody
	public Msg findHotPlaylist(Msg msg) {
		Msg resultMsg = playlistService.findHotPlaylist();
		@SuppressWarnings("unchecked")
		List<Playlist> playList = (List<Playlist>) resultMsg.getMap().get("list");
		List<Playlist> playResultList = new ArrayList<Playlist>();
		// 获取前八条
		for (int i = 0; i < 8; i++) {
			playResultList.add(playList.get(i));
		}
		return Msg.success().add("hotPlayListTop", playResultList);
	}

	/**
	 * 根据歌单ID找出它收纳的所有歌曲
	 * 
	 * @param playlistId
	 *            歌单ID
	 * @return Msg["songList":List{Song}:歌曲集合] Msg["msg":"查询失败"]
	 */
	@RequestMapping("findSongByPlayListId")
	@ResponseBody
	public Msg findSongByPlayListId(@RequestParam(required = true) long playlistId) {
		Msg msg = playlistService.selectSongByPlaylist(playlistId);
		if (msg.getCode() == 100) {
			return Msg.success().add("songList", msg.getMap().get("list"));
		} else {
			return Msg.error().add("msg", "查询失败");
		}
	}

	/**
	 * 跳转到全部歌单页面
	 * 
	 * @param tagName
	 *            为null时查询全部，否则根据标签查找
	 * @return "message" 错误信息 "playlistPageInfo" 歌单信息 "tagName" 选择的标签名
	 * 
	 */
	@RequestMapping("showPlaylist")
	public String showPlayList(@RequestParam(value = "pn", defaultValue = "1") Integer pn, String tagName, Model model) {
		if (tagName == null || tagName.equals("")) {
			// 获取所有歌单列表
			Msg msg = playlistService.selectPlayList(pn);
			@SuppressWarnings("unchecked")
			PageInfo<Playlist> playlistPageInfo = (PageInfo<Playlist>) msg.getMap().get("msg");

			if (playlistPageInfo != null && playlistPageInfo.getList().size() != 0) {

				model.addAttribute("playlistPageInfo", playlistPageInfo);
				return "playlist/showPlaylist";
			} else {
				model.addAttribute("message", "暂无数据");
				return "error";
			}
		} else {
			Msg msg = null;
			// 一个标签
			if (!tagName.contains(",") && !tagName.contains("，")) {
				msg = tagService.selectPlaylistPageInfoByTagName(tagName, pn);
			} else {// 多个标签
				// 先分割字符串
				String[] tagNames = tagName.split(",");
				List<String> tagNameList = new ArrayList<String>();
				for (String tagNameStr : tagNames) {
					tagNameList.add(tagNameStr);
				}
				msg = tagService.selectPlaylistPageInfoByTagNameList(tagNameList, pn);
			}

			@SuppressWarnings("unchecked")
			PageInfo<Playlist> playlistPageInfo = (PageInfo<Playlist>) msg.getMap().get("list");

			if (playlistPageInfo == null) {
				model.addAttribute("message", "查询出错");
				return "error";
			}

			if (playlistPageInfo.getList().size() != 0) {
				model.addAttribute("playlistPageInfo", playlistPageInfo);
				// 返回选择的标签名
				model.addAttribute("tagName", tagName);
				return "playlist/showPlaylist";
			} else {
				// 返回选择的标签名
				model.addAttribute("tagName", tagName);
				model.addAttribute("message", "暂无数据");
				return "playlist/showPlaylist";
			}
		}
	}

	/**
	 * 显示歌单图片
	 */
	@RequestMapping(value = "showPlaylistImg")
	@ResponseBody
	public String showPlaylistImg(@RequestParam(required = true) long playlistId, HttpServletResponse response) {
		// response.setContentType("image/*");
		// 获取歌单的信息
		Msg msg = playlistService.selectPlayListByPlayListId(playlistId);
		Playlist playlist = new Playlist();
		if (msg.getCode() == 100) {
			playlist = (Playlist) msg.getMap().get("msg");
		} else {
			return Msg.error().getMsg();
		}
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(playlist.getImgPath());
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
	 * 跳转到歌单主页面
	 * 
	 * @param playlistId
	 * @param model
	 * @return ["playlistInfo",歌单基本信息;"playlistId",歌单id;"userId",用户id;"userNickname",用户昵称;
	 *         "playTaglist",歌单标签;"nicePlaylist",相关歌单推荐]
	 */
	@RequestMapping(value = "showPlaylistPage")
	public String showPlaylistPage(@RequestParam(required = true) long playlistId, Model model) {

		Msg msg = playlistService.selectPlayListByPlayListId(playlistId);

		if (msg.getCode() == 200) {
			return "error";
		}

		// 根据歌单id获取歌单信息
		Playlist playlistInfo = (Playlist) msg.getMap().get("msg");

		// 根据歌单id获取创建用户
		Msg userMsg = userService.selectUserById(playlistInfo.getUserId());
		User user = (User) userMsg.getMap().get("user");

		// 根据歌单id获取歌单的标签id
		List<Long> playlistTagList = tagService.findTagNameByPlaylistId(playlistId);

		List<TagName> tagList = new ArrayList<TagName>();

		// 根据标签id获取标签
		for (Long tagId : playlistTagList) {
			Msg tagMsg = tagService.selectTagNameByTagId(tagId);
			tagList.add((TagName) tagMsg.getMap().get("tagName"));
		}

		// 获取相关歌单推荐
		Msg nicePlaylistMsg = playlistService.findNicePlaylistByTagName(tagList);
		@SuppressWarnings("unchecked")
		List<Playlist> nicePlaylist = (List<Playlist>) nicePlaylistMsg.getMap().get("list");

		for (Playlist playlist : nicePlaylist) {
			// 根据歌单id获取创建用户
			User createdUser = (User) userService.selectUserById(playlist.getUserId()).getMap().get("user");
			playlist.setUserName(createdUser.getNickname());
		}

		// 歌单基本信息
		model.addAttribute("playlistInfo", playlistInfo);
		model.addAttribute("playlistId", playlistId);
		model.addAttribute("userId", user.getId());
		model.addAttribute("userNickname", user.getNickname());
		model.addAttribute("playTaglist", tagList);
		model.addAttribute("nicePlaylist", nicePlaylist);
		return "listDetail";
	}

	/**
	 * 获取热门歌手
	 * 
	 * @param topNum
	 *            获取前topNum位(topNum=all时,获取全部)
	 */
	@RequestMapping(value = "selectSingerTopNum")
	@ResponseBody
	public Msg selectSingerTopNum(String topNum) {
		Msg msg = playlistService.selectSingerDescByPlayTimes();
		if (msg.getCode() == 200)
			return Msg.error();

		@SuppressWarnings("unchecked")
		List<String> singerListSelected = (List<String>) msg.getMap().get("list");
		if (!topNum.equals("all")) {

			// 判断是否超出限制
			if (Integer.valueOf(topNum) > singerListSelected.size())
				return Msg.error().add("msg", "超出范围");

			List<String> singerList = new ArrayList<String>();
			// 获取前topNum位
			for (int i = 0; i < Integer.valueOf(topNum); i++) {
				singerList.add(singerListSelected.get(i));
			}
			return Msg.success().add("selectSingerTopNum", singerList);
		}
		return Msg.success().add("selectSingerTopNum", singerListSelected);
	}

	/**
	 * 根据用户个性化推荐
	 * 
	 * @param userId
	 *            用户ID
	 * @return Msg["list":List<Playlist>:歌单集合]
	 */
	@RequestMapping(value = "findFavoritePlaylistByUserId")
	@ResponseBody
	public Msg findFavoritePlaylistByUserId(long userId) {
		Msg msg = playlistService.findFavoritePlaylistByUserId(userId);
		if (msg.getCode() == 200)
			return Msg.error().add("msg", "推荐出错");
		@SuppressWarnings("unchecked")
		List<Playlist> playlist = (List<Playlist>) msg.getMap().get("list");
		if (playlist.size() == 0)
			return Msg.error().add("msg", "暂无推荐记录");
		return msg;
	}

	/**
	 * 新建一个歌单
	 * 
	 * @return Msg的map中，有两个键。[“msg”:String:储存提示信息]<br/>
	 *         ，[“playlist”:Playlist:为刚插入的playlist对象，其id值为数据库自增的结果]
	 */
	@RequestMapping(value = "createPlaylist")
	@ResponseBody
	public Msg createPlaylistByUserId(@RequestParam(required = true) long userId, @RequestParam(required = true) String playlistName) {
		Playlist playlist = new Playlist();
		// 根据用户id获取用户信息
		Msg userMsg = userService.selectUserById(userId);
		User user = (User) userMsg.getMap().get("user");

		playlist.setUserId(userId);
		playlist.setUserName(user.getNickname());
		playlist.setListName(playlistName);
		playlist.setCollection(0L);
		playlist.setPlayTimes(0L);
		playlist.setCreateTime(new Date());
		playlist.setImgPath(Const.PLAYLIST_IMG_PATH + "default.jpg");
		// 将歌单信息存入数据库
		Msg msg = playlistService.insertPlaylist(playlist);
		if (msg.getCode() == 200)
			return msg;
		return msg;
	}

	/**
	 * 根据歌单id获取歌单信息
	 *
	 * @param playlistId
	 * @return Msg["msg" : error:提示信息；success：歌单信息]
	 */
	@RequestMapping(value = "findPlaylistById")
	@ResponseBody
	public Msg findPlaylistById(@RequestParam(required = true) long playlistId) {
		// 根据歌单id获取歌单信息
		Msg playlistMsg = playlistService.selectPlayListByPlayListId(playlistId);
		Playlist playlistInfo = (Playlist) playlistMsg.getMap().get("msg");

		// 根据歌单id获取歌单的标签id
		List<Long> playlistTagList = tagService.findTagNameByPlaylistId(playlistId);
		List<TagName> tagList = new ArrayList<TagName>();

		// 根据标签id获取标签
		for (Long tagId : playlistTagList) {
			Msg tagMsg = tagService.selectTagNameByTagId(tagId);
			tagList.add((TagName) tagMsg.getMap().get("tagName"));
		}

		return playlistService.selectPlayListByPlayListId(playlistId).add("msg", playlistInfo).add("tagList", tagList);
	}

	/**
	 * 某个歌单的播放次数+1
	 *
	 * @param id
	 *            歌单ID
	 *
	 * @return Msg["msg":操作信息]
	 */
	@RequestMapping("addPlaytimeOfPlaylist")
	@ResponseBody
	public Msg addPlaytimeOfPlaylist(long playlistId) {
		return playlistService.addPlaytimeOfPlaylist(playlistId);
	}

	/**
	 * 用户收藏歌单
	 * 
	 * @param userId
	 *            用户ID
	 * @param playlistId
	 *            歌单ID
	 * @return Msg[]
	 */
	@RequestMapping("collectPlaylist")
	@ResponseBody
	public Msg collectPlaylist(@RequestParam(required = true) long userId, @RequestParam(required = true) long playlistId) {

		// 该歌单的收藏次数加一
		Msg playlistMsg = playlistService.addCollectionOfPlaylist(playlistId);

		if (playlistMsg.getCode() == 200)
			return playlistMsg;

		return playlistService.insertPlaylistCollection(userId, playlistId);
	}

	/**
	 * 将歌曲 加入 歌单
	 * 
	 * @param playlistId
	 *            歌单ID
	 * @param songIds
	 *            要加入的歌曲ID数组集合
	 * @return Msg包含的map键。[“msg”：String:提示信息 ]
	 */
	@RequestMapping("insertPlaylistWithSong")
	@ResponseBody
	public Msg insertPlaylistWithSong(@RequestParam(required = true) long playlistId, @RequestParam(required = true) List<Long> songIds) {
		return playlistService.insertPlaylistWithSong(playlistId, songIds);
	}

	/**
	 * 删除用户创建的歌单
	 * 
	 * @param playlistId
	 *            歌单id
	 * @return Msg
	 */
	@RequestMapping("deleteCreatePlaylist")
	@ResponseBody
	public Msg deleteCreatePlaylist(@RequestParam(required = true) long playlistId) {
		return playlistService.deletePlaylistById(playlistId);
	}

	/**
	 * 跳转到上传歌单图片页面
	 * 
	 * @param model
	 * @param playlistId
	 * @return
	 */
	@RequestMapping("showUploadImgPage")
	public String showUploadImgPage(Model model, long playlistId) {
		model.addAttribute("playlistId", playlistId);
		return "playlist/uploadPlaylistImg";
	}

	/**
	 * 上传歌单图片
	 * 
	 * @param imgBase64
	 * @param playlistId
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("uploadPlaylistImg")
	public Msg uploadPlaylistImg(String imgBase64, long playlistId) throws IOException {
		System.out.println("imgBase64" + imgBase64);
		// 获取图片的base64码
		imgBase64 = imgBase64.substring(22);
		@SuppressWarnings("restriction")
		byte[] buffer = new BASE64Decoder().decodeBuffer(imgBase64);

		// 获取原先图片路径
		Msg playlistMsg = playlistService.selectPlayListByPlayListId(playlistId);
		if (playlistMsg.getCode() == 200)
			return playlistMsg;
		Playlist playlist = (Playlist) playlistMsg.getMap().get("msg");
		String preImgPath = playlist.getImgPath();

		// 文件存储路径
		String fileName = Const.PLAYLIST_IMG_PATH + System.currentTimeMillis() + ".jpg";

		// 将解密后的文件存入文件夹
		FileUtils.writeByteArrayToFile(new File(fileName), buffer);

		// 将文件路径存入数据库
		Msg updatePlaylistMsg = playlistService.updatePlaylistImgPath(playlistId, fileName);

		if (updatePlaylistMsg.getCode() == 100) {
			System.out.println(preImgPath.substring(preImgPath.lastIndexOf("/") + 1, preImgPath.length()));
			// 将原来的图片删掉
			if (!preImgPath.substring(preImgPath.lastIndexOf("/") + 1, preImgPath.length()).equals("default.jpg")) {
				File file = new File(preImgPath);
				file.delete();
			}

			return Msg.success().add("msg", updatePlaylistMsg.getMsg());
		} else {
			return Msg.error().add("msg", updatePlaylistMsg.getMsg());
		}
	}

	/**
	 * 修改歌单
	 * 
	 * @param playlist
	 * @param tagName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("updatePlaylist")
	public Msg updatePlaylist(Playlist playlist, String tagName) throws IOException {

		// 修改歌单信息
		Msg playlistmsg = playlistService.updatePlaylistByPrimaryKey(playlist);

		if (playlistmsg.getCode() == 200)
			return playlistmsg;

		// 假如标签不为空,则为歌单添加标签
		if (tagName != null) {
			List<String> tagNameList = new ArrayList<String>();
			String[] tagNameStrs = tagName.split(",");
			for (String tagNameStr : tagNameStrs) {
				// 根据标签名获取标签信息
				Msg tagNameMsg = tagService.selectByTagName(tagNameStr);
				if (tagNameMsg.getCode() == 200)
					return tagNameMsg;
				tagNameList.add(tagNameStr);
			}

			playlistService.updatePlaylistTagName(playlist.getId(), tagNameList);
		}

		return Msg.success();
	}

	/**
	 * 判断用户是否收藏该歌单
	 * 
	 * @param playlistId
	 *            歌单id
	 * @return 已收藏返回Msg.error(),否则Msg.success()
	 */
	@ResponseBody
	@RequestMapping("isCollection")
	public Msg isCollection(@RequestParam(required = true) long playlistId, HttpSession session) {
		// 获取用户信息
		User user = (User) session.getAttribute("userInfo");

		// 获取该用户收藏的歌单集合
		Msg playlistMsg = playlistService.selectPlaylistCollectionByUserId(user.getId());
		@SuppressWarnings("unchecked")
		List<Playlist> playlistList = (List<Playlist>) playlistMsg.getMap().get("list");

		for (Playlist playlist : playlistList) {
			if (playlist.getId() == playlistId)
				return Msg.error();
		}
		return Msg.success();
	}

	/**
	 * 取消收藏歌单
	 * 
	 * @param playlistId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("undoPlaylistCollection")
	public Msg undoPlaylistCollection(@RequestParam(required = true) long playlistId, HttpSession session) {

		// 该歌单的收藏次数减一
		Msg playlistMsg = playlistService.subCollectionOfPlaylist(playlistId);

		if (playlistMsg.getCode() == 200)
			return playlistMsg;

		// 获取用户信息
		User user = (User) session.getAttribute("userInfo");
		// 取消收藏歌单
		return playlistService.undoPlaylistCollection(user.getId(), playlistId);
	}

	/**
	 * 根据用户id查找出该用户创建的所有歌单
	 * 
	 * @param userId
	 * @return Msg.success["list":List&lt;Playlist&gt;:歌单集合]
	 */
	@ResponseBody
	@RequestMapping("getCreatePlaylist")
	public Msg getCreatePlaylist(@RequestParam(required = true) long userId) {
		// 获取用户创建的歌单
		return playlistService.selectPlaylistByUserId(userId);
	}

}
