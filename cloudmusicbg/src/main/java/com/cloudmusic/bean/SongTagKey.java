package com.cloudmusic.bean;

import java.io.Serializable;

public class SongTagKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long songId;

	private Long tagId;

	public Long getSongId() {
		return songId;
	}

	public void setSongId(Long songId) {
		this.songId = songId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
}