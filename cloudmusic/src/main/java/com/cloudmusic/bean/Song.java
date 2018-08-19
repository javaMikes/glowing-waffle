package com.cloudmusic.bean;

import java.io.Serializable;

public class Song implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String songName;

	private String singer;

	private String lyrics;

	private String composition;

	private String musicTime;

	private Long collection;

	private Long playTimes;

	private String path;

	private String introduction;

	private Long integral;

	private String imgPath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName == null ? null : songName.trim();
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer == null ? null : singer.trim();
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics == null ? null : lyrics.trim();
	}

	public String getComposition() {
		return composition;
	}

	public void setComposition(String composition) {
		this.composition = composition == null ? null : composition.trim();
	}

	public String getMusicTime() {
		return musicTime;
	}

	public void setMusicTime(String musicTime) {
		this.musicTime = musicTime == null ? null : musicTime.trim();
	}

	public Long getCollection() {
		return collection;
	}

	public void setCollection(Long collection) {
		this.collection = collection;
	}

	public Long getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(Long playTimes) {
		this.playTimes = playTimes;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? null : path.trim();
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction == null ? null : introduction.trim();
	}

	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath == null ? null : imgPath.trim();
	}
}