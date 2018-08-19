package com.cloudmusic.service;

import java.util.List;

import com.cloudmusic.bean.Playlist;
import com.cloudmusic.bean.PlaylistExample;
import com.cloudmusic.bean.TagName;
import com.cloudmusic.util.Msg;

public interface IPlaylistService {
	/**
	 * ����һ���µĸ赥��
	 * 
	 * @return Msg��map�У�����������[��msg��:String:������ʾ��Ϣ]��[��playlist��:Playlist:Ϊ�ղ����playlist������idֵΪ���ݿ������Ľ��]
	 */
	public Msg insertPlaylist(Playlist playlist);

	/**
	 * �½�һ���赥������Ϊ����赥���ñ�ǩ
	 * 
	 * @param playlist
	 *            �赥
	 * @param tagList
	 *            ��ǩ����
	 * @return Msg.error��ʾ�½��赥ʧ��<br/>
	 *         Msg.success��ʾ�ɹ�.["playlist":Playlist:����һ���ո��½��ɹ���Playlist����]
	 */
	public Msg insertPlaylistWithTagName(Playlist playlist, List<TagName> tagList);

	/**
	 * ������ ���� �赥
	 * 
	 * @param playlistId
	 *            �赥ID
	 * @param songIds
	 *            Ҫ����ĸ���ID���鼯��
	 * @return Msg������map������1��[��msg����String:��ʾ��Ϣ ]
	 */
	public Msg insertPlaylistWithSong(Long playlistId, List<Long> songIds);

	/**
	 * ����example�����Ҹ赥
	 * 
	 * @param example
	 *            PlaylistExample�Ķ���
	 * @return Msg��������,["list":List<Playlist> ���赥�ļ���]
	 */
	public Msg selectPlaylistByExample(PlaylistExample example);

	/**
	 * Ѱ�����Ÿ赥
	 * 
	 * @return Msg["list":List<Playlist>���Ÿ赥�ļ���]
	 * 
	 */
	public Msg findHotPlaylist();

	/**
	 * �������Ӹ赥��ɾ��
	 * 
	 * @param id
	 *            �赥ID
	 * @param songIds
	 *            ����ID����
	 * @return Msg
	 */
	public Msg removeSongFromPlaylistById(long id, List<Long> songIds);

	/*
	 * ĳ���赥�Ĳ��Ŵ���+1
	 */
	public Msg addPlaytimeOfPlaylist(long id);

	/*
	 * ĳ���赥���ղش���+1
	 */
	public Msg addCollectionOfPlaylist(long id);

	/*
	 * ĳ���赥���ղش���-1
	 */
	public Msg subCollectionOfPlaylist(long id);

	/**
	 * ���ݸ赥IDɾ���赥
	 * 
	 * @param id
	 *            �赥ID
	 * @return Msg[]
	 */
	public Msg deletePlaylistById(long id);

	/**
	 * �û��ղظ赥
	 * 
	 * @param userId
	 *            �û�ID
	 * @param playlistId
	 *            �赥ID
	 * @return Msg[]
	 */
	public Msg insertPlaylistCollection(long userId, long playlistId);

	/**
	 * ����ĳһ���û����ղصĸ赥����
	 * 
	 * @param userId
	 *            �û�ID
	 * @return Msg["list":List<Playlist>:���ĸ赥����]
	 */
	public Msg selectPlaylistCollectionByUserId(long userId);

	/**
	 * �����û�����������ϲ���ĸ赥
	 * 
	 * @param userId
	 *            �û�ID
	 * @return Msg["list":List<Playlist>:�赥����]
	 */
	public Msg findFavoritePlaylistByUserId(long userId);

	/**
	 * ����һ���赥�б������ȶ����������򣬵�һ��Ϊ��������
	 * 
	 * @param list
	 *            ��Ҫ����ĸ赥����
	 * @return Msg["list":List{Playlist}:�ź���ĸ赥����]
	 */
	public Msg sortPlaylistBasisHot(List<Playlist> list);

