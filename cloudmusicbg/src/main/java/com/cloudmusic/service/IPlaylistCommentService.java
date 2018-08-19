package com.cloudmusic.service;

import com.cloudmusic.util.Msg;

public interface IPlaylistCommentService {

	/**
	 * 新增一条歌单评论（不做userId和playlistId的任何验证）
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
	public Msg insertPlaylistComment(long userId, long playlistId, String content);

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
	 * 根据歌单id搜索出该歌单的所有评论(不包括回复)，并按照时间顺序（降序）排序.分页显示
	 * 
	 * @param playlistId
	 *            歌单id
	 * @return success,Map["list":List&lt;PlaylistComment&gt;:评论集合]
	 */
	public Msg selectCommentByPlaylistId(long playlistId, Integer pn);

	/**
	 * 根据一条评论的ID，获取这条评论的所有回复
	 * 
	 * @param commentId
	 *            评论ID
	 * @return success,Map["list":List&lt;PlaylistComment&gt;:回复评论集合]
	 */
	public Msg selectReplyCommentByParentId(long commentId);

	/**
	 * 根据用户id搜索出该用户发表过的评论，按时间顺序（降序）排序
	 * 
	 * @param userId
	 *            用户id
	 * @return success,Map["list":List&lt;PlaylistComment&gt;:评论集合]
	 */
	public Msg selectCommentByUserId(long userId);

	/**
	 * 根据用户id，读取该用户未读的歌单评论集合
	 * 
	 * @param userId
	 *            用户id
	 * @return Msg["list1":List{PlaylistComment}:返回该用户创建的歌单，被评论但未看的评论集合]<br/>
	 *         Msg["list2":List{PlaylistComment}:返回，回复该用户评论的未看评论集合]
	 */
	public Msg selectInformByUserId(long userId);

	/**
	 * 更新未读评论状态，更新为已读
	 * 
	 * @param playlistCommentId
	 *            评论id
	 * @return Msg.success <br/>
	 *         失败则error
	 */
	public Msg updateIsRead(long playlistCommentId);

	/**
	 * 根据评论id获取评论信息
	 * 
	 * @param commentId
	 *            评论id
	 * @return 成功"PlaylistComment"， 评论信息 失败error
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
	 * @return Msg["list":List{PlaylistComment}:被举报的歌单评论]
	 */
	public Msg selectReportedComment();
}
