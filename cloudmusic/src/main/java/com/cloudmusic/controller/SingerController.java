package com.cloudmusic.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudmusic.bean.Singer;
import com.cloudmusic.bean.Song;
import com.cloudmusic.service.ISingerService;
import com.cloudmusic.service.ISongService;
import com.cloudmusic.util.Msg;

@Controller
public class SingerController {

	@Autowired
	ISingerService singerService;

	@Autowired
	ISongService songService;

	/**
	 * ���ݸ������ֻ�ȡ������Ϣ
	 * 
	 * @param singerName
	 * @param model
	 * @return ["singerInfo" ������Ϣ,"hotSongList",���Ÿ赥]
	 */
	@RequestMapping("showSingerInfoPage")
	public String showSingerInfoPage(@RequestParam(required = true) String singerName, Model model) {

		Msg msg = singerService.selectSingerBySingerName(singerName);

		if (msg.getCode() == 200) {
			model.addAttribute("message", "��ѯ������Ϣ����");
			return "error";
		}
		Singer singer = (Singer) msg.getMap().get("list");

		// ���ݸ��ֵ����ֻ�ȡ�ø��ֵ����Ÿ���
		Msg songMsg = songService.selectSingerSongAndHotSort(singerName);

		if (songMsg.getCode() == 200) {
			model.addAttribute("message", "��ѯ���Ÿ�����Ϣ����");
			return "error";
		}

		@SuppressWarnings("unchecked")
		List<Song> allSongList = (List<Song>) songMsg.getMap().get("list");
		// �洢ǰ�������Ÿ���
		List<Song> songList = new ArrayList<Song>();

		for (int i = 0; i < allSongList.size() && i < 6; i++) {
			songList.add(allSongList.get(i));
		}

		// ���ظ�����Ϣ
		model.addAttribute("singerInfo", singer);
		// �������Ÿ���
		model.addAttribute("hotSongList", songList);
		return "singer";
	}

	/**
	 * ���ݸ���id��ȡ���ֵĸ���
	 * 
	 * @param singerId
	 * @return Msg����������[msg : "��ʾ��Ϣ"]; ["list" : List<Song>�����б�]
	 */
	@RequestMapping("findSongBySingerId")
	@ResponseBody
	public Msg getSongBySingerId(@RequestParam(required = true) long singerId) {
		Msg msg = singerService.selectSingerById(singerId);
		if (msg.getCode() == 200)
			return msg;

		// ���ݸ���id��ȡ������Ϣ
		Msg singerMsg = singerService.selectSingerById(singerId);
		Singer singer = (Singer) singerMsg.getMap().get("singer");

		// ���ݸ��ֵ����ֻ�ȡ�ø��ֵĸ���
		Msg songMsg = songService.selectSongBySinger(singer.getSingerName());

		if (songMsg.getCode() == 200)
			return songMsg;

		return songMsg;
	}

	/**
	 * ���ݸ���id��ȡ���ֵ����Ÿ���
	 * 
	 * @param singerId
	 * @return ["hotSongList",ǰ��λ���Ÿ�������]
	 */
	@RequestMapping("findHotSongBySingerId")
	@ResponseBody
	public Msg getHotSongBySingerId(@RequestParam(required = true) long singerId) {
		Msg msg = singerService.selectSingerById(singerId);
		if (msg.getCode() == 200)
			return msg;

		// ���ݸ���id��ȡ������Ϣ
		Msg singerMsg = singerService.selectSingerById(singerId);
		Singer singer = (Singer) singerMsg.getMap().get("singer");

		// ���ݸ��ֵ����ֻ�ȡ�ø��ֵ����Ÿ���
		Msg songMsg = songService.selectSingerSongAndHotSort(singer.getSingerName());

		if (songMsg.getCode() == 200)
			return songMsg;

		@SuppressWarnings("unchecked")
		List<Song> allSongList = (List<Song>) songMsg.getMap().get("list");
		// �洢ǰ�������Ÿ���
		List<Song> songList = new ArrayList<Song>();

		for (int i = 0; i < allSongList.size() && i < 6; i++) {
			songList.add(allSongList.get(i));
		}

		return Msg.success().add("hotSongList", songList);
	}

	/**
	 * ���ݸ���id��ʾ����ͼ��
	 * 
	 * @param singerId
	 * @return ["hotSongList",ǰ��λ���Ÿ�������]
	 */
	@RequestMapping(value = "showSingerImg")
	@ResponseBody
	public String showSingerImg(@RequestParam(required = true) long singerId, HttpServletResponse response) {
		// response.setContentType("image/*");
		// ��ȡ�赥����Ϣ
		Msg msg = singerService.selectSingerById(singerId);
		Singer singer = new Singer();
		if (msg.getCode() == 100) {
			singer = (Singer) msg.getMap().get("singer");
		} else {
			return Msg.error().getMsg();
		}
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(singer.getImgPath());
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
	 * ��ȡ�½����֣����µ�3��
	 * 
	 * @return Msg["list":List{Singer}:���ּ���]
	 */
	@RequestMapping(value = "selectNewestSinger")
	@ResponseBody
	public Msg selectNewestSinger() {
		return singerService.selectNewestSinger();
	}

}
