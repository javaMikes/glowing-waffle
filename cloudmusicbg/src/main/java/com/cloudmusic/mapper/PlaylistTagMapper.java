package com.cloudmusic.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.PlaylistTagExample;
import com.cloudmusic.bean.PlaylistTagKey;

public interface PlaylistTagMapper {
    int countByExample(PlaylistTagExample example);

    int deleteByExample(PlaylistTagExample example);

    int deleteByPrimaryKey(PlaylistTagKey key);

    int insert(PlaylistTagKey record);

    int insertSelective(PlaylistTagKey record);

    List<PlaylistTagKey> selectByExample(PlaylistTagExample example);

    int updateByExampleSelective(@Param("record") PlaylistTagKey record, @Param("example") PlaylistTagExample example);

    int updateByExample(@Param("record") PlaylistTagKey record, @Param("example") PlaylistTagExample example);
}