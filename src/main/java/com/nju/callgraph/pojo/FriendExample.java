package com.nju.callgraph.pojo;

import java.util.ArrayList;
import java.util.List;

public class FriendExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    protected int startRow;

    protected int pageSize;

    public FriendExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameIsNull() {
            addCriterion("followingloginname is null");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameIsNotNull() {
            addCriterion("followingloginname is not null");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameEqualTo(String value) {
            addCriterion("followingloginname =", value, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameNotEqualTo(String value) {
            addCriterion("followingloginname <>", value, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameGreaterThan(String value) {
            addCriterion("followingloginname >", value, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameGreaterThanOrEqualTo(String value) {
            addCriterion("followingloginname >=", value, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameLessThan(String value) {
            addCriterion("followingloginname <", value, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameLessThanOrEqualTo(String value) {
            addCriterion("followingloginname <=", value, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameLike(String value) {
            addCriterion("followingloginname like", value, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameNotLike(String value) {
            addCriterion("followingloginname not like", value, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameIn(List<String> values) {
            addCriterion("followingloginname in", values, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameNotIn(List<String> values) {
            addCriterion("followingloginname not in", values, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameBetween(String value1, String value2) {
            addCriterion("followingloginname between", value1, value2, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingloginnameNotBetween(String value1, String value2) {
            addCriterion("followingloginname not between", value1, value2, "followingloginname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameIsNull() {
            addCriterion("followingviewname is null");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameIsNotNull() {
            addCriterion("followingviewname is not null");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameEqualTo(String value) {
            addCriterion("followingviewname =", value, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameNotEqualTo(String value) {
            addCriterion("followingviewname <>", value, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameGreaterThan(String value) {
            addCriterion("followingviewname >", value, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameGreaterThanOrEqualTo(String value) {
            addCriterion("followingviewname >=", value, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameLessThan(String value) {
            addCriterion("followingviewname <", value, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameLessThanOrEqualTo(String value) {
            addCriterion("followingviewname <=", value, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameLike(String value) {
            addCriterion("followingviewname like", value, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameNotLike(String value) {
            addCriterion("followingviewname not like", value, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameIn(List<String> values) {
            addCriterion("followingviewname in", values, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameNotIn(List<String> values) {
            addCriterion("followingviewname not in", values, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameBetween(String value1, String value2) {
            addCriterion("followingviewname between", value1, value2, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowingviewnameNotBetween(String value1, String value2) {
            addCriterion("followingviewname not between", value1, value2, "followingviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameIsNull() {
            addCriterion("followedloginname is null");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameIsNotNull() {
            addCriterion("followedloginname is not null");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameEqualTo(String value) {
            addCriterion("followedloginname =", value, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameNotEqualTo(String value) {
            addCriterion("followedloginname <>", value, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameGreaterThan(String value) {
            addCriterion("followedloginname >", value, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameGreaterThanOrEqualTo(String value) {
            addCriterion("followedloginname >=", value, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameLessThan(String value) {
            addCriterion("followedloginname <", value, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameLessThanOrEqualTo(String value) {
            addCriterion("followedloginname <=", value, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameLike(String value) {
            addCriterion("followedloginname like", value, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameNotLike(String value) {
            addCriterion("followedloginname not like", value, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameIn(List<String> values) {
            addCriterion("followedloginname in", values, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameNotIn(List<String> values) {
            addCriterion("followedloginname not in", values, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameBetween(String value1, String value2) {
            addCriterion("followedloginname between", value1, value2, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedloginnameNotBetween(String value1, String value2) {
            addCriterion("followedloginname not between", value1, value2, "followedloginname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameIsNull() {
            addCriterion("followedviewname is null");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameIsNotNull() {
            addCriterion("followedviewname is not null");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameEqualTo(String value) {
            addCriterion("followedviewname =", value, "followedviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameNotEqualTo(String value) {
            addCriterion("followedviewname <>", value, "followedviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameGreaterThan(String value) {
            addCriterion("followedviewname >", value, "followedviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameGreaterThanOrEqualTo(String value) {
            addCriterion("followedviewname >=", value, "followedviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameLessThan(String value) {
            addCriterion("followedviewname <", value, "followedviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameLessThanOrEqualTo(String value) {
            addCriterion("followedviewname <=", value, "followedviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameLike(String value) {
            addCriterion("followedviewname like", value, "followedviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameNotLike(String value) {
            addCriterion("followedviewname not like", value, "followedviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameIn(List<String> values) {
            addCriterion("followedviewname in", values, "followedviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameNotIn(List<String> values) {
            addCriterion("followedviewname not in", values, "followedviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameBetween(String value1, String value2) {
            addCriterion("followedviewname between", value1, value2, "followedviewname");
            return (Criteria) this;
        }

        public Criteria andFollowedviewnameNotBetween(String value1, String value2) {
            addCriterion("followedviewname not between", value1, value2, "followedviewname");
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