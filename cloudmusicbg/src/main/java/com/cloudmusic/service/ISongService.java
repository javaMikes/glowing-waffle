package com.cloudmusic.service;

import java.util.List;

import com.cloudmusic.bean.Song;
import com.cloudmusic.bean.TagName;
// import com.cloudmusic.bean.Song;
import com.cloudmusic.util.Msg;

public interface ISongService {

	/**
	 * �ϴ�����
	 *
	 * @param song
	 * @return
	 */
	public Msg uploadSong(Song song);

	/**
	 * ͨ���û�id��ѯ�û��������ĸ���
	 *
	 * @param userId
	 * @return Msg["list" : List<Song>�������ĸ����б�]
	 */
	public Msg selectSongListenedByUserId(long userId);

	/**
	 * ͨ���û�id��ѯ�û��ղع��ĸ���
	 *
	 * @param userId
	 * @return Msg["list":List<Song>:�û������ղؼ���]
	 */
	public Msg selectSongCollectedByUserId(long userId);

	/**
	 * ĳ�׸������Ŵ�����1
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "��ʾ��Ϣ"]
	 */
	public Msg addOnePlaytimeOfSong(long userId, long songId);

	/**
	 * ĳ�û��ղ�ĳ�׸���
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "��ʾ��Ϣ"]
	 */
	public Msg addCollectionOfSong(long userId, long songId);

	/**
	 * ĳ�û�ȡ���ղ�ĳ�׸���
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg": "��ʾ��Ϣ"]
	 */
	public Msg subCollectionOfSong(long userId, long songId);

	/**
	 * ���ݸ���id�����ø��������ŵĴ���
	 *
	 * @param songId
	 * @return Msg(������������"msg"����ʾ��Ϣ�� "sum" �� ���Ŵ���)
	 */
	public Msg selectTimesPlayOfSong(long songId);

	/**
	 * �����û�id������id���ж�ĳ���û��Ƿ�������ĳ������
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg" : true(������)��false(δ������)]
	 */
	public Msg isListenedTheSong(long userId, long songId);

	/**
	 * �����û�id������id���ж�ĳ���û��Ƿ��ղ�ĳ�׸���
	 *
	 * @param userId
	 * @param songId
	 * @return Msg["msg" : true(���ղ�)��false(δ�ղ�)]
	 */
	public Msg isCollectTheSong(long userId, long songId);

	/**
	 * �����û�id���������ĸ������������ٽ�������
	 *
	 * @param userId
	 * @return
	 */
	public Msg rankSongListenTimes(long userId);

	/**
	 * ���ݸ�����������
	 *
	 * @param singer
	 * @return Msg����������[msg : "��ʾ��Ϣ"]; ["list" : List<Song>�����б�]
	 */
	public Msg selectSongBySinger(String singer);

	/**
	 * ���ݸ���ģ����������
	 *
	 * @param songName
	 * @return Msg����������[msg : "��ʾ��Ϣ"]; ["list" : List<Song>�����б�]
	 */
	public Msg selectSongBySongName(String songName);

	/**
	 * ��ȡ���Ÿ���
	 *
	 * @return Msg[list : List<Song>���Ÿ�������]
	 */
	public Msg getHotSongs();

	/**
	 * ��ȡ���Ÿ���
	 *
	 * @return Msg["list" : List<String> ���Ÿ��ּ���]
	 */
	public Msg getHotSingers();

	/**
	 * ���ݸ���ID������һ�׸�
	 *
	 * @param id
	 * @return �и����Msg.success map["song":Song:��������]<br/>
	 *         û�и����Msg.error
	 */
	public Msg selectSongBySongId(Long id);

	/**
	 * �����û�id�����ҳ�������ϲ���ĸ���������˳��������������������һ�����sizeΪ24��list
	 *
	 * @param userId
	 *            �û�id
	 * @return Msg.success map["list":List{Song}:����Ϊ0��24֮���list]
	 */
	public Msg findFavoriteSongByUserId(Long userId);

	/**
	 * ��ѯ�赥�����ղ��Ŵ�����������
	 * 
	 * @return
	 */
	public Msg selectSongDescByPlayTimes();

	/**
	 * ��ѯ�赥�������ղش�����������
	 * 
	 * @return
	 */
	public Msg selectSongDescByCollection();
	
	/**
	 * ���ݸ�������ģ������������
	 * @param name
	 * @return success["list":List{Song}:��������]
	 */
	public Msg selectSongByLikeName(String name);
	
	/**
	 * ���ݶ����ǩ���Ƽ��ʺ���Щ��ǩ�ĸ���
	 * @param list_tag ��ǩ�б�
	 * @return Msg["list":List&lt;Song&gt;:��������]
	 */
	public Msg findNiceSongByTagName(List<TagName> list_tag);
	
	/**
	 * ���ݸ������֣��ó��ø��ֵ����и����������ȶȽ�������
	 * @param singerName ��������
	 * @return Msg["list":List{Song}:��������]
	 */
	public Msg selectSingerSongAndHotSort(String singerName);
	
	/**
	 * Ϊ�������ñ�ǩ���Ḳ��ԭ�����úõı�ǩ��
	 * @param songId ����id
	 * @param list_tag ��ǩ������
	 * @return Msg.success or Msg.error
	 */
	public Msg updateSongTagByTagNameList(long songId, List<String> list_tag);
}
