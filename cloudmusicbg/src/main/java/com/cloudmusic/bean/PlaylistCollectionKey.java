package com.cloudmusic.bean;

import java.io.Serializable;

public class PlaylistCollectionKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

	private Long playlistId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(Long playlistId) {
		this.playlistId = playlistId;
	}
}