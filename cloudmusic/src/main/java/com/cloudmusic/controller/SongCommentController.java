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
	 * 评论歌曲
	 * 
	 * @param userId
	 *            评论人ID
	 * @param songId
	 *            歌单ID
	 * @param content
	 *            评论内容（请包含 <u>换行</u> 等一些特殊符）
	 * @return 成功success,并返回刚新建评论的对象.Map["songComment",SongComment,刚新建的评论对象，包含自动生成的id]<br/>
	 *         失败error
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

		// 根据用户id获取用户信息
		Msg userInfoMsg = userService.selectUserById(songComment.getUserId());
		User user = (User) userInfoMsg.getMap().get("user");

		if (user == null)
			return Msg.error().add("msg", "不存在该评论的用户id");

		songComment.setUserName(user.getNickname());

		return Msg.success().add("songComment", songComment);
	}

	/**
	 * 根据歌单id搜索出该歌单的所有评论，并按照时间顺序（降序）排序
	 * 
	 * @param songId
	 *            歌单id
	 * @return success,Map["songCommentPageInfo":评论集合]
	 *         Map["songReplyCommentTotalList":评论的回复集合]
	 */
	@RequestMapping("showCommentBySongId")
	@ResponseBody
	public Msg selectCommentByPlaylistId(@RequestParam(required = true) long songId, @RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		Msg msg = songCommentService.selectCommentBySongId(songId, pn);

		if (msg.getCode() == 200) {
			return msg;
		}

		// 获取评论的集合
		@SuppressWarnings("unchecked")
		PageInfo<SongComment> songtCommentPageInfo = (PageInfo<SongComment>) msg.getMap().get("list");

		List<SongComment> songReplyCommentTotalList = new ArrayList<SongComment>();
		// 根据评论id获取所有的回复
		for (SongComment SongComment : songtCommentPageInfo.getList()) {
			Msg replyMsg = songCommentService.selectReplyCommentByParentId(SongComment.getId());
			@SuppressWarnings("unchecked")
			List<SongComment> songReplyCommentList = (List<SongComment>) replyMsg.getMap().get("list");
			for (SongComment songReplyComment : songReplyCommentList) {

				// 根据用户id获取用户信息
				Msg userInfoMsg = userService.selectUserById(songReplyComment.getUserId());
				User user = (User) userInfoMsg.getMap().get("user");

				if (user == null)
					return Msg.error().add("msg", "不存在该评论的用户id");

				songReplyComment.setUserName(user.getNickname());

				songReplyCommentTotalList.add(songReplyComment);
			}
		}

		// 获取每条评论的用户id并根据用户id获取用户名
		for (SongComment songComment : songtCommentPageInfo.getList()) {
			// 根据用户id获取用户信息
			Msg userInfoMsg = userService.selectUserById(songComment.getUserId());
			User user = (User) userInfoMsg.getMap().get("user");

			if (user == null)
				return Msg.error().add("msg", "不存在该评论的用户id");

			songComment.setUserName(user.getNickname());
		}
		return Msg.success().add("songtCommentPageInfo", songtCommentPageInfo).add("songReplyCommentTotalList", songReplyCommentTotalList);
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
	 * @return 成功success，Map["songComment",SongComment,新创建的回复的评论对象]<br/>
	 *         失败error
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

		// 根据用户id获取用户信息
		Msg userInfoMsg = userService.selectUserById(songComment.getUserId());
		User user = (User) userInfoMsg.getMap().get("user");

		if (user == null)
			return Msg.error().add("msg", "不存在该评论的用户id");

		songComment.setUserName(user.getNickname());

		return Msg.success().add("songComment", songComment);

	}

	/**
	 * 管理员处理举报评论，评论审核为没问题时，将评论状态设置为已通过审核
	 * 
	 * @param commentId
	 *            被举报评论id
	 * @return 评论1变2,或2变2，返回success<br/>
	 *         如果评论为0，则返回error
	 */
	@RequestMapping("reportSongComment")
	@ResponseBody
	public Msg reportSongComment(@RequestParam(required = true) long commentId) {
		return songCommentService.reportComment(commentId);
	}
}
