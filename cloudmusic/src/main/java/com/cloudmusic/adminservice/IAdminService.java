package com.cloudmusic.adminservice;

import java.util.List;

import com.cloudmusic.bean.Singer;
import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.User;
import com.cloudmusic.util.Msg;

public interface IAdminService {

	/**
	 * 管理员登录
	 * @return Msg["msg" : 提示信息；"userInfo" : 用户信息]
	 */
	public Msg adminLogin(User user);
	
	/**
	 * 查询数据库所有歌曲
	 * @return Msg["msg": 1:存在歌曲，返回 歌曲集合；2：不存在歌曲，返回提示信息]
	 */
	public Msg songSelect(Integer pn);
	
	/**
	 * 查询数据库所有歌手
	 * @param pn
	 * @return
	 */
	public Msg singerSelect(Integer pn);
	
	/**
	 * 查询数据库所有评论
	 * @param pn
	 * @return
	 */
	public Msg songCommentSelect(Integer pn);
	
	/**
	 * 查询数据所有被举报的评论
	 * @param pn
	 * @return
	 */
	public Msg songCommentReportSelect(Integer pn);
	
	/**
	 * 查询数据所有的管理员
	 * @param pn
	 * @return
	 */
	public Msg superUserSelect(Integer pn);
	
	/**
	 * 查询所有的被举报的歌单评论
	 * @param pn
	 * @return
	 */
	public Msg playlistCommentSelect(Integer pn);
	
	/**
	 * 根据歌曲id获取歌曲信息
	 * @param id
	 * @return
	 */
	public Msg songSelectById(long id);
	
	/**
	 * 根据歌手Id获取歌手信息
	 * @param id
	 * @return
	 */
	public Msg singerSelectById(long id);
	
	/**
	 * 根据歌曲id修改歌曲信息
	 * @param id
	 * @return Msg["msg" : 修改数量]
	 */
	public Msg songUpdateById(Song song);
	
	/**
	 * 根据歌手id修改歌手信息
	 * @param singer
	 * @return Msg["msg" : 修改数量]
	 */
	public Msg singerUpdateById(Singer singer);
	
	/**
	 * 根据歌曲id删除歌曲
	 * @param id
	 * @return Msg["msg" : 删除的歌曲数量]
	 */
	public Msg songDeleteById(long id);
	
	/**
	 * 根据歌手id删除歌手
	 * @param id
	 * @return Msg["msg" : 删除的歌手数量]
	 */
	public Msg singerDeleteById(long id);
	
	/**
	 * 根据歌曲评论ID删除评论
	 * @param id
	 * @return
	 */
	public Msg songCommentDeleteById(long id);
	
	/**
	 * 根据歌单评论ID删除评论
	 * @param id
	 */
	public Msg playlistCommentDeleteById(long id);
	
	/**
	 * 根据歌曲id集合删除歌曲
	 * @param list
	 * @return Msg["msg" : 删除的歌曲数量]
	 */
	public Msg songDeleteByCheckbox(List<Long> list);
	
	/**
	 * 根据歌单id集合删除歌单
	 * @param list
	 * @return
	 */
	public Msg deletePlaylistCommentByCheckbox(List<Long> list);
	
	/**
	 * 根据歌手id集合删除歌手
	 * @param list
	 * @return Msg["msg" : 删除的歌手数量]
	 */
	public Msg singerDeleteByCheckbox(List<Long> list);
	
	/**
	 * 歌手添加
	 * @param singer
	 * @return
	 */
	public Msg addSinger(Singer singer);
	
	/**
	 * 歌曲上传
	 * @param song
	 * @return
	 */
	public Msg uploadSong(Song song);
	
	/**
	 * 获取音乐播放时长
	 * @param path
	 */
	public Msg getMusicTime(String path);
	
	/**
	 * 根据id查询歌曲评论
	 * @param id
	 * @return
	 */
	public Msg songCommentSelectById(long id);
	
	/**
	 * 根据歌单id查询歌单评论
	 * @param id
	 * @return
	 */
	public Msg playlistCommentSelectById(long id);
	
	/**
	 * 根据id将评论修改为已处理状态
	 * @param id
	 * @return
	 */
	public Msg updateReportStatusById(long id);
	
	/**
	 * 根据id将歌单评论修改为已处理状态
	 * @param id
	 * @return
	 */
	public Msg updatePlaylistCommentStatusById(long id);
	
	/**
	 * 向数据库插入superUser
	 * @param user
	 * @return
	 */
	public Msg superUserInsert(User user);
	
	/**
	 * 根据管理员ID删除管理员
	 * @param id
	 * @return
	 */
	public Msg superUserDeleteById(long id);
	
	/**
	 * 批量删除歌曲评论
	 * @param list
	 * @return
	 */
	public Msg deleteSongCommentByCheckbox(List<Long> list);
}
