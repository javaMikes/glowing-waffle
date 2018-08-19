package com.cloudmusic.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.PlaylistComment;
import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.SongComment;
import com.cloudmusic.bean.User;
import com.cloudmusic.service.IPlaylistCommentService;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ISongCommentService;
import com.cloudmusic.service.ISongService;
import com.cloudmusic.util.Msg;

/**
 * �û�����Ϣ������
 * 
 * @author Mike
 *
 */
@Controller
public class MessageController {

	@Autowired
	IPlaylistCommentService playlistCommentService;

	@Autowired
	ISongCommentService songCommentService;

	@Autowired
	IPlaylistService playlistService;

	@Autowired
	ISongService songService;

	/**
	 * ��ѯ�û��Ƿ���δ���ĸ赥��Ϣ
	 * 
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findPlaylistMessageByUserId")
	public void findPlaylistMessageByUserId(HttpServletResponse resp, HttpSession session) throws IOException {
		// ContentType ����ָ��Ϊ text/event-stream
		resp.setContentType("text/event-stream");
		// CharacterEncoding ����ָ��Ϊ UTF-8
		resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
		// ��ȡ�����۵ĸ赥
		String playlistResult = "";

		// ��ȡ���۵ĸ赥
		String playlistCommentResult = "";

		// ��ȡ���۵ĸ���
		String songCommentResult = "";

		User user = (User) session.getAttribute("userInfo");

		// ��ȡ�û��ĸ赥��Ϣ
		Msg msg = playlistCommentService.selectInformByUserId(user.getId());

		// ��ȡ�û��ĸ�����Ϣ
		Msg songMsg = songCommentService.selectInformByUserId(user.getId());

		// ��ȡ�û�δ���ĸ赥����
		@SuppressWarnings("unchecked")
		List<PlaylistComment> list1 = (List<PlaylistComment>) msg.getMap().get("list1");

		// ��ȡ�û�δ���ĸ赥���ۻظ�
		@SuppressWarnings("unchecked")
		List<PlaylistComment> list2 = (List<PlaylistComment>) msg.getMap().get("list2");

		// ��ȡ�û�δ���ĸ������۵Ļظ�
		@SuppressWarnings("unchecked")
		List<SongComment> list3 = (List<SongComment>) songMsg.getMap().get("list");

		// ��ȡ�����۵ĸ赥��
		if (list1.size() > 0) {
			for (PlaylistComment playlistComment : list1) {
				Msg playlistMsg = playlistService.selectPlayListByPlayListId(playlistComment.getPlaylistId());
				Playlist playlist = (Playlist) playlistMsg.getMap().get("msg");
				// ȥ���ظ��ĸ赥��
				if (!playlistCommentResult.contains(playlist.getListName()))
					playlistResult += "'" + playlist.getListName() + "',";
			}
			// ȥ�����һ������
			playlistResult = playlistResult.substring(0, playlistResult.length() - 1);
			playlistResult = "��ĸ赥" + playlistResult + "��������\n\n" + "������۱��ظ���\n\n";
		}

		// ��ȡ�û����۵ĸ赥��
		if (list2.size() > 0) {
			for (PlaylistComment playlistComment : list2) {
				Msg playlistCommentMsg = playlistCommentService.selectByPrimarykey(playlistComment.getParentId());
				PlaylistComment playlistCommnet = (PlaylistComment) playlistCommentMsg.getMap().get("playlistCommnet");
				// ����id��ȡ�赥��Ϣ
				Msg playlistMsg = playlistService.selectPlayListByPlayListId(playlistCommnet.getPlaylistId());

				Playlist playlist = (Playlist) playlistMsg.getMap().get("msg");

				// ȥ���ظ��ĸ赥��
				if (!playlistCommentResult.contains(playlist.getListName()))
					playlistCommentResult += "'" + playlist.getListName() + "',";
			}
			// ȥ�����һ������
			playlistCommentResult = playlistCommentResult.substring(0, playlistCommentResult.length() - 1);
			playlistCommentResult = "��Ը赥" + playlistCommentResult + "�����۱��ظ���\n\n";
		}

		// ��ȡ�û����۵ĸ�����
		if (list3.size() > 0) {
			for (SongComment songComment : list3) {
				Msg songInfoMsg = songService.selectSongBySongId(songComment.getSongId());
				Song song = (Song) songInfoMsg.getMap().get("song");

				// ȥ���ظ��ĸ�����
				if (!songCommentResult.contains(song.getSongName()))
					songCommentResult += "'" + song.getSongName() + "',";
			}
			// ȥ�����һ������
			songCommentResult = songCommentResult.substring(0, songCommentResult.length() - 1);
			songCommentResult = "��Ը赥" + songCommentResult + "�����۱��ظ���\n\n";
		}

		pw.write("data: " + playlistResult);
		pw.write(playlistCommentResult);
		pw.write(songCommentResult);
		pw.close();
	}

	/**
	 * ���û�δ������Ϣ�ĳ��Ѷ�
	 * 
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("readMessage")
	@ResponseBody
	public Msg readMessage(HttpServletResponse resp, HttpSession session) throws IOException {

		User user = (User) session.getAttribute("userInfo");
		Msg msg = playlistCommentService.selectInformByUserId(user.getId());
		// ��ȡ�û��ĸ�����Ϣ
		Msg songMsg = songCommentService.selectInformByUserId(user.getId());

		// ��ȡ�û�δ���ĸ������۵Ļظ�
		@SuppressWarnings("unchecked")
		List<SongComment> list3 = (List<SongComment>) songMsg.getMap().get("list");

		@SuppressWarnings("unchecked")
		List<PlaylistComment> list1 = (List<PlaylistComment>) msg.getMap().get("list1");
		@SuppressWarnings("unchecked")
		List<PlaylistComment> list2 = (List<PlaylistComment>) msg.getMap().get("list2");

		// ���û������и赥����֪ͨ��Ϊ�Ѷ�
		if (list1.size() > 0) {
			for (PlaylistComment playlistComment : list1) {
				Msg playlistMsg = playlistCommentService.updateIsRead(playlistComment.getId());
				if (playlistMsg.getCode() == 200) {
					return playlistMsg;
				}
			}
		}

		// ���û������и赥���ۻظ���Ϊ�Ѷ�
		if (list2.size() > 0) {
			for (PlaylistComment playlistComment : list2) {
				Msg playlistCommentMsg = playlistCommentService.updateIsRead(playlistComment.getId());
				// ����id��ȡ�赥��Ϣ
				if (playlistCommentMsg.getCode() == 200) {
					return playlistCommentMsg;
				}
			}
		}

		// ���û������и������ۻظ���Ϊ�Ѷ�
		if (list3.size() > 0) {
			for (SongComment songComment : list3) {
				Msg songCommentMsg = songCommentService.updateIsRead(songComment.getId());
				// ����id��ȡ�赥��Ϣ
				if (songCommentMsg.getCode() == 200) {
					return songCommentMsg;
				}
			}
		}
		return Msg.success();
	}

}
