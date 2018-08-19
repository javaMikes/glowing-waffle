package com.cloudmusic.admincontroller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cloudmusic.adminservice.IAdminService;
import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.PlaylistComment;
import com.cloudmusic.bean.Singer;
import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.SongComment;
import com.cloudmusic.bean.User;
import com.cloudmusic.config.SessionListener;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ISongService;
import com.cloudmusic.service.IUserService;

import com.cloudmusic.util.Msg;
import com.cloudmusic.util.ShaUtil;
import com.github.pagehelper.PageInfo;

@Controller
@SessionAttributes("userInfo")
public class AdminController {

	public int pageSize = 5;// ?????????

	@Autowired
	IAdminService adminService;

	@Autowired
	IUserService userService;

	@Autowired
	ISongService songService;

	@Autowired
	IPlaylistService playlistService;

	/**
	 * ????????
	 * 
	 * @param user
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("adminLogin")
	public void login(User user, HttpSession session, HttpServletResponse response) throws Exception {
		// ???????????
		Msg msg = adminService.adminLogin(user);
		if (msg.getCode() == 100) {
			// ????????????
			User adminLogin = (User) msg.getMap().get("userInfo");

			session.setAttribute("userInfo", adminLogin);
			sessionHandlerByCacheMap(adminLogin.getId(), session);

			response.sendRedirect("adminMain.jsp");

		} else {
			response.sendRedirect("adminLogin.jsp");
		}
	}

	@RequestMapping("adminlogout")
	@ResponseBody
	public Msg adminlogout(HttpSession session, SessionStatus status) {
		User user = (User) session.getAttribute("userInfo");
		long userId = user.getId();
		session.invalidate();
		status.setComplete();
		SessionListener.sessionContext.getSessionMap().remove(String.valueOf(userId));
		return Msg.success();
	}

	/**
	 * ?ж????????????????????
	 * 
	 * @param session
	 */
	public void sessionHandlerByCacheMap(long userId, HttpSession session) {

		String userid = String.valueOf(userId);
		if (SessionListener.sessionContext.getSessionMap().get(userid) != null) {
			HttpSession userSession = (HttpSession) SessionListener.sessionContext.getSessionMap().get(userid);
			if (!userSession.equals(session)) {
				// ??????????
				try {
					userSession.invalidate();
				} catch (Exception e) {
					System.out.println("session???????????????????????");
				}

				SessionListener.sessionContext.getSessionMap().remove(userid);
				// ???????????????map,?滻map sessionid
				SessionListener.sessionContext.getSessionMap().remove(session.getId());
				SessionListener.sessionContext.getSessionMap().put(userid, session);
			}
		} else {
			// ??????sessionid ?session???? ????map key=????? value=session???? ???map
			SessionListener.sessionContext.getSessionMap().get(session.getId());
			SessionListener.sessionContext.getSessionMap().put(userid, SessionListener.sessionContext.getSessionMap().get(session.getId()));
			SessionListener.sessionContext.getSessionMap().remove(session.getId());
		}
	}

