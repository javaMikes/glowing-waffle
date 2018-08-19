package com.cloudmusic.bean;

import java.io.Serializable;

public class Mv extends MvKey implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

    private String name;

    private String path;

    private String mvTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getMvTime() {
        return mvTime;
    }

    public void setMvTime(String mvTime) {
        this.mvTime = mvTime == null ? null : mvTime.trim();
    }
}