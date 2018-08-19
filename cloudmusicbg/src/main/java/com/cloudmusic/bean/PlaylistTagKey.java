package com.cloudmusic.bean;

import java.io.Serializable;

public class PlaylistTagKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long playlistId;

	private Long tagId;

	public Long getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(Long playlistId) {
		this.playlistId = playlistId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
}