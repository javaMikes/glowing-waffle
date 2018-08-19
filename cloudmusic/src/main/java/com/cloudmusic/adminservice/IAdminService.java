package com.cloudmusic.adminservice;

import java.util.List;

import com.cloudmusic.bean.Singer;
import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.User;
import com.cloudmusic.util.Msg;

public interface IAdminService {

	/**
	 * ����Ա��¼
	 * @return Msg["msg" : ��ʾ��Ϣ��"userInfo" : �û���Ϣ]
	 */
	public Msg adminLogin(User user);
	
	/**
	 * ��ѯ���ݿ����и���
	 * @return Msg["msg": 1:���ڸ��������� �������ϣ�2�������ڸ�����������ʾ��Ϣ]
	 */
	public Msg songSelect(Integer pn);
	
	/**
	 * ��ѯ���ݿ����и���
	 * @param pn
	 * @return
	 */
	public Msg singerSelect(Integer pn);
	
	/**
	 * ��ѯ���ݿ���������
	 * @param pn
	 * @return
	 */
	public Msg songCommentSelect(Integer pn);
	
	/**
	 * ��ѯ�������б��ٱ�������
	 * @param pn
	 * @return
	 */
	public Msg songCommentReportSelect(Integer pn);
	
	/**
	 * ��ѯ�������еĹ���Ա
	 * @param pn
	 * @return
	 */
	public Msg superUserSelect(Integer pn);
	
	/**
	 * ��ѯ���еı��ٱ��ĸ赥����
	 * @param pn
	 * @return
	 */
	public Msg playlistCommentSelect(Integer pn);
	
	/**
	 * ���ݸ���id��ȡ������Ϣ
	 * @param id
	 * @return
	 */
	public Msg songSelectById(long id);
	
	/**
	 * ���ݸ���Id��ȡ������Ϣ
	 * @param id
	 * @return
	 */
	public Msg singerSelectById(long id);
	
	/**
	 * ���ݸ���id�޸ĸ�����Ϣ
	 * @param id
	 * @return Msg["msg" : �޸�����]
	 */
	public Msg songUpdateById(Song song);
	
	/**
	 * ���ݸ���id�޸ĸ�����Ϣ
	 * @param singer
	 * @return Msg["msg" : �޸�����]
	 */
	public Msg singerUpdateById(Singer singer);
	
	/**
	 * ���ݸ���idɾ������
	 * @param id
	 * @return Msg["msg" : ɾ���ĸ�������]
	 */
	public Msg songDeleteById(long id);
	
	/**
	 * ���ݸ���idɾ������
	 * @param id
	 * @return Msg["msg" : ɾ���ĸ�������]
	 */
	public Msg singerDeleteById(long id);
	
	/**
	 * ���ݸ�������IDɾ������
	 * @param id
	 * @return
	 */
	public Msg songCommentDeleteById(long id);
	
	/**
	 * ���ݸ赥����IDɾ������
	 * @param id
	 */
	public Msg playlistCommentDeleteById(long id);
	
	/**
	 * ���ݸ���id����ɾ������
	 * @param list
	 * @return Msg["msg" : ɾ���ĸ�������]
	 */
	public Msg songDeleteByCheckbox(List<Long> list);
	
	/**
	 * ���ݸ赥id����ɾ���赥
	 * @param list
	 * @return
	 */
	public Msg deletePlaylistCommentByCheckbox(List<Long> list);
	
	/**
	 * ���ݸ���id����ɾ������
	 * @param list
	 * @return Msg["msg" : ɾ���ĸ�������]
	 */
	public Msg singerDeleteByCheckbox(List<Long> list);
	
	/**
	 * �������
	 * @param singer
	 * @return
	 */
	public Msg addSinger(Singer singer);
	
	/**
	 * �����ϴ�
	 * @param song
	 * @return
	 */
	public Msg uploadSong(Song song);
	
	/**
	 * ��ȡ���ֲ���ʱ��
	 * @param path
	 */
	public Msg getMusicTime(String path);
	
	/**
	 * ����id��ѯ��������
	 * @param id
	 * @return
	 */
	public Msg songCommentSelectById(long id);
	
	/**
	 * ���ݸ赥id��ѯ�赥����
	 * @param id
	 * @return
	 */
	public Msg playlistCommentSelectById(long id);
	
	/**
	 * ����id�������޸�Ϊ�Ѵ���״̬
	 * @param id
	 * @return
	 */
	public Msg updateReportStatusById(long id);
	
	/**
	 * ����id���赥�����޸�Ϊ�Ѵ���״̬
	 * @param id
	 * @return
	 */
	public Msg updatePlaylistCommentStatusById(long id);
	
	/**
	 * �����ݿ����superUser
	 * @param user
	 * @return
	 */
	public Msg superUserInsert(User user);
	
	/**
	 * ���ݹ���ԱIDɾ������Ա
	 * @param id
	 * @return
	 */
	public Msg superUserDeleteById(long id);
	
	/**
	 * ����ɾ����������
	 * @param list
	 * @return
	 */
	public Msg deleteSongCommentByCheckbox(List<Long> list);
}
