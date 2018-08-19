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
 * �赥�б������
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
	 * ���������Ƽ�ǰ8��
	 */
	@RequestMapping("findHotPlaylistTop")
	@ResponseBody
	public Msg findHotPlaylist(Msg msg) {
		Msg resultMsg = playlistService.findHotPlaylist();
		@SuppressWarnings("unchecked")
		List<Playlist> playList = (List<Playlist>) resultMsg.getMap().get("list");
		List<Playlist> playResultList = new ArrayList<Playlist>();
		// ��ȡǰ����
		for (int i = 0; i < 8; i++) {
			playResultList.add(playList.get(i));
		}
		return Msg.success().add("hotPlayListTop", playResultList);
	}

	/**
	 * ���ݸ赥ID�ҳ������ɵ����и���
	 * 
	 * @param playlistId
	 *            �赥ID
	 * @return Msg["songList":List{Song}:��������] Msg["msg":"��ѯʧ��"]
	 */
	@RequestMapping("findSongByPlayListId")
	@ResponseBody
	public Msg findSongByPlayListId(@RequestParam(required = true) long playlistId) {
		Msg msg = playlistService.selectSongByPlaylist(playlistId);
		if (msg.getCode() == 100) {
			return Msg.success().add("songList", msg.getMap().get("list"));
		} else {
			return Msg.error().add("msg", "��ѯʧ��");
		}
	}

	/**
	 * ��ת��ȫ���赥ҳ��
	 * 
	 * @param tagName
	 *            Ϊnullʱ��ѯȫ����������ݱ�ǩ����
	 * @return "message" ������Ϣ "playlistPageInfo" �赥��Ϣ "tagName" ѡ��ı�ǩ��
	 * 
	 */
	@RequestMapping("showPlaylist")
	public String showPlayList(@RequestParam(value = "pn", defaultValue = "1") Integer pn, String tagName, Model model) {
		if (tagName == null || tagName.equals("")) {
			// ��ȡ���и赥�б�
			Msg msg = playlistService.selectPlayList(pn);
			@SuppressWarnings("unchecked")
			PageInfo<Playlist> playlistPageInfo = (PageInfo<Playlist>) msg.getMap().get("msg");

			if (playlistPageInfo != null && playlistPageInfo.getList().size() != 0) {

				model.addAttribute("playlistPageInfo", playlistPageInfo);
				return "playlist/showPlaylist";
			} else {
				model.addAttribute("message", "��������");
				return "error";
			}
		} else {
			Msg msg = null;
			// һ����ǩ
			if (!tagName.contains(",") && !tagName.contains("��")) {
				msg = tagService.selectPlaylistPageInfoByTagName(tagName, pn);
			} else {// �����ǩ
				// �ȷָ��ַ���
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
				model.addAttribute("message", "��ѯ����");
				return "error";
			}

			if (playlistPageInfo.getList().size() != 0) {
				model.addAttribute("playlistPageInfo", playlistPageInfo);
				// ����ѡ��ı�ǩ��
				model.addAttribute("tagName", tagName);
				return "playlist/showPlaylist";
			} else {
				// ����ѡ��ı�ǩ��
				model.addAttribute("tagName", tagName);
				model.addAttribute("message", "��������");
				return "playlist/showPlaylist";
			}
		}
	}

	/**
	 * ��ʾ�赥ͼƬ
	 */
	@RequestMapping(value = "showPlaylistImg")
	@ResponseBody
	public String showPlaylistImg(@RequestParam(required = true) long playlistId, HttpServletResponse response) {
		// response.setContentType("image/*");
		// ��ȡ�赥����Ϣ
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
	 * ��ת���赥��ҳ��
	 * 
	 * @param playlistId
	 * @param model
	 * @return ["playlistInfo",�赥������Ϣ;"playlistId",�赥id;"userId",�û�id;"userNickname",�û��ǳ�;
	 *         "playTaglist",�赥��ǩ;"nicePlaylist",��ظ赥�Ƽ�]
	 */
	@RequestMapping(value = "showPlaylistPage")
	public String showPlaylistPage(@RequestParam(required = true) long playlistId, Model model) {

		Msg msg = playlistService.selectPlayListByPlayListId(playlistId);

		if (msg.getCode() == 200) {
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

		// ��ȡ��ظ赥�Ƽ�
		Msg nicePlaylistMsg = playlistService.findNicePlaylistByTagName(tagList);
		@SuppressWarnings("unchecked")
		List<Playlist> nicePlaylist = (List<Playlist>) nicePlaylistMsg.getMap().get("list");

		for (Playlist playlist : nicePlaylist) {
			// ���ݸ赥id��ȡ�����û�
			User createdUser = (User) userService.selectUserById(playlist.getUserId()).getMap().get("user");
			playlist.setUserName(createdUser.getNickname());
		}

		// �赥������Ϣ
		model.addAttribute("playlistInfo", playlistInfo);
		model.addAttribute("playlistId", playlistId);
		model.addAttribute("userId", user.getId());
		model.addAttribute("userNickname", user.getNickname());
		model.addAttribute("playTaglist", tagList);
		model.addAttribute("nicePlaylist", nicePlaylist);
		return "listDetail";
	}

	/**
	 * ��ȡ���Ÿ���
	 * 
	 * @param topNum
	 *            ��ȡǰtopNumλ(topNum=allʱ,��ȡȫ��)
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

			// �ж��Ƿ񳬳�����
			if (Integer.valueOf(topNum) > singerListSelected.size())
				return Msg.error().add("msg", "������Χ");

			List<String> singerList = new ArrayList<String>();
			// ��ȡǰtopNumλ
			for (int i = 0; i < Integer.valueOf(topNum); i++) {
				singerList.add(singerListSelected.get(i));
			}
			return Msg.success().add("selectSingerTopNum", singerList);
		}
		return Msg.success().add("selectSingerTopNum", singerListSelected);
	}

	/**
	 * �����û����Ի��Ƽ�
	 * 
	 * @param userId
	 *            �û�ID
	 * @return Msg["list":List<Playlist>:�赥����]
	 */
	@RequestMapping(value = "findFavoritePlaylistByUserId")
	@ResponseBody
	public Msg findFavoritePlaylistByUserId(long userId) {
		Msg msg = playlistService.findFavoritePlaylistByUserId(userId);
		if (msg.getCode() == 200)
			return Msg.error().add("msg", "�Ƽ�����");
		@SuppressWarnings("unchecked")
		List<Playlist> playlist = (List<Playlist>) msg.getMap().get("list");
		if (playlist.size() == 0)
			return Msg.error().add("msg", "�����Ƽ���¼");
		return msg;
	}

	/**
	 * �½�һ���赥
	 * 
	 * @return Msg��map�У�����������[��msg��:String:������ʾ��Ϣ]<br/>
	 *         ��[��playlist��:Playlist:Ϊ�ղ����playlist������idֵΪ���ݿ������Ľ��]
	 */
	@RequestMapping(value = "createPlaylist")
	@ResponseBody
	public Msg createPlaylistByUserId(@RequestParam(required = true) long userId, @RequestParam(required = true) String playlistName) {
		Playlist playlist = new Playlist();
		// �����û�id��ȡ�û���Ϣ
		Msg userMsg = userService.selectUserById(userId);
		User user = (User) userMsg.getMap().get("user");

		playlist.setUserId(userId);
		playlist.setUserName(user.getNickname());
		playlist.setListName(playlistName);
		playlist.setCollection(0L);
		playlist.setPlayTimes(0L);
		playlist.setCreateTime(new Date());
		playlist.setImgPath(Const.PLAYLIST_IMG_PATH + "default.jpg");
		// ���赥��Ϣ�������ݿ�
		Msg msg = playlistService.insertPlaylist(playlist);
		if (msg.getCode() == 200)
			return msg;
		return msg;
	}

	/**
	 * ���ݸ赥id��ȡ�赥��Ϣ
	 *
	 * @param playlistId
	 * @return Msg["msg" : error:��ʾ��Ϣ��success���赥��Ϣ]
	 */
	@RequestMapping(value = "findPlaylistById")
	@ResponseBody
	public Msg findPlaylistById(@RequestParam(required = true) long playlistId) {
		// ���ݸ赥id��ȡ�赥��Ϣ
		Msg playlistMsg = playlistService.selectPlayListByPlayListId(playlistId);
		Playlist playlistInfo = (Playlist) playlistMsg.getMap().get("msg");

		// ���ݸ赥id��ȡ�赥�ı�ǩid
		List<Long> playlistTagList = tagService.findTagNameByPlaylistId(playlistId);
		List<TagName> tagList = new ArrayList<TagName>();

		// ���ݱ�ǩid��ȡ��ǩ
		for (Long tagId : playlistTagList) {
			Msg tagMsg = tagService.selectTagNameByTagId(tagId);
			tagList.add((TagName) tagMsg.getMap().get("tagName"));
		}

		return playlistService.selectPlayListByPlayListId(playlistId).add("msg", playlistInfo).add("tagList", tagList);
	}

	/**
	 * ĳ���赥�Ĳ��Ŵ���+1
	 *
	 * @param id
	 *            �赥ID
	 *
	 * @return Msg["msg":������Ϣ]
	 */
	@RequestMapping("addPlaytimeOfPlaylist")
	@ResponseBody
	public Msg addPlaytimeOfPlaylist(long playlistId) {
		return playlistService.addPlaytimeOfPlaylist(playlistId);
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
	@RequestMapping("collectPlaylist")
	@ResponseBody
	public Msg collectPlaylist(@RequestParam(required = true) long userId, @RequestParam(required = true) long playlistId) {

		// �ø赥���ղش�����һ
		Msg playlistMsg = playlistService.addCollectionOfPlaylist(playlistId);

		if (playlistMsg.getCode() == 200)
			return playlistMsg;

		return playlistService.insertPlaylistCollection(userId, playlistId);
	}

	/**
	 * ������ ���� �赥
	 * 
	 * @param playlistId
	 *            �赥ID
	 * @param songIds
	 *            Ҫ����ĸ���ID���鼯��
	 * @return Msg������map����[��msg����String:��ʾ��Ϣ ]
	 */
	@RequestMapping("insertPlaylistWithSong")
	@ResponseBody
	public Msg insertPlaylistWithSong(@RequestParam(required = true) long playlistId, @RequestParam(required = true) List<Long> songIds) {
		return playlistService.insertPlaylistWithSong(playlistId, songIds);
	}

	/**
	 * ɾ���û������ĸ赥
	 * 
	 * @param playlistId
	 *            �赥id
	 * @return Msg
	 */
	@RequestMapping("deleteCreatePlaylist")
	@ResponseBody
	public Msg deleteCreatePlaylist(@RequestParam(required = true) long playlistId) {
		return playlistService.deletePlaylistById(playlistId);
	}

	/**
	 * ��ת���ϴ��赥ͼƬҳ��
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
	 * �ϴ��赥ͼƬ
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
		// ��ȡͼƬ��base64��
		imgBase64 = imgBase64.substring(22);
		@SuppressWarnings("restriction")
		byte[] buffer = new BASE64Decoder().decodeBuffer(imgBase64);

		// ��ȡԭ��ͼƬ·��
		Msg playlistMsg = playlistService.selectPlayListByPlayListId(playlistId);
		if (playlistMsg.getCode() == 200)
			return playlistMsg;
		Playlist playlist = (Playlist) playlistMsg.getMap().get("msg");
		String preImgPath = playlist.getImgPath();

		// �ļ��洢·��
		String fileName = Const.PLAYLIST_IMG_PATH + System.currentTimeMillis() + ".jpg";

		// �����ܺ���ļ������ļ���
		FileUtils.writeByteArrayToFile(new File(fileName), buffer);

		// ���ļ�·���������ݿ�
		Msg updatePlaylistMsg = playlistService.updatePlaylistImgPath(playlistId, fileName);

		if (updatePlaylistMsg.getCode() == 100) {
			System.out.println(preImgPath.substring(preImgPath.lastIndexOf("/") + 1, preImgPath.length()));
			// ��ԭ����ͼƬɾ��
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
	 * �޸ĸ赥
	 * 
	 * @param playlist
	 * @param tagName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("updatePlaylist")
	public Msg updatePlaylist(Playlist playlist, String tagName) throws IOException {

		// �޸ĸ赥��Ϣ
		Msg playlistmsg = playlistService.updatePlaylistByPrimaryKey(playlist);

		if (playlistmsg.getCode() == 200)
			return playlistmsg;

		// �����ǩ��Ϊ��,��Ϊ�赥��ӱ�ǩ
		if (tagName != null) {
			List<String> tagNameList = new ArrayList<String>();
			String[] tagNameStrs = tagName.split(",");
			for (String tagNameStr : tagNameStrs) {
				// ���ݱ�ǩ����ȡ��ǩ��Ϣ
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
	 * �ж��û��Ƿ��ղظø赥
	 * 
	 * @param playlistId
	 *            �赥id
	 * @return ���ղط���Msg.error(),����Msg.success()
	 */
	@ResponseBody
	@RequestMapping("isCollection")
	public Msg isCollection(@RequestParam(required = true) long playlistId, HttpSession session) {
		// ��ȡ�û���Ϣ
		User user = (User) session.getAttribute("userInfo");

		// ��ȡ���û��ղصĸ赥����
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
	 * ȡ���ղظ赥
	 * 
	 * @param playlistId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("undoPlaylistCollection")
	public Msg undoPlaylistCollection(@RequestParam(required = true) long playlistId, HttpSession session) {

		// �ø赥���ղش�����һ
		Msg playlistMsg = playlistService.subCollectionOfPlaylist(playlistId);

		if (playlistMsg.getCode() == 200)
			return playlistMsg;

		// ��ȡ�û���Ϣ
		User user = (User) session.getAttribute("userInfo");
		// ȡ���ղظ赥
		return playlistService.undoPlaylistCollection(user.getId(), playlistId);
	}

	/**
	 * �����û�id���ҳ����û����������и赥
	 * 
	 * @param userId
	 * @return Msg.success["list":List&lt;Playlist&gt;:�赥����]
	 */
	@ResponseBody
	@RequestMapping("getCreatePlaylist")
	public Msg getCreatePlaylist(@RequestParam(required = true) long userId) {
		// ��ȡ�û������ĸ赥
		return playlistService.selectPlaylistByUserId(userId);
	}

}
