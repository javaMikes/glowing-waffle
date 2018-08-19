package com.cloudmusic.bean;

import java.io.Serializable;

public class ReceiveKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long songId;

	private Long playlistId;

	public Long getSongId() {
		return songId;
	}

	public void setSongId(Long songId) {
		this.songId = songId;
	}

	public Long getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(Long playlistId) {
		this.playlistId = playlistId;
	}
}