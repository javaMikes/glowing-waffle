package com.cloudmusic.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingerExample implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public SingerExample() {
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

		public Criteria andSingerNameIsNull() {
			addCriterion("singer_name is null");
			return (Criteria) this;
		}

		public Criteria andSingerNameIsNotNull() {
			addCriterion("singer_name is not null");
			return (Criteria) this;
		}

		public Criteria andSingerNameEqualTo(String value) {
			addCriterion("singer_name =", value, "singerName");
			return (Criteria) this;
		}

		public Criteria andSingerNameNotEqualTo(String value) {
			addCriterion("singer_name <>", value, "singerName");
			return (Criteria) this;
		}

		public Criteria andSingerNameGreaterThan(String value) {
			addCriterion("singer_name >", value, "singerName");
			return (Criteria) this;
		}

		public Criteria andSingerNameGreaterThanOrEqualTo(String value) {
			addCriterion("singer_name >=", value, "singerName");
			return (Criteria) this;
		}

		public Criteria andSingerNameLessThan(String value) {
			addCriterion("singer_name <", value, "singerName");
			return (Criteria) this;
		}

		public Criteria andSingerNameLessThanOrEqualTo(String value) {
			addCriterion("singer_name <=", value, "singerName");
			return (Criteria) this;
		}

		public Criteria andSingerNameLike(String value) {
			addCriterion("singer_name like", value, "singerName");
			return (Criteria) this;
		}

		public Criteria andSingerNameNotLike(String value) {
			addCriterion("singer_name not like", value, "singerName");
			return (Criteria) this;
		}

		public Criteria andSingerNameIn(List<String> values) {
			addCriterion("singer_name in", values, "singerName");
			return (Criteria) this;
		}

		public Criteria andSingerNameNotIn(List<String> values) {
			addCriterion("singer_name not in", values, "singerName");
			return (Criteria) this;
		}

		public Criteria andSingerNameBetween(String value1, String value2) {
			addCriterion("singer_name between", value1, value2, "singerName");
			return (Criteria) this;
		}

		public Criteria andSingerNameNotBetween(String value1, String value2) {
			addCriterion("singer_name not between", value1, value2, "singerName");
			return (Criteria) this;
		}

		public Criteria andSettledTimeIsNull() {
			addCriterion("settled_time is null");
			return (Criteria) this;
		}

		public Criteria andSettledTimeIsNotNull() {
			addCriterion("settled_time is not null");
			return (Criteria) this;
		}

		public Criteria andSettledTimeEqualTo(Date value) {
			addCriterion("settled_time =", value, "settledTime");
			return (Criteria) this;
		}

		public Criteria andSettledTimeNotEqualTo(Date value) {
			addCriterion("settled_time <>", value, "settledTime");
			return (Criteria) this;
		}

		public Criteria andSettledTimeGreaterThan(Date value) {
			addCriterion("settled_time >", value, "settledTime");
			return (Criteria) this;
		}

		public Criteria andSettledTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("settled_time >=", value, "settledTime");
			return (Criteria) this;
		}

		public Criteria andSettledTimeLessThan(Date value) {
			addCriterion("settled_time <", value, "settledTime");
			return (Criteria) this;
		}

		public Criteria andSettledTimeLessThanOrEqualTo(Date value) {
			addCriterion("settled_time <=", value, "settledTime");
			return (Criteria) this;
		}

		public Criteria andSettledTimeIn(List<Date> values) {
			addCriterion("settled_time in", values, "settledTime");
			return (Criteria) this;
		}

		public Criteria andSettledTimeNotIn(List<Date> values) {
			addCriterion("settled_time not in", values, "settledTime");
			return (Criteria) this;
		}

		public Criteria andSettledTimeBetween(Date value1, Date value2) {
			addCriterion("settled_time between", value1, value2, "settledTime");
			return (Criteria) this;
		}

		public Criteria andSettledTimeNotBetween(Date value1, Date value2) {
			addCriterion("settled_time not between", value1, value2, "settledTime");
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