package com.cloudmusic.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.SongTagExample;
import com.cloudmusic.bean.SongTagKey;

public interface SongTagMapper {
    int countByExample(SongTagExample example);

    int deleteByExample(SongTagExample example);

    int deleteByPrimaryKey(SongTagKey key);

    int insert(SongTagKey record);

    int insertSelective(SongTagKey record);

    List<SongTagKey> selectByExample(SongTagExample example);

    int updateByExampleSelective(@Param("record") SongTagKey record, @Param("example") SongTagExample example);

    int updateByExample(@Param("record") SongTagKey record, @Param("example") SongTagExample example);
}