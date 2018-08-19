package com.cloudmusic.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.ReceiveExample;
import com.cloudmusic.bean.ReceiveKey;

public interface ReceiveMapper {
    int countByExample(ReceiveExample example);

    int deleteByExample(ReceiveExample example);

    int deleteByPrimaryKey(ReceiveKey key);

    int insert(ReceiveKey record);

    int insertSelective(ReceiveKey record);

    List<ReceiveKey> selectByExample(ReceiveExample example);

    int updateByExampleSelective(@Param("record") ReceiveKey record, @Param("example") ReceiveExample example);

    int updateByExample(@Param("record") ReceiveKey record, @Param("example") ReceiveExample example);
}