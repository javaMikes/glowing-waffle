package com.cloudmusic.service;

import com.cloudmusic.util.Msg;

public interface ISongCommentService {
	
	/**
	 * 新建一条评论
	 * @param userId 创建者用户id
	 * @param songId 歌曲id
	 * @param content 内容
	 * @return Msg["songComment":SongComment:刚增加的歌曲评论对象]
	 */
	public Msg insertSongComment(long userId, long songId, String content);
	
	/**
	 * 回复某条song评论
	 * 
	 * @param commentId
	 *            被回复的那一条歌单评论的id
	 * @param userId
	 *            回复者的userId
	 * @param content
	 *            回复内容
	 * @return 成功success，Map["songComment",SongComment,新创建的回复的评论对象]<br/>
	 *         失败error &lt;hi&gt;
	 */
	public Msg replyComment(long commentId, long userId, String content);
	
	/**
	 * 根据评论id删除评论
	 * 
	 * @param commentId
	 *            评论id
	 * @return 删除成功success，失败error
	 */
	public Msg deleteComment(long commentId);
	
	/**
	 * 根据歌单id搜索出该歌曲的所有评论(不包括回复)，并按照时间顺序（降序）排序.分页显示
	 * 
	 * @param songId
	 *            歌单id
	 * @return success,Map["list":List&lt;SongComment&gt;:评论集合]
	 */
	public Msg selectCommentBySongId(long songId, Integer pn);

	/**
	 * 根据一条评论的ID，获取这条评论的所有回复
	 * @param commentId 评论ID
	 * @return success,Map["list":List&lt;SongComment&gt;:回复评论集合]
	 */
	public Msg selectReplyCommentByParentId(long commentId);
	
	/**
	 * 根据用户id搜索出该用户发表过的评论，按时间顺序（降序）排序
	 * 
	 * @param userId
	 *            用户id
	 * @return success,Map["list":List&lt;SongComment&gt;:评论集合]
	 */
	public Msg selectCommentByUserId(long userId);
	
	/**
	 * 根据用户id，读取该用户未读的歌曲评论集合
	 * 
	 * @param userId
	 *            用户id
	 * @return 
	 *         Msg["list":List{SongComment}:返回，回复该用户评论的未看评论集合]
	 */
	public Msg selectInformByUserId(long userId);

	/**
	 * 更新未读评论状态，更新为已读
	 * 
	 * @param songCommentId
	 *            评论id
	 * @return Msg.success <br/>
	 *         失败则error
	 */
	public Msg updateIsRead(long songCommentId);
	
	/**
	 * 根据评论id获取评论信息
	 * 
	 * @param commentId
	 *            评论id
	 * @return 成功"SongComment"， 评论信息 失败error
	 */
	public Msg selectByPrimarykey(long commentId);
	
	/**
	 * 举报评论，0变1，1变1 ,2 会返回error
	 * @param commentId 评论id
	 * @return 评论举报状态为0或者1都会变成1，返回success<br/>
	 * 举报状态为2时，返回error
	 */
	public Msg reportComment(long commentId);
	
	/**
	 * 管理员处理举报评论，评论审核为没问题时，将评论状态设置为已通过审核
	 * @param commentId 被举报评论id
	 * @return 评论1变2,或2变2，返回success<br/>
	 * 如果评论为0，则返回error
	 */
	public Msg dealReportComment(long commentId);
	
	/**
	 * 查找被举报，未处理的评论
	 * @return Msg["list":List{SongComment}:被举报的歌曲评论]
	 */
	public Msg selectReportedComment();
}
