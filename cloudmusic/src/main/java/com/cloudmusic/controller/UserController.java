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
	 * �û���¼
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	public Msg login(User user, HttpSession session) {
		// ��ȡ���ص���Ϣ
		Msg msg = userService.login(user);
		if (msg.getCode() == 100) {
			// ��ȡ��¼�û���Ϣ
			User userLogin = (User) msg.getMap().get("userInfo");
			// ���û���Ϣ����session
			session.setAttribute("userInfo", userLogin);
			sessionHandlerByCacheMap(userLogin.getId(), session);
			// ��ȡ�û���ע�������ͷ�˿
			session.setAttribute("concerned", userService.getNumOfConcerned(userLogin.getId()).getMap().get("msg"));
			session.setAttribute("fans", userService.getNumOfFans(userLogin.getId()).getMap().get("msg"));
			return Msg.success().add("userInfo", userLogin);
		} else {
			return Msg.error();
		}
	}

	/**
	 * �û�ע��
	 * 
	 * @param user
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping("register")
	public Msg register(@Valid User user, BindingResult result, HttpSession session) {

		if (result.hasErrors()) {
			// JSR303У��δͨ��
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> list = result.getFieldErrors();
			for (FieldError fieldError : list) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}

			return Msg.error().add("map", map);
		} else {
			// ����ǰ̨���������룬�����м��ܲ���
			//String password = MD5Util.encode2hex(user.getPassword());
			String password;
			try {
				password = ShaUtil.shaEncode(user.getPassword());
				user.setPassword(password);
			} catch (Exception e1) {
				System.out.println("ע��ʱ sha��ϣʧ��");
			}
			
			
			System.out.println("oo");
			Msg msg = userService.register(user);

			if (msg.getCode() == 100) {
				// ���������֤��
				String activationCode = MD5Util.encode2hex(RandomString.getRandomString(5));
				System.out.println("�û�����Ϊ��" + user.getEmail() + " �ļ������ǣ�" + activationCode);

				// ʹ��application��洢������
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
					
					content = "<h1><b>" + user.getNickname() + "</b></h1>��<h3 style='color:#CDBE70'>�ܸ�����ʶ�㡣��л������CloudMusic�������������ӳ�Ϊ���ǵ������Ա�ɣ�</h3>"
							+ "<h2>http://" + localIp + ":8080/cloudmusic/validateEmail?email=" + user.getEmail()
							+ "&activationCode=" + activationCode + "</h2>";
					SendMailUtil.send(user.getEmail(), content);
				} catch (UnknownHostException e) {
					System.out.println("�����ʼ�ʱ����ȡ����IP��ַ����");
				}

				return Msg.success().add("msg", "ע��ɹ�,�뵽������֤��");
			} else if (msg.getCode() == 200) {
				return Msg.error().add("msg", "ע��ʧ��");
			}
		}
		return Msg.error().add("msg", "ע��ʧ��");
	}

	/**
	 * ע��������֤
	 * 
	 * @param v_email
	 * @param v_name
	 * @return
	 */
	@RequestMapping("validateEmail")
	public ModelAndView validateEmail(@RequestParam(value = "email") String v_email, @RequestParam(value = "activationCode") String v_code,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect");
		System.out.println("���뼤����Ϊ��" + v_code + ",����֤������Ϊ��" + v_email);

		HashMap map = (HashMap) session.getServletContext().getAttribute("userRegisterActive");

		if (map == null || map.get(v_email) == null) {
			mav.addObject("message", "���ļ������ѹ���");
			return mav;
		}

		System.out.println("���û�����ȷ������Ϊ:" + map.get(v_email));

		String validateCode = (String) map.get(v_email);

		if (validateCode != null && validateCode.equals(v_code)) {
			Msg msg = userService.updateUserActiveStatus(v_email);
			if (msg.getCode() == 100) {
				// Ϊ�û�����һ��Ĭ�ϸ赥
				User user = userService.selectPersonByEmail(v_email);
				Playlist playlist = new Playlist();

				playlist.setUserId(user.getId());
				playlist.setUserName(user.getNickname());
				playlist.setListName(user.getNickname() + "ϲ��������");
				playlist.setCollection(0L);
				playlist.setPlayTimes(0L);
				playlist.setCreateTime(new Date());
				playlist.setImgPath(Const.PLAYLIST_IMG_PATH + "default.jpg");
				// ���赥��Ϣ�������ݿ�
				Msg playlistmsg = playlistService.insertPlaylist(playlist);
				if (playlistmsg.getCode() == 200) {
					mav.addObject("message", "�赥��Ϣ�������ݿ����");
					return mav;
				}

				// ������ʹ����󣬽����������
				map.remove(v_email);

				mav.addObject("message", "����ɹ�");
				return mav;
			} else {
				mav.addObject("message", "����ʧ��");
				return mav;
			}
		}
		mav.addObject("message", "���������");
		return mav;

	}

	/** ===============�����������빦��================== */
	/**
	 * ��������
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("forgotPsw")
	@ResponseBody
	public Msg forgotPsw(User user, HttpSession session) {
		// ��ȡ���ص���Ϣ
		String code = (String) session.getAttribute("sendCode");
		if (code.equals(user.getNickname())) {
			System.out.println("�޸���Ϣ:" + user.getEmail() + " " + user.getPassword() + " " + user.getNickname());
			Msg msg = userService.forgot(user);
			if (msg.getCode() == 100) {
				return Msg.success().add("msg", "�޸�����ɹ���");
			} else {
				return Msg.error().add("msg", "�޸�����ʧ�ܣ�");
			}
		}
		return Msg.error();
	}

	/**
	 * ������֤��
	 * 
	 * @param user
	 * @return
	 */
	// �����˵� ���� �� ���루��Ȩ�룩
	public static String myEmailAccount = "bingganing@yeah.net";
	public static String myEmailPassword = "sqm123";
	// ����� SMTP ��������ַ
	public static String myEmailSMTPHost = "smtp.yeah.net";
	// �ռ�������
	public static String receiveMailAccount = "605847681@qq.com";

	@SuppressWarnings("unused")
	@RequestMapping("sendCode")
	@ResponseBody
	public Msg sendCode(User user, HttpSession sessions) throws Exception {
		// ��ȡ����
		receiveMailAccount = user.getEmail();
		String verifyCode = MD5Util.encode2hex(RandomString.getRandomString(6));
		System.out.println("�����û��������֤���ǣ�" + verifyCode.substring(0, 6));

		if (verifyCode != null) {
			sessions.setAttribute("sendCode", verifyCode.substring(0, 6));
			// =================�����ʼ�============================
			// ������������, ���������ʼ��������Ĳ�������
			Properties props = new Properties(); // ��������
			props.setProperty("mail.transport.protocol", "smtp"); // ʹ�õ�Э�飨JavaMail�淶Ҫ��
			props.setProperty("mail.smtp.host", myEmailSMTPHost); // �����˵������
																	// SMTP
																	// ��������ַ
			props.setProperty("mail.smtp.auth", "true"); // ��Ҫ������֤

			// �������ô����Ự����, ���ں��ʼ�����������
			Session session = Session.getDefaultInstance(props);
			session.setDebug(true); // ����Ϊdebugģʽ, ���Բ鿴��ϸ�ķ��� log

			// ����һ���ʼ�
			MimeMessage message = createMimeMessage(session, verifyCode.substring(0, 6), myEmailAccount, receiveMailAccount);

			// ���� Session ��ȡ�ʼ��������
			Transport transport = session.getTransport();

			// ʹ�� �����˺� �� ���� �����ʼ�������
			transport.connect(myEmailAccount, myEmailPassword);

			// �����ʼ�, �������е��ռ���ַ
			transport.sendMessage(message, message.getAllRecipients());

			// �ر�����
			transport.close();
			// ===================================================
			return Msg.success();
		} else {
			return Msg.error();
		}
	}

	/**
	 * ����һ��ֻ�����ı��ļ��ʼ�
	 *
	 * @param session
	 *            �ͷ����������ĻỰ
	 * @param sendMail
	 *            ����������
	 * @param receiveMail
	 *            �ռ�������
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createMimeMessage(Session session, String code, String sendMail, String receiveMail) throws Exception {
		// ����һ���ʼ�
		MimeMessage message = new MimeMessage(session);

		// From: ������
		message.setFrom(new InternetAddress(sendMail, "cloudmusic", "UTF-8"));

		// To: �ռ���
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "С���", "UTF-8"));

		// Subject: �ʼ����⣨�����й�����ɣ����ⱻ�ʼ�����������Ϊ���ķ������������ʧ�ܣ����޸ı��⣩
		message.setSubject("���͵�������֤��Ϣ", "UTF-8");

		// Content: �ʼ����ģ�����ʹ��html��ǩ���������й�����ɣ����ⱻ�ʼ�����������Ϊ���ķ������������ʧ�ܣ����޸ķ������ݣ�
		message.setContent("�����͵¡���ӭʹ�ÿ��͵����ַ���������֤��Ϊ��" + code + "��", "text/html;charset=UTF-8");

		// ���÷���ʱ��
		message.setSentDate(new Date());

		// ��������
		message.saveChanges();

		return message;
	}

	/**
	 * ��������
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Msg deleteCode(HttpSession session) {
		// ��ȡ���ص���Ϣ
		String code = (String) session.getAttribute("sendCode");
		System.out.println(code);
		session.removeAttribute("sendCode");
		code = (String) session.getAttribute("sendCode");
		System.out.println(code);
		return Msg.success();
	}

	/** ================================================ */

	/**
	 * ��ת���ϴ�ͷ��ҳ��
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
	 * �ϴ�ͷ��
	 * 
	 * @param imgBase64
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("upload_head_img")
	public Msg uploadHeadImg(String imgBase64, @ModelAttribute("userInfo") User user, HttpSession session) throws IOException {
		System.out.println("imgBase64" + imgBase64);
		// ��ȡͼƬ��base64��
		imgBase64 = imgBase64.substring(22);
		@SuppressWarnings("restriction")
		byte[] buffer = new BASE64Decoder().decodeBuffer(imgBase64);

		// ��ȡԭ��ͷ��·��
		String preImgPath = user.getHeadImage();

		// �ļ��洢·��
		String fileName = Const.HEAD_PATH + System.currentTimeMillis() + ".jpg";

		// �����ܺ���ļ������ļ���
		FileUtils.writeByteArrayToFile(new File(fileName), buffer);
		// ���ļ�·���������ݿ�
		Msg msg = userService.uploadPhoto(user.getId(), fileName);
		if (msg.getCode() == 100) {
			System.out.println(preImgPath.substring(preImgPath.lastIndexOf("/") + 1, preImgPath.length()));
			// ��ԭ����ͷ��ɾ��
			if (!preImgPath.substring(preImgPath.lastIndexOf("/") + 1, preImgPath.length()).equals("default.jpg")) {
				File file = new File(preImgPath);
				file.delete();
			}

			// �޸�session�е�ͷ��·��
			user.setHeadImage(fileName);
			session.setAttribute("userInfo", user);
			return Msg.success().add("msg", msg.getMsg());
		} else {
			return Msg.error().add("msg", msg.getMsg());
		}
	}

	/**
	 * ��ת����������ҳ��
	 */
	@RequestMapping("showUserUpdate")
	public String showUserUpdate(long id, Model model) {
		Msg msg = userService.selectUserById(id);
		model.addAttribute("userInfo", msg.getMap().get("user"));
		return "userUpdate";
	}

	/**
	 * ��������
	 */
	@RequestMapping("userUpdate")
	@ResponseBody
	public String userUpdate(long id, HttpSession session) {
		if (session.getAttribute("userInfo") != null)
			return "success";
		return "error";
	}

	/**
	 * �޸��û�����
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
	 * �����û�id��ʾͷ��
	 */
	@RequestMapping(value = "seekExperts")
	@ResponseBody
	public String createFolw(HttpServletRequest request, HttpServletResponse response) {
		// response.setContentType("image/*");
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			// ���û�û�е�¼����ʾĬ��ͼƬ
			if (!request.getParameter("userId").equals("")) {
				// ��ȡ�û���id
				Long userId = Long.valueOf(request.getParameter("userId"));
				// �����û�id��ȡ�û���Ϣ
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
	 * �����û�id��ȡ�û���
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
	 * �û�ǩ��
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
	 * ��ת��������ҳ������
	 */
	@RequestMapping("showPersonPage")
	public String showPersonPage(@RequestParam(required = true) long userId, Model model) {
		// �����û���id��ȡ�û���Ϣ
		Msg userInfoMsg = userService.selectUserById(userId);
		if (userInfoMsg.getCode() == 200) {
			model.addAttribute("message", "��ȡ�û���Ϣʧ��");
			return "error";
		}

		// �����û���id��ȡ�û���ע������
		Msg concernedMsg = userService.getNumOfConcerned(userId);
		if (concernedMsg.getCode() == 200) {
			model.addAttribute("message", "��ȡ�û���ע������ʧ��");
			return "error";
		}

		// �����û���id��ȡ�û��ķ�˿
		Msg fansMsg = userService.getNumOfFans(userId);
		if (fansMsg.getCode() == 200) {
			model.addAttribute("message", "��ȡ�û��ķ�˿ʧ��");
			return "error";
		}

		// �����û���id��ȡ�û������ĸ赥
		Msg createPlaylistMsg = playlistService.selectPlaylistByUserId(userId);
		if (createPlaylistMsg.getCode() == 200) {
			model.addAttribute("message", "��ȡ�û������ĸ赥ʧ��");
			return "error";
		}

		// �����û���id��ȡ�û��ղصĸ赥
		Msg collectionPlaylistMsg = playlistService.selectPlaylistCollectionByUserId(userId);
		if (collectionPlaylistMsg.getCode() == 200) {
			model.addAttribute("message", "��ȡ�û��ղصĸ赥ʧ��");
			return "error";
		}

		// �����û���Ϣ
		model.addAttribute("userInfoSelected", userInfoMsg.getMap().get("user"));

		// �����û���ע������
		model.addAttribute("concernNum", concernedMsg.getMap().get("msg"));

		// �����û��ķ�˿��
		model.addAttribute("fansNum", fansMsg.getMap().get("msg"));

		// �����û������ĸ赥
		model.addAttribute("createPlayList", createPlaylistMsg.getMap().get("list"));

		// �����û��ղصĸ赥
		model.addAttribute("collectionPlaylist", collectionPlaylistMsg.getMap().get("list"));

		return "userPage";
	}

	/**
	 * �����û�Id��ȡ�û�����������
	 * 
	 * @param userId
	 * @return ["rankSonglist",�û�����������]
	 */
	@RequestMapping("findRankSong")
	@ResponseBody
	public Msg findRankSong(@RequestParam(required = true) long userId) {
		// �����û���id��ȡ�û�����������
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
	 * ��ע�û�
	 * 
	 * @param userId
	 *            ��ע�û�id
	 * @param userId_ed
	 *            ����ע�û�id
	 * @return
	 */
	@RequestMapping("concernUser")
	@ResponseBody
	public Msg concernUser(@RequestParam(required = true) long userId, @RequestParam(required = true) long userId_ed) {
		return userService.concernUser(userId, userId_ed);
	}

	/**
	 * ȡ����ע
	 * 
	 * @param userId
	 *            ȡ����ע�û�id
	 * @param userId_ed
	 *            ��ȡ����ע�û�id
	 * @return
	 */
	@RequestMapping("cancelConcernUser")
	@ResponseBody
	public Msg cancelConcernUser(@RequestParam(required = true) long userId, @RequestParam(required = true) long userId_ed) {
		return userService.cancelConcernUser(userId, userId_ed);
	}

	/**
	 * ע��
	 */
	@RequestMapping(value = "logout")
	@ResponseBody
	public Msg logout(HttpSession session, SessionStatus sessionStatus) {
		// System.out.println("enter success");
		// // ע�������û�
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
	 * �����û�id��ȡ�û��Ļ���
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
	 * �жϸ��û��Ƿ��������ط���¼
	 * 
	 * @param session
	 */
	public void sessionHandlerByCacheMap(long userId, HttpSession session) {
		String userid = String.valueOf(userId);
		if (SessionListener.sessionContext.getSessionMap().get(userid) != null) {

			HttpSession userSession = (HttpSession) SessionListener.sessionContext.getSessionMap().get(userid);
			if (!userSession.equals(session)) {
				// ע�������û�
				try {
					userSession.invalidate();
				} catch (Exception e) {
					// ��session��Ϊ�û���ʱ��δ��������������ʱ��������map������ûremove���ͻᱨ��
					// ��ʱ�����µ�session
					System.out.println("session�����٣���ֹ���뱨����ֹ����");
				}

				SessionListener.sessionContext.getSessionMap().remove(userid);
				// ��������û��󣬸���map,�滻map sessionid
				SessionListener.sessionContext.getSessionMap().remove(session.getId());
				SessionListener.sessionContext.getSessionMap().put(userid, session);
			}
		} else {
			// ���ݵ�ǰsessionid ȡsession���� ����map key=�û��� value=session���� ɾ��map
			SessionListener.sessionContext.getSessionMap().get(session.getId());
			SessionListener.sessionContext.getSessionMap().put(userid, SessionListener.sessionContext.getSessionMap().get(session.getId()));
			SessionListener.sessionContext.getSessionMap().remove(session.getId());
		}
	}
}
