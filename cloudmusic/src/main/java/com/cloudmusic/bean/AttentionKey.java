package com.cloudmusic.bean;

import java.io.Serializable;

public class AttentionKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

	private Long userIdEd;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdEd() {
		return userIdEd;
	}

	public void setUserIdEd(Long userIdEd) {
		this.userIdEd = userIdEd;
	}
}