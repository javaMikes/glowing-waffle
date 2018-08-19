package com.cloudmusic.service;

import com.cloudmusic.util.Msg;

public interface IPlaylistCommentService {

	/**
	 * ����һ���赥���ۣ�����userId��playlistId���κ���֤��
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
	public Msg insertPlaylistComment(long userId, long playlistId, String content);

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
	 *         ʧ��error &lt;hi&gt;
	 */
	public Msg replyComment(long commentId, long userId, String content);

	/**
	 * ��������idɾ������
	 * 
	 * @param commentId
	 *            ����id
	 * @return ɾ���ɹ�success��ʧ��error
	 */
	public Msg deleteComment(long commentId);

	/**
	 * ���ݸ赥id�������ø赥����������(�������ظ�)��������ʱ��˳�򣨽�������.��ҳ��ʾ
	 * 
	 * @param playlistId
	 *            �赥id
	 * @return success,Map["list":List&lt;PlaylistComment&gt;:���ۼ���]
	 */
	public Msg selectCommentByPlaylistId(long playlistId, Integer pn);

	/**
	 * ����һ�����۵�ID����ȡ�������۵����лظ�
	 * 
	 * @param commentId
	 *            ����ID
	 * @return success,Map["list":List&lt;PlaylistComment&gt;:�ظ����ۼ���]
	 */
	public Msg selectReplyCommentByParentId(long commentId);

	/**
	 * �����û�id���������û�����������ۣ���ʱ��˳�򣨽�������
	 * 
	 * @param userId
	 *            �û�id
	 * @return success,Map["list":List&lt;PlaylistComment&gt;:���ۼ���]
	 */
	public Msg selectCommentByUserId(long userId);

	/**
	 * �����û�id����ȡ���û�δ���ĸ赥���ۼ���
	 * 
	 * @param userId
	 *            �û�id
	 * @return Msg["list1":List{PlaylistComment}:���ظ��û������ĸ赥�������۵�δ�������ۼ���]<br/>
	 *         Msg["list2":List{PlaylistComment}:���أ��ظ����û����۵�δ�����ۼ���]
	 */
	public Msg selectInformByUserId(long userId);

	/**
	 * ����δ������״̬������Ϊ�Ѷ�
	 * 
	 * @param playlistCommentId
	 *            ����id
	 * @return Msg.success <br/>
	 *         ʧ����error
	 */
	public Msg updateIsRead(long playlistCommentId);

	/**
	 * ��������id��ȡ������Ϣ
	 * 
	 * @param commentId
	 *            ����id
	 * @return �ɹ�"PlaylistComment"�� ������Ϣ ʧ��error
	 */
	public Msg selectByPrimarykey(long commentId);
	
	/**
	 * �ٱ����ۣ�0��1��1��1 ,2 �᷵��error
	 * @param commentId ����id
	 * @return ���۾ٱ�״̬Ϊ0����1������1������success<br/>
	 * �ٱ�״̬Ϊ2ʱ������error
	 */
	public Msg reportComment(long commentId);
	
	/**
	 * ����Ա����ٱ����ۣ��������Ϊû����ʱ��������״̬����Ϊ��ͨ�����
	 * @param commentId ���ٱ�����id
	 * @return ����1��2,��2��2������success<br/>
	 * �������Ϊ0���򷵻�error
	 */
	public Msg dealReportComment(long commentId);
	
	/**
	 * ���ұ��ٱ���δ���������
	 * @return Msg["list":List{PlaylistComment}:���ٱ��ĸ赥����]
	 */
	public Msg selectReportedComment();
}
