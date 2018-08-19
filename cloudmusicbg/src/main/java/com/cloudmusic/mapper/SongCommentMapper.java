package com.cloudmusic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.SongComment;
import com.cloudmusic.bean.SongCommentExample;

public interface SongCommentMapper {
	int countByExample(SongCommentExample example);

	int deleteByExample(SongCommentExample example);

	int deleteByPrimaryKey(Long id);

	int insert(SongComment record);

	int insertSelective(SongComment record);

	List<SongComment> selectByExample(SongCommentExample example);

	SongComment selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") SongComment record, @Param("example") SongCommentExample example);

	int updateByExample(@Param("record") SongComment record, @Param("example") SongCommentExample example);

	int updateByPrimaryKeySelective(SongComment record);

	int updateByPrimaryKey(SongComment record);
}