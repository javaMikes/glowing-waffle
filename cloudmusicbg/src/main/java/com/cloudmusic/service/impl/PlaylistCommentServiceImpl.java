package com.cloudmusic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.PlaylistComment;
import com.cloudmusic.bean.PlaylistCommentExample;
import com.cloudmusic.bean.PlaylistCommentExample.Criteria;
import com.cloudmusic.bean.PlaylistExample;
import com.cloudmusic.mapper.PlaylistCommentMapper;
import com.cloudmusic.mapper.PlaylistMapper;
import com.cloudmusic.service.IPlaylistCommentService;
import com.cloudmusic.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class PlaylistCommentServiceImpl implements IPlaylistCommentService {

	@Autowired
	PlaylistCommentMapper playlistCommentMapper;

	@Autowired
	PlaylistMapper playlistMapper;

	public Msg insertPlaylistComment(long userId, long playlistId, String content) {
		PlaylistComment p = new PlaylistComment();
		p.setUserId(userId);
		p.setPlaylistId(playlistId);
		p.setContent(content);
		// 设置消息未读
		p.setIsRead(false);
		p.setIsReported(0);
		// p.setCommentTime(new Date());

		int count = playlistCommentMapper.insert(p);
		if (count <= 0)
			return Msg.error();

		return Msg.success().add("playlistComment", p);
	}

	public Msg replyComment(long commentId, long userId, String content) {
		PlaylistComment key = playlistCommentMapper.selectByPrimaryKey(commentId);
		if (key == null)
			return Msg.error();

		PlaylistComment p = new PlaylistComment();
		p.setUserId(userId);
		p.setContent(content);
		// p.setCommentTime(new Date());
		// 设置消息未读
		p.setIsRead(false);
		p.setIsReported(0);
		p.setParentId(commentId);
		p.setPlaylistId(key.getPlaylistId());
		int count = playlistCommentMapper.insert(p);
		if (count <= 0)
			return Msg.error();

		return Msg.success().add("playlistComment", p);
	}

	public Msg deleteComment(long commentId) {
		int count = playlistCommentMapper.deleteByPrimaryKey(commentId);
		return (count > 0) ? Msg.success() : Msg.error();
	}

	public Msg selectCommentByPlaylistId(long playlistId, Integer pn) {
		PlaylistCommentExample example = new PlaylistCommentExample();
		Criteria c = example.createCriteria();
		c.andPlaylistIdEqualTo(playlistId);
		c.andParentIdIsNull();
		example.setOrderByClause("comment_time DESC");

		// 指定PageHelper当前页,每页显示长度
		PageHelper.startPage(pn, 5);

		List<PlaylistComment> list = playlistCommentMapper.selectByExample(example);

		PageInfo<PlaylistComment> playlistCommentPageInfo = new PageInfo<PlaylistComment>(list, 5);
		return Msg.success().add("list", playlistCommentPageInfo);
	}

	public Msg selectCommentByUserId(long userId) {
		PlaylistCommentExample example = new PlaylistCommentExample();
		Criteria c = example.createCriteria();
		c.andUserIdEqualTo(userId);
		example.setOrderByClause("comment_time DESC");
		List<PlaylistComment> list = playlistCommentMapper.selectByExample(example);
		return Msg.success().add("list", list);
	}

	public Msg selectReplyCommentByParentId(long commentId) {
		PlaylistCommentExample example = new PlaylistCommentExample();
		Criteria c = example.createCriteria();
		c.andParentIdEqualTo(commentId);
		example.setOrderByClause("comment_time DESC");

		List<PlaylistComment> list = playlistCommentMapper.selectByExample(example);

		return Msg.success().add("list", list);
	}

	public Msg selectInformByUserId(long userId) {
		List<PlaylistComment> listResult1 = null;
		List<PlaylistComment> listResult2 = null;

		// 先获取该用户创建的歌单id中，未读评论
		PlaylistExample example1 = new PlaylistExample();
		com.cloudmusic.bean.PlaylistExample.Criteria c1 = example1.createCriteria();
		c1.andUserIdEqualTo(userId);
		List<Playlist> list1 = playlistMapper.selectByExample(example1);
		if (list1.size() <= 0) {
			listResult1 = new ArrayList<PlaylistComment>();
		} else {
			List<Long> list_cup = new ArrayList<Long>();
			for (Playlist p : list1) {
				list_cup.add(p.getId());
			}

			PlaylistCommentExample example2 = new PlaylistCommentExample();
			Criteria c2 = example2.createCriteria();
			c2.andPlaylistIdIn(list_cup);
			c2.andIsReadEqualTo(false);// 设置为未读
			c2.andParentIdIsNull();// 并且是基础评论
			example2.setOrderByClause("comment_time DESC");
			listResult1 = playlistCommentMapper.selectByExample(example2);
		}

		// 再获取回复该用户的未读评论
		PlaylistCommentExample example3 = new PlaylistCommentExample();
		Criteria c3 = example3.createCriteria();
		c3.andParentIdIsNull();
		c3.andUserIdEqualTo(userId);// 获取该用户发表过的基础评论
		List<PlaylistComment> list2 = playlistCommentMapper.selectByExample(example3);
		if (list2.size() <= 0) {
			listResult2 = new ArrayList<PlaylistComment>();
		} else {
			List<Long> list_cup = new ArrayList<Long>();
			for (PlaylistComment p : list2) {
				list_cup.add(p.getId());
			}

			PlaylistCommentExample example4 = new PlaylistCommentExample();
			Criteria c4 = example4.createCriteria();
			c4.andParentIdIn(list_cup);
			c4.andIsReadEqualTo(false);

			example4.setOrderByClause("comment_time DESC");
			listResult2 = playlistCommentMapper.selectByExample(example4);
		}

		return Msg.success().add("list1", listResult1).add("list2", listResult2);
	}

	public Msg updateIsRead(long playlistCommentId) {
		PlaylistComment p = playlistCommentMapper.selectByPrimaryKey(playlistCommentId);
		if (p == null)
			return Msg.error();

		p.setIsRead(true);
		return (playlistCommentMapper.updateByPrimaryKey(p) > 0) ? Msg.success() : Msg.error();
	}

	// 根据评论id获取评论信息
	public Msg selectByPrimarykey(long commentId) {
		PlaylistComment p = playlistCommentMapper.selectByPrimaryKey(commentId);
		if (p == null)
			return Msg.error();
		return Msg.success().add("playlistCommnet", p);
	}

	public Msg reportComment(long commentId) {
		PlaylistComment p = playlistCommentMapper.selectByPrimaryKey(commentId);
		if (p == null)
			return Msg.error();

		int reported = p.getIsReported();
		if (reported == 2)
			return Msg.error();

		p.setIsReported(1);// 将举报状态设置为1
		playlistCommentMapper.updateByPrimaryKey(p);
		return Msg.success();
	}

	public Msg dealReportComment(long commentId) {
		PlaylistComment p = playlistCommentMapper.selectByPrimaryKey(commentId);
		if (p == null)
			return Msg.error();

		if (p.getIsReported() == 0)
			return Msg.error();

		p.setIsReported(2);
		playlistCommentMapper.updateByPrimaryKey(p);
		return Msg.success();
	}

	public Msg selectReportedComment() {
		PlaylistCommentExample example = new PlaylistCommentExample();
		Criteria c = example.createCriteria();
		c.andIsReportedEqualTo(1);
		example.setOrderByClause("id");
		List<PlaylistComment> list = playlistCommentMapper.selectByExample(example);
		return Msg.success().add("list", list);
	}

}
