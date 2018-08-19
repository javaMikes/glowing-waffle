package com.cloudmusic.service;

import com.cloudmusic.bean.User;
import com.cloudmusic.util.Msg;

public interface IUserService {

	/**
	 * ��¼
	 * 
	 * @param user
	 * @return
	 */
	public Msg login(User user);

	/**
	 * ע��
	 * 
	 * @param user
	 * @return
	 */
	public Msg register(User user);

	/**
	 * �û�����
	 * 
	 * @param email
	 * @return
	 */
	public Msg updateUserActiveStatus(String email);

	// =================================================
	/**
	 * ��������
	 * 
	 * @param user
	 * @return
	 */
	public Msg forgot(User user);

	// =================================================
	/**
	 * �ϴ�ͷ��
	 * 
	 * @param id
	 * @param headImage
	 * @return
	 */
	public Msg uploadPhoto(long id, String headImage);

	/**
	 * �����û�id������һ���û�
	 * 
	 * @param id
	 * @return ����Ѳ����򷵻�Msg.error; �ɹ��򷵻�Msg Map["user":User:�û�����]
	 */
	public Msg selectUserById(long id);

	/**
	 * ��ѡ���޸�User
	 * 
	 * @param user
	 *            ����һ��User����id����Ϊ�գ������ֶ�������Ĭ�ϲ��޸�
	 * @return �ɹ��޸�Msg.success Msg["user":User:���޸ĳɹ��Ķ���]<br/>
	 *         �޸�ʧ��Msg.error
	 */
	public Msg updateUserByUser(User user);

	/**
	 * �����û�id�����û�ע��ʱ���û��ĵ�¼״̬�޸�Ϊ��
	 * 
	 * @param userId
	 * @return
	 */
	public Msg updateLoginStateWhenLogout(long userId);

	/**
	 * Ϊĳ���û�ǩ��
	 * 
	 * @param userId
	 * @return ǩ���ɹ�����success<br/>
	 *         ǩ��ʧ�ܣ�����֮ǰ��ǩ���������û�id������ ��������error������Map["msg":String:ʧ��ԭ��]
	 */
	public Msg userSignIn(long userId);

	/**
	 * �������û���ǩ��״̬����Ϊfalse
	 * 
	 * @return �ɹ����÷���success<br/>
	 *         ���ݿ�����г��ִ���ʱ������error
	 */
	public Msg allUserSignOut();

	/**
	 * �����û�id��ȡ�û���ע���û�����
	 * 
	 * @param userId
	 * @return Msg["msg" error:��ʾ��Ϣ ��success����ע���û�����]
	 */
	public Msg getNumOfConcerned(long userId);

	/**
	 * �����û�id��ȡ�û��ķ�˿����
	 * 
	 * @param userId
	 * @return Msg["msg" error:��ʾ��Ϣ ��success����˿����]
	 */
	public Msg getNumOfFans(long userId);

	/**
	 * �û�userId��עuserId_ed
	 * 
	 * @param userId
	 * @param userId_ed
	 * @return Msg["msg" : ��ʾ��Ϣ]
	 */
	public Msg concernUser(long userId, long userId_ed);

	/**
	 * �û�userIdȡ����עuserId_ed
	 * 
	 * @param userId
	 * @param userId_ed
	 * @return Msg["msg" : ��ʾ��Ϣ]
	 */
	public Msg cancelConcernUser(long userId, long userId_ed);

	/**
	 * �����û�����ģ���������û�
	 * 
	 * @param name
	 *            ����
	 * @return Msg["list":User:�û�����]
	 */
	public Msg selectUserByLikeName(String name);

	/**
	 * �����û�id��ȡ����û���ע���û�����
	 * 
	 * @param userId
	 *            �û�id
	 * @return Msg["list":List{User}:�û�����]
	 */
	public Msg selectAttentionByUserId(long userId);

	/**
	 * �����û�id���õ����ķ�˿����
	 * 
	 * @param userId
	 * @return Msg["list":List{User}:��˿����]
	 */
	public Msg selectFansByUserId(long userId);

	/**
	 * �����û��Ļ��֡�����������᷵��error
	 * 
	 * @param userId
	 *            �û�id
	 * @param integral
	 *            Ҫ���Ļ���
	 * @return �ɹ�success���������ּ���error
	 */
	public Msg subUserIntegral(long userId, long integral);

	/**
	 * ����Email����һ��user��ȫ����Ϣ
	 * 
	 * @param email
	 * @return User�������޴�����null
	 */
	public User selectPersonByEmail(String email);
}
