package com.cloudmusic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudmusic.bean.SongComment;
import com.cloudmusic.bean.SongCommentExample;
import com.cloudmusic.bean.SongCommentExample.Criteria;
import com.cloudmusic.mapper.SongCommentMapper;
import com.cloudmusic.service.ISongCommentService;
import com.cloudmusic.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SongCommentServiceImpl implements ISongCommentService {

	@Autowired
	SongCommentMapper songCommentMapper;

	public Msg insertSongComment(long userId, long songId, String content) {
		SongComment p = new SongComment();
		p.setUserId(userId);
		p.setSongId(songId);
		p.setContent(content);
		// 设置消息未读
		p.setIsRead(false);
		p.setIsReported(0);
		int count = songCommentMapper.insert(p);
		if (count <= 0)
			return Msg.error();

		return Msg.success().add("songComment", p);
	}

	public Msg replyComment(long commentId, long userId, String content) {
		SongComment key = songCommentMapper.selectByPrimaryKey(commentId);
		if (key == null)
			return Msg.error();

		SongComment sC = new SongComment();
		sC.setUserId(userId);
		sC.setContent(content);
		sC.setParentId(commentId);
		sC.setSongId(key.getSongId());
		// 设置消息未读
		sC.setIsRead(false);
		sC.setIsReported(0);

		int count = songCommentMapper.insert(sC);
		if (count <= 0)
			return Msg.error();

		return Msg.success().add("songComment", sC);
	}

	public Msg deleteComment(long commentId) {
		int count = songCommentMapper.deleteByPrimaryKey(commentId);
		return (count > 0) ? Msg.success() : Msg.error();
	}

	public Msg selectCommentBySongId(long songId, Integer pn) {
		SongCommentExample example = new SongCommentExample();
		Criteria c = example.createCriteria();
		c.andSongIdEqualTo(songId);
		c.andParentIdIsNull();
		example.setOrderByClause("comment_time DESC");

		// 指定PageHelper
		PageHelper.startPage(pn, 5);

		List<SongComment> list = songCommentMapper.selectByExample(example);

		PageInfo<SongComment> songCommentPageInfo = new PageInfo<SongComment>(list, 5);
		return Msg.success().add("list", songCommentPageInfo);
	}

	public Msg selectReplyCommentByParentId(long commentId) {
		SongCommentExample example = new SongCommentExample();
		Criteria c = example.createCriteria();
		c.andParentIdEqualTo(commentId);
		example.setOrderByClause("comment_time DESC");

		List<SongComment> list = songCommentMapper.selectByExample(example);

		return Msg.success().add("list", list);
	}

	public Msg selectCommentByUserId(long userId) {
		SongCommentExample example = new SongCommentExample();
		Criteria c = example.createCriteria();
		c.andUserIdEqualTo(userId);
		example.setOrderByClause("comment_time DESC");
		List<SongComment> list = songCommentMapper.selectByExample(example);
		return Msg.success().add("list", list);
	}

	public Msg selectInformByUserId(long userId) {

		// 先获得该用户发表过的基础评论
		SongCommentExample example1 = new SongCommentExample();
		Criteria c1 = example1.createCriteria();
		c1.andParentIdIsNull();
		c1.andUserIdEqualTo(userId);
		List<SongComment> list1 = songCommentMapper.selectByExample(example1);
		if (list1.size() <= 0)
			return Msg.success().add("list", new ArrayList<SongComment>());

		// 取出这些基础评论的id
		List<Long> list_cup = new ArrayList<Long>();
		for (SongComment p : list1) {
			list_cup.add(p.getId());
		}

		// 获得回复该用户评论的回复评论
		SongCommentExample example2 = new SongCommentExample();
		Criteria c2 = example2.createCriteria();
		c2.andParentIdIn(list_cup);
		c2.andIsReadEqualTo(false);
		example2.setOrderByClause("comment_time DESC");

		List<SongComment> result = songCommentMapper.selectByExample(example2);
		return Msg.success().add("list", result);
	}

	public Msg updateIsRead(long songCommentId) {
		SongComment p = songCommentMapper.selectByPrimaryKey(songCommentId);
		if (p == null)
			return Msg.error();

		p.setIsRead(true);
		return (songCommentMapper.updateByPrimaryKey(p) > 0) ? Msg.success() : Msg.error();
	}

	public Msg selectByPrimarykey(long commentId) {
		SongComment p = songCommentMapper.selectByPrimaryKey(commentId);
		if (p == null)
			return Msg.error();

		return Msg.success().add("songComment", p);
	}

	public Msg reportComment(long commentId) {
		SongComment s = songCommentMapper.selectByPrimaryKey(commentId);
		if (s == null)
			return Msg.error();

		if (s.getIsReported() == 2)
			return Msg.error();

		s.setIsReported(1);
		songCommentMapper.updateByPrimaryKey(s);
		return Msg.success();
	}

	public Msg dealReportComment(long commentId) {
		SongComment s = songCommentMapper.selectByPrimaryKey(commentId);
		if (s == null)
			return Msg.error();

		if (s.getIsReported() == 0)
			return Msg.error();

		s.setIsReported(2);
		songCommentMapper.updateByPrimaryKey(s);
		return Msg.success();
	}

	public Msg selectReportedComment() {
		SongCommentExample example = new SongCommentExample();
		Criteria c = example.createCriteria();
		c.andIsReportedEqualTo(1);
		example.setOrderByClause("id");

		List<SongComment> list = songCommentMapper.selectByExample(example);
		return Msg.success().add("list", list);
	}

}