	/**
	 * ���ݸ赥ID�ҳ������ɵ����и���
	 * 
	 * @param playlistId
	 *            �赥ID
	 * @return Msg["list":List{Song}:��������]
	 */
	public Msg selectSongByPlaylist(long playlistId);

	/**
	 * ��ѯ���еĸ赥
	 * 
	 * @return Msg[msg:1�����ڸ���������list���ϣ�2�������ڸ赥��������ʾ��Ϣ]
	 */
	public Msg selectPlayList(Integer pn);

	/**
	 * ���ݸ赥id��ȡ�赥��Ϣ
	 * 
	 * @param playlistId
	 * @return Msg["msg" : error:��ʾ��Ϣ��success���赥��Ϣ]
	 */
	public Msg selectPlayListByPlayListId(long playlistId);

	/**
	 * ��ѯ�赥�����ղ��Ŵ�����������
	 * 
	 * @return
	 */
	public Msg selectPlayListDescByPlayTimes();

	/**
	 * ��ѯ�赥�������ղش�����������
	 * 
	 * @return
	 */
	public Msg selectPlayListDescByCollection();

	/**
	 * ���ո��ֵĸ貥�Ŵ������н�������
	 * 
	 * @return Msg["list" : �����ֵĸ������Ŵ������н�������ĸ��ּ���]
	 */
	public Msg selectSingerDescByPlayTimes();

	/**
	 * ���ݶ����ǩ���Ƽ��ʺ���Щ��ǩ�ĸ赥,�赥�ǽ������еģ���һ��������ϱ�ǩ�ĸ赥���Դ�����
	 * 
	 * @param list_tag
	 *            ��ǩ�б�
	 * @return Msg["list":List&lt;Playlist&gt;:�赥����]
	 */
	public Msg findNicePlaylistByTagName(List<TagName> list_tag);

	/**
	 * �����û�id���ҳ����û����������и赥
	 * 
	 * @param userId
	 * @return Msg.success["list":List&lt;Playlist&gt;:�赥����]
	 */
	public Msg selectPlaylistByUserId(long userId);

	/**
	 * ���ݸ赥����ģ���������赥
	 * 
	 * @param name
	 *            �赥����
	 * @return success["list":List{Playlist}:�赥����]
	 */
	public Msg selectPlaylistByLikeName(String name);

	/**
	 * �û�ȡ���ղظ赥
	 * 
	 * @param userId
	 *            �û�id
	 * @param playlistId
	 *            �赥id
	 * @return �ɹ�success<br/>
	 * 		ʧ��error
	 */
	public Msg undoPlaylistCollection(long userId, long playlistId);

	/**
	 * �޸ĸ赥��ͼƬ·��
	 * 
	 * @param playlistId
	 * @param img_path
	 * @return �ɹ�success,map["playlist":Playlist:���޸ĳɹ��ĸ赥����]<br/>
	 *         ʧ��error
	 */
	public Msg updatePlaylistImgPath(long playlistId, String img_path);

	/**
	 * ���¸赥��Ϣ������playlist�����������,��ѡ��ĸ�
	 * 
	 * @param playlist
	 *            ��Ҫ�޸ĵĸ赥����
	 * @return �ɹ�sucess��map["playlist":Playlist:���޸ĳɹ��ĸ赥����]<br/>
	 *         ʧ��error
	 */
	public Msg updatePlaylistByPrimaryKey(Playlist playlist);

	/**
	 * �޸ĸ赥�ı�ǩ�����ԭ���Ѿ����õı�ǩ���ǣ�
	 * 
	 * @param playlistId
	 *            �赥id
	 * @param list_tagName
	 *            ��ǩ��
	 * @return �ɹ�success��ʧ��error
	 */
	public Msg updatePlaylistTagName(long playlistId, List<String> list_tagName);
}
