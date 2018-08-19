package com.cloudmusic.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
//============================================

//============================================
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.cloudmusic.bean.Listen;
import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.User;
import com.cloudmusic.config.SessionListener;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ISongService;
import com.cloudmusic.service.IUserService;
import com.cloudmusic.util.Const;
import com.cloudmusic.util.MD5Util;
import com.cloudmusic.util.Msg;
import com.cloudmusic.util.RandomString;
import com.cloudmusic.util.SendMailUtil;
import com.cloudmusic.util.ShaUtil;

import sun.misc.BASE64Decoder;

@Controller
@SessionAttributes("userInfo")
public class UserController {

	@Autowired
	IUserService userService;

	@Autowired
	ISongService songService;

	@Autowired
	IPlaylistService playlistService;

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	public Msg login(User user, HttpSession session) {
		// 获取返回的信息
		Msg msg = userService.login(user);
		if (msg.getCode() == 100) {
			// 获取登录用户信息
			User userLogin = (User) msg.getMap().get("userInfo");
			// 将用户信息存入session
			session.setAttribute("userInfo", userLogin);
			sessionHandlerByCacheMap(userLogin.getId(), session);
			// 获取用户关注的人数和粉丝
			session.setAttribute("concerned", userService.getNumOfConcerned(userLogin.getId()).getMap().get("msg"));
			session.setAttribute("fans", userService.getNumOfFans(userLogin.getId()).getMap().get("msg"));
			return Msg.success().add("userInfo", userLogin);
		} else {
			return Msg.error();
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping("register")
	public Msg register(@Valid User user, BindingResult result, HttpSession session) {

		if (result.hasErrors()) {
			// JSR303校验未通过
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> list = result.getFieldErrors();
			for (FieldError fieldError : list) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}

			return Msg.error().add("map", map);
		} else {
			// 接收前台传来的密码，并进行加密操作
			//String password = MD5Util.encode2hex(user.getPassword());
			String password;
			try {
				password = ShaUtil.shaEncode(user.getPassword());
				user.setPassword(password);
			} catch (Exception e1) {
				System.out.println("注册时 sha哈希失败");
			}
			
			
			System.out.println("oo");
			Msg msg = userService.register(user);

			if (msg.getCode() == 100) {
				// 随机生成验证码
				String activationCode = MD5Util.encode2hex(RandomString.getRandomString(5));
				System.out.println("用户邮箱为：" + user.getEmail() + " 的激活码是：" + activationCode);

				// 使用application域存储激活码
				ServletContext context = session.getServletContext();

				if (context.getAttribute("userRegisterActive") == null) {
					Map<String, Object> sessionMap = new HashMap<String, Object>();
					sessionMap.put(user.getEmail(), activationCode);
					context.setAttribute("userRegisterActive", sessionMap);
				} else {
					Map sessionMap = (HashMap) context.getAttribute("userRegisterActive");
					sessionMap.put(user.getEmail(), activationCode);
					context.setAttribute("userRegisterActive", sessionMap);
				}

				String content;
				try {
					String localIp = InetAddress.getLocalHost().getHostAddress();
					
					content = "<h1><b>" + user.getNickname() + "</b></h1>，<h3 style='color:#CDBE70'>很高兴认识你。感谢您加入CloudMusic，快点击下面链接成为我们的终身会员吧！</h3>"
							+ "<h2>http://" + localIp + ":8080/cloudmusic/validateEmail?email=" + user.getEmail()
							+ "&activationCode=" + activationCode + "</h2>";
					SendMailUtil.send(user.getEmail(), content);
				} catch (UnknownHostException e) {
					System.out.println("发送邮件时，获取本机IP地址出错");
				}

				return Msg.success().add("msg", "注册成功,请到邮箱验证！");
			} else if (msg.getCode() == 200) {
				return Msg.error().add("msg", "注册失败");
			}
		}
		return Msg.error().add("msg", "注册失败");
	}

	/**
	 * 注册邮箱验证
	 * 
	 * @param v_email
	 * @param v_name
	 * @return
	 */
	@RequestMapping("validateEmail")
	public ModelAndView validateEmail(@RequestParam(value = "email") String v_email, @RequestParam(value = "activationCode") String v_code,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect");
		System.out.println("传入激活码为：" + v_code + ",需验证的邮箱为：" + v_email);

		HashMap map = (HashMap) session.getServletContext().getAttribute("userRegisterActive");

		if (map == null || map.get(v_email) == null) {
			mav.addObject("message", "您的激活码已过期");
			return mav;
		}

		System.out.println("该用户的正确激活码为:" + map.get(v_email));

		String validateCode = (String) map.get(v_email);

		if (validateCode != null && validateCode.equals(v_code)) {
			Msg msg = userService.updateUserActiveStatus(v_email);
			if (msg.getCode() == 100) {
				// 为用户创建一个默认歌单
				User user = userService.selectPersonByEmail(v_email);
				Playlist playlist = new Playlist();

				playlist.setUserId(user.getId());
				playlist.setUserName(user.getNickname());
				playlist.setListName(user.getNickname() + "喜欢的音乐");
				playlist.setCollection(0L);
				playlist.setPlayTimes(0L);
				playlist.setCreateTime(new Date());
				playlist.setImgPath(Const.PLAYLIST_IMG_PATH + "default.jpg");
				// 将歌单信息存入数据库
				Msg playlistmsg = playlistService.insertPlaylist(playlist);
				if (playlistmsg.getCode() == 200) {
					mav.addObject("message", "歌单信息存入数据库出错");
					return mav;
				}

				// 激活码使用完后，将激活码清除
				map.remove(v_email);

				mav.addObject("message", "激活成功");
				return mav;
			} else {
				mav.addObject("message", "激活失败");
				return mav;
			}
		}
		mav.addObject("message", "激活码错误");
		return mav;

	}

	/** ===============新增忘记密码功能================== */
	/**
	 * 忘记密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("forgotPsw")
	@ResponseBody
	public Msg forgotPsw(User user, HttpSession session) {
		// 获取返回的信息
		String code = (String) session.getAttribute("sendCode");
		if (code.equals(user.getNickname())) {
			System.out.println("修改信息:" + user.getEmail() + " " + user.getPassword() + " " + user.getNickname());
			Msg msg = userService.forgot(user);
			if (msg.getCode() == 100) {
				return Msg.success().add("msg", "修改密码成功！");
			} else {
				return Msg.error().add("msg", "修改密码失败！");
			}
		}
		return Msg.error();
	}

	/**
	 * 发送验证码
	 * 
	 * @param user
	 * @return
	 */
	// 发件人的 邮箱 和 密码（授权码）
	public static String myEmailAccount = "bingganing@yeah.net";
	public static String myEmailPassword = "sqm123";
	// 邮箱的 SMTP 服务器地址
	public static String myEmailSMTPHost = "smtp.yeah.net";
	// 收件人邮箱
	public static String receiveMailAccount = "605847681@qq.com";

	@SuppressWarnings("unused")
	@RequestMapping("sendCode")
	@ResponseBody
	public Msg sendCode(User user, HttpSession sessions) throws Exception {
		// 获取邮箱
		receiveMailAccount = user.getEmail();
		String verifyCode = MD5Util.encode2hex(RandomString.getRandomString(6));
		System.out.println("发往用户邮箱的验证码是：" + verifyCode.substring(0, 6));

		if (verifyCode != null) {
			sessions.setAttribute("sendCode", verifyCode.substring(0, 6));
			// =================发送邮件============================
			// 创建参数配置, 用于连接邮件服务器的参数配置
			Properties props = new Properties(); // 参数配置
			props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
			props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的
																	// SMTP
																	// 服务器地址
			props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

			// 根据配置创建会话对象, 用于和邮件服务器交互
			Session session = Session.getDefaultInstance(props);
			session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log

			// 创建一封邮件
			MimeMessage message = createMimeMessage(session, verifyCode.substring(0, 6), myEmailAccount, receiveMailAccount);

			// 根据 Session 获取邮件传输对象
			Transport transport = session.getTransport();

			// 使用 邮箱账号 和 密码 连接邮件服务器
			transport.connect(myEmailAccount, myEmailPassword);

			// 发送邮件, 发到所有的收件地址
			transport.sendMessage(message, message.getAllRecipients());

			// 关闭连接
			transport.close();
			// ===================================================
			return Msg.success();
		} else {
			return Msg.error();
		}
	}

	/**
	 * 创建一封只包含文本的简单邮件
	 *
	 * @param session
	 *            和服务器交互的会话
	 * @param sendMail
	 *            发件人邮箱
	 * @param receiveMail
	 *            收件人邮箱
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createMimeMessage(Session session, String code, String sendMail, String receiveMail) throws Exception {
		// 创建一封邮件
		MimeMessage message = new MimeMessage(session);

		// From: 发件人
		message.setFrom(new InternetAddress(sendMail, "cloudmusic", "UTF-8"));

		// To: 收件人
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "小彬彬", "UTF-8"));

		// Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
		message.setSubject("克劳德音乐验证消息", "UTF-8");

		// Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
		message.setContent("【克劳德】欢迎使用克劳德音乐服务！您的验证码为：" + code + "。", "text/html;charset=UTF-8");

		// 设置发件时间
		message.setSentDate(new Date());

		// 保存设置
		message.saveChanges();

		return message;
	}

	/**
	 * 忘记密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Msg deleteCode(HttpSession session) {
		// 获取返回的信息
		String code = (String) session.getAttribute("sendCode");
		System.out.println(code);
		session.removeAttribute("sendCode");
		code = (String) session.getAttribute("sendCode");
		System.out.println(code);
		return Msg.success();
	}

	/** ================================================ */

	/**
	 * 跳转到上传头像页面
	 * 
	 * @param user
	 * @param result
	 * @return
	 */
	@RequestMapping("upload_head")
	public String uploadImage() {
		return "uploadHead";
	}

	/**
	 * 上传头像
	 * 
	 * @param imgBase64
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("upload_head_img")
	public Msg uploadHeadImg(String imgBase64, @ModelAttribute("userInfo") User user, HttpSession session) throws IOException {
		System.out.println("imgBase64" + imgBase64);
		// 获取图片的base64码
		imgBase64 = imgBase64.substring(22);
		@SuppressWarnings("restriction")
		byte[] buffer = new BASE64Decoder().decodeBuffer(imgBase64);

		// 获取原先头像路径
		String preImgPath = user.getHeadImage();

		// 文件存储路径
		String fileName = Const.HEAD_PATH + System.currentTimeMillis() + ".jpg";

		// 将解密后的文件存入文件夹
		FileUtils.writeByteArrayToFile(new File(fileName), buffer);
		// 将文件路径存入数据库
		Msg msg = userService.uploadPhoto(user.getId(), fileName);
		if (msg.getCode() == 100) {
			System.out.println(preImgPath.substring(preImgPath.lastIndexOf("/") + 1, preImgPath.length()));
			// 将原来的头像删掉
			if (!preImgPath.substring(preImgPath.lastIndexOf("/") + 1, preImgPath.length()).equals("default.jpg")) {
				File file = new File(preImgPath);
				file.delete();
			}

			// 修改session中的头像路径
			user.setHeadImage(fileName);
			session.setAttribute("userInfo", user);
			return Msg.success().add("msg", msg.getMsg());
		} else {
			return Msg.error().add("msg", msg.getMsg());
		}
	}

	/**
	 * 跳转到个人设置页面
	 */
	@RequestMapping("showUserUpdate")
	public String showUserUpdate(long id, Model model) {
		Msg msg = userService.selectUserById(id);
		model.addAttribute("userInfo", msg.getMap().get("user"));
		return "userUpdate";
	}

	/**
	 * 个人设置
	 */
	@RequestMapping("userUpdate")
	@ResponseBody
	public String userUpdate(long id, HttpSession session) {
		if (session.getAttribute("userInfo") != null)
			return "success";
		return "error";
	}

	/**
	 * 修改用户资料
	 */
	@RequestMapping(value = "updateUserByUser")
	@ResponseBody
	public Msg updateUserByUser(User user, HttpSession session) {
		User sessionUser = (User) session.getAttribute("userInfo");
		user.setId(sessionUser.getId());
		Msg msg = userService.updateUserByUser(user);
		User users = (User) msg.getMap().get("user");
		session.setAttribute("userInfo", users);
		User sessionUsers = (User) session.getAttribute("userInfo");
		System.out.println(sessionUsers.getNickname());
		if (msg.getCode() == 100)
			return Msg.success();
		else
			return Msg.error();
	}

	/**
	 * 根据用户id显示头像
	 */
	@RequestMapping(value = "seekExperts")
	@ResponseBody
	public String createFolw(HttpServletRequest request, HttpServletResponse response) {
		// response.setContentType("image/*");
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			// 若用户没有登录，显示默认图片
			if (!request.getParameter("userId").equals("")) {
				// 获取用户的id
				Long userId = Long.valueOf(request.getParameter("userId"));
				// 根据用户id获取用户信息
				Msg msg = userService.selectUserById(userId);
				if (msg.getCode() == 200)
					return "error";
				User user = (User) msg.getMap().get("user");
				fis = new FileInputStream(user.getHeadImage());
			} else {
				fis = new FileInputStream(Const.HEAD_PATH + "default.jpg");
			}
			os = response.getOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((count = fis.read(buffer)) != -1) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			fis.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "ok";
	}

	/**
	 * 根据用户id获取用户名
	 */
	@RequestMapping(value = "findUserNameById")
	@ResponseBody
	public Msg findUserNameById(@RequestParam(required = true) long userId) {
		Msg msg = userService.selectUserById(userId);
		if (msg.getCode() == 200)
			return Msg.error();

		User user = (User) msg.getMap().get("user");
		return Msg.success().add("userName", user.getNickname());
	}

	/**
	 * 用户签到
	 */
	@RequestMapping(value = "signIn")
	@ResponseBody
	public Msg signIn(@RequestParam(required = true) long userId, HttpSession session) {
		System.out.println("enter success");
		Msg msg = userService.userSignIn(userId);
		if (msg.getCode() == 100) {
			User user = (User) session.getAttribute("userInfo");
			user.setIsSignin(true);
			session.setAttribute("userInfo", user);
			return Msg.success();
		} else {
			return Msg.error().add("msg", msg.getMap().get("msg"));
		}
	}

	/**
	 * 跳转到个人主页控制器
	 */
	@RequestMapping("showPersonPage")
	public String showPersonPage(@RequestParam(required = true) long userId, Model model) {
		// 根据用户的id获取用户信息
		Msg userInfoMsg = userService.selectUserById(userId);
		if (userInfoMsg.getCode() == 200) {
			model.addAttribute("message", "获取用户信息失败");
			return "error";
		}

		// 根据用户的id获取用户关注的人数
		Msg concernedMsg = userService.getNumOfConcerned(userId);
		if (concernedMsg.getCode() == 200) {
			model.addAttribute("message", "获取用户关注的人数失败");
			return "error";
		}

		// 根据用户的id获取用户的粉丝
		Msg fansMsg = userService.getNumOfFans(userId);
		if (fansMsg.getCode() == 200) {
			model.addAttribute("message", "获取用户的粉丝失败");
			return "error";
		}

		// 根据用户的id获取用户创建的歌单
		Msg createPlaylistMsg = playlistService.selectPlaylistByUserId(userId);
		if (createPlaylistMsg.getCode() == 200) {
			model.addAttribute("message", "获取用户创建的歌单失败");
			return "error";
		}

		// 根据用户的id获取用户收藏的歌单
		Msg collectionPlaylistMsg = playlistService.selectPlaylistCollectionByUserId(userId);
		if (collectionPlaylistMsg.getCode() == 200) {
			model.addAttribute("message", "获取用户收藏的歌单失败");
			return "error";
		}

		// 返回用户信息
		model.addAttribute("userInfoSelected", userInfoMsg.getMap().get("user"));

		// 返回用户关注的人数
		model.addAttribute("concernNum", concernedMsg.getMap().get("msg"));

		// 返回用户的粉丝数
		model.addAttribute("fansNum", fansMsg.getMap().get("msg"));

		// 返回用户创建的歌单
		model.addAttribute("createPlayList", createPlaylistMsg.getMap().get("list"));

		// 返回用户收藏的歌单
		model.addAttribute("collectionPlaylist", collectionPlaylistMsg.getMap().get("list"));

		return "userPage";
	}

	/**
	 * 根据用户Id获取用户的听歌排行
	 * 
	 * @param userId
	 * @return ["rankSonglist",用户的听歌排行]
	 */
	@RequestMapping("findRankSong")
	@ResponseBody
	public Msg findRankSong(@RequestParam(required = true) long userId) {
		// 根据用户的id获取用户的听歌排行
		Msg rankSongListenMsg = songService.rankSongListenTimes(userId);
		if (rankSongListenMsg.getCode() == 200) {
			return rankSongListenMsg;
		}
		@SuppressWarnings("unchecked")
		List<Listen> listenList = (List<Listen>) rankSongListenMsg.getMap().get("list");
		List<Song> rankSonglist = new ArrayList<Song>();
		for (Listen listen : listenList) {
			Msg songMsg = songService.selectSongBySongId(listen.getSongId());
			Song song = (Song) songMsg.getMap().get("song");
			song.setPlayTimes(listen.getTimes());
			rankSonglist.add((Song) songMsg.getMap().get("song"));
		}
		return Msg.success().add("rankSonglist", rankSonglist);
	}

	/**
	 * 关注用户
	 * 
	 * @param userId
	 *            关注用户id
	 * @param userId_ed
	 *            被关注用户id
	 * @return
	 */
	@RequestMapping("concernUser")
	@ResponseBody
	public Msg concernUser(@RequestParam(required = true) long userId, @RequestParam(required = true) long userId_ed) {
		return userService.concernUser(userId, userId_ed);
	}

	/**
	 * 取消关注
	 * 
	 * @param userId
	 *            取消关注用户id
	 * @param userId_ed
	 *            被取消关注用户id
	 * @return
	 */
	@RequestMapping("cancelConcernUser")
	@ResponseBody
	public Msg cancelConcernUser(@RequestParam(required = true) long userId, @RequestParam(required = true) long userId_ed) {
		return userService.cancelConcernUser(userId, userId_ed);
	}

	/**
	 * 注销
	 */
	@RequestMapping(value = "logout")
	@ResponseBody
	public Msg logout(HttpSession session, SessionStatus sessionStatus) {
		// System.out.println("enter success");
		// // 注销在线用户
		// HttpSession userSession = (HttpSession)
		// SessionListener.sessionContext.getSessionMap().get("userInfo");
		// userSession.invalidate();
		User user = (User) session.getAttribute("userInfo");
		SessionListener.sessionContext.getSessionMap().remove(String.valueOf(user.getId()));
		// session.removeAttribute("userInfo");
		session.invalidate();
		sessionStatus.setComplete();
		return Msg.success();
	}

	/**
	 * 根据用户id获取用户的积分
	 */
	@RequestMapping(value = "getIntegralByUserId")
	@ResponseBody
	public Msg getIntegralByUserId(long userId) {
		Msg msg = userService.selectUserById(userId);
		if (msg.getCode() == 200)
			return Msg.error();

		User user = (User) msg.getMap().get("user");
		return Msg.success().add("integral", user.getIntegral());
	}

	/**
	 * 判断该用户是否在其他地方登录
	 * 
	 * @param session
	 */
	public void sessionHandlerByCacheMap(long userId, HttpSession session) {
		String userid = String.valueOf(userId);
		if (SessionListener.sessionContext.getSessionMap().get(userid) != null) {

			HttpSession userSession = (HttpSession) SessionListener.sessionContext.getSessionMap().get(userid);
			if (!userSession.equals(session)) {
				// 注销在线用户
				try {
					userSession.invalidate();
				} catch (Exception e) {
					// 当session因为用户长时间未操作而自行销毁时，存在于map里面又没remove，就会报错。
					// 此时加入新的session
					System.out.println("session已销毁，防止代码报错中止运行");
				}

				SessionListener.sessionContext.getSessionMap().remove(userid);
				// 清除在线用户后，更新map,替换map sessionid
				SessionListener.sessionContext.getSessionMap().remove(session.getId());
				SessionListener.sessionContext.getSessionMap().put(userid, session);
			}
		} else {
			// 根据当前sessionid 取session对象。 更新map key=用户名 value=session对象 删除map
			SessionListener.sessionContext.getSessionMap().get(session.getId());
			SessionListener.sessionContext.getSessionMap().put(userid, SessionListener.sessionContext.getSessionMap().get(session.getId()));
			SessionListener.sessionContext.getSessionMap().remove(session.getId());
		}
	}
}
