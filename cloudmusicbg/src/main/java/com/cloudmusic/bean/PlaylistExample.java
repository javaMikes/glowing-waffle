package com.cloudmusic.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PlaylistExample implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public PlaylistExample() {
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

		protected void addCriterionForJDBCDate(String condition, Date value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value.getTime()), property);
		}

		protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property + " cannot be null or empty");
			}
			List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
			Iterator<Date> iter = values.iterator();
			while (iter.hasNext()) {
				dateList.add(new java.sql.Date(iter.next().getTime()));
			}
			addCriterion(condition, dateList, property);
		}

		protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

		public Criteria andListNameIsNull() {
			addCriterion("list_name is null");
			return (Criteria) this;
		}

		public Criteria andListNameIsNotNull() {
			addCriterion("list_name is not null");
			return (Criteria) this;
		}

		public Criteria andListNameEqualTo(String value) {
			addCriterion("list_name =", value, "listName");
			return (Criteria) this;
		}

		public Criteria andListNameNotEqualTo(String value) {
			addCriterion("list_name <>", value, "listName");
			return (Criteria) this;
		}

		public Criteria andListNameGreaterThan(String value) {
			addCriterion("list_name >", value, "listName");
			return (Criteria) this;
		}

		public Criteria andListNameGreaterThanOrEqualTo(String value) {
			addCriterion("list_name >=", value, "listName");
			return (Criteria) this;
		}

		public Criteria andListNameLessThan(String value) {
			addCriterion("list_name <", value, "listName");
			return (Criteria) this;
		}

		public Criteria andListNameLessThanOrEqualTo(String value) {
			addCriterion("list_name <=", value, "listName");
			return (Criteria) this;
		}

		public Criteria andListNameLike(String value) {
			addCriterion("list_name like", value, "listName");
			return (Criteria) this;
		}

		public Criteria andListNameNotLike(String value) {
			addCriterion("list_name not like", value, "listName");
			return (Criteria) this;
		}

		public Criteria andListNameIn(List<String> values) {
			addCriterion("list_name in", values, "listName");
			return (Criteria) this;
		}

		public Criteria andListNameNotIn(List<String> values) {
			addCriterion("list_name not in", values, "listName");
			return (Criteria) this;
		}

		public Criteria andListNameBetween(String value1, String value2) {
			addCriterion("list_name between", value1, value2, "listName");
			return (Criteria) this;
		}

		public Criteria andListNameNotBetween(String value1, String value2) {
			addCriterion("list_name not between", value1, value2, "listName");
			return (Criteria) this;
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

		public Criteria andProfileIsNull() {
			addCriterion("profile is null");
			return (Criteria) this;
		}

		public Criteria andProfileIsNotNull() {
			addCriterion("profile is not null");
			return (Criteria) this;
		}

		public Criteria andProfileEqualTo(String value) {
			addCriterion("profile =", value, "profile");
			return (Criteria) this;
		}

		public Criteria andProfileNotEqualTo(String value) {
			addCriterion("profile <>", value, "profile");
			return (Criteria) this;
		}

		public Criteria andProfileGreaterThan(String value) {
			addCriterion("profile >", value, "profile");
			return (Criteria) this;
		}

		public Criteria andProfileGreaterThanOrEqualTo(String value) {
			addCriterion("profile >=", value, "profile");
			return (Criteria) this;
		}

		public Criteria andProfileLessThan(String value) {
			addCriterion("profile <", value, "profile");
			return (Criteria) this;
		}

		public Criteria andProfileLessThanOrEqualTo(String value) {
			addCriterion("profile <=", value, "profile");
			return (Criteria) this;
		}

		public Criteria andProfileLike(String value) {
			addCriterion("profile like", value, "profile");
			return (Criteria) this;
		}

		public Criteria andProfileNotLike(String value) {
			addCriterion("profile not like", value, "profile");
			return (Criteria) this;
		}

		public Criteria andProfileIn(List<String> values) {
			addCriterion("profile in", values, "profile");
			return (Criteria) this;
		}

		public Criteria andProfileNotIn(List<String> values) {
			addCriterion("profile not in", values, "profile");
			return (Criteria) this;
		}

		public Criteria andProfileBetween(String value1, String value2) {
			addCriterion("profile between", value1, value2, "profile");
			return (Criteria) this;
		}

		public Criteria andProfileNotBetween(String value1, String value2) {
			addCriterion("profile not between", value1, value2, "profile");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNull() {
			addCriterion("create_time is null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNotNull() {
			addCriterion("create_time is not null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeEqualTo(Date value) {
			addCriterionForJDBCDate("create_time =", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("create_time <>", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("create_time >", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("create_time >=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThan(Date value) {
			addCriterionForJDBCDate("create_time <", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("create_time <=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIn(List<Date> values) {
			addCriterionForJDBCDate("create_time in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("create_time not in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("create_time between", value1, value2, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("create_time not between", value1, value2, "createTime");
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