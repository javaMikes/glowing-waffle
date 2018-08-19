package com.cloudmusic.adminservice.impl;

import java.io.File;
import java.util.List;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudmusic.adminservice.IAdminService;
import com.cloudmusic.bean.PlaylistComment;
import com.cloudmusic.bean.PlaylistCommentExample;
import com.cloudmusic.bean.Singer;
import com.cloudmusic.bean.SingerExample;
import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.SongComment;
import com.cloudmusic.bean.SongCommentExample;
import com.cloudmusic.bean.SongExample;
import com.cloudmusic.bean.User;
import com.cloudmusic.bean.UserExample;
import com.cloudmusic.bean.UserExample.Criteria;
import com.cloudmusic.mapper.PlaylistCommentMapper;
import com.cloudmusic.mapper.SingerMapper;
import com.cloudmusic.mapper.SongCommentMapper;
import com.cloudmusic.mapper.SongMapper;
import com.cloudmusic.mapper.UserMapper;

import com.cloudmusic.util.Msg;
import com.cloudmusic.util.ShaUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	SongMapper songMapper;

	@Autowired
	SingerMapper singerMapper;

	@Autowired
	SongCommentMapper songCommentMapper;

	@Autowired
	PlaylistCommentMapper playlistCommentMapper;

	/**
	 * ����Ա��¼
	 * 
	 * @return Msg["msg" : ��ʾ��Ϣ��"userInfo" : �û���Ϣ]
	 */
	public Msg adminLogin(User user) {

		// ��������м���
		
		try {
			user.setPassword(ShaUtil.shaEncode(user.getPassword()));
		} catch (Exception e) {
			System.out.println("��̨��¼ sha��ϣ����");
		}
		
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andEmailEqualTo(user.getEmail());
		criteria.andPasswordEqualTo(user.getPassword());

		List<User> userlist = userMapper.selectByExample(userExample);

		if (userlist.size() != 0) {
			if (userlist.get(0).getIsSuper() == false)
				return Msg.error().add("msg", "���û����ǹ���Ա");
			else {
				if (userlist.get(0).getIsActive() == false)
					return Msg.error().add("msg", "�û�δ������ȵ����伤���ٵ�¼");

				User userSelected = userlist.get(0);

				userMapper.updateByPrimaryKeySelective(userSelected);
				return Msg.success().add("userInfo", userSelected);
			}
		} else {
			return Msg.error().add("msg", "������������");
		}
	}

	/**
	 * ��ѯ���ݿ����и���
	 * 
	 * @return Msg["msg": 1:���ڸ��������� �������ϣ�2�������ڸ�����������ʾ��Ϣ]
	 */
	public Msg songSelect(Integer pn) {
		// ָ��PageHelper��ǰҳ��ÿҳ��ʾ����
		PageHelper.startPage(pn, 10);

		List<Song> songList = songMapper.selectByExample(null);

		// ��PageInfo,���� ,������ҳ��
		PageInfo<Song> songPageInfo = new PageInfo<Song>(songList, 5);

		if (songList.size() == 0 || songList == null) {
			return Msg.error().add("msg", "û�и���");
		}
		return Msg.success().add("msg", songPageInfo);
	}

	/**
	 * ��ѯ���ݿ����и���
	 * 
	 * @param pn
	 * @return Msg["msg": 1:���ڸ��֣����� ���ּ��ϣ�2�������ڸ��֣�������ʾ��Ϣ]
	 */
	public Msg singerSelect(Integer pn) {
		// ָ��PageHelper��ǰҳ��ÿҳ��ʾ����
		PageHelper.startPage(pn, 10);

		List<Singer> singerList = singerMapper.selectByExample(null);

		// ��PageInfo,���� ,������ҳ��
		PageInfo<Singer> singerPageInfo = new PageInfo<Singer>(singerList, 5);

		if (singerList.size() == 0 || singerList == null) {
			return Msg.error().add("msg", "û�и���");
		}
		return Msg.success().add("msg", singerPageInfo);
	}

	/**
	 * ��ѯ���ݿ���������
	 * 
	 * @param pn
	 * @return
	 */
	public Msg songCommentSelect(Integer pn) {
		// ָ��PageHelper��ǰҳ��ÿҳ��ʾ����
		PageHelper.startPage(pn, 10);

		List<SongComment> songCommentList = songCommentMapper.selectByExample(null);

		// ��PageInfo,���� ,������ҳ��
		PageInfo<SongComment> songCommentPageInfo = new PageInfo<SongComment>(songCommentList, 5);

		if (songCommentList.size() == 0 || songCommentList == null) {
			return Msg.error().add("msg", "û������");
		}
		return Msg.success().add("msg", songCommentPageInfo);
	}

	/**
	 * ��ѯ�������б��ٱ�������
	 * 
	 * @param pn
	 * @return
	 */
	public Msg songCommentReportSelect(Integer pn) {
		// ָ��PageHelper��ǰҳ��ÿҳ��ʾ����
		PageHelper.startPage(pn, 10);
		SongCommentExample example = new SongCommentExample();
		com.cloudmusic.bean.SongCommentExample.Criteria criteria = example.createCriteria();
		criteria.andIsReportedEqualTo(1);
		List<SongComment> songCommentReportList = songCommentMapper.selectByExample(example);

		// ��PageInfo,���� ,������ҳ��
		PageInfo<SongComment> songCommentReportPageInfo = new PageInfo<SongComment>(songCommentReportList, 5);

		if (songCommentReportList.size() == 0 || songCommentReportList == null) {
			return Msg.error().add("msg", "û�б��ٱ�������");
		}
		return Msg.success().add("msg", songCommentReportPageInfo);
	}

	/**
	 * ��ѯ�������еĹ���Ա
	 * 
	 * @param pn
	 * @return
	 */
	public Msg superUserSelect(Integer pn) {
		// ָ��PageHelper��ǰҳ��ÿҳ��ʾ����
		PageHelper.startPage(pn, 10);
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsSuperEqualTo(true);
		List<User> superuserList = userMapper.selectByExample(example);

		// ��PageInfo,���� ,������ҳ��
		PageInfo<User> superUserPageInfo = new PageInfo<User>(superuserList, 5);

		if (superuserList.size() == 0 || superuserList == null) {
			return Msg.error().add("msg", "û�й���Ա");
		}
		return Msg.success().add("msg", superUserPageInfo);
	}

	/**
	 * ��ѯ���еı��ٱ��ĸ赥����
	 * 
	 * @param pn
	 * @return
	 */
	public Msg playlistCommentSelect(Integer pn) {
		// ָ��PageHelper��ǰҳ��ÿҳ��ʾ����
		PageHelper.startPage(pn, 10);
		PlaylistCommentExample example = new PlaylistCommentExample();
		com.cloudmusic.bean.PlaylistCommentExample.Criteria criteria = example.createCriteria();
		criteria.andIsReportedEqualTo(1);

		List<PlaylistComment> playlistCommentList = playlistCommentMapper.selectByExample(example);

		// ��PageInfo,���� ,������ҳ��
		PageInfo<PlaylistComment> playlistCommentPageInfo = new PageInfo<PlaylistComment>(playlistCommentList, 5);

		if (playlistCommentList.size() == 0 || playlistCommentList == null) {
			return Msg.error().add("msg", "û�й���Ա");
		}
		return Msg.success().add("msg", playlistCommentPageInfo);
	}

	/**
	 * ���ݸ���id��ȡ������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public Msg songSelectById(long id) {
		Song song = songMapper.selectByPrimaryKey(id);
		return Msg.success().add("msg", song);
	}

	/**
	 * ���ݸ���Id��ȡ������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public Msg singerSelectById(long id) {
		Singer singer = singerMapper.selectByPrimaryKey(id);
		return Msg.success().add("msg", singer);
	}

	/**
	 * ���ݸ���id�޸ĸ�����Ϣ
	 * 
	 * @param id
	 * @return Msg["msg" : �޸�����]
	 */
	public Msg songUpdateById(Song song) {
		int num = songMapper.updateByPrimaryKeySelective(song);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * ���ݸ���id�޸ĸ�����Ϣ
	 * 
	 * @param singer
	 * @return Msg["msg" : �޸�����]
	 */
	public Msg singerUpdateById(Singer singer) {
		int num = singerMapper.updateByPrimaryKeySelective(singer);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * ���ݸ���idɾ������
	 * 
	 * @param id
	 * @return Msg["msg" : ɾ���ĸ�������]
	 */
	public Msg songDeleteById(long id) {
		int num = songMapper.deleteByPrimaryKey(id);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * ���ݸ���idɾ������
	 * 
	 * @param id
	 * @return Msg["msg" : ɾ���ĸ�������]
	 */
	public Msg singerDeleteById(long id) {
		int num = singerMapper.deleteByPrimaryKey(id);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * ���ݸ�������ɾ������
	 * 
	 * @param id
	 * @return
	 */
	public Msg songCommentDeleteById(long id) {
		System.out.println("����id=" + id);
		int num = songCommentMapper.deleteByPrimaryKey(id);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * ���ݸ赥����IDɾ������
	 * 
	 * @param id
	 */
	public Msg playlistCommentDeleteById(long id) {
		System.out.println("����id=" + id);
		int num = playlistCommentMapper.deleteByPrimaryKey(id);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * ���ݸ���id����ɾ������
	 * 
	 * @param list
	 * @return Msg["msg" : ɾ���ĸ�������]
	 */
	public Msg songDeleteByCheckbox(List<Long> list) {
		SongExample example = new SongExample();
		com.cloudmusic.bean.SongExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		int num = songMapper.deleteByExample(example);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * ���ݸ赥id����ɾ���赥
	 * 
	 * @param list
	 * @return
	 */
	public Msg deletePlaylistCommentByCheckbox(List<Long> list) {
		PlaylistCommentExample example = new PlaylistCommentExample();
		com.cloudmusic.bean.PlaylistCommentExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		int num = playlistCommentMapper.deleteByExample(example);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * ���ݸ���id����ɾ������
	 * 
	 * @param list
	 * @return Msg["msg" : ɾ���ĸ�������]
	 */
	public Msg singerDeleteByCheckbox(List<Long> list) {
		SingerExample example = new SingerExample();
		com.cloudmusic.bean.SingerExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		int num = singerMapper.deleteByExample(example);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * �������
	 * 
	 * @param singer
	 * @return Msg["msg" : �����Ŀ]
	 */
	public Msg addSinger(Singer singer) {
		int num = singerMapper.insert(singer);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * �����ϴ�
	 * 
	 * @param song
	 * @return Msg["msg" : �����Ŀ]
	 */
	public Msg uploadSong(Song song) {
		int num = songMapper.insert(song);
		if (num > 0) {
			return Msg.success().add("msg", num).add("songId", song.getId());
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * ����id��ѯ��������
	 * 
	 * @param id
	 * @return
	 */
	public Msg songCommentSelectById(long id) {
		SongComment songComment = songCommentMapper.selectByPrimaryKey(id);
		return Msg.success().add("msg", songComment);
	}

	/**
	 * ���ݸ赥id��ѯ�赥����
	 * 
	 * @param id
	 * @return
	 */
	public Msg playlistCommentSelectById(long id) {
		PlaylistComment playlistComment = playlistCommentMapper.selectByPrimaryKey(id);
		return Msg.success().add("msg", playlistComment);
	}

	/**
	 * ����id�������޸�Ϊ�Ѵ���״̬
	 * 
	 * @param id
	 * @return
	 */
	public Msg updateReportStatusById(long id) {
		SongComment songComment = songCommentMapper.selectByPrimaryKey(id);
		songComment.setIsReported(2);
		int num = songCommentMapper.updateByPrimaryKey(songComment);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * ����id���赥�����޸�Ϊ�Ѵ���״̬
	 * 
	 * @param id
	 * @return
	 */
	public Msg updatePlaylistCommentStatusById(long id) {
		PlaylistComment playlistComment = playlistCommentMapper.selectByPrimaryKey(id);
		playlistComment.setIsReported(2);
		int num = playlistCommentMapper.updateByPrimaryKey(playlistComment);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * �����ݿ����superUser
	 * 
	 * @param user
	 * @return Msg["msg" �� ����Ĺ���Ա����]
	 */
	public Msg superUserInsert(User user) {
		int num = userMapper.insertSelective(user);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.success().add("msg", num);
	}

	/**
	 * ���ݹ���ԱIDɾ������Ա
	 * 
	 * @param id
	 * @return
	 */
	public Msg superUserDeleteById(long id) {
		int num = userMapper.deleteByPrimaryKey(id);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.success().add("msg", num);
	}

	/**
	 * ����ɾ����������
	 * 
	 * @param list
	 * @return
	 */
	public Msg deleteSongCommentByCheckbox(List<Long> list) {
		SongCommentExample example = new SongCommentExample();
		com.cloudmusic.bean.SongCommentExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		int num = songCommentMapper.deleteByExample(example);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * ��ȡ���ֲ���ʱ��
	 * 
	 * @param path
	 */
	public Msg getMusicTime(String path) {
		String musicTime = translate(getDuration(path));
		return Msg.success().add("msg", musicTime);
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

}
