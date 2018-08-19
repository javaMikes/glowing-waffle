package com.cloudmusic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.PlaylistExample;

public interface PlaylistMapper {
	int countByExample(PlaylistExample example);

	int deleteByExample(PlaylistExample example);

	int deleteByPrimaryKey(Long id);

	int insert(Playlist record);

	int insertSelective(Playlist record);

	List<Playlist> selectByExample(PlaylistExample example);

	Playlist selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") Playlist record, @Param("example") PlaylistExample example);

	int updateByExample(@Param("record") Playlist record, @Param("example") PlaylistExample example);

	int updateByPrimaryKeySelective(Playlist record);

	int updateByPrimaryKey(Playlist record);
}