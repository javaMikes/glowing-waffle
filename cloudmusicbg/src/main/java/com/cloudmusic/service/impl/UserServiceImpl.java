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
 * 实现User的登录与注册功能
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	PlaylistMapper playlistMapper;

	@Autowired
	SqlSessionFactory sqlSessionFactory; // 事务处理

	@Autowired
	AttentionMapper attentionMapper;

	/**
	 * 登录
	 * 
	 * @return 通过json的方式，传递结果给前台控制层
	 */
	public Msg login(User user) {

		// 对密码进行加密
		try {
			user.setPassword(ShaUtil.shaEncode(user.getPassword()));
		} catch (Exception e) {
			System.out.println("SHA 对密码哈希时出错");
		}

		UserExample userExample = new UserExample();

		Criteria criteria = userExample.createCriteria();

		criteria.andEmailEqualTo(user.getEmail());

		criteria.andPasswordEqualTo(user.getPassword());

		List<User> userlist = userMapper.selectByExample(userExample);

		if (userlist.size() != 0) {
			if (userlist.get(0).getIsActive() == false)
				return Msg.error().add("msg", "用户未激活，请先到邮箱激活再登录");

			User userSelected = userlist.get(0);

			// 判断该用户是否已登录
			// if (userSelected.getLoginStatus()) {
			// return Msg.error().add("msg", "该用户已登录");
			// }
			userMapper.updateByPrimaryKeySelective(userSelected);
			return Msg.success().add("userInfo", userSelected);
		} else {
			return Msg.error().add("msg", "邮箱或密码错误");
		}

	}

	/**
	 * 通过前台传来的User对象，实现注册功能
	 * 
	 * @return 通过json的方式，传递结果给前台控制层
	 */
	@ResponseBody
	public Msg register(User user) {

		System.out.println("in register");
		// 先验证邮箱是否已存在
		UserExample example = new UserExample();
		Criteria c = example.createCriteria();
		c.andEmailEqualTo(user.getEmail());
		if ((userMapper.selectByExample(example)).size() == 0) {
			// 邮箱不存在，允许用户注册
			user.setIsActive(false);
			// 积分设为0
			user.setIntegral(0L);
			// 设置用户默认头像
			user.setHeadImage("E:/upload/cloudmusic/head/default.jpg");
			// 登录状态设为false
			user.setLoginStatus(false);
			// 签到状态设为false
			user.setIsSignin(false);
			int num = userMapper.insert(user);
			if (num > 0) {
				return Msg.success().add("msg", "注册成功,待激活");
			}
		}

		// 邮箱存在，不允许用户注册，返回失败信息
		return Msg.error().add("msg", "邮箱已存在，无法注册");

	}

	/**
	 * 验证email是否已经存在
	 * 
	 * @param email
	 *            邮箱帐号
	 * @return Msg的Json
	 */
	@ResponseBody
	public Msg selectEmailIsExist(String email) {
		UserExample example = new UserExample();
		Criteria c = example.createCriteria();
		c.andEmailEqualTo(email);

		return (userMapper.selectByExample(example).size() > 0) ? Msg.error() : Msg.success();
	}

	/**
	 * 根据Email查找一个user的全部信息
	 * 
	 * @param email
	 * @return User对象，若无此人则null
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
	 * 用户激活
	 */
	public Msg updateUserActiveStatus(String email) {
		User user = selectPersonByEmail(email);
		if (user == null)
			return Msg.error().add("msg", "用户不存在");

		if (user.getIsActive() == true)
			return Msg.error().add("msg", "用户已激活");

		user.setIsActive(true);

		int num = userMapper.updateByPrimaryKey(user);
		return (num > 0) ? Msg.success() : Msg.error();
	}

	// ===========================================================
	/**
	 * 忘记密码
	 * 
	 * @return
	 */
	public Msg forgot(User user) {
		User user1 = selectPersonByEmail(user.getEmail());
		// 对密码进行加密
		// System.out.println("xiugai,user1"+user.getEmail()+user1.getNickname());
		if (user1 != null) {

			try {
				user1.setPassword(ShaUtil.shaEncode(user.getPassword()));
			} catch (Exception e) {
				System.out.println("SHA 哈希密码时出错");
			}

			// System.out.println("chengzhengxiugai");
			int num = userMapper.updateByPrimaryKey(user1);
			if (num > 0) {
				return Msg.success().add("msg", "修改密码成功！");
			}
			return Msg.success();
		} else {
			return Msg.error().add("msg", "修改密码错误！");
		}

	}

	// ===========================================================
	/**
	 * 头像上传
	 */
	public Msg uploadPhoto(long id, String headImage) {
		User user = new User();
		user = userMapper.selectByPrimaryKey(id);

		// System.out.println("id:" + user.getId());

		if (user == null)
			return Msg.error().add("msg", "用户不存在！");
		else {
			user.setHeadImage(headImage);
			int num = userMapper.updateByPrimaryKeySelective(user);
			if (num > 0)
				return Msg.success().add("msg", "头像上传成功！");
			return Msg.error().add("msg", "头像上传失败！");
		}
	}

	/**
	 * 根据用户id搜索出一个用户
	 * 
	 * @param id
	 * @return 如果搜不到则返回Msg.error; 成功则返回Msg Map["user":User:用户对象]
	 */
	public Msg selectUserById(long id) {
		User user = userMapper.selectByPrimaryKey(id);
		if (user == null)
			return Msg.error();

		return Msg.success().add("user", user);
	}

	/**
	 * 可选地修改User
	 * 
	 * @param user
	 *            传入一个User对象，id不能为空，其他字段若空则默认不修改
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
	 * 根据用户id，当用户注销时将用户的登录状态修改为否
	 * 
	 * @param userId
	 * @return
	 */
	public Msg updateLoginStateWhenLogout(long userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		user.setLoginStatus(false);
		int num = userMapper.updateByPrimaryKey(user);
		if (num > 0)
			return Msg.success().add("msg", "登录状态修改成功");
		return Msg.error().add("msg", "登录状态修改失败");
	}

	/**
	 * 为某个用户签到
	 * 
	 * @param userId
	 * @return 签到成功返回success<br/>
	 *         签到失败（可能之前已签到，或者用户id不存在 ），返回error。附带Map["msg":String:失败原因]
	 */
	public Msg userSignIn(long userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		if (user == null)
			return Msg.error().add("msg", "用户id为" + userId + "的用户不存在");

		if (user.getIsSignin())
			return Msg.error().add("msg", "用户id为" + userId + "的用户今天已签到");

		user.setIsSignin(true);
		long integral = user.getIntegral();
		integral += 2;
		user.setIntegral(integral);

		int num = userMapper.updateByPrimaryKeySelective(user);

		return (num > 0) ? Msg.success() : Msg.error().add("msg", "用户签到失败");
	}

	/**
	 * 将所有用户的签到状态设置为false
	 * 
	 * @return 成功设置返回success<br/>
	 *         数据库操作中出现错误时，返回error
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
	 * 根据用户id获取用户关注的用户人数
	 * 
	 * @param userId
	 * @return Msg["msg" error:提示信息 ；success：关注的用户人数]
	 */
	public Msg getNumOfConcerned(long userId) {
		AttentionExample example = new AttentionExample();
		com.cloudmusic.bean.AttentionExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<AttentionKey> list = attentionMapper.selectByExample(example);
		if (list == null) {
			return Msg.error().add("msg", "该用户未关注其他用户");
		}
		return Msg.success().add("msg", list.size());
	}

	/**
	 * 根据用户id获取用户的粉丝人数
	 * 
	 * @param userId
	 * @return Msg["msg" error:提示信息 ；success：粉丝人数]
	 */
	public Msg getNumOfFans(long userId) {
		AttentionExample example = new AttentionExample();
		com.cloudmusic.bean.AttentionExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEdEqualTo(userId);
		List<AttentionKey> list = attentionMapper.selectByExample(example);
		if (list == null) {
			return Msg.error().add("msg", "该用户没有粉丝");
			// return Msg.error().add("msg", 0);
		}
		return Msg.success().add("msg", list.size());
	}

	/**
	 * 用户userId关注userId_ed
	 * 
	 * @param userId
	 * @param userId_ed
	 * @return Msg["msg" : 提示信息]
	 */
	public Msg concernUser(long userId, long userId_ed) {
		AttentionKey key = new AttentionKey();
		key.setUserId(userId);
		key.setUserIdEd(userId_ed);
		int num = attentionMapper.insert(key);
		if (num > 0)
			return Msg.success().add("msg", "关注成功");
		return Msg.error().add("msg", "关注失败");
	}

	/**
	 * 用户userId取消关注userId_ed
	 * 
	 * @param userId
	 * @param userId_ed
	 * @return Msg["msg" : 提示信息]
	 */
	public Msg cancelConcernUser(long userId, long userId_ed) {
		AttentionKey key = new AttentionKey();
		key.setUserId(userId);
		key.setUserIdEd(userId_ed);
		int num = attentionMapper.deleteByPrimaryKey(key);
		if (num > 0)
			return Msg.success().add("msg", "取消关注成功");
		return Msg.error().add("msg", "取消关注失败");
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

		long userIntegral = user.getIntegral();// 获取当前用户所拥有的积分
		if (userIntegral < integral)
			return Msg.error();

		user.setIntegral(userIntegral - integral);
		int num = userMapper.updateByPrimaryKey(user);
		return (num > 0) ? Msg.success() : Msg.error();
	}

}
