package com.cloudmusic.test;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cloudmusic.bean.PlaylistComment;
import com.cloudmusic.bean.SongComment;
import com.cloudmusic.bean.User;
import com.cloudmusic.mapper.PlaylistCommentMapper;
import com.cloudmusic.mapper.PlaylistMapper;
import com.cloudmusic.mapper.ReceiveMapper;
import com.cloudmusic.mapper.SongCommentMapper;
import com.cloudmusic.mapper.SongMapper;
import com.cloudmusic.mapper.UserMapper;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ITagService;
import com.cloudmusic.service.IUserService;

/*****数据表测试****

@author Mike
 *
 */
 @RunWith(SpringJUnit4ClassRunner.class)
 @ContextConfiguration(locations = { "classpath:applicationContext.xml" })
 public class MybaitsTest {

 @Autowired
 UserMapper userMapper;

 @Autowired
 ReceiveMapper receiveMapper;

 @Autowired
 ITagService impl;

 @Autowired
 SongCommentMapper songCommentMapper;

 // ========================user表==============================
 @Test
 public void insertUserTest() {
 User user = new User();
 user.setNickname("abcde");
 user.setPassword("123456");
 user.setSex(false);
 user.setBirthday(new Timestamp(System.currentTimeMillis()));
 user.setEmail("1980971109@qq.com");
 user.setRegion("广东省");
 user.setHeadImage("C://");

 user.setIntegral(10L);
 user.setLoginStatus(true);
 user.setIsSignin(false);
 int id = userMapper.insert(user);
 System.out.println("刚刚插入的一条数据，自动生成的ID为:" + user.getId());
 }

 @Test
 public void deleteUserTest() {
 userMapper.deleteByPrimaryKey(4L);
 }

 @Test
 public void updateUserTest() {
 User user = new User();
 user.setId(2L);
 user.setPassword("0987765");
 userMapper.updateByPrimaryKeySelective(user);
 System.out.println("end");
 }

 @Test
 public void selectByNickName() {
 User user = userMapper.selectByPrimaryKey(2232L);
 System.out.println(user == null);
 }

 @Test
 public void selectByPrimaryKey() {
 userMapper.selectByPrimaryKey(39L);
 }

 @Autowired
 PlaylistMapper playlistMapper;

 @Autowired
 ITagService tagService;

 @Autowired
 SongMapper songMapper;

 @Autowired
 IPlaylistService playlistService;

 @Autowired
 IUserService userService;

 @Autowired
 PlaylistCommentMapper playlistCommentMapper;

 @Test
 public void testt() throws Exception {
 PlaylistComment p = new PlaylistComment();
 p.setUserId(2L);
 p.setPlaylistId(2L);
 p.setContent("hi");

 // p.setCommentTime(new Date());
 playlistCommentMapper.insert(p);

 // PlaylistComment p = playlistCommentMapper.selectByPrimaryKey(4L);
 // System.out.println(p.getCommentTime());
 // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd
 // HH:mm:ss");
 // Date date1 = format.parse(p.getCommentTime());
 //
 // Date date2 = format.parse("2017-10-10 11:15:30");
 //
 // System.out.println(date1.compareTo(date2) < 0);
 }

 // 歌曲评论测试
 @Test
 public void selectSongCommontByPrimaryKey() {
 // SongComment songComment = new SongComment();
 // songComment.setUserId(70L);
 // songComment.setSongId(1L);
 // songComment.setContent("伤感");
 // songCommentMapper.insert(songComment);
 SongComment songComment = songCommentMapper.selectByPrimaryKey(1L);
 System.out.println(songComment.getCommentTime());
 }

 // 歌单评论测试
 @Test
 public void selectPlaylistCommentByPrimaryKey() {
 // SongComment songComment = new SongComment();
 // songComment.setUserId(70L);
 // songComment.setSongId(1L);
 // songComment.setContent("伤感");
 // songCommentMapper.insert(songComment);
 PlaylistComment playlistComment =
 playlistCommentMapper.selectByPrimaryKey(11L);
 System.out.println(playlistComment.getCommentTime());
 }

 }
