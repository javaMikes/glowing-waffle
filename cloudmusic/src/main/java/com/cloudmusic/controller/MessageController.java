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
 * 用户的消息控制器
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
	 * 查询用户是否有未读的歌单消息
	 * 
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findPlaylistMessageByUserId")
	public void findPlaylistMessageByUserId(HttpServletResponse resp, HttpSession session) throws IOException {
		// ContentType 必须指定为 text/event-stream
		resp.setContentType("text/event-stream");
		// CharacterEncoding 必须指定为 UTF-8
		resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
		// 获取被评论的歌单
		String playlistResult = "";

		// 获取评论的歌单
		String playlistCommentResult = "";

		// 获取评论的歌曲
		String songCommentResult = "";

		User user = (User) session.getAttribute("userInfo");

		// 获取用户的歌单消息
		Msg msg = playlistCommentService.selectInformByUserId(user.getId());

		// 获取用户的歌曲消息
		Msg songMsg = songCommentService.selectInformByUserId(user.getId());

		// 获取用户未读的歌单评论
		@SuppressWarnings("unchecked")
		List<PlaylistComment> list1 = (List<PlaylistComment>) msg.getMap().get("list1");

		// 获取用户未读的歌单评论回复
		@SuppressWarnings("unchecked")
		List<PlaylistComment> list2 = (List<PlaylistComment>) msg.getMap().get("list2");

		// 获取用户未读的歌曲评论的回复
		@SuppressWarnings("unchecked")
		List<SongComment> list3 = (List<SongComment>) songMsg.getMap().get("list");

		// 获取被评论的歌单名
		if (list1.size() > 0) {
			for (PlaylistComment playlistComment : list1) {
				Msg playlistMsg = playlistService.selectPlayListByPlayListId(playlistComment.getPlaylistId());
				Playlist playlist = (Playlist) playlistMsg.getMap().get("msg");
				// 去掉重复的歌单名
				if (!playlistCommentResult.contains(playlist.getListName()))
					playlistResult += "'" + playlist.getListName() + "',";
			}
			// 去掉最后一个逗号
			playlistResult = playlistResult.substring(0, playlistResult.length() - 1);
			playlistResult = "你的歌单" + playlistResult + "被评论了\n\n" + "你的评论被回复了\n\n";
		}

		// 获取用户评论的歌单名
		if (list2.size() > 0) {
			for (PlaylistComment playlistComment : list2) {
				Msg playlistCommentMsg = playlistCommentService.selectByPrimarykey(playlistComment.getParentId());
				PlaylistComment playlistCommnet = (PlaylistComment) playlistCommentMsg.getMap().get("playlistCommnet");
				// 根据id获取歌单信息
				Msg playlistMsg = playlistService.selectPlayListByPlayListId(playlistCommnet.getPlaylistId());

				Playlist playlist = (Playlist) playlistMsg.getMap().get("msg");

				// 去掉重复的歌单名
				if (!playlistCommentResult.contains(playlist.getListName()))
					playlistCommentResult += "'" + playlist.getListName() + "',";
			}
			// 去掉最后一个逗号
			playlistCommentResult = playlistCommentResult.substring(0, playlistCommentResult.length() - 1);
			playlistCommentResult = "你对歌单" + playlistCommentResult + "的评论被回复了\n\n";
		}

		// 获取用户评论的歌曲名
		if (list3.size() > 0) {
			for (SongComment songComment : list3) {
				Msg songInfoMsg = songService.selectSongBySongId(songComment.getSongId());
				Song song = (Song) songInfoMsg.getMap().get("song");

				// 去掉重复的歌曲名
				if (!songCommentResult.contains(song.getSongName()))
					songCommentResult += "'" + song.getSongName() + "',";
			}
			// 去掉最后一个逗号
			songCommentResult = songCommentResult.substring(0, songCommentResult.length() - 1);
			songCommentResult = "你对歌单" + songCommentResult + "的评论被回复了\n\n";
		}

		pw.write("data: " + playlistResult);
		pw.write(playlistCommentResult);
		pw.write(songCommentResult);
		pw.close();
	}

	/**
	 * 将用户未读的消息改成已读
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
		// 获取用户的歌曲消息
		Msg songMsg = songCommentService.selectInformByUserId(user.getId());

		// 获取用户未读的歌曲评论的回复
		@SuppressWarnings("unchecked")
		List<SongComment> list3 = (List<SongComment>) songMsg.getMap().get("list");

		@SuppressWarnings("unchecked")
		List<PlaylistComment> list1 = (List<PlaylistComment>) msg.getMap().get("list1");
		@SuppressWarnings("unchecked")
		List<PlaylistComment> list2 = (List<PlaylistComment>) msg.getMap().get("list2");

		// 将用户的所有歌单评论通知设为已读
		if (list1.size() > 0) {
			for (PlaylistComment playlistComment : list1) {
				Msg playlistMsg = playlistCommentService.updateIsRead(playlistComment.getId());
				if (playlistMsg.getCode() == 200) {
					return playlistMsg;
				}
			}
		}

		// 将用户的所有歌单评论回复设为已读
		if (list2.size() > 0) {
			for (PlaylistComment playlistComment : list2) {
				Msg playlistCommentMsg = playlistCommentService.updateIsRead(playlistComment.getId());
				// 根据id获取歌单信息
				if (playlistCommentMsg.getCode() == 200) {
					return playlistCommentMsg;
				}
			}
		}

		// 将用户的所有歌曲评论回复设为已读
		if (list3.size() > 0) {
			for (SongComment songComment : list3) {
				Msg songCommentMsg = songCommentService.updateIsRead(songComment.getId());
				// 根据id获取歌单信息
				if (songCommentMsg.getCode() == 200) {
					return songCommentMsg;
				}
			}
		}
		return Msg.success();
	}

}
