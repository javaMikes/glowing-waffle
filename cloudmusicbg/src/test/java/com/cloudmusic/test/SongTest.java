package com.cloudmusic.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Scanner;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.SongExample;
import com.cloudmusic.bean.SongExample.Criteria;
import com.cloudmusic.bean.SongTagKey;
import com.cloudmusic.mapper.PlaylistMapper;
import com.cloudmusic.mapper.ReceiveMapper;
import com.cloudmusic.mapper.SongMapper;
import com.cloudmusic.mapper.SongTagMapper;
import com.cloudmusic.mapper.UserMapper;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ITagService;

import javazoom.jl.player.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SongTest {
	@Autowired
	UserMapper userMapper;

	@Autowired
	ReceiveMapper receiveMapper;

	@Autowired
	ITagService impl;

	@Autowired
	PlaylistMapper playlistMapper;

	@Autowired
	ITagService tagService;

	@Autowired
	SongMapper songMapper;

	@Autowired
	IPlaylistService playlistService;

	@Autowired
	SongTagMapper songTagMapper;

	// Ϊ�ض�ID����ĸ������ñ�ǩ
	@Test
	public void addTagToSong() {
		SongExample example1 = new SongExample();
		Criteria c1 = example1.createCriteria();
		c1.andIdBetween(1L, 12L);
		List<Song> songList = songMapper.selectByExample(example1);

		Scanner in = new Scanner(System.in);
		for (int i = 0; i < songList.size(); i++) {
			System.out.print(songList.get(i).getId() + " Ϊ " + songList.get(i).getSongName() + " ���׸����ñ�ǩ��");
			String str = in.nextLine();
			String[] strList = str.split(" ");
			for (int j = 0; j < strList.length; j++) {
				SongTagKey key = new SongTagKey();
				key.setSongId(songList.get(i).getId());
				String s = strList[j].trim();
				long tagId = Long.parseLong(s);
				key.setTagId(tagId);
				songTagMapper.insert(key);
			}
			System.out.print("�����ǩ�ɹ�");
			System.out.println("-------------------------------------");
		}

	}

	// ��������������
	@Test
	public void uploadMusicToMySQL() throws Exception {
		File f = new File("D:\\upload\\cloudmusic\\MP3�Զ��������ݿ�");
		File[] files = f.listFiles();

		String singer = "����";
		String musicPath = "D:\\upload\\cloudmusic\\song\\���� - ΢΢һЦ�����.mp3";
		String intro = "�����й��ڵ�����Ա��1991��9��9�ճ������Ϻ������ᰲ�պϷʣ���ҵ���й������ž�����ѧԺ2003���赸ϵ";
		String imgPath = "D:\\upload\\cloudmusic\\img\\song\\����.jpg";
		String lric = "D:\\upload\\cloudmusic\\lrc\\���� - ΢΢һЦ�����.lrc";

		for (File file : files) {
			String str = file.getName();
			str = str.substring(0, str.length() - 4);
			System.out.println(str);

			String str_time = translate(getDuration(file.getAbsolutePath()));

			Song song = new Song();
			song.setSongName(str);
			System.out.println(song.getSongName());
			song.setSinger(singer);
			System.out.println(song.getSinger());
			song.setLyrics(lric);
			song.setComposition(singer);
			song.setMusicTime(str_time);
			song.setPlayTimes(0L);
			song.setCollection(0L);
			song.setPath(musicPath);
			song.setIntroduction(intro);
			song.setIntegral(0L);
			song.setImgPath(imgPath);

			songMapper.insert(song);
		}
		System.out.println("finish");

	}

	// ������ת��Ϊ--:--:--���ַ�����ʽ
	public static String translate(int seconds) {
		int hour = seconds / (60 * 60);
		int min = seconds / 60;
		int second = seconds % 60;

		String str_hour, str_min, str_second;
		if (hour < 10)
			str_hour = "0" + hour;
		else
			str_hour = hour + "";

		if (min < 10)
			str_min = "0" + min;
		else
			str_min = min + "";

		if (second < 10)
			str_second = "0" + second;
		else
			str_second = second + "";

		return str_hour + ":" + str_min + ":" + str_second;
	}

	// ����һ��MP3
	public static void play(String position) {
		try {
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(position));
			Player player = new Player(buffer);
			player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ��ȡMP3��ʱ��
	public static int getDuration(String position) {

		int length = 0;
		try {
			MP3File mp3File = (MP3File) AudioFileIO.read(new File(position));
			MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();

			// ��λΪ��
			length = audioHeader.getTrackLength();

			return length;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return length;
	}
	
	/**
	 * ����ר�������������ת��Ϊ��ϵͳ��ʶ���������ʸ�ʽ
	 * @throws Exception
	 */
	@Test
	public static void transferLrc() throws Exception
	{
		File f = new File("D:/upload/cloudmusic/lrc");
		File[] files = f.listFiles();
		
		int count = 0;
		for(File file : files)
		{
			System.out.println("----------------------------------------------");
			System.out.println("���ڽ��е��ǣ�"+file.getName());
			boolean needTransfer = false;
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			
			String line = "";
			String lrcFileStr = "";
			
			while((line = br.readLine()) != null)
			{
				//��һ��
				
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
			
			//��ʼ����д���ļ�
			FileOutputStream fos=new FileOutputStream(file);
	        OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
	        BufferedWriter  bw=new BufferedWriter(osw);
			
	        bw.write(lrcFileStr);
	        
	        bw.close();
	        osw.close();
	        fos.close();
	        if(needTransfer)
	        {
	        	count++;
	        	System.out.println("���ļ���Ҫת��");
	        }
	        System.out.println("----------------------------------------------");
		}
		System.out.println("============= һ����"+files.length+"���ļ����ɹ�ת����"+count+"���ļ�");
	}
}
