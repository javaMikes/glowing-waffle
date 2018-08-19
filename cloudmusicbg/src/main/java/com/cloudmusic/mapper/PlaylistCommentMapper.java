package com.cloudmusic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.PlaylistComment;
import com.cloudmusic.bean.PlaylistCommentExample;

public interface PlaylistCommentMapper {
	int countByExample(PlaylistCommentExample example);

	int deleteByExample(PlaylistCommentExample example);

	int deleteByPrimaryKey(Long id);

	int insert(PlaylistComment record);

	int insertSelective(PlaylistComment record);

	List<PlaylistComment> selectByExample(PlaylistCommentExample example);

	PlaylistComment selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") PlaylistComment record, @Param("example") PlaylistCommentExample example);

	int updateByExample(@Param("record") PlaylistComment record, @Param("example") PlaylistCommentExample example);

	int updateByPrimaryKeySelective(PlaylistComment record);

	int updateByPrimaryKey(PlaylistComment record);
}