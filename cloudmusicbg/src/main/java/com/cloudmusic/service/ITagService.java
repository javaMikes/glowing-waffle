package com.cloudmusic.service;

import java.util.List;

import com.cloudmusic.util.Msg;

public interface ITagService {

	/**
	 * ���ҳ��û���ϲ���ı�ǩ����˳������������һ����ǩ��������ı�ǩ�����һ�����������ı�ǩ<br/>
	 * ʵ��˼�룺�����û������ĸ������ղصĸ������ղصĸ赥��3�߽������ϡ�����ǩ������������Ƶ�ʱȽ�
	 * 
	 * @param userId
	 *            �û�ID
	 * @return Msg["list":List<TagName>:��ǩ����]
	 */
	public Msg findFavoriteTagByUserId(long userId);

	/**
	 * ���ݸ���id���������ı�ǩid
	 * 
	 * @param songId
	 *            ����ID
	 * @return
	 */
	public List<Long> findTagNameBySongId(long songId);

	/**
	 * ���ݸ赥id���������ı�ǩid
	 * 
	 * @param playlistId
	 *            ����ID
	 * @return
	 */
	public List<Long> findTagNameByPlaylistId(long playlistId);

	/**
	 * ���ݱ�ǩid�������ñ�ǩ
	 * 
	 * @param id
	 * @return Msg["tagName":TagName:��ǩ]
	 */
	public Msg selectTagNameByTagId(long id);

	/**
	 * ���ҳ����б�ǩ
	 * 
	 * @return Msg["list":list{TagName}:��ǩ����]
	 */
	public Msg selectAllTagName();

	/**
	 * ���ݱ�ǩ���Ͳ��ҳ�������͵����б�ǩ
	 * 
	 * @param type
	 *            ��ǩ��������
	 * @return Msg.errorʱ["msg":String:������Ϣ��ʾ] ; <br/>
	 *         Msg.successʱ��ʾ�ɹ�["list":List{TagName}:�����͵ı�ǩ����]
	 */
	public Msg selectTagNameType(String type);

	/**
	 * ���ݱ�ǩ���������������ñ�ǩ�����и赥
	 * 
	 * @param name
	 *            ��ǩ��
	 * @return Msg["list":List{Playlist}:�赥���ϣ��������޶��᷵��]
	 */
	public Msg selectPlaylistByTagName(String name);

	/**
	 * ���ݱ�ǩ���б���ҳ������������Щ��ǩ�����и赥���赥ȡ��Щ��ǩ�Ľ�����
	 * 
	 * @param list_tagName
	 *            ��ǩ���ַ����б�
	 * @param pn
	 *            ҳ��
	 * @return Msg["list":List&lt;Playlist&gt;:��������]
	 */
	public Msg selectPlaylistPageInfoByTagNameList(List<String> list_tagName, Integer pn);

	/**
	 * ���ݱ�ǩ������ҳ�����������ñ�ǩ�����и赥
	 *
	 * @param name
	 *            ��ǩ��
	 * @return Msg["list":List{Playlist}:��ҳ�ĸ赥���ϣ��������޶��᷵��]
	 */
	public Msg selectPlaylistPageInfoByTagName(String name, Integer pn);

	/**
	 * ���ݱ�ǩ���������������ñ�ǩ�����и���
	 * 
	 * @return Msg["list":List{Song}:�������ϣ��������޶��᷵��һ��list]
	 */
	public Msg selectSongByTagName(String name);

	/**
	 * ���ݱ�ǩ����ȡ��ǩ��Ϣ
	 * 
	 * @return Msg["tagName":��ǩ��Ϣ]
	 */
	public Msg selectByTagName(String name);
}
