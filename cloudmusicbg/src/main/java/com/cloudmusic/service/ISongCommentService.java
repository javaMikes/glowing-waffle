package com.cloudmusic.service;

import com.cloudmusic.util.Msg;

public interface ISongCommentService {
	
	/**
	 * �½�һ������
	 * @param userId �������û�id
	 * @param songId ����id
	 * @param content ����
	 * @return Msg["songComment":SongComment:�����ӵĸ������۶���]
	 */
	public Msg insertSongComment(long userId, long songId, String content);
	
	/**
	 * �ظ�ĳ��song����
	 * 
	 * @param commentId
	 *            ���ظ�����һ���赥���۵�id
	 * @param userId
	 *            �ظ��ߵ�userId
	 * @param content
	 *            �ظ�����
	 * @return �ɹ�success��Map["songComment",SongComment,�´����Ļظ������۶���]<br/>
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
	 * ���ݸ赥id�������ø�������������(�������ظ�)��������ʱ��˳�򣨽�������.��ҳ��ʾ
	 * 
	 * @param songId
	 *            �赥id
	 * @return success,Map["list":List&lt;SongComment&gt;:���ۼ���]
	 */
	public Msg selectCommentBySongId(long songId, Integer pn);

	/**
	 * ����һ�����۵�ID����ȡ�������۵����лظ�
	 * @param commentId ����ID
	 * @return success,Map["list":List&lt;SongComment&gt;:�ظ����ۼ���]
	 */
	public Msg selectReplyCommentByParentId(long commentId);
	
	/**
	 * �����û�id���������û�����������ۣ���ʱ��˳�򣨽�������
	 * 
	 * @param userId
	 *            �û�id
	 * @return success,Map["list":List&lt;SongComment&gt;:���ۼ���]
	 */
	public Msg selectCommentByUserId(long userId);
	
	/**
	 * �����û�id����ȡ���û�δ���ĸ������ۼ���
	 * 
	 * @param userId
	 *            �û�id
	 * @return 
	 *         Msg["list":List{SongComment}:���أ��ظ����û����۵�δ�����ۼ���]
	 */
	public Msg selectInformByUserId(long userId);

	/**
	 * ����δ������״̬������Ϊ�Ѷ�
	 * 
	 * @param songCommentId
	 *            ����id
	 * @return Msg.success <br/>
	 *         ʧ����error
	 */
	public Msg updateIsRead(long songCommentId);
	
	/**
	 * ��������id��ȡ������Ϣ
	 * 
	 * @param commentId
	 *            ����id
	 * @return �ɹ�"SongComment"�� ������Ϣ ʧ��error
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
	 * @return Msg["list":List{SongComment}:���ٱ��ĸ�������]
	 */
	public Msg selectReportedComment();
}
