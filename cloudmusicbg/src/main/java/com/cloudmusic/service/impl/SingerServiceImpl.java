package com.cloudmusic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudmusic.bean.Singer;
import com.cloudmusic.bean.SingerExample;
import com.cloudmusic.bean.SingerExample.Criteria;
import com.cloudmusic.mapper.SingerMapper;
import com.cloudmusic.service.ISingerService;
import com.cloudmusic.util.Msg;

@Service
public class SingerServiceImpl implements ISingerService {

	@Autowired
	SingerMapper singerMapper;

	/**
	 * 根据歌手姓名获取歌手信息
	 * 
	 * @param singer_name
	 * @return Msg["list" : 歌手信息]
	 */
	public Msg selectSingerBySingerName(String singer_name) {
		SingerExample example = new SingerExample();
		Criteria criteria = example.createCriteria();
		criteria.andSingerNameEqualTo(singer_name);
		List<Singer> singerList = singerMapper.selectByExample(example);
		if (singerList == null || singerList.size() == 0) {
			return Msg.error();
		}
		return Msg.success().add("list", singerList.get(0));
	}

	public Msg selectSingerByLikeName(String name) {
		SingerExample example = new SingerExample();
		Criteria c = example.createCriteria();
		c.andSingerNameLike("%" + name + "%");
		List<Singer> list = singerMapper.selectByExample(example);

		return Msg.success().add("list", list);
	}

	public Msg selectSingerById(long id) {
		Singer singer = singerMapper.selectByPrimaryKey(id);
		return Msg.success().add("singer", singer);
	}

	public Msg selectNewestSinger() {
		SingerExample example = new SingerExample();
		// Criteria c = example.createCriteria();
		example.setOrderByClause("settled_time DESC");

		List<Singer> list = singerMapper.selectByExample(example);
		if (list.size() <= 3)
			return Msg.success().add("list", list);

		List<Singer> listResult = new ArrayList<Singer>();

		for (int i = 0; i < 3; i++) {
			listResult.add(list.get(i));
		}
		return Msg.success().add("list", listResult);
	}
}