	@RequestMapping("songUpload")
	@ResponseBody
	public Msg songUpload(@RequestParam("song_file") CommonsMultipartFile songfile, @RequestParam("songLyrics_file") CommonsMultipartFile lyricsfile,
			@RequestParam("songImg_file") CommonsMultipartFile imgfile, String upload_song_name, String upload_singer_name,
			String upload_composition_name, Long upload_integral, String upload_song_introduction, String tagName)
			throws IllegalStateException, IOException {

		// ??????????·??
		String songType = songfile.getOriginalFilename();
		songType = songType.substring(songType.indexOf('.'));
		String path = "D:\\upload\\cloudmusic\\song\\" + upload_song_name + songType;
		System.out.println("????????·???"+path);
		// ????·??????
		String song_path = "\\song\\" + upload_song_name + songType;
		// ??????????·??
		
		String lyricsPath = "D:\\upload\\cloudmusic\\lrc\\" + upload_song_name + ".lrc";
		// ??????????·??
		
		String imgType = imgfile.getOriginalFilename();
		imgType = imgType.substring(imgType.indexOf('.'));
		
		String imgPath = "D:\\upload\\cloudmusic\\img\\song\\" + upload_song_name + imgType;

		//???????????????????д???????????????????
		File newFile = new File(path);
		songfile.transferTo(newFile);

		File lyricsFile = new File(lyricsPath);
		lyricsfile.transferTo(lyricsFile);

		File imgFile = new File(imgPath);
		imgfile.transferTo(imgFile);

		//???????lrc??????и?????
		File file_lrc = new File(lyricsPath);
		
		System.out.println("----------------------------------------------");
		System.out.println("??????е????"+file_lrc.getName());
		boolean needTransfer = false;
		FileInputStream fis = new FileInputStream(file_lrc);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		
		String line = "";
		String lrcFileStr = "";
		
		while((line = br.readLine()) != null)
		{
			//?????
			
			int num = line.indexOf("]");
			if(num == 10)
			{
				needTransfer = true;
				String str_1 = line.substring(0, 9);
				String str_2 = line.substring(num, line.length());
				String new_line = str_1 + str_2;
				lrcFileStr = lrcFileStr + new_line + "\n";
			}
			else
				lrcFileStr = lrcFileStr + line + "\n";
		}
		br.close();
		isr.close();
		fis.close();
		
		//???????д?????
		FileOutputStream fos=new FileOutputStream(file_lrc);
        OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter  bw=new BufferedWriter(osw);
		
        bw.write(lrcFileStr);
        
        bw.close();
        osw.close();
        fos.close();
        if(needTransfer)
        {
        	System.out.println("???????????");
        }
        System.out.println("----------------------------------------------");
		
		//?????????
		
		Song song = new Song();
		// ??????????????
		Msg msg = adminService.getMusicTime(newFile.getAbsolutePath());
		if (msg.getCode() == 100) {
			String musicTime = (String) msg.getMap().get("msg");
			System.out.println("?????????" + musicTime);
			song.setMusicTime(musicTime);
		}
		song.setSongName(upload_song_name);
		song.setSinger(upload_singer_name);
		song.setLyrics(lyricsPath);
		song.setComposition(upload_composition_name);
		song.setCollection(0L);
		song.setPlayTimes(0L);
		song.setIntegral(upload_integral);
		song.setPath(song_path);
		song.setIntroduction(upload_song_introduction);
		song.setImgPath(imgPath);
		// ???????
		Msg msg2 = adminService.uploadSong(song);
		if (msg2.getCode() == 100) {
			int num = (Integer) msg2.getMap().get("msg");
			if (num > 0) {
				// ???????????
				if (tagName != null && !tagName.equals("")) {
					String[] tagNames = tagName.split(",");
					List<String> tagNameList = new ArrayList<String>();
					for (int i = 0; i < tagNames.length; i++) {
						tagNameList.add(tagNames[i]);
					}
					songService.updateSongTagByTagNameList((Long) msg2.getMap().get("songId"), tagNameList);
				}
				return Msg.success().add("msg", "??????????");
			}
			return Msg.error().error().add("msg", "??????????");
		}
		return Msg.error().error().add("msg", "??????????");
	}

	@ResponseBody
	@RequestMapping("songSelect")
	public Msg songSelect(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		Msg msg = adminService.songSelect(pn);
		System.out.println("msg=" + msg.getCode());
		if (msg.getCode() == 100) {
			PageInfo<Song> songPageInfo = (PageInfo<Song>) msg.getMap().get("msg");
			System.out.println("??С??" + songPageInfo.getList().size());
			if (songPageInfo.getList().size() != 0) {
				return Msg.success().add("pageInfo", songPageInfo);
			}
			return Msg.error();
		}
		return Msg.error();
	}

	@ResponseBody
	@RequestMapping("singerSelect")
	public Msg singerSelect(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		Msg msg = adminService.singerSelect(pn);
		if (msg.getCode() == 100) {
			PageInfo<Singer> singerPageInfo = (PageInfo<Singer>) msg.getMap().get("msg");
			if (singerPageInfo.getList().size() != 0) {
				return Msg.success().add("singerPageInfo", singerPageInfo);
			}
			return Msg.error();
		}
		return Msg.error();
	}

