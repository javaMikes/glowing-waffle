package com.cloudmusic.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.AttentionExample;
import com.cloudmusic.bean.AttentionKey;

public interface AttentionMapper {
    int countByExample(AttentionExample example);

    int deleteByExample(AttentionExample example);

    int deleteByPrimaryKey(AttentionKey key);

    int insert(AttentionKey record);

    int insertSelective(AttentionKey record);

    List<AttentionKey> selectByExample(AttentionExample example);

    int updateByExampleSelective(@Param("record") AttentionKey record, @Param("example") AttentionExample example);

    int updateByExample(@Param("record") AttentionKey record, @Param("example") AttentionExample example);
}