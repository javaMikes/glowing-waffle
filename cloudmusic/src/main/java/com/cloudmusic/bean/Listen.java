package com.cloudmusic.bean;

import java.io.Serializable;

public class Listen extends ListenKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long times;

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}
}