	// @ResponseBody
	// @RequestMapping("songCommentSelect")
	// public Msg songCommentSelect(@RequestParam(value = "pn", defaultValue =
	// "1") Integer pn){
	// Msg msg = adminService.songCommentSelect(pn);
	// if(msg.getCode() == 100){
	// PageInfo<SongComment> songCommentPageInfo = (PageInfo<SongComment>)
	// msg.getMap().get("msg");
	// if(songCommentPageInfo.getList().size() != 0){
	// return Msg.success().add("songCommentPageInfo", songCommentPageInfo);
	// }
	// return Msg.error();
	// }
	// return Msg.error();
	// }

	@ResponseBody
	@RequestMapping("songCommentReportSelect")
	public Msg songCommentReportSelect(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		Msg msg = adminService.songCommentReportSelect(pn);
		if (msg.getCode() == 100) {
			PageInfo<SongComment> songCommentReportPageInfo = (PageInfo<SongComment>) msg.getMap().get("msg");
			if (songCommentReportPageInfo.getList().size() != 0) {
				return Msg.success().add("songCommentReportPageInfo", songCommentReportPageInfo);
			}
			return Msg.error();
		}
		return Msg.error();
	}

	@ResponseBody
	@RequestMapping("playlistCommentSelect")
	public Msg playlistCommentSelect(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		Msg msg = adminService.playlistCommentSelect(pn);
		if (msg.getCode() == 100) {
			PageInfo<PlaylistComment> playlistCommentPageInfo = (PageInfo<PlaylistComment>) msg.getMap().get("msg");
			if (playlistCommentPageInfo.getList().size() != 0) {
				return Msg.success().add("playlistCommentPageInfo", playlistCommentPageInfo);
			}
			return Msg.error();
		}
		return Msg.error();
	}

	@ResponseBody
	@RequestMapping("superUserSelect")
	public Msg superUserSelect(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		Msg msg = adminService.superUserSelect(pn);
		if (msg.getCode() == 100) {
			PageInfo<User> superUserPageInfo = (PageInfo<User>) msg.getMap().get("msg");
			if (superUserPageInfo.getList().size() != 0) {
				return Msg.success().add("superUserPageInfo", superUserPageInfo);
			}
			return Msg.error();
		}
		return Msg.error();
	}

	@ResponseBody
	@RequestMapping("songCommentSelectById")
	public Msg songCommentSelectById(long id) {
		Msg msg = adminService.songCommentSelectById(id);
		if (msg.getCode() == 100) {
			SongComment songComment = (SongComment) msg.getMap().get("msg");
			Msg msg2 = userService.selectUserById(songComment.getUserId());

			String userName = "";
			String songName = "";

			if (msg2.getCode() == 100) {
				User user = (User) msg2.getMap().get("user");
				userName = user.getNickname();
				System.out.println("userName=" + userName);
			}
			Msg msg3 = songService.selectSongBySongId(songComment.getSongId());
			if (msg3.getCode() == 100) {
				Song song = (Song) msg3.getMap().get("song");
				songName = song.getSongName();
				System.out.println("songName=" + songName);
			}

			return Msg.success().add("songComment", songComment).add("userName", userName).add("songName", songName);
		}
		return Msg.error().add("msg", "???????");
	}

	@ResponseBody
	@RequestMapping("playlistCommentSelectById")
	public Msg playlistCommentSelectById(long id) {
		Msg msg = adminService.playlistCommentSelectById(id);
		if (msg.getCode() == 100) {
			PlaylistComment playlistComment = (PlaylistComment) msg.getMap().get("msg");
			Msg msg2 = userService.selectUserById(playlistComment.getUserId());

			String userName = "";
			String playlistName = "";

			if (msg2.getCode() == 100) {
				User user = (User) msg2.getMap().get("user");
				userName = user.getNickname();
				System.out.println("userName=" + userName);
			}
			Msg msg3 = playlistService.selectPlayListByPlayListId(playlistComment.getPlaylistId());
			if (msg3.getCode() == 100) {
				Playlist playlist = (Playlist) msg3.getMap().get("msg");
				playlistName = playlist.getListName();
			}

			return Msg.success().add("playlistComment", playlistComment).add("userName", userName).add("playlistName", playlistName);
		}
		return Msg.error().add("msg", "???????");
	}

