package com.cloudmusic.service;

import com.cloudmusic.bean.User;
import com.cloudmusic.util.Msg;

public interface IUserService {

	/**
	 * 登录
	 * 
	 * @param user
	 * @return
	 */
	public Msg login(User user);

	/**
	 * 注册
	 * 
	 * @param user
	 * @return
	 */
	public Msg register(User user);

	/**
	 * 用户激活
	 * 
	 * @param email
	 * @return
	 */
	public Msg updateUserActiveStatus(String email);

	// =================================================
	/**
	 * 忘记密码
	 * 
	 * @param user
	 * @return
	 */
	public Msg forgot(User user);

	// =================================================
	/**
	 * 上传头像
	 * 
	 * @param id
	 * @param headImage
	 * @return
	 */
	public Msg uploadPhoto(long id, String headImage);

	/**
	 * 根据用户id搜索出一个用户
	 * 
	 * @param id
	 * @return 如果搜不到则返回Msg.error; 成功则返回Msg Map["user":User:用户对象]
	 */
	public Msg selectUserById(long id);

	/**
	 * 可选地修改User
	 * 
	 * @param user
	 *            传入一个User对象，id不能为空，其他字段若空则默认不修改
	 * @return 成功修改Msg.success Msg["user":User:刚修改成功的对象]<br/>
	 *         修改失败Msg.error
	 */
	public Msg updateUserByUser(User user);

	/**
	 * 根据用户id，当用户注销时将用户的登录状态修改为否
	 * 
	 * @param userId
	 * @return
	 */
	public Msg updateLoginStateWhenLogout(long userId);

	/**
	 * 为某个用户签到
	 * 
	 * @param userId
	 * @return 签到成功返回success<br/>
	 *         签到失败（可能之前已签到，或者用户id不存在 ），返回error。附带Map["msg":String:失败原因]
	 */
	public Msg userSignIn(long userId);

	/**
	 * 将所有用户的签到状态设置为false
	 * 
	 * @return 成功设置返回success<br/>
	 *         数据库操作中出现错误时，返回error
	 */
	public Msg allUserSignOut();

	/**
	 * 根据用户id获取用户关注的用户人数
	 * 
	 * @param userId
	 * @return Msg["msg" error:提示信息 ；success：关注的用户人数]
	 */
	public Msg getNumOfConcerned(long userId);

	/**
	 * 根据用户id获取用户的粉丝人数
	 * 
	 * @param userId
	 * @return Msg["msg" error:提示信息 ；success：粉丝人数]
	 */
	public Msg getNumOfFans(long userId);

	/**
	 * 用户userId关注userId_ed
	 * 
	 * @param userId
	 * @param userId_ed
	 * @return Msg["msg" : 提示信息]
	 */
	public Msg concernUser(long userId, long userId_ed);

	/**
	 * 用户userId取消关注userId_ed
	 * 
	 * @param userId
	 * @param userId_ed
	 * @return Msg["msg" : 提示信息]
	 */
	public Msg cancelConcernUser(long userId, long userId_ed);

	/**
	 * 根据用户名字模糊搜索出用户
	 * 
	 * @param name
	 *            名字
	 * @return Msg["list":User:用户集合]
	 */
	public Msg selectUserByLikeName(String name);

	/**
	 * 根据用户id获取这个用户关注的用户集合
	 * 
	 * @param userId
	 *            用户id
	 * @return Msg["list":List{User}:用户集合]
	 */
	public Msg selectAttentionByUserId(long userId);

	/**
	 * 根据用户id，得到他的粉丝集合
	 * 
	 * @param userId
	 * @return Msg["list":List{User}:粉丝集合]
	 */
	public Msg selectFansByUserId(long userId);

	/**
	 * 减少用户的积分。如果不够减会返回error
	 * 
	 * @param userId
	 *            用户id
	 * @param integral
	 *            要减的积分
	 * @return 成功success；不够积分减则error
	 */
	public Msg subUserIntegral(long userId, long integral);

	/**
	 * 根据Email查找一个user的全部信息
	 * 
	 * @param email
	 * @return User对象，若无此人则null
	 */
	public User selectPersonByEmail(String email);
}
