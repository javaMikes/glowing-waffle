package com.cloudmusic.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.PlaylistCollectionExample;
import com.cloudmusic.bean.PlaylistCollectionKey;

public interface PlaylistCollectionMapper {
    int countByExample(PlaylistCollectionExample example);

    int deleteByExample(PlaylistCollectionExample example);

    int deleteByPrimaryKey(PlaylistCollectionKey key);

    int insert(PlaylistCollectionKey record);

    int insertSelective(PlaylistCollectionKey record);

    List<PlaylistCollectionKey> selectByExample(PlaylistCollectionExample example);

    int updateByExampleSelective(@Param("record") PlaylistCollectionKey record, @Param("example") PlaylistCollectionExample example);

    int updateByExample(@Param("record") PlaylistCollectionKey record, @Param("example") PlaylistCollectionExample example);
}