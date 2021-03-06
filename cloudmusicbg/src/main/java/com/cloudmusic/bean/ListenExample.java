package com.cloudmusic.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListenExample implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public ListenExample() {
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

		public Criteria andUserIdIsNull() {
			addCriterion("user_id is null");
			return (Criteria) this;
		}

		public Criteria andUserIdIsNotNull() {
			addCriterion("user_id is not null");
			return (Criteria) this;
		}

		public Criteria andUserIdEqualTo(Long value) {
			addCriterion("user_id =", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotEqualTo(Long value) {
			addCriterion("user_id <>", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThan(Long value) {
			addCriterion("user_id >", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
			addCriterion("user_id >=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThan(Long value) {
			addCriterion("user_id <", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThanOrEqualTo(Long value) {
			addCriterion("user_id <=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdIn(List<Long> values) {
			addCriterion("user_id in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotIn(List<Long> values) {
			addCriterion("user_id not in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdBetween(Long value1, Long value2) {
			addCriterion("user_id between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotBetween(Long value1, Long value2) {
			addCriterion("user_id not between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andSongIdIsNull() {
			addCriterion("song_id is null");
			return (Criteria) this;
		}

		public Criteria andSongIdIsNotNull() {
			addCriterion("song_id is not null");
			return (Criteria) this;
		}

		public Criteria andSongIdEqualTo(Long value) {
			addCriterion("song_id =", value, "songId");
			return (Criteria) this;
		}

		public Criteria andSongIdNotEqualTo(Long value) {
			addCriterion("song_id <>", value, "songId");
			return (Criteria) this;
		}

		public Criteria andSongIdGreaterThan(Long value) {
			addCriterion("song_id >", value, "songId");
			return (Criteria) this;
		}

		public Criteria andSongIdGreaterThanOrEqualTo(Long value) {
			addCriterion("song_id >=", value, "songId");
			return (Criteria) this;
		}

		public Criteria andSongIdLessThan(Long value) {
			addCriterion("song_id <", value, "songId");
			return (Criteria) this;
		}

		public Criteria andSongIdLessThanOrEqualTo(Long value) {
			addCriterion("song_id <=", value, "songId");
			return (Criteria) this;
		}

		public Criteria andSongIdIn(List<Long> values) {
			addCriterion("song_id in", values, "songId");
			return (Criteria) this;
		}

		public Criteria andSongIdNotIn(List<Long> values) {
			addCriterion("song_id not in", values, "songId");
			return (Criteria) this;
		}

		public Criteria andSongIdBetween(Long value1, Long value2) {
			addCriterion("song_id between", value1, value2, "songId");
			return (Criteria) this;
		}

		public Criteria andSongIdNotBetween(Long value1, Long value2) {
			addCriterion("song_id not between", value1, value2, "songId");
			return (Criteria) this;
		}

		public Criteria andTimesIsNull() {
			addCriterion("times is null");
			return (Criteria) this;
		}

		public Criteria andTimesIsNotNull() {
			addCriterion("times is not null");
			return (Criteria) this;
		}

		public Criteria andTimesEqualTo(Long value) {
			addCriterion("times =", value, "times");
			return (Criteria) this;
		}

		public Criteria andTimesNotEqualTo(Long value) {
			addCriterion("times <>", value, "times");
			return (Criteria) this;
		}

		public Criteria andTimesGreaterThan(Long value) {
			addCriterion("times >", value, "times");
			return (Criteria) this;
		}

		public Criteria andTimesGreaterThanOrEqualTo(Long value) {
			addCriterion("times >=", value, "times");
			return (Criteria) this;
		}

		public Criteria andTimesLessThan(Long value) {
			addCriterion("times <", value, "times");
			return (Criteria) this;
		}

		public Criteria andTimesLessThanOrEqualTo(Long value) {
			addCriterion("times <=", value, "times");
			return (Criteria) this;
		}

		public Criteria andTimesIn(List<Long> values) {
			addCriterion("times in", values, "times");
			return (Criteria) this;
		}

		public Criteria andTimesNotIn(List<Long> values) {
			addCriterion("times not in", values, "times");
			return (Criteria) this;
		}

		public Criteria andTimesBetween(Long value1, Long value2) {
			addCriterion("times between", value1, value2, "times");
			return (Criteria) this;
		}

		public Criteria andTimesNotBetween(Long value1, Long value2) {
			addCriterion("times not between", value1, value2, "times");
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