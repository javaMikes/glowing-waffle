package com.cloudmusic.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.SongCollectionExample;
import com.cloudmusic.bean.SongCollectionKey;

public interface SongCollectionMapper {
    int countByExample(SongCollectionExample example);

    int deleteByExample(SongCollectionExample example);

    int deleteByPrimaryKey(SongCollectionKey key);

    int insert(SongCollectionKey record);

    int insertSelective(SongCollectionKey record);

    List<SongCollectionKey> selectByExample(SongCollectionExample example);

    int updateByExampleSelective(@Param("record") SongCollectionKey record, @Param("example") SongCollectionExample example);

    int updateByExample(@Param("record") SongCollectionKey record, @Param("example") SongCollectionExample example);
}