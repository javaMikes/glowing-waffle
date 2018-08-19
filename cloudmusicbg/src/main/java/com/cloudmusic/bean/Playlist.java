package com.cloudmusic.bean;

import java.io.Serializable;
import java.util.Date;

public class Playlist implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String listName;

	private Long userId;

	private String userName;

	private String profile;

	private Date createTime;

	private Long playTimes;

	private Long collection;

	private String imgPath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName == null ? null : listName.trim();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile == null ? null : profile.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(Long playTimes) {
		this.playTimes = playTimes;
	}

	public Long getCollection() {
		return collection;
	}

	public void setCollection(Long collection) {
		this.collection = collection;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath == null ? null : imgPath.trim();
	}
}