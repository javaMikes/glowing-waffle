package com.cloudmusic.service;

import com.cloudmusic.util.Msg;

public interface ISingerService {

	/**
	 * ���ݸ���������ȡ������Ϣ
	 * @param singer_name
	 * @return Msg["list" : ������Ϣ]
	 */
	public Msg selectSingerBySingerName(String singer_name);
	
	/**
	 * ���ݸ�������ģ������������
	 * @param name
	 * @return Msg["list":List{Singer}:���ּ���]
	 */
	public Msg selectSingerByLikeName(String name);
	
	/**
	 * ���ݸ���id��ȡ������Ϣ
	 * @param id ����ID
	 * @return Msg["singer":Singer:������Ϣ]
	 */
	public Msg selectSingerById(long id);
	
	/**
	 * ��ȡ�½����֣����µ�3��
	 * @return Msg["list":List{Singer}:���ּ���]
	 */
	public Msg selectNewestSinger();
}
