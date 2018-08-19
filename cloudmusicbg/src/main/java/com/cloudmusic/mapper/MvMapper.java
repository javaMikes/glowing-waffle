package com.cloudmusic.mapper;

import com.cloudmusic.bean.Mv;
import com.cloudmusic.bean.MvExample;
import com.cloudmusic.bean.MvKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MvMapper {
    int countByExample(MvExample example);

    int deleteByExample(MvExample example);

    int deleteByPrimaryKey(MvKey key);

    int insert(Mv record);

    int insertSelective(Mv record);

    List<Mv> selectByExample(MvExample example);

    Mv selectByPrimaryKey(MvKey key);

    int updateByExampleSelective(@Param("record") Mv record, @Param("example") MvExample example);

    int updateByExample(@Param("record") Mv record, @Param("example") MvExample example);

    int updateByPrimaryKeySelective(Mv record);

    int updateByPrimaryKey(Mv record);
}