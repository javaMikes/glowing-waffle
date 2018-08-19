package com.cloudmusic.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.cloudmusic.bean.TagName;
import com.cloudmusic.bean.TagNameExample;

public interface TagNameMapper {
    int countByExample(TagNameExample example);

    int deleteByExample(TagNameExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TagName record);

    int insertSelective(TagName record);

    List<TagName> selectByExample(TagNameExample example);

    TagName selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TagName record, @Param("example") TagNameExample example);

    int updateByExample(@Param("record") TagName record, @Param("example") TagNameExample example);

    int updateByPrimaryKeySelective(TagName record);

    int updateByPrimaryKey(TagName record);
}