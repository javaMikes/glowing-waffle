package com.cloudmusic.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.Listen;
import com.cloudmusic.bean.ListenExample;
import com.cloudmusic.bean.ListenKey;

public interface ListenMapper {
    int countByExample(ListenExample example);

    int deleteByExample(ListenExample example);

    int deleteByPrimaryKey(ListenKey key);

    int insert(Listen record);

    int insertSelective(Listen record);

    List<Listen> selectByExample(ListenExample example);

    Listen selectByPrimaryKey(ListenKey key);

    int updateByExampleSelective(@Param("record") Listen record, @Param("example") ListenExample example);

    int updateByExample(@Param("record") Listen record, @Param("example") ListenExample example);

    int updateByPrimaryKeySelective(Listen record);

    int updateByPrimaryKey(Listen record);
}