	/**
	 * ???????id??????????
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("songSelectById")
	public Msg songSelectById(long id) {
		Msg msg = adminService.songSelectById(id);
		if (msg.getCode() == 100) {
			Song song = (Song) msg.getMap().get("msg");
			return Msg.success().add("pageInfo", song);
		}
		return Msg.error().add("pageInfo", msg.getMap().get("msg"));
	}

	@ResponseBody
	@RequestMapping("singerSelectById")
	public Msg singerSelectById(long id) {
		Msg msg = adminService.singerSelectById(id);
		if (msg.getCode() == 100) {
			Singer singer = (Singer) msg.getMap().get("msg");
			return Msg.success().add("singerPageInfo", singer);
		}
		return Msg.error().add("singerPageInfo", msg.getMap().get("msg"));
	}

	@ResponseBody
	@RequestMapping("songUpdateById")
	public Msg songUpdateById(Song song) {
		Msg msg = adminService.songUpdateById(song);
		if (msg.getCode() == 100) {
			int num = (Integer) msg.getMap().get("msg");
			if (num > 0)
				return Msg.success().add("msg", "????????????");
			return Msg.error().add("msg", "?????????????");
		}
		return Msg.error().add("msg", "?????????????");
	}

	@ResponseBody
	@RequestMapping("singerUpdateById")
	public Msg singerUpdateById(Singer singer, String settledTime_) throws ParseException {
		System.out.println(singer.getSingerName() + "??" + singer.getIntroduction() + settledTime_);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date settledTime = formatter.parse(settledTime_);
		singer.setSettledTime(settledTime);
		Msg msg = adminService.singerUpdateById(singer);
		if (msg.getCode() == 100) {
			int num = (Integer) msg.getMap().get("msg");
			if (num > 0)
				return Msg.success().add("msg", "????????????");
			return Msg.error().add("msg", "?????????????");
		}
		return Msg.error().add("msg", "?????????????");
	}

	@ResponseBody
	@RequestMapping("songDeleteById")
	public Msg songDeleteById(long id) {
		Msg msg = adminService.songDeleteById(id);
		if (msg.getCode() == 100) {
			int num = (Integer) msg.getMap().get("msg");
			if (num > 0) {
				return Msg.success().add("msg", "??????????");
			}
			return Msg.error().add("msg", "??????????");
		}
		return Msg.error().add("msg", "??????????");
	}

	@ResponseBody
	@RequestMapping("singerDeleteById")
	public Msg singerDeleteById(long id) {
		Msg msg = adminService.singerDeleteById(id);
		if (msg.getCode() == 100) {
			int num = (Integer) msg.getMap().get("msg");
			if (num > 0) {
				return Msg.success().add("msg", "??????????");
			}
			return Msg.error().add("msg", "??????????");
		}
		return Msg.error().add("msg", "??????????");
	}

	@ResponseBody
	@RequestMapping("songCommentDeleteById")
	public Msg songCommentDeleteById(long id) {
		Msg msg = adminService.songCommentDeleteById(id);
		if (msg.getCode() == 100) {
			int num = (Integer) msg.getMap().get("msg");
			if (num > 0) {
				return Msg.success().add("msg", "??????????????");
			}
			return Msg.error().add("msg", "??????????????");
		}
		return Msg.error().add("msg", "??????????????");
	}

	@ResponseBody
	@RequestMapping("playlistCommentDeleteById")
	public Msg playlistCommentDeleteById(long id) {
		Msg msg = adminService.playlistCommentDeleteById(id);
		if (msg.getCode() == 100) {
			int num = (Integer) msg.getMap().get("msg");
			if (num > 0) {
				return Msg.success().add("msg", "?赥??????????");
			}
			return Msg.error().add("msg", "?赥??????????");
		}
		return Msg.error().add("msg", "?赥??????????");
	}

	/**
	 * ??????????????????????1??checkbox???????????2??checkbox??ж??
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteSongByCheckbox")
	public Msg deleteSongByCheckbox(String ids) {
		if (ids.contains("-")) {
			// ??????
			List<Long> list = new ArrayList<Long>();
			String[] str_ids = ids.split("-");
			for (String id : str_ids) {
				list.add(Long.parseLong(id));
			}
			Msg msg = adminService.songDeleteByCheckbox(list);
			if (msg.getCode() == 100) {
				int num = (Integer) msg.getMap().get("msg");
				if (num > 0)
					return Msg.success().add("msg", "??????");
				return Msg.error();
			}
			return Msg.error();
		} else {
			// ??????
			Msg msg = adminService.songDeleteById(Long.parseLong(ids));
			if (msg.getCode() == 100) {
				int num = (Integer) msg.getMap().get("msg");
				if (num > 0)
					return Msg.success().add("msg", "??????");
				return Msg.error();
			}
			return Msg.error();
		}
	}

	/**
	 * ??????????????????????1??checkbox???????????2??checkbox??ж??
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deletePlaylistCommentByCheckbox")
	public Msg deletePlaylistCommentByCheckbox(String ids) {
		if (ids.contains("-")) {
			// ??????
			List<Long> list = new ArrayList<Long>();
			String[] str_ids = ids.split("-");
			for (String id : str_ids) {
				list.add(Long.parseLong(id));
			}
			Msg msg = adminService.deletePlaylistCommentByCheckbox(list);
			if (msg.getCode() == 100) {
				int num = (Integer) msg.getMap().get("msg");
				if (num > 0)
					return Msg.success().add("msg", "??????");
				return Msg.error();
			}
			return Msg.error();
		} else {
			// ??????
			Msg msg = adminService.songDeleteById(Long.parseLong(ids));
			if (msg.getCode() == 100) {
				int num = (Integer) msg.getMap().get("msg");
				if (num > 0)
					return Msg.success().add("msg", "??????");
				return Msg.error();
			}
			return Msg.error();
		}
	}

	/**
	 * ??????????????????????1??checkbox???????????2??checkbox??ж??
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteSingerByCheckbox")
	public Msg deleteSingerByCheckbox(String ids) {
		if (ids.contains("-")) {
			// ??????
			List<Long> list = new ArrayList<Long>();
			String[] str_ids = ids.split("-");
			for (String id : str_ids) {
				list.add(Long.parseLong(id));
			}
			Msg msg = adminService.singerDeleteByCheckbox(list);
			if (msg.getCode() == 100) {
				int num = (Integer) msg.getMap().get("msg");
				if (num > 0)
					return Msg.success().add("msg", "??????");
				return Msg.error();
			}
			return Msg.error();
		} else {
			// ??????
			Msg msg = adminService.singerDeleteById(Long.parseLong(ids));
			if (msg.getCode() == 100) {
				int num = (Integer) msg.getMap().get("msg");
				if (num > 0)
					return Msg.success().add("msg", "??????");
				return Msg.error();
			}
			return Msg.error();
		}
	}

	@ResponseBody
	@RequestMapping("addSinger")
	public Msg addSinger(@RequestParam("singerImg_file") CommonsMultipartFile singerImgfile, String add_singerName, String add_settledTime,
			String add_introduction) throws ParseException, IllegalStateException, IOException {

		String singerImgPath = "D:\\upload\\cloudmusic\\img\\singer\\" + singerImgfile.getOriginalFilename();
		File newFile = new File(singerImgPath);
		singerImgfile.transferTo(newFile);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date settledTime = formatter.parse(add_settledTime);

		Singer singer = new Singer();
		singer.setSingerName(add_singerName);
		singer.setSettledTime(settledTime);
		singer.setImgPath(singerImgPath);
		singer.setIntroduction(add_introduction);
		Msg msg = adminService.addSinger(singer);
		if (msg.getCode() == 100) {
			int num = (Integer) msg.getMap().get("msg");
			if (num > 0)
				return Msg.success().add("msg", "?????");
			return Msg.error();
		}
		return Msg.error();
	}

	@ResponseBody
	@RequestMapping("updateReportStatusById")
	public Msg updateReportStatusById(long id) {
		Msg msg = adminService.updateReportStatusById(id);
		if (msg.getCode() == 100) {
			int num = (Integer) msg.getMap().get("msg");
			if (num > 0) {
				return Msg.success().add("msg", "????????????");
			}
			return Msg.error().add("msg", "????????????");
		}
		return Msg.error().add("msg", "????????????");
	}

	@ResponseBody
	@RequestMapping("updatePlaylistCommentStatusById")
	public Msg updatePlaylistCommentStatusById(long id) {
		Msg msg = adminService.updatePlaylistCommentStatusById(id);
		if (msg.getCode() == 100) {
			int num = (Integer) msg.getMap().get("msg");
			if (num > 0) {
				return Msg.success().add("msg", "????????????");
			}
			return Msg.error().add("msg", "????????????");
		}
		return Msg.error().add("msg", "????????????");
	}

	@ResponseBody
	@RequestMapping("superUserInsert")
	public Msg superUserInsert(@RequestParam("img_file") CommonsMultipartFile file, String add_superUserName, String add_superUserPwd,
			boolean add_superUserSex, String add_birthday, String add_email, String add_region, String add_personsignature)
			throws IllegalStateException, IOException, ParseException {

		String path = "D:/upload/cloudmusic/head/" + file.getOriginalFilename();

		System.out.println(path);
		File newFile = new File(path);
		file.transferTo(newFile);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = format.parse(add_birthday);

		User user = new User();
		user.setNickname(add_superUserName);
		// ?????????SHA????
		
		//user.setPassword(MD5Util.encode2hex(add_superUserPwd));
		try {
			user.setPassword(ShaUtil.shaEncode(add_superUserPwd));
		} catch (Exception e) {
			System.out.println("SHA ???????");
		}
		
		user.setSex(add_superUserSex);
		user.setBirthday(birthday);
		user.setEmail(add_email);
		user.setRegion(add_region);
		user.setHeadImage(path);
		user.setPersonSignature(add_personsignature);
		user.setIsSuper(true);
		// ??????????????
		Msg msg = adminService.superUserInsert(user);
		if (msg.getCode() == 100) {
			int num = (Integer) msg.getMap().get("msg");
			if (num > 0)
				return Msg.success().add("msg", "??????????");
			return Msg.error().error().add("msg", "???????????");
		}
		return Msg.error().error().add("msg", "???????????");
	}

	@ResponseBody
	@RequestMapping("superUserDeleteById")
	public Msg superUserDeleteById(long id) {
		Msg msg = adminService.superUserDeleteById(id);
		if (msg.getCode() == 100) {
			int num = (Integer) msg.getMap().get("msg");
			if (num > 0) {
				return Msg.success().add("msg", "???????????");
			}
			return Msg.error().add("msg", "???????????");
		}
		return Msg.error().add("msg", "???????????");
	}

	/**
	 * ??????????????????????1??checkbox???????????2??checkbox??ж??
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteSongCommentByCheckbox")
	public Msg deleteSongCommentByCheckbox(String ids) {
		if (ids.contains("-")) {
			// ??????
			List<Long> list = new ArrayList<Long>();
			String[] str_ids = ids.split("-");
			for (String id : str_ids) {
				list.add(Long.parseLong(id));
			}
			Msg msg = adminService.deleteSongCommentByCheckbox(list);
			if (msg.getCode() == 100) {
				int num = (Integer) msg.getMap().get("msg");
				if (num > 0)
					return Msg.success().add("msg", "??????");
				return Msg.error();
			}
			return Msg.error();
		} else {
			// ??????
			Msg msg = adminService.songCommentDeleteById(Long.parseLong(ids));
			if (msg.getCode() == 100) {
				int num = (Integer) msg.getMap().get("msg");
				if (num > 0)
					return Msg.success().add("msg", "??????");
				return Msg.error();
			}
			return Msg.error();
		}
	}

}
