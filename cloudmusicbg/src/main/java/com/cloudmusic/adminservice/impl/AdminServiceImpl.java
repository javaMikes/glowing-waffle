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
	 * 管理员登录
	 * 
	 * @return Msg["msg" : 提示信息；"userInfo" : 用户信息]
	 */
	public Msg adminLogin(User user) {

		// 对密码进行加密
		
		try {
			user.setPassword(ShaUtil.shaEncode(user.getPassword()));
		} catch (Exception e) {
			System.out.println("后台登录 sha哈希出错");
		}
		
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andEmailEqualTo(user.getEmail());
		criteria.andPasswordEqualTo(user.getPassword());

		List<User> userlist = userMapper.selectByExample(userExample);

		if (userlist.size() != 0) {
			if (userlist.get(0).getIsSuper() == false)
				return Msg.error().add("msg", "该用户不是管理员");
			else {
				if (userlist.get(0).getIsActive() == false)
					return Msg.error().add("msg", "用户未激活，请先到邮箱激活再登录");

				User userSelected = userlist.get(0);

				userMapper.updateByPrimaryKeySelective(userSelected);
				return Msg.success().add("userInfo", userSelected);
			}
		} else {
			return Msg.error().add("msg", "邮箱或密码错误");
		}
	}

	/**
	 * 查询数据库所有歌曲
	 * 
	 * @return Msg["msg": 1:存在歌曲，返回 歌曲集合；2：不存在歌曲，返回提示信息]
	 */
	public Msg songSelect(Integer pn) {
		// 指定PageHelper当前页，每页显示长度
		PageHelper.startPage(pn, 10);

		List<Song> songList = songMapper.selectByExample(null);

		// 绑定PageInfo,集合 ,导航条页码
		PageInfo<Song> songPageInfo = new PageInfo<Song>(songList, 5);

		if (songList.size() == 0 || songList == null) {
			return Msg.error().add("msg", "没有歌曲");
		}
		return Msg.success().add("msg", songPageInfo);
	}

	/**
	 * 查询数据库所有歌手
	 * 
	 * @param pn
	 * @return Msg["msg": 1:存在歌手，返回 歌手集合；2：不存在歌手，返回提示信息]
	 */
	public Msg singerSelect(Integer pn) {
		// 指定PageHelper当前页，每页显示长度
		PageHelper.startPage(pn, 10);

		List<Singer> singerList = singerMapper.selectByExample(null);

		// 绑定PageInfo,集合 ,导航条页码
		PageInfo<Singer> singerPageInfo = new PageInfo<Singer>(singerList, 5);

		if (singerList.size() == 0 || singerList == null) {
			return Msg.error().add("msg", "没有歌手");
		}
		return Msg.success().add("msg", singerPageInfo);
	}

	/**
	 * 查询数据库所有评论
	 * 
	 * @param pn
	 * @return
	 */
	public Msg songCommentSelect(Integer pn) {
		// 指定PageHelper当前页，每页显示长度
		PageHelper.startPage(pn, 10);

		List<SongComment> songCommentList = songCommentMapper.selectByExample(null);

		// 绑定PageInfo,集合 ,导航条页码
		PageInfo<SongComment> songCommentPageInfo = new PageInfo<SongComment>(songCommentList, 5);

		if (songCommentList.size() == 0 || songCommentList == null) {
			return Msg.error().add("msg", "没有评论");
		}
		return Msg.success().add("msg", songCommentPageInfo);
	}

	/**
	 * 查询数据所有被举报的评论
	 * 
	 * @param pn
	 * @return
	 */
	public Msg songCommentReportSelect(Integer pn) {
		// 指定PageHelper当前页，每页显示长度
		PageHelper.startPage(pn, 10);
		SongCommentExample example = new SongCommentExample();
		com.cloudmusic.bean.SongCommentExample.Criteria criteria = example.createCriteria();
		criteria.andIsReportedEqualTo(1);
		List<SongComment> songCommentReportList = songCommentMapper.selectByExample(example);

		// 绑定PageInfo,集合 ,导航条页码
		PageInfo<SongComment> songCommentReportPageInfo = new PageInfo<SongComment>(songCommentReportList, 5);

		if (songCommentReportList.size() == 0 || songCommentReportList == null) {
			return Msg.error().add("msg", "没有被举报的评论");
		}
		return Msg.success().add("msg", songCommentReportPageInfo);
	}

	/**
	 * 查询数据所有的管理员
	 * 
	 * @param pn
	 * @return
	 */
	public Msg superUserSelect(Integer pn) {
		// 指定PageHelper当前页，每页显示长度
		PageHelper.startPage(pn, 10);
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsSuperEqualTo(true);
		List<User> superuserList = userMapper.selectByExample(example);

		// 绑定PageInfo,集合 ,导航条页码
		PageInfo<User> superUserPageInfo = new PageInfo<User>(superuserList, 5);

		if (superuserList.size() == 0 || superuserList == null) {
			return Msg.error().add("msg", "没有管理员");
		}
		return Msg.success().add("msg", superUserPageInfo);
	}

	/**
	 * 查询所有的被举报的歌单评论
	 * 
	 * @param pn
	 * @return
	 */
	public Msg playlistCommentSelect(Integer pn) {
		// 指定PageHelper当前页，每页显示长度
		PageHelper.startPage(pn, 10);
		PlaylistCommentExample example = new PlaylistCommentExample();
		com.cloudmusic.bean.PlaylistCommentExample.Criteria criteria = example.createCriteria();
		criteria.andIsReportedEqualTo(1);

		List<PlaylistComment> playlistCommentList = playlistCommentMapper.selectByExample(example);

		// 绑定PageInfo,集合 ,导航条页码
		PageInfo<PlaylistComment> playlistCommentPageInfo = new PageInfo<PlaylistComment>(playlistCommentList, 5);

		if (playlistCommentList.size() == 0 || playlistCommentList == null) {
			return Msg.error().add("msg", "没有管理员");
		}
		return Msg.success().add("msg", playlistCommentPageInfo);
	}

	/**
	 * 根据歌曲id获取歌曲信息
	 * 
	 * @param id
	 * @return
	 */
	public Msg songSelectById(long id) {
		Song song = songMapper.selectByPrimaryKey(id);
		return Msg.success().add("msg", song);
	}

	/**
	 * 根据歌手Id获取歌手信息
	 * 
	 * @param id
	 * @return
	 */
	public Msg singerSelectById(long id) {
		Singer singer = singerMapper.selectByPrimaryKey(id);
		return Msg.success().add("msg", singer);
	}

	/**
	 * 根据歌曲id修改歌曲信息
	 * 
	 * @param id
	 * @return Msg["msg" : 修改数量]
	 */
	public Msg songUpdateById(Song song) {
		int num = songMapper.updateByPrimaryKeySelective(song);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * 根据歌手id修改歌手信息
	 * 
	 * @param singer
	 * @return Msg["msg" : 修改数量]
	 */
	public Msg singerUpdateById(Singer singer) {
		int num = singerMapper.updateByPrimaryKeySelective(singer);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * 根据歌曲id删除歌曲
	 * 
	 * @param id
	 * @return Msg["msg" : 删除的歌曲数量]
	 */
	public Msg songDeleteById(long id) {
		int num = songMapper.deleteByPrimaryKey(id);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * 根据歌手id删除歌手
	 * 
	 * @param id
	 * @return Msg["msg" : 删除的歌手数量]
	 */
	public Msg singerDeleteById(long id) {
		int num = singerMapper.deleteByPrimaryKey(id);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * 根据歌曲评论删除评论
	 * 
	 * @param id
	 * @return
	 */
	public Msg songCommentDeleteById(long id) {
		System.out.println("评论id=" + id);
		int num = songCommentMapper.deleteByPrimaryKey(id);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * 根据歌单评论ID删除评论
	 * 
	 * @param id
	 */
	public Msg playlistCommentDeleteById(long id) {
		System.out.println("评论id=" + id);
		int num = playlistCommentMapper.deleteByPrimaryKey(id);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * 根据歌曲id集合删除歌曲
	 * 
	 * @param list
	 * @return Msg["msg" : 删除的歌曲数量]
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
	 * 根据歌单id集合删除歌单
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
	 * 根据歌手id集合删除歌手
	 * 
	 * @param list
	 * @return Msg["msg" : 删除的歌手数量]
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
	 * 歌手添加
	 * 
	 * @param singer
	 * @return Msg["msg" : 添加数目]
	 */
	public Msg addSinger(Singer singer) {
		int num = singerMapper.insert(singer);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * 歌曲上传
	 * 
	 * @param song
	 * @return Msg["msg" : 添加数目]
	 */
	public Msg uploadSong(Song song) {
		int num = songMapper.insert(song);
		if (num > 0) {
			return Msg.success().add("msg", num).add("songId", song.getId());
		}
		return Msg.error().add("msg", num);
	}

	/**
	 * 根据id查询歌曲评论
	 * 
	 * @param id
	 * @return
	 */
	public Msg songCommentSelectById(long id) {
		SongComment songComment = songCommentMapper.selectByPrimaryKey(id);
		return Msg.success().add("msg", songComment);
	}

	/**
	 * 根据歌单id查询歌单评论
	 * 
	 * @param id
	 * @return
	 */
	public Msg playlistCommentSelectById(long id) {
		PlaylistComment playlistComment = playlistCommentMapper.selectByPrimaryKey(id);
		return Msg.success().add("msg", playlistComment);
	}

	/**
	 * 根据id将评论修改为已处理状态
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
	 * 根据id将歌单评论修改为已处理状态
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
	 * 向数据库插入superUser
	 * 
	 * @param user
	 * @return Msg["msg" ： 插入的管理员数量]
	 */
	public Msg superUserInsert(User user) {
		int num = userMapper.insertSelective(user);
		if (num > 0) {
			return Msg.success().add("msg", num);
		}
		return Msg.success().add("msg", num);
	}

	/**
	 * 根据管理员ID删除管理员
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
	 * 批量删除歌曲评论
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
	 * 获取音乐播放时长
	 * 
	 * @param path
	 */
	public Msg getMusicTime(String path) {
		String musicTime = translate(getDuration(path));
		return Msg.success().add("msg", musicTime);
	}

	// 获取MP3的时长
	public static int getDuration(String position) {

		int length = 0;
		try {
			MP3File mp3File = (MP3File) AudioFileIO.read(new File(position));
			MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();

			// 单位为秒
			length = audioHeader.getTrackLength();

			return length;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return length;
	}

	// 将秒数转化为--:--:--的字符串格式
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
