package com.cloudmusic.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudmusic.bean.SongComment;
import com.cloudmusic.bean.User;
import com.cloudmusic.service.ISingerService;
import com.cloudmusic.service.ISongCommentService;
import com.cloudmusic.service.IUserService;
import com.cloudmusic.util.Msg;
import com.github.pagehelper.PageInfo;

@Controller
public class SongCommentController {

	@Autowired
	ISingerService singerService;

	@Autowired
	ISongCommentService songCommentService;

	@Autowired
	IUserService userService;

	/**
	 * ���۸���
	 * 
	 * @param userId
	 *            ������ID
	 * @param songId
	 *            �赥ID
	 * @param content
	 *            �������ݣ������ <u>����</u> ��һЩ�������
	 * @return �ɹ�success,�����ظ��½����۵Ķ���.Map["songComment",SongComment,���½������۶��󣬰����Զ����ɵ�id]<br/>
	 *         ʧ��error
	 */
	@RequestMapping("addSongComment")
	@ResponseBody
	public Msg insertSongComment(@RequestParam(required = true) long userId, @RequestParam(required = true) long songId,
			@RequestParam(required = true) String content) {

		Msg msg = songCommentService.insertSongComment(userId, songId, content);

		if (msg.getCode() == 200) {
			return msg;
		}

		SongComment songComment = (SongComment) msg.getMap().get("songComment");

		// �����û�id��ȡ�û���Ϣ
		Msg userInfoMsg = userService.selectUserById(songComment.getUserId());
		User user = (User) userInfoMsg.getMap().get("user");

		if (user == null)
			return Msg.error().add("msg", "�����ڸ����۵��û�id");

		songComment.setUserName(user.getNickname());

		return Msg.success().add("songComment", songComment);
	}

	/**
	 * ���ݸ赥id�������ø赥���������ۣ�������ʱ��˳�򣨽�������
	 * 
	 * @param songId
	 *            �赥id
	 * @return success,Map["songCommentPageInfo":���ۼ���]
	 *         Map["songReplyCommentTotalList":���۵Ļظ�����]
	 */
	@RequestMapping("showCommentBySongId")
	@ResponseBody
	public Msg selectCommentByPlaylistId(@RequestParam(required = true) long songId, @RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		Msg msg = songCommentService.selectCommentBySongId(songId, pn);

		if (msg.getCode() == 200) {
			return msg;
		}

		// ��ȡ���۵ļ���
		@SuppressWarnings("unchecked")
		PageInfo<SongComment> songtCommentPageInfo = (PageInfo<SongComment>) msg.getMap().get("list");

		List<SongComment> songReplyCommentTotalList = new ArrayList<SongComment>();
		// ��������id��ȡ���еĻظ�
		for (SongComment SongComment : songtCommentPageInfo.getList()) {
			Msg replyMsg = songCommentService.selectReplyCommentByParentId(SongComment.getId());
			@SuppressWarnings("unchecked")
			List<SongComment> songReplyCommentList = (List<SongComment>) replyMsg.getMap().get("list");
			for (SongComment songReplyComment : songReplyCommentList) {

				// �����û�id��ȡ�û���Ϣ
				Msg userInfoMsg = userService.selectUserById(songReplyComment.getUserId());
				User user = (User) userInfoMsg.getMap().get("user");

				if (user == null)
					return Msg.error().add("msg", "�����ڸ����۵��û�id");

				songReplyComment.setUserName(user.getNickname());

				songReplyCommentTotalList.add(songReplyComment);
			}
		}

		// ��ȡÿ�����۵��û�id�������û�id��ȡ�û���
		for (SongComment songComment : songtCommentPageInfo.getList()) {
			// �����û�id��ȡ�û���Ϣ
			Msg userInfoMsg = userService.selectUserById(songComment.getUserId());
			User user = (User) userInfoMsg.getMap().get("user");

			if (user == null)
				return Msg.error().add("msg", "�����ڸ����۵��û�id");

			songComment.setUserName(user.getNickname());
		}
		return Msg.success().add("songtCommentPageInfo", songtCommentPageInfo).add("songReplyCommentTotalList", songReplyCommentTotalList);
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
	 * @return �ɹ�success��Map["songComment",SongComment,�´����Ļظ������۶���]<br/>
	 *         ʧ��error
	 */
	@RequestMapping("replySongComment")
	@ResponseBody
	public Msg replySongComment(@RequestParam(required = true) long commentId, @RequestParam(required = true) long userId,
			@RequestParam(required = true) String content) {
		Msg msg = songCommentService.replyComment(commentId, userId, content);

		if (msg.getCode() == 200) {
			return msg;
		}

		SongComment songComment = (SongComment) msg.getMap().get("songComment");

		// �����û�id��ȡ�û���Ϣ
		Msg userInfoMsg = userService.selectUserById(songComment.getUserId());
		User user = (User) userInfoMsg.getMap().get("user");

		if (user == null)
			return Msg.error().add("msg", "�����ڸ����۵��û�id");

		songComment.setUserName(user.getNickname());

		return Msg.success().add("songComment", songComment);

	}

	/**
	 * ����Ա����ٱ����ۣ��������Ϊû����ʱ��������״̬����Ϊ��ͨ�����
	 * 
	 * @param commentId
	 *            ���ٱ�����id
	 * @return ����1��2,��2��2������success<br/>
	 *         �������Ϊ0���򷵻�error
	 */
	@RequestMapping("reportSongComment")
	@ResponseBody
	public Msg reportSongComment(@RequestParam(required = true) long commentId) {
		return songCommentService.reportComment(commentId);
	}
}
