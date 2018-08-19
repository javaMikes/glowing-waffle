package com.cloudmusic.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SongExample implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public SongExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andIdIsNull() {
			addCriterion("id is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(Long value) {
			addCriterion("id =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Long value) {
			addCriterion("id <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Long value) {
			addCriterion("id >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Long value) {
			addCriterion("id >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Long value) {
			addCriterion("id <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Long value) {
			addCriterion("id <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Long> values) {
			addCriterion("id in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Long> values) {
			addCriterion("id not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Long value1, Long value2) {
			addCriterion("id between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Long value1, Long value2) {
			addCriterion("id not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andSongNameIsNull() {
			addCriterion("song_name is null");
			return (Criteria) this;
		}

		public Criteria andSongNameIsNotNull() {
			addCriterion("song_name is not null");
			return (Criteria) this;
		}

		public Criteria andSongNameEqualTo(String value) {
			addCriterion("song_name =", value, "songName");
			return (Criteria) this;
		}

		public Criteria andSongNameNotEqualTo(String value) {
			addCriterion("song_name <>", value, "songName");
			return (Criteria) this;
		}

		public Criteria andSongNameGreaterThan(String value) {
			addCriterion("song_name >", value, "songName");
			return (Criteria) this;
		}

		public Criteria andSongNameGreaterThanOrEqualTo(String value) {
			addCriterion("song_name >=", value, "songName");
			return (Criteria) this;
		}

		public Criteria andSongNameLessThan(String value) {
			addCriterion("song_name <", value, "songName");
			return (Criteria) this;
		}

		public Criteria andSongNameLessThanOrEqualTo(String value) {
			addCriterion("song_name <=", value, "songName");
			return (Criteria) this;
		}

		public Criteria andSongNameLike(String value) {
			addCriterion("song_name like", value, "songName");
			return (Criteria) this;
		}

		public Criteria andSongNameNotLike(String value) {
			addCriterion("song_name not like", value, "songName");
			return (Criteria) this;
		}

		public Criteria andSongNameIn(List<String> values) {
			addCriterion("song_name in", values, "songName");
			return (Criteria) this;
		}

		public Criteria andSongNameNotIn(List<String> values) {
			addCriterion("song_name not in", values, "songName");
			return (Criteria) this;
		}

		public Criteria andSongNameBetween(String value1, String value2) {
			addCriterion("song_name between", value1, value2, "songName");
			return (Criteria) this;
		}

		public Criteria andSongNameNotBetween(String value1, String value2) {
			addCriterion("song_name not between", value1, value2, "songName");
			return (Criteria) this;
		}

		public Criteria andSingerIsNull() {
			addCriterion("singer is null");
			return (Criteria) this;
		}

		public Criteria andSingerIsNotNull() {
			addCriterion("singer is not null");
			return (Criteria) this;
		}

		public Criteria andSingerEqualTo(String value) {
			addCriterion("singer =", value, "singer");
			return (Criteria) this;
		}

		public Criteria andSingerNotEqualTo(String value) {
			addCriterion("singer <>", value, "singer");
			return (Criteria) this;
		}

		public Criteria andSingerGreaterThan(String value) {
			addCriterion("singer >", value, "singer");
			return (Criteria) this;
		}

		public Criteria andSingerGreaterThanOrEqualTo(String value) {
			addCriterion("singer >=", value, "singer");
			return (Criteria) this;
		}

		public Criteria andSingerLessThan(String value) {
			addCriterion("singer <", value, "singer");
			return (Criteria) this;
		}

		public Criteria andSingerLessThanOrEqualTo(String value) {
			addCriterion("singer <=", value, "singer");
			return (Criteria) this;
		}

		public Criteria andSingerLike(String value) {
			addCriterion("singer like", value, "singer");
			return (Criteria) this;
		}

		public Criteria andSingerNotLike(String value) {
			addCriterion("singer not like", value, "singer");
			return (Criteria) this;
		}

		public Criteria andSingerIn(List<String> values) {
			addCriterion("singer in", values, "singer");
			return (Criteria) this;
		}

		public Criteria andSingerNotIn(List<String> values) {
			addCriterion("singer not in", values, "singer");
			return (Criteria) this;
		}

		public Criteria andSingerBetween(String value1, String value2) {
			addCriterion("singer between", value1, value2, "singer");
			return (Criteria) this;
		}

		public Criteria andSingerNotBetween(String value1, String value2) {
			addCriterion("singer not between", value1, value2, "singer");
			return (Criteria) this;
		}

		public Criteria andLyricsIsNull() {
			addCriterion("lyrics is null");
			return (Criteria) this;
		}

		public Criteria andLyricsIsNotNull() {
			addCriterion("lyrics is not null");
			return (Criteria) this;
		}

		public Criteria andLyricsEqualTo(String value) {
			addCriterion("lyrics =", value, "lyrics");
			return (Criteria) this;
		}

		public Criteria andLyricsNotEqualTo(String value) {
			addCriterion("lyrics <>", value, "lyrics");
			return (Criteria) this;
		}

		public Criteria andLyricsGreaterThan(String value) {
			addCriterion("lyrics >", value, "lyrics");
			return (Criteria) this;
		}

		public Criteria andLyricsGreaterThanOrEqualTo(String value) {
			addCriterion("lyrics >=", value, "lyrics");
			return (Criteria) this;
		}

		public Criteria andLyricsLessThan(String value) {
			addCriterion("lyrics <", value, "lyrics");
			return (Criteria) this;
		}

		public Criteria andLyricsLessThanOrEqualTo(String value) {
			addCriterion("lyrics <=", value, "lyrics");
			return (Criteria) this;
		}

		public Criteria andLyricsLike(String value) {
			addCriterion("lyrics like", value, "lyrics");
			return (Criteria) this;
		}

		public Criteria andLyricsNotLike(String value) {
			addCriterion("lyrics not like", value, "lyrics");
			return (Criteria) this;
		}

		public Criteria andLyricsIn(List<String> values) {
			addCriterion("lyrics in", values, "lyrics");
			return (Criteria) this;
		}

		public Criteria andLyricsNotIn(List<String> values) {
			addCriterion("lyrics not in", values, "lyrics");
			return (Criteria) this;
		}

		public Criteria andLyricsBetween(String value1, String value2) {
			addCriterion("lyrics between", value1, value2, "lyrics");
			return (Criteria) this;
		}

		public Criteria andLyricsNotBetween(String value1, String value2) {
			addCriterion("lyrics not between", value1, value2, "lyrics");
			return (Criteria) this;
		}

		public Criteria andCompositionIsNull() {
			addCriterion("composition is null");
			return (Criteria) this;
		}

		public Criteria andCompositionIsNotNull() {
			addCriterion("composition is not null");
			return (Criteria) this;
		}

		public Criteria andCompositionEqualTo(String value) {
			addCriterion("composition =", value, "composition");
			return (Criteria) this;
		}

		public Criteria andCompositionNotEqualTo(String value) {
			addCriterion("composition <>", value, "composition");
			return (Criteria) this;
		}

		public Criteria andCompositionGreaterThan(String value) {
			addCriterion("composition >", value, "composition");
			return (Criteria) this;
		}

		public Criteria andCompositionGreaterThanOrEqualTo(String value) {
			addCriterion("composition >=", value, "composition");
			return (Criteria) this;
		}

		public Criteria andCompositionLessThan(String value) {
			addCriterion("composition <", value, "composition");
			return (Criteria) this;
		}

		public Criteria andCompositionLessThanOrEqualTo(String value) {
			addCriterion("composition <=", value, "composition");
			return (Criteria) this;
		}

		public Criteria andCompositionLike(String value) {
			addCriterion("composition like", value, "composition");
			return (Criteria) this;
		}

		public Criteria andCompositionNotLike(String value) {
			addCriterion("composition not like", value, "composition");
			return (Criteria) this;
		}

		public Criteria andCompositionIn(List<String> values) {
			addCriterion("composition in", values, "composition");
			return (Criteria) this;
		}

		public Criteria andCompositionNotIn(List<String> values) {
			addCriterion("composition not in", values, "composition");
			return (Criteria) this;
		}

		public Criteria andCompositionBetween(String value1, String value2) {
			addCriterion("composition between", value1, value2, "composition");
			return (Criteria) this;
		}

		public Criteria andCompositionNotBetween(String value1, String value2) {
			addCriterion("composition not between", value1, value2, "composition");
			return (Criteria) this;
		}

		public Criteria andMusicTimeIsNull() {
			addCriterion("music_time is null");
			return (Criteria) this;
		}

		public Criteria andMusicTimeIsNotNull() {
			addCriterion("music_time is not null");
			return (Criteria) this;
		}

		public Criteria andMusicTimeEqualTo(String value) {
			addCriterion("music_time =", value, "musicTime");
			return (Criteria) this;
		}

		public Criteria andMusicTimeNotEqualTo(String value) {
			addCriterion("music_time <>", value, "musicTime");
			return (Criteria) this;
		}

		public Criteria andMusicTimeGreaterThan(String value) {
			addCriterion("music_time >", value, "musicTime");
			return (Criteria) this;
		}

		public Criteria andMusicTimeGreaterThanOrEqualTo(String value) {
			addCriterion("music_time >=", value, "musicTime");
			return (Criteria) this;
		}

		public Criteria andMusicTimeLessThan(String value) {
			addCriterion("music_time <", value, "musicTime");
			return (Criteria) this;
		}

		public Criteria andMusicTimeLessThanOrEqualTo(String value) {
			addCriterion("music_time <=", value, "musicTime");
			return (Criteria) this;
		}

		public Criteria andMusicTimeLike(String value) {
			addCriterion("music_time like", value, "musicTime");
			return (Criteria) this;
		}

		public Criteria andMusicTimeNotLike(String value) {
			addCriterion("music_time not like", value, "musicTime");
			return (Criteria) this;
		}

		public Criteria andMusicTimeIn(List<String> values) {
			addCriterion("music_time in", values, "musicTime");
			return (Criteria) this;
		}

		public Criteria andMusicTimeNotIn(List<String> values) {
			addCriterion("music_time not in", values, "musicTime");
			return (Criteria) this;
		}

		public Criteria andMusicTimeBetween(String value1, String value2) {
			addCriterion("music_time between", value1, value2, "musicTime");
			return (Criteria) this;
		}

		public Criteria andMusicTimeNotBetween(String value1, String value2) {
			addCriterion("music_time not between", value1, value2, "musicTime");
			return (Criteria) this;
		}

		public Criteria andCollectionIsNull() {
			addCriterion("collection is null");
			return (Criteria) this;
		}

		public Criteria andCollectionIsNotNull() {
			addCriterion("collection is not null");
			return (Criteria) this;
		}

		public Criteria andCollectionEqualTo(Long value) {
			addCriterion("collection =", value, "collection");
			return (Criteria) this;
		}

		public Criteria andCollectionNotEqualTo(Long value) {
			addCriterion("collection <>", value, "collection");
			return (Criteria) this;
		}

		public Criteria andCollectionGreaterThan(Long value) {
			addCriterion("collection >", value, "collection");
			return (Criteria) this;
		}

		public Criteria andCollectionGreaterThanOrEqualTo(Long value) {
			addCriterion("collection >=", value, "collection");
			return (Criteria) this;
		}

		public Criteria andCollectionLessThan(Long value) {
			addCriterion("collection <", value, "collection");
			return (Criteria) this;
		}

		public Criteria andCollectionLessThanOrEqualTo(Long value) {
			addCriterion("collection <=", value, "collection");
			return (Criteria) this;
		}

		public Criteria andCollectionIn(List<Long> values) {
			addCriterion("collection in", values, "collection");
			return (Criteria) this;
		}

		public Criteria andCollectionNotIn(List<Long> values) {
			addCriterion("collection not in", values, "collection");
			return (Criteria) this;
		}

		public Criteria andCollectionBetween(Long value1, Long value2) {
			addCriterion("collection between", value1, value2, "collection");
			return (Criteria) this;
		}

		public Criteria andCollectionNotBetween(Long value1, Long value2) {
			addCriterion("collection not between", value1, value2, "collection");
			return (Criteria) this;
		}

		public Criteria andPlayTimesIsNull() {
			addCriterion("play_times is null");
			return (Criteria) this;
		}

		public Criteria andPlayTimesIsNotNull() {
			addCriterion("play_times is not null");
			return (Criteria) this;
		}

		public Criteria andPlayTimesEqualTo(Long value) {
			addCriterion("play_times =", value, "playTimes");
			return (Criteria) this;
		}

		public Criteria andPlayTimesNotEqualTo(Long value) {
			addCriterion("play_times <>", value, "playTimes");
			return (Criteria) this;
		}

		public Criteria andPlayTimesGreaterThan(Long value) {
			addCriterion("play_times >", value, "playTimes");
			return (Criteria) this;
		}

		public Criteria andPlayTimesGreaterThanOrEqualTo(Long value) {
			addCriterion("play_times >=", value, "playTimes");
			return (Criteria) this;
		}

		public Criteria andPlayTimesLessThan(Long value) {
			addCriterion("play_times <", value, "playTimes");
			return (Criteria) this;
		}

		public Criteria andPlayTimesLessThanOrEqualTo(Long value) {
			addCriterion("play_times <=", value, "playTimes");
			return (Criteria) this;
		}

		public Criteria andPlayTimesIn(List<Long> values) {
			addCriterion("play_times in", values, "playTimes");
			return (Criteria) this;
		}

		public Criteria andPlayTimesNotIn(List<Long> values) {
			addCriterion("play_times not in", values, "playTimes");
			return (Criteria) this;
		}

		public Criteria andPlayTimesBetween(Long value1, Long value2) {
			addCriterion("play_times between", value1, value2, "playTimes");
			return (Criteria) this;
		}

		public Criteria andPlayTimesNotBetween(Long value1, Long value2) {
			addCriterion("play_times not between", value1, value2, "playTimes");
			return (Criteria) this;
		}

		public Criteria andPathIsNull() {
			addCriterion("path is null");
			return (Criteria) this;
		}

		public Criteria andPathIsNotNull() {
			addCriterion("path is not null");
			return (Criteria) this;
		}

		public Criteria andPathEqualTo(String value) {
			addCriterion("path =", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathNotEqualTo(String value) {
			addCriterion("path <>", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathGreaterThan(String value) {
			addCriterion("path >", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathGreaterThanOrEqualTo(String value) {
			addCriterion("path >=", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathLessThan(String value) {
			addCriterion("path <", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathLessThanOrEqualTo(String value) {
			addCriterion("path <=", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathLike(String value) {
			addCriterion("path like", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathNotLike(String value) {
			addCriterion("path not like", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathIn(List<String> values) {
			addCriterion("path in", values, "path");
			return (Criteria) this;
		}

		public Criteria andPathNotIn(List<String> values) {
			addCriterion("path not in", values, "path");
			return (Criteria) this;
		}

		public Criteria andPathBetween(String value1, String value2) {
			addCriterion("path between", value1, value2, "path");
			return (Criteria) this;
		}

		public Criteria andPathNotBetween(String value1, String value2) {
			addCriterion("path not between", value1, value2, "path");
			return (Criteria) this;
		}

		public Criteria andIntroductionIsNull() {
			addCriterion("introduction is null");
			return (Criteria) this;
		}

		public Criteria andIntroductionIsNotNull() {
			addCriterion("introduction is not null");
			return (Criteria) this;
		}

		public Criteria andIntroductionEqualTo(String value) {
			addCriterion("introduction =", value, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntroductionNotEqualTo(String value) {
			addCriterion("introduction <>", value, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntroductionGreaterThan(String value) {
			addCriterion("introduction >", value, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntroductionGreaterThanOrEqualTo(String value) {
			addCriterion("introduction >=", value, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntroductionLessThan(String value) {
			addCriterion("introduction <", value, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntroductionLessThanOrEqualTo(String value) {
			addCriterion("introduction <=", value, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntroductionLike(String value) {
			addCriterion("introduction like", value, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntroductionNotLike(String value) {
			addCriterion("introduction not like", value, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntroductionIn(List<String> values) {
			addCriterion("introduction in", values, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntroductionNotIn(List<String> values) {
			addCriterion("introduction not in", values, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntroductionBetween(String value1, String value2) {
			addCriterion("introduction between", value1, value2, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntroductionNotBetween(String value1, String value2) {
			addCriterion("introduction not between", value1, value2, "introduction");
			return (Criteria) this;
		}

		public Criteria andIntegralIsNull() {
			addCriterion("integral is null");
			return (Criteria) this;
		}

		public Criteria andIntegralIsNotNull() {
			addCriterion("integral is not null");
			return (Criteria) this;
		}

		public Criteria andIntegralEqualTo(Long value) {
			addCriterion("integral =", value, "integral");
			return (Criteria) this;
		}

		public Criteria andIntegralNotEqualTo(Long value) {
			addCriterion("integral <>", value, "integral");
			return (Criteria) this;
		}

		public Criteria andIntegralGreaterThan(Long value) {
			addCriterion("integral >", value, "integral");
			return (Criteria) this;
		}

		public Criteria andIntegralGreaterThanOrEqualTo(Long value) {
			addCriterion("integral >=", value, "integral");
			return (Criteria) this;
		}

		public Criteria andIntegralLessThan(Long value) {
			addCriterion("integral <", value, "integral");
			return (Criteria) this;
		}

		public Criteria andIntegralLessThanOrEqualTo(Long value) {
			addCriterion("integral <=", value, "integral");
			return (Criteria) this;
		}

		public Criteria andIntegralIn(List<Long> values) {
			addCriterion("integral in", values, "integral");
			return (Criteria) this;
		}

		public Criteria andIntegralNotIn(List<Long> values) {
			addCriterion("integral not in", values, "integral");
			return (Criteria) this;
		}

		public Criteria andIntegralBetween(Long value1, Long value2) {
			addCriterion("integral between", value1, value2, "integral");
			return (Criteria) this;
		}

		public Criteria andIntegralNotBetween(Long value1, Long value2) {
			addCriterion("integral not between", value1, value2, "integral");
			return (Criteria) this;
		}

		public Criteria andImgPathIsNull() {
			addCriterion("img_path is null");
			return (Criteria) this;
		}

		public Criteria andImgPathIsNotNull() {
			addCriterion("img_path is not null");
			return (Criteria) this;
		}

		public Criteria andImgPathEqualTo(String value) {
			addCriterion("img_path =", value, "imgPath");
			return (Criteria) this;
		}

		public Criteria andImgPathNotEqualTo(String value) {
			addCriterion("img_path <>", value, "imgPath");
			return (Criteria) this;
		}

		public Criteria andImgPathGreaterThan(String value) {
			addCriterion("img_path >", value, "imgPath");
			return (Criteria) this;
		}

		public Criteria andImgPathGreaterThanOrEqualTo(String value) {
			addCriterion("img_path >=", value, "imgPath");
			return (Criteria) this;
		}

		public Criteria andImgPathLessThan(String value) {
			addCriterion("img_path <", value, "imgPath");
			return (Criteria) this;
		}

		public Criteria andImgPathLessThanOrEqualTo(String value) {
			addCriterion("img_path <=", value, "imgPath");
			return (Criteria) this;
		}

		public Criteria andImgPathLike(String value) {
			addCriterion("img_path like", value, "imgPath");
			return (Criteria) this;
		}

		public Criteria andImgPathNotLike(String value) {
			addCriterion("img_path not like", value, "imgPath");
			return (Criteria) this;
		}

		public Criteria andImgPathIn(List<String> values) {
			addCriterion("img_path in", values, "imgPath");
			return (Criteria) this;
		}

		public Criteria andImgPathNotIn(List<String> values) {
			addCriterion("img_path not in", values, "imgPath");
			return (Criteria) this;
		}

		public Criteria andImgPathBetween(String value1, String value2) {
			addCriterion("img_path between", value1, value2, "imgPath");
			return (Criteria) this;
		}

		public Criteria andImgPathNotBetween(String value1, String value2) {
			addCriterion("img_path not between", value1, value2, "imgPath");
			return (Criteria) this;
		}
	}

	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}
	}

	public static class Criterion {
		private String condition;

		private Object value;

		private Object secondValue;

		private boolean noValue;

		private boolean singleValue;

		private boolean betweenValue;

		private boolean listValue;

		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}
}