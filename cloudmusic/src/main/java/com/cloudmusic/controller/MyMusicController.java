package com.cloudmusic.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.TagName;
import com.cloudmusic.bean.User;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ITagService;
import com.cloudmusic.service.IUserService;
import com.cloudmusic.util.Msg;

@Controller
public class MyMusicController {

	@Autowired
	IPlaylistService playlistService;

	@Autowired
	IUserService userService;

	@Autowired
	ITagService tagService;

	/**
	 * 跳转到我的音乐界面
	 * 
	 * @param userId
	 * @param playlistId
	 * @param model
	 * @return ["playlistInfo",歌单基本信息;"playlistId",歌单id;"userId",用户id;"userNickname",用户昵称;
	 *         "playTaglist",歌单标签;"createPlayList",用户创建的歌单;"collectionPlaylist",用户收藏的歌单]
	 */
	@RequestMapping("showMyMusicPage")
	public String showMyMusicPage(@RequestParam(required = true) long userId, Long playlistId, Model model) {
		// 根据用户的id获取用户创建的歌单
		Msg createPlaylistMsg = playlistService.selectPlaylistByUserId(userId);
		if (createPlaylistMsg.getCode() == 200) {
			model.addAttribute("message", "获取用户创建的歌单失败");
			return "error";
		}

		// 根据用户的id获取用户收藏的歌单
		Msg collectionPlaylistMsg = playlistService.selectPlaylistCollectionByUserId(userId);
		if (collectionPlaylistMsg.getCode() == 200) {
			model.addAttribute("message", "获取用户收藏的歌单失败");
			return "error";
		}

		@SuppressWarnings("unchecked")
		List<Playlist> collectionPlaylist = (List<Playlist>) collectionPlaylistMsg.getMap().get("list");

		// 获取的收藏歌单数不为0
		if (collectionPlaylist.size() != 0) {
			// 获取收藏歌单的创建者
			for (Playlist playlist : collectionPlaylist) {
				// 根据歌单id获取创建用户
				Msg userMsgforCollection = userService.selectUserById(playlist.getUserId());
				User user = (User) userMsgforCollection.getMap().get("user");
				playlist.setUserName(user.getNickname());
			}
		}

		// 若playlistId为null,则设置playlistId为用户创建的第一个歌单id
		if (playlistId == null) {
			@SuppressWarnings("unchecked")
			List<Playlist> playlistList = (List<Playlist>) createPlaylistMsg.getMap().get("list");
			playlistId = playlistList.get(0).getId();
		}

		Msg msg = playlistService.selectPlayListByPlayListId(playlistId);

		if (msg.getCode() == 200) {
			model.addAttribute("message", msg.getMap().get("msg"));
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

		// 歌单基本信息
		model.addAttribute("playlistInfo", playlistInfo);
		model.addAttribute("playlistId", playlistId);
		model.addAttribute("userId", user.getId());
		model.addAttribute("userNickname", user.getNickname());
		model.addAttribute("playTaglist", tagList);

		// 返回用户创建的歌单
		model.addAttribute("createPlayList", createPlaylistMsg.getMap().get("list"));

		// 返回用户收藏的歌单
		model.addAttribute("collectionPlaylist", collectionPlaylist);

		return "myMusic";
	}
}
