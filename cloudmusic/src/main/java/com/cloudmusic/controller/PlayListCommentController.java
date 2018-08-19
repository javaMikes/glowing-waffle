package com.cloudmusic.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudmusic.bean.PlaylistComment;
import com.cloudmusic.bean.User;
import com.cloudmusic.service.IPlaylistCommentService;
import com.cloudmusic.service.IUserService;
import com.cloudmusic.util.Msg;
import com.github.pagehelper.PageInfo;

@Controller
public class PlayListCommentController {

	@Autowired
	IPlaylistCommentService playlistCommentService;

	@Autowired
	IUserService userService;

	/**
	 * ���۸赥
	 * 
	 * @param userId
	 *            ������ID
	 * @param playlistId
	 *            �赥ID
	 * @param content
	 *            �������ݣ������ <u>����</u> ��һЩ�������
	 * @return �ɹ�success,�����ظ��½����۵Ķ���.Map["playlistComment",PlaylistComment,���½������۶��󣬰����Զ����ɵ�id]<br/>
	 *         ʧ��error
	 */
	@RequestMapping("addPlayListComment")
	@ResponseBody
	public Msg insertPlaylistComment(@RequestParam(required = true) long userId, @RequestParam(required = true) long playlistId,
			@RequestParam(required = true) String content) {

		Msg msg = playlistCommentService.insertPlaylistComment(userId, playlistId, content);

		if (msg.getCode() == 200) {
			return msg;
		}

		PlaylistComment playlistComment = (PlaylistComment) msg.getMap().get("playlistComment");

		// �����û�id��ȡ�û���Ϣ
		Msg userInfoMsg = userService.selectUserById(playlistComment.getUserId());
		User user = (User) userInfoMsg.getMap().get("user");

		if (user == null)
			return Msg.error().add("msg", "�����ڸ����۵��û�id");

		playlistComment.setUserName(user.getNickname());

		return Msg.success().add("playlistComment", playlistComment);
	}

	/**
	 * ���ݸ赥id�������ø赥���������ۣ�������ʱ��˳�򣨽�������
	 * 
	 * @param playlistId
	 *            �赥id
	 * @return success,Map["playlistCommentPageInfo":���ۼ���]
	 *         Map["playlistReplyCommentTotalList":���۵Ļظ�����]
	 */
	@RequestMapping("showCommentByPlaylistId")
	@ResponseBody
	public Msg selectCommentByPlaylistId(@RequestParam(required = true) long playlistId, @RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		Msg msg = playlistCommentService.selectCommentByPlaylistId(playlistId, pn);

		if (msg.getCode() == 200) {
			return msg;
		}

		// ��ȡ���۵ļ���
		@SuppressWarnings("unchecked")
		PageInfo<PlaylistComment> playlistCommentPageInfo = (PageInfo<PlaylistComment>) msg.getMap().get("list");

		List<PlaylistComment> playlistReplyCommentTotalList = new ArrayList<PlaylistComment>();
		// ��������id��ȡ���еĻظ�
		for (PlaylistComment playlistComment : playlistCommentPageInfo.getList()) {
			Msg replyMsg = playlistCommentService.selectReplyCommentByParentId(playlistComment.getId());
			@SuppressWarnings("unchecked")
			List<PlaylistComment> playlistReplyCommentList = (List<PlaylistComment>) replyMsg.getMap().get("list");
			for (PlaylistComment playlistReplyComment : playlistReplyCommentList) {

				// �����û�id��ȡ�û���Ϣ
				Msg userInfoMsg = userService.selectUserById(playlistReplyComment.getUserId());
				User user = (User) userInfoMsg.getMap().get("user");

				if (user == null)
					return Msg.error().add("msg", "�����ڸ����۵��û�id");

				playlistReplyComment.setUserName(user.getNickname());

				playlistReplyCommentTotalList.add(playlistReplyComment);
			}
		}

		// ��ȡÿ�����۵��û�id�������û�id��ȡ�û���
		for (PlaylistComment playlistComment : playlistCommentPageInfo.getList()) {
			// �����û�id��ȡ�û���Ϣ
			Msg userInfoMsg = userService.selectUserById(playlistComment.getUserId());
			User user = (User) userInfoMsg.getMap().get("user");

			if (user == null)
				return Msg.error().add("msg", "�����ڸ����۵��û�id");

			playlistComment.setUserName(user.getNickname());
		}
		return Msg.success().add("playlistCommentPageInfo", playlistCommentPageInfo).add("playlistReplyCommentTotalList",
				playlistReplyCommentTotalList);
	}

	/**
	 * �ظ�ĳ���赥����
	 * 
	 * @param commentId
	 *            ���ظ�����һ���赥���۵�id
	 * @param userId
	 *            �ظ��ߵ�userId
	 * @param content
	 *            �ظ�����
	 * @return �ɹ�success��Map["playlistComment",PlaylistComment,�´����Ļظ������۶���]<br/>
	 *         ʧ��error
	 */
	@RequestMapping("replyComment")
	@ResponseBody
	public Msg replyComment(@RequestParam(required = true) long commentId, @RequestParam(required = true) long userId,
			@RequestParam(required = true) String content) {
		Msg msg = playlistCommentService.replyComment(commentId, userId, content);

		if (msg.getCode() == 200) {
			return msg;
		}

		PlaylistComment playlistComment = (PlaylistComment) msg.getMap().get("playlistComment");

		// �����û�id��ȡ�û���Ϣ
		Msg userInfoMsg = userService.selectUserById(playlistComment.getUserId());
		User user = (User) userInfoMsg.getMap().get("user");

		if (user == null)
			return Msg.error().add("msg", "�����ڸ����۵��û�id");

		playlistComment.setUserName(user.getNickname());

		return Msg.success().add("playlistComment", playlistComment);

	}

	/**
	 * �ٱ����ۣ�0��1��1��1 ,2 �᷵��error
	 * 
	 * @param commentId
	 *            ����id
	 * @return ���۾ٱ�״̬Ϊ0����1������1������success<br/>
	 *         �ٱ�״̬Ϊ2ʱ������error
	 */
	@RequestMapping("reportPlaylistComment")
	@ResponseBody
	public Msg reportPlaylistComment(@RequestParam(required = true) long commentId) {
		return playlistCommentService.reportComment(commentId);
	}

}
