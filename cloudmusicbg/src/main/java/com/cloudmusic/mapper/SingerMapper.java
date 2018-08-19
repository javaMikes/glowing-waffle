package com.cloudmusic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.Singer;
import com.cloudmusic.bean.SingerExample;

public interface SingerMapper {
	int countByExample(SingerExample example);

	int deleteByExample(SingerExample example);

	int deleteByPrimaryKey(Long id);

	int insert(Singer record);

	int insertSelective(Singer record);

	List<Singer> selectByExample(SingerExample example);

	Singer selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") Singer record, @Param("example") SingerExample example);

	int updateByExample(@Param("record") Singer record, @Param("example") SingerExample example);

	int updateByPrimaryKeySelective(Singer record);

	int updateByPrimaryKey(Singer record);
}