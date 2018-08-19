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
	 * 评论歌单
	 * 
	 * @param userId
	 *            评论人ID
	 * @param playlistId
	 *            歌单ID
	 * @param content
	 *            评论内容（请包含 <u>换行</u> 等一些特殊符）
	 * @return 成功success,并返回刚新建评论的对象.Map["playlistComment",PlaylistComment,刚新建的评论对象，包含自动生成的id]<br/>
	 *         失败error
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

		// 根据用户id获取用户信息
		Msg userInfoMsg = userService.selectUserById(playlistComment.getUserId());
		User user = (User) userInfoMsg.getMap().get("user");

		if (user == null)
			return Msg.error().add("msg", "不存在该评论的用户id");

		playlistComment.setUserName(user.getNickname());

		return Msg.success().add("playlistComment", playlistComment);
	}

	/**
	 * 根据歌单id搜索出该歌单的所有评论，并按照时间顺序（降序）排序
	 * 
	 * @param playlistId
	 *            歌单id
	 * @return success,Map["playlistCommentPageInfo":评论集合]
	 *         Map["playlistReplyCommentTotalList":评论的回复集合]
	 */
	@RequestMapping("showCommentByPlaylistId")
	@ResponseBody
	public Msg selectCommentByPlaylistId(@RequestParam(required = true) long playlistId, @RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		Msg msg = playlistCommentService.selectCommentByPlaylistId(playlistId, pn);

		if (msg.getCode() == 200) {
			return msg;
		}

		// 获取评论的集合
		@SuppressWarnings("unchecked")
		PageInfo<PlaylistComment> playlistCommentPageInfo = (PageInfo<PlaylistComment>) msg.getMap().get("list");

		List<PlaylistComment> playlistReplyCommentTotalList = new ArrayList<PlaylistComment>();
		// 根据评论id获取所有的回复
		for (PlaylistComment playlistComment : playlistCommentPageInfo.getList()) {
			Msg replyMsg = playlistCommentService.selectReplyCommentByParentId(playlistComment.getId());
			@SuppressWarnings("unchecked")
			List<PlaylistComment> playlistReplyCommentList = (List<PlaylistComment>) replyMsg.getMap().get("list");
			for (PlaylistComment playlistReplyComment : playlistReplyCommentList) {

				// 根据用户id获取用户信息
				Msg userInfoMsg = userService.selectUserById(playlistReplyComment.getUserId());
				User user = (User) userInfoMsg.getMap().get("user");

				if (user == null)
					return Msg.error().add("msg", "不存在该评论的用户id");

				playlistReplyComment.setUserName(user.getNickname());

				playlistReplyCommentTotalList.add(playlistReplyComment);
			}
		}

		// 获取每条评论的用户id并根据用户id获取用户名
		for (PlaylistComment playlistComment : playlistCommentPageInfo.getList()) {
			// 根据用户id获取用户信息
			Msg userInfoMsg = userService.selectUserById(playlistComment.getUserId());
			User user = (User) userInfoMsg.getMap().get("user");

			if (user == null)
				return Msg.error().add("msg", "不存在该评论的用户id");

			playlistComment.setUserName(user.getNickname());
		}
		return Msg.success().add("playlistCommentPageInfo", playlistCommentPageInfo).add("playlistReplyCommentTotalList",
				playlistReplyCommentTotalList);
	}

	/**
	 * 回复某条歌单评论
	 * 
	 * @param commentId
	 *            被回复的那一条歌单评论的id
	 * @param userId
	 *            回复者的userId
	 * @param content
	 *            回复内容
	 * @return 成功success，Map["playlistComment",PlaylistComment,新创建的回复的评论对象]<br/>
	 *         失败error
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

		// 根据用户id获取用户信息
		Msg userInfoMsg = userService.selectUserById(playlistComment.getUserId());
		User user = (User) userInfoMsg.getMap().get("user");

		if (user == null)
			return Msg.error().add("msg", "不存在该评论的用户id");

		playlistComment.setUserName(user.getNickname());

		return Msg.success().add("playlistComment", playlistComment);

	}

	/**
	 * 举报评论，0变1，1变1 ,2 会返回error
	 * 
	 * @param commentId
	 *            评论id
	 * @return 评论举报状态为0或者1都会变成1，返回success<br/>
	 *         举报状态为2时，返回error
	 */
	@RequestMapping("reportPlaylistComment")
	@ResponseBody
	public Msg reportPlaylistComment(@RequestParam(required = true) long commentId) {
		return playlistCommentService.reportComment(commentId);
	}

}
