package com.cloudmusic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudmusic.bean.AttentionExample;
import com.cloudmusic.bean.AttentionKey;
import com.cloudmusic.bean.User;
import com.cloudmusic.bean.UserExample;
import com.cloudmusic.bean.UserExample.Criteria;
import com.cloudmusic.mapper.AttentionMapper;
import com.cloudmusic.mapper.PlaylistMapper;
import com.cloudmusic.mapper.UserMapper;
import com.cloudmusic.service.IUserService;
import com.cloudmusic.util.Msg;
import com.cloudmusic.util.ShaUtil;

/**
 * ʵ��User�ĵ�¼��ע�Ṧ��
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	PlaylistMapper playlistMapper;

	@Autowired
	SqlSessionFactory sqlSessionFactory; // ������

	@Autowired
	AttentionMapper attentionMapper;

	/**
	 * ��¼
	 * 
	 * @return ͨ��json�ķ�ʽ�����ݽ����ǰ̨���Ʋ�
	 */
	public Msg login(User user) {

		// ��������м���
		try {
			user.setPassword(ShaUtil.shaEncode(user.getPassword()));
		} catch (Exception e) {
			System.out.println("SHA �������ϣʱ����");
		}

		UserExample userExample = new UserExample();

		Criteria criteria = userExample.createCriteria();

		criteria.andEmailEqualTo(user.getEmail());

		criteria.andPasswordEqualTo(user.getPassword());

		List<User> userlist = userMapper.selectByExample(userExample);

		if (userlist.size() != 0) {
			if (userlist.get(0).getIsActive() == false)
				return Msg.error().add("msg", "�û�δ������ȵ����伤���ٵ�¼");

			User userSelected = userlist.get(0);

			// �жϸ��û��Ƿ��ѵ�¼
			// if (userSelected.getLoginStatus()) {
			// return Msg.error().add("msg", "���û��ѵ�¼");
			// }
			userMapper.updateByPrimaryKeySelective(userSelected);
			return Msg.success().add("userInfo", userSelected);
		} else {
			return Msg.error().add("msg", "������������");
		}

	}

	/**
	 * ͨ��ǰ̨������User����ʵ��ע�Ṧ��
	 * 
	 * @return ͨ��json�ķ�ʽ�����ݽ����ǰ̨���Ʋ�
	 */
	@ResponseBody
	public Msg register(User user) {

		System.out.println("in register");
		// ����֤�����Ƿ��Ѵ���
		UserExample example = new UserExample();
		Criteria c = example.createCriteria();
		c.andEmailEqualTo(user.getEmail());
		if ((userMapper.selectByExample(example)).size() == 0) {
			// ���䲻���ڣ������û�ע��
			user.setIsActive(false);
			// ������Ϊ0
			user.setIntegral(0L);
			// �����û�Ĭ��ͷ��
			user.setHeadImage("E:/upload/cloudmusic/head/default.jpg");
			// ��¼״̬��Ϊfalse
			user.setLoginStatus(false);
			// ǩ��״̬��Ϊfalse
			user.setIsSignin(false);
			int num = userMapper.insert(user);
			if (num > 0) {
				return Msg.success().add("msg", "ע��ɹ�,������");
			}
		}

		// ������ڣ��������û�ע�ᣬ����ʧ����Ϣ
		return Msg.error().add("msg", "�����Ѵ��ڣ��޷�ע��");

	}

	/**
	 * ��֤email�Ƿ��Ѿ�����
	 * 
	 * @param email
	 *            �����ʺ�
	 * @return Msg��Json
	 */
	@ResponseBody
	public Msg selectEmailIsExist(String email) {
		UserExample example = new UserExample();
		Criteria c = example.createCriteria();
		c.andEmailEqualTo(email);

		return (userMapper.selectByExample(example).size() > 0) ? Msg.error() : Msg.success();
	}

	/**
	 * ����Email����һ��user��ȫ����Ϣ
	 * 
	 * @param email
	 * @return User�������޴�����null
	 */
	public User selectPersonByEmail(String email) {
		UserExample example = new UserExample();
		Criteria c = example.createCriteria();
		c.andEmailEqualTo(email);

		List<User> user = userMapper.selectByExample(example);
		if (user.size() == 1)
			return user.get(0);

		return null;
	}

	/**
	 * �û�����
	 */
	public Msg updateUserActiveStatus(String email) {
		User user = selectPersonByEmail(email);
		if (user == null)
			return Msg.error().add("msg", "�û�������");

		if (user.getIsActive() == true)
			return Msg.error().add("msg", "�û��Ѽ���");

		user.setIsActive(true);

		int num = userMapper.updateByPrimaryKey(user);
		return (num > 0) ? Msg.success() : Msg.error();
	}

	// ===========================================================
	/**
	 * ��������
	 * 
	 * @return
	 */
	public Msg forgot(User user) {
		User user1 = selectPersonByEmail(user.getEmail());
		// ��������м���
		// System.out.println("xiugai,user1"+user.getEmail()+user1.getNickname());
		if (user1 != null) {

			try {
				user1.setPassword(ShaUtil.shaEncode(user.getPassword()));
			} catch (Exception e) {
				System.out.println("SHA ��ϣ����ʱ����");
			}

			// System.out.println("chengzhengxiugai");
			int num = userMapper.updateByPrimaryKey(user1);
			if (num > 0) {
				return Msg.success().add("msg", "�޸�����ɹ���");
			}
			return Msg.success();
		} else {
			return Msg.error().add("msg", "�޸��������");
		}

	}

	// ===========================================================
	/**
	 * ͷ���ϴ�
	 */
	public Msg uploadPhoto(long id, String headImage) {
		User user = new User();
		user = userMapper.selectByPrimaryKey(id);

		// System.out.println("id:" + user.getId());

		if (user == null)
			return Msg.error().add("msg", "�û������ڣ�");
		else {
			user.setHeadImage(headImage);
			int num = userMapper.updateByPrimaryKeySelective(user);
			if (num > 0)
				return Msg.success().add("msg", "ͷ���ϴ��ɹ���");
			return Msg.error().add("msg", "ͷ���ϴ�ʧ�ܣ�");
		}
	}

	/**
	 * �����û�id������һ���û�
	 * 
	 * @param id
	 * @return ����Ѳ����򷵻�Msg.error; �ɹ��򷵻�Msg Map["user":User:�û�����]
	 */
	public Msg selectUserById(long id) {
		User user = userMapper.selectByPrimaryKey(id);
		if (user == null)
			return Msg.error();

		return Msg.success().add("user", user);
	}

	/**
	 * ��ѡ���޸�User
	 * 
	 * @param user
	 *            ����һ��User����id����Ϊ�գ������ֶ�������Ĭ�ϲ��޸�
	 * @return
	 */
	public Msg updateUserByUser(User user) {
		if (user.getId() == null)
			return Msg.error();
		int num = userMapper.updateByPrimaryKeySelective(user);
		if (num > 0) {
			User userResult = userMapper.selectByPrimaryKey(user.getId());
			return Msg.success().add("user", userResult);
		}
		return Msg.error();

	}

	/**
	 * �����û�id�����û�ע��ʱ���û��ĵ�¼״̬�޸�Ϊ��
	 * 
	 * @param userId
	 * @return
	 */
	public Msg updateLoginStateWhenLogout(long userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		user.setLoginStatus(false);
		int num = userMapper.updateByPrimaryKey(user);
		if (num > 0)
			return Msg.success().add("msg", "��¼״̬�޸ĳɹ�");
		return Msg.error().add("msg", "��¼״̬�޸�ʧ��");
	}

	/**
	 * Ϊĳ���û�ǩ��
	 * 
	 * @param userId
	 * @return ǩ���ɹ�����success<br/>
	 *         ǩ��ʧ�ܣ�����֮ǰ��ǩ���������û�id������ ��������error������Map["msg":String:ʧ��ԭ��]
	 */
	public Msg userSignIn(long userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		if (user == null)
			return Msg.error().add("msg", "�û�idΪ" + userId + "���û�������");

		if (user.getIsSignin())
			return Msg.error().add("msg", "�û�idΪ" + userId + "���û�������ǩ��");

		user.setIsSignin(true);
		long integral = user.getIntegral();
		integral += 2;
		user.setIntegral(integral);

		int num = userMapper.updateByPrimaryKeySelective(user);

		return (num > 0) ? Msg.success() : Msg.error().add("msg", "�û�ǩ��ʧ��");
	}

	/**
	 * �������û���ǩ��״̬����Ϊfalse
	 * 
	 * @return �ɹ����÷���success<br/>
	 *         ���ݿ�����г��ִ���ʱ������error
	 */
	public Msg allUserSignOut() {

		SqlSession sqlSession = sqlSessionFactory.openSession(false);
		try {

			int num = userMapper.updateAllUserSignOut();
			if (num < 0)
				throw new Exception();

		} catch (Exception e) {
			sqlSession.rollback();
			return Msg.error();
		} finally {
			sqlSession.close();
		}
		return Msg.success();
	}

	/**
	 * �����û�id��ȡ�û���ע���û�����
	 * 
	 * @param userId
	 * @return Msg["msg" error:��ʾ��Ϣ ��success����ע���û�����]
	 */
	public Msg getNumOfConcerned(long userId) {
		AttentionExample example = new AttentionExample();
		com.cloudmusic.bean.AttentionExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<AttentionKey> list = attentionMapper.selectByExample(example);
		if (list == null) {
			return Msg.error().add("msg", "���û�δ��ע�����û�");
		}
		return Msg.success().add("msg", list.size());
	}

	/**
	 * �����û�id��ȡ�û��ķ�˿����
	 * 
	 * @param userId
	 * @return Msg["msg" error:��ʾ��Ϣ ��success����˿����]
	 */
	public Msg getNumOfFans(long userId) {
		AttentionExample example = new AttentionExample();
		com.cloudmusic.bean.AttentionExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEdEqualTo(userId);
		List<AttentionKey> list = attentionMapper.selectByExample(example);
		if (list == null) {
			return Msg.error().add("msg", "���û�û�з�˿");
			// return Msg.error().add("msg", 0);
		}
		return Msg.success().add("msg", list.size());
	}

	/**
	 * �û�userId��עuserId_ed
	 * 
	 * @param userId
	 * @param userId_ed
	 * @return Msg["msg" : ��ʾ��Ϣ]
	 */
	public Msg concernUser(long userId, long userId_ed) {
		AttentionKey key = new AttentionKey();
		key.setUserId(userId);
		key.setUserIdEd(userId_ed);
		int num = attentionMapper.insert(key);
		if (num > 0)
			return Msg.success().add("msg", "��ע�ɹ�");
		return Msg.error().add("msg", "��עʧ��");
	}

	/**
	 * �û�userIdȡ����עuserId_ed
	 * 
	 * @param userId
	 * @param userId_ed
	 * @return Msg["msg" : ��ʾ��Ϣ]
	 */
	public Msg cancelConcernUser(long userId, long userId_ed) {
		AttentionKey key = new AttentionKey();
		key.setUserId(userId);
		key.setUserIdEd(userId_ed);
		int num = attentionMapper.deleteByPrimaryKey(key);
		if (num > 0)
			return Msg.success().add("msg", "ȡ����ע�ɹ�");
		return Msg.error().add("msg", "ȡ����עʧ��");
	}

	public Msg selectUserByLikeName(String name) {
		UserExample example = new UserExample();
		Criteria c = example.createCriteria();
		c.andNicknameLike("%" + name + "%");
		List<User> list = userMapper.selectByExample(example);

		return Msg.success().add("list", list);
	}

	public Msg selectAttentionByUserId(long userId) {
		AttentionExample example = new AttentionExample();
		com.cloudmusic.bean.AttentionExample.Criteria c = example.createCriteria();
		c.andUserIdEqualTo(userId);
		List<AttentionKey> list = attentionMapper.selectByExample(example);
		if (list.size() <= 0)
			return Msg.success().add("list", new ArrayList<User>());

		List<Long> list2 = new ArrayList<Long>();
		for (AttentionKey k : list) {
			list2.add(k.getUserIdEd());
		}

		UserExample example2 = new UserExample();
		Criteria c2 = example2.createCriteria();
		c2.andIdIn(list2);
		List<User> listResult = userMapper.selectByExample(example2);

		return Msg.success().add("list", listResult);
	}

	public Msg selectFansByUserId(long userId) {
		AttentionExample example = new AttentionExample();
		com.cloudmusic.bean.AttentionExample.Criteria c = example.createCriteria();
		c.andUserIdEdEqualTo(userId);
		List<AttentionKey> list = attentionMapper.selectByExample(example);
		if (list.size() <= 0)
			return Msg.success().add("list", new ArrayList<User>());

		List<Long> list2 = new ArrayList<Long>();
		for (AttentionKey k : list) {
			list2.add(k.getUserId());
		}

		UserExample example2 = new UserExample();
		Criteria c2 = example2.createCriteria();
		c2.andIdIn(list2);
		List<User> listResult = userMapper.selectByExample(example2);

		return Msg.success().add("list", listResult);
	}

	public Msg subUserIntegral(long userId, long integral) {
		User user = userMapper.selectByPrimaryKey(userId);
		if (user == null)
			return Msg.error();

		long userIntegral = user.getIntegral();// ��ȡ��ǰ�û���ӵ�еĻ���
		if (userIntegral < integral)
			return Msg.error();

		user.setIntegral(userIntegral - integral);
		int num = userMapper.updateByPrimaryKey(user);
		return (num > 0) ? Msg.success() : Msg.error();
	}

}
