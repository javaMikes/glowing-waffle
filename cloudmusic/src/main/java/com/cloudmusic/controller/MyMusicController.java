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
	 * ��ת���ҵ����ֽ���
	 * 
	 * @param userId
	 * @param playlistId
	 * @param model
	 * @return ["playlistInfo",�赥������Ϣ;"playlistId",�赥id;"userId",�û�id;"userNickname",�û��ǳ�;
	 *         "playTaglist",�赥��ǩ;"createPlayList",�û������ĸ赥;"collectionPlaylist",�û��ղصĸ赥]
	 */
	@RequestMapping("showMyMusicPage")
	public String showMyMusicPage(@RequestParam(required = true) long userId, Long playlistId, Model model) {
		// �����û���id��ȡ�û������ĸ赥
		Msg createPlaylistMsg = playlistService.selectPlaylistByUserId(userId);
		if (createPlaylistMsg.getCode() == 200) {
			model.addAttribute("message", "��ȡ�û������ĸ赥ʧ��");
			return "error";
		}

		// �����û���id��ȡ�û��ղصĸ赥
		Msg collectionPlaylistMsg = playlistService.selectPlaylistCollectionByUserId(userId);
		if (collectionPlaylistMsg.getCode() == 200) {
			model.addAttribute("message", "��ȡ�û��ղصĸ赥ʧ��");
			return "error";
		}

		@SuppressWarnings("unchecked")
		List<Playlist> collectionPlaylist = (List<Playlist>) collectionPlaylistMsg.getMap().get("list");

		// ��ȡ���ղظ赥����Ϊ0
		if (collectionPlaylist.size() != 0) {
			// ��ȡ�ղظ赥�Ĵ�����
			for (Playlist playlist : collectionPlaylist) {
				// ���ݸ赥id��ȡ�����û�
				Msg userMsgforCollection = userService.selectUserById(playlist.getUserId());
				User user = (User) userMsgforCollection.getMap().get("user");
				playlist.setUserName(user.getNickname());
			}
		}

		// ��playlistIdΪnull,������playlistIdΪ�û������ĵ�һ���赥id
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

		// ���ݸ赥id��ȡ�赥��Ϣ
		Playlist playlistInfo = (Playlist) msg.getMap().get("msg");

		// ���ݸ赥id��ȡ�����û�
		Msg userMsg = userService.selectUserById(playlistInfo.getUserId());
		User user = (User) userMsg.getMap().get("user");

		// ���ݸ赥id��ȡ�赥�ı�ǩid
		List<Long> playlistTagList = tagService.findTagNameByPlaylistId(playlistId);

		List<TagName> tagList = new ArrayList<TagName>();

		// ���ݱ�ǩid��ȡ��ǩ
		for (Long tagId : playlistTagList) {
			Msg tagMsg = tagService.selectTagNameByTagId(tagId);
			tagList.add((TagName) tagMsg.getMap().get("tagName"));
		}

		// �赥������Ϣ
		model.addAttribute("playlistInfo", playlistInfo);
		model.addAttribute("playlistId", playlistId);
		model.addAttribute("userId", user.getId());
		model.addAttribute("userNickname", user.getNickname());
		model.addAttribute("playTaglist", tagList);

		// �����û������ĸ赥
		model.addAttribute("createPlayList", createPlaylistMsg.getMap().get("list"));

		// �����û��ղصĸ赥
		model.addAttribute("collectionPlaylist", collectionPlaylist);

		return "myMusic";
	}
}
