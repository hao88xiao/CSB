package com.linkage.bss.crm.intf.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FingerPhotoCutExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FingerPhotoCutExample() {
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

        public Criteria andOlIdIsNull() {
            addCriterion("OL_ID is null");
            return (Criteria) this;
        }

        public Criteria andOlIdIsNotNull() {
            addCriterion("OL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOlIdEqualTo(Long value) {
            addCriterion("OL_ID =", value, "olId");
            return (Criteria) this;
        }

        public Criteria andOlIdNotEqualTo(Long value) {
            addCriterion("OL_ID <>", value, "olId");
            return (Criteria) this;
        }

        public Criteria andOlIdGreaterThan(Long value) {
            addCriterion("OL_ID >", value, "olId");
            return (Criteria) this;
        }

        public Criteria andOlIdGreaterThanOrEqualTo(Long value) {
            addCriterion("OL_ID >=", value, "olId");
            return (Criteria) this;
        }

        public Criteria andOlIdLessThan(Long value) {
            addCriterion("OL_ID <", value, "olId");
            return (Criteria) this;
        }

        public Criteria andOlIdLessThanOrEqualTo(Long value) {
            addCriterion("OL_ID <=", value, "olId");
            return (Criteria) this;
        }

        public Criteria andOlIdIn(List<Long> values) {
            addCriterion("OL_ID in", values, "olId");
            return (Criteria) this;
        }

        public Criteria andOlIdNotIn(List<Long> values) {
            addCriterion("OL_ID not in", values, "olId");
            return (Criteria) this;
        }

        public Criteria andOlIdBetween(Long value1, Long value2) {
            addCriterion("OL_ID between", value1, value2, "olId");
            return (Criteria) this;
        }

        public Criteria andOlIdNotBetween(Long value1, Long value2) {
            addCriterion("OL_ID not between", value1, value2, "olId");
            return (Criteria) this;
        }

        public Criteria andBoIdIsNull() {
            addCriterion("BO_ID is null");
            return (Criteria) this;
        }

        public Criteria andBoIdIsNotNull() {
            addCriterion("BO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBoIdEqualTo(Long value) {
            addCriterion("BO_ID =", value, "boId");
            return (Criteria) this;
        }

        public Criteria andBoIdNotEqualTo(Long value) {
            addCriterion("BO_ID <>", value, "boId");
            return (Criteria) this;
        }

        public Criteria andBoIdGreaterThan(Long value) {
            addCriterion("BO_ID >", value, "boId");
            return (Criteria) this;
        }

        public Criteria andBoIdGreaterThanOrEqualTo(Long value) {
            addCriterion("BO_ID >=", value, "boId");
            return (Criteria) this;
        }

        public Criteria andBoIdLessThan(Long value) {
            addCriterion("BO_ID <", value, "boId");
            return (Criteria) this;
        }

        public Criteria andBoIdLessThanOrEqualTo(Long value) {
            addCriterion("BO_ID <=", value, "boId");
            return (Criteria) this;
        }

        public Criteria andBoIdIn(List<Long> values) {
            addCriterion("BO_ID in", values, "boId");
            return (Criteria) this;
        }

        public Criteria andBoIdNotIn(List<Long> values) {
            addCriterion("BO_ID not in", values, "boId");
            return (Criteria) this;
        }

        public Criteria andBoIdBetween(Long value1, Long value2) {
            addCriterion("BO_ID between", value1, value2, "boId");
            return (Criteria) this;
        }

        public Criteria andBoIdNotBetween(Long value1, Long value2) {
            addCriterion("BO_ID not between", value1, value2, "boId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdIsNull() {
            addCriterion("BO_ACTION_TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdIsNotNull() {
            addCriterion("BO_ACTION_TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdEqualTo(String value) {
            addCriterion("BO_ACTION_TYPE_ID =", value, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdNotEqualTo(String value) {
            addCriterion("BO_ACTION_TYPE_ID <>", value, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdGreaterThan(String value) {
            addCriterion("BO_ACTION_TYPE_ID >", value, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("BO_ACTION_TYPE_ID >=", value, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdLessThan(String value) {
            addCriterion("BO_ACTION_TYPE_ID <", value, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdLessThanOrEqualTo(String value) {
            addCriterion("BO_ACTION_TYPE_ID <=", value, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdLike(String value) {
            addCriterion("BO_ACTION_TYPE_ID like", value, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdNotLike(String value) {
            addCriterion("BO_ACTION_TYPE_ID not like", value, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdIn(List<String> values) {
            addCriterion("BO_ACTION_TYPE_ID in", values, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdNotIn(List<String> values) {
            addCriterion("BO_ACTION_TYPE_ID not in", values, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdBetween(String value1, String value2) {
            addCriterion("BO_ACTION_TYPE_ID between", value1, value2, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andBoActionTypeIdNotBetween(String value1, String value2) {
            addCriterion("BO_ACTION_TYPE_ID not between", value1, value2, "boActionTypeId");
            return (Criteria) this;
        }

        public Criteria andProdIdIsNull() {
            addCriterion("PROD_ID is null");
            return (Criteria) this;
        }

        public Criteria andProdIdIsNotNull() {
            addCriterion("PROD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProdIdEqualTo(Long value) {
            addCriterion("PROD_ID =", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdNotEqualTo(Long value) {
            addCriterion("PROD_ID <>", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdGreaterThan(Long value) {
            addCriterion("PROD_ID >", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdGreaterThanOrEqualTo(Long value) {
            addCriterion("PROD_ID >=", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdLessThan(Long value) {
            addCriterion("PROD_ID <", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdLessThanOrEqualTo(Long value) {
            addCriterion("PROD_ID <=", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdIn(List<Long> values) {
            addCriterion("PROD_ID in", values, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdNotIn(List<Long> values) {
            addCriterion("PROD_ID not in", values, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdBetween(Long value1, Long value2) {
            addCriterion("PROD_ID between", value1, value2, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdNotBetween(Long value1, Long value2) {
            addCriterion("PROD_ID not between", value1, value2, "prodId");
            return (Criteria) this;
        }

        public Criteria andStaffIdIsNull() {
            addCriterion("STAFF_ID is null");
            return (Criteria) this;
        }

        public Criteria andStaffIdIsNotNull() {
            addCriterion("STAFF_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStaffIdEqualTo(Long value) {
            addCriterion("STAFF_ID =", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdNotEqualTo(Long value) {
            addCriterion("STAFF_ID <>", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdGreaterThan(Long value) {
            addCriterion("STAFF_ID >", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdGreaterThanOrEqualTo(Long value) {
            addCriterion("STAFF_ID >=", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdLessThan(Long value) {
            addCriterion("STAFF_ID <", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdLessThanOrEqualTo(Long value) {
            addCriterion("STAFF_ID <=", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdIn(List<Long> values) {
            addCriterion("STAFF_ID in", values, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdNotIn(List<Long> values) {
            addCriterion("STAFF_ID not in", values, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdBetween(Long value1, Long value2) {
            addCriterion("STAFF_ID between", value1, value2, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdNotBetween(Long value1, Long value2) {
            addCriterion("STAFF_ID not between", value1, value2, "staffId");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNull() {
            addCriterion("PARTY_ID is null");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNotNull() {
            addCriterion("PARTY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPartyIdEqualTo(Long value) {
            addCriterion("PARTY_ID =", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotEqualTo(Long value) {
            addCriterion("PARTY_ID <>", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThan(Long value) {
            addCriterion("PARTY_ID >", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("PARTY_ID >=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThan(Long value) {
            addCriterion("PARTY_ID <", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThanOrEqualTo(Long value) {
            addCriterion("PARTY_ID <=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdIn(List<Long> values) {
            addCriterion("PARTY_ID in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotIn(List<Long> values) {
            addCriterion("PARTY_ID not in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdBetween(Long value1, Long value2) {
            addCriterion("PARTY_ID between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotBetween(Long value1, Long value2) {
            addCriterion("PARTY_ID not between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andCreatDtIsNull() {
            addCriterion("CREAT_DT is null");
            return (Criteria) this;
        }

        public Criteria andCreatDtIsNotNull() {
            addCriterion("CREAT_DT is not null");
            return (Criteria) this;
        }

        public Criteria andCreatDtEqualTo(Date value) {
            addCriterionForJDBCDate("CREAT_DT =", value, "creatDt");
            return (Criteria) this;
        }

        public Criteria andCreatDtNotEqualTo(Date value) {
            addCriterionForJDBCDate("CREAT_DT <>", value, "creatDt");
            return (Criteria) this;
        }

        public Criteria andCreatDtGreaterThan(Date value) {
            addCriterionForJDBCDate("CREAT_DT >", value, "creatDt");
            return (Criteria) this;
        }

        public Criteria andCreatDtGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CREAT_DT >=", value, "creatDt");
            return (Criteria) this;
        }

        public Criteria andCreatDtLessThan(Date value) {
            addCriterionForJDBCDate("CREAT_DT <", value, "creatDt");
            return (Criteria) this;
        }

        public Criteria andCreatDtLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CREAT_DT <=", value, "creatDt");
            return (Criteria) this;
        }

        public Criteria andCreatDtIn(List<Date> values) {
            addCriterionForJDBCDate("CREAT_DT in", values, "creatDt");
            return (Criteria) this;
        }

        public Criteria andCreatDtNotIn(List<Date> values) {
            addCriterionForJDBCDate("CREAT_DT not in", values, "creatDt");
            return (Criteria) this;
        }

        public Criteria andCreatDtBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREAT_DT between", value1, value2, "creatDt");
            return (Criteria) this;
        }

        public Criteria andCreatDtNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREAT_DT not between", value1, value2, "creatDt");
            return (Criteria) this;
        }

        public Criteria andExtFields1IsNull() {
            addCriterion("EXT_FIELDS1 is null");
            return (Criteria) this;
        }

        public Criteria andExtFields1IsNotNull() {
            addCriterion("EXT_FIELDS1 is not null");
            return (Criteria) this;
        }

        public Criteria andExtFields1EqualTo(String value) {
            addCriterion("EXT_FIELDS1 =", value, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields1NotEqualTo(String value) {
            addCriterion("EXT_FIELDS1 <>", value, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields1GreaterThan(String value) {
            addCriterion("EXT_FIELDS1 >", value, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields1GreaterThanOrEqualTo(String value) {
            addCriterion("EXT_FIELDS1 >=", value, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields1LessThan(String value) {
            addCriterion("EXT_FIELDS1 <", value, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields1LessThanOrEqualTo(String value) {
            addCriterion("EXT_FIELDS1 <=", value, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields1Like(String value) {
            addCriterion("EXT_FIELDS1 like", value, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields1NotLike(String value) {
            addCriterion("EXT_FIELDS1 not like", value, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields1In(List<String> values) {
            addCriterion("EXT_FIELDS1 in", values, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields1NotIn(List<String> values) {
            addCriterion("EXT_FIELDS1 not in", values, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields1Between(String value1, String value2) {
            addCriterion("EXT_FIELDS1 between", value1, value2, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields1NotBetween(String value1, String value2) {
            addCriterion("EXT_FIELDS1 not between", value1, value2, "extFields1");
            return (Criteria) this;
        }

        public Criteria andExtFields2IsNull() {
            addCriterion("EXT_FIELDS2 is null");
            return (Criteria) this;
        }

        public Criteria andExtFields2IsNotNull() {
            addCriterion("EXT_FIELDS2 is not null");
            return (Criteria) this;
        }

        public Criteria andExtFields2EqualTo(String value) {
            addCriterion("EXT_FIELDS2 =", value, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields2NotEqualTo(String value) {
            addCriterion("EXT_FIELDS2 <>", value, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields2GreaterThan(String value) {
            addCriterion("EXT_FIELDS2 >", value, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields2GreaterThanOrEqualTo(String value) {
            addCriterion("EXT_FIELDS2 >=", value, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields2LessThan(String value) {
            addCriterion("EXT_FIELDS2 <", value, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields2LessThanOrEqualTo(String value) {
            addCriterion("EXT_FIELDS2 <=", value, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields2Like(String value) {
            addCriterion("EXT_FIELDS2 like", value, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields2NotLike(String value) {
            addCriterion("EXT_FIELDS2 not like", value, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields2In(List<String> values) {
            addCriterion("EXT_FIELDS2 in", values, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields2NotIn(List<String> values) {
            addCriterion("EXT_FIELDS2 not in", values, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields2Between(String value1, String value2) {
            addCriterion("EXT_FIELDS2 between", value1, value2, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields2NotBetween(String value1, String value2) {
            addCriterion("EXT_FIELDS2 not between", value1, value2, "extFields2");
            return (Criteria) this;
        }

        public Criteria andExtFields3IsNull() {
            addCriterion("EXT_FIELDS3 is null");
            return (Criteria) this;
        }

        public Criteria andExtFields3IsNotNull() {
            addCriterion("EXT_FIELDS3 is not null");
            return (Criteria) this;
        }

        public Criteria andExtFields3EqualTo(String value) {
            addCriterion("EXT_FIELDS3 =", value, "extFields3");
            return (Criteria) this;
        }

        public Criteria andExtFields3NotEqualTo(String value) {
            addCriterion("EXT_FIELDS3 <>", value, "extFields3");
            return (Criteria) this;
        }

        public Criteria andExtFields3GreaterThan(String value) {
            addCriterion("EXT_FIELDS3 >", value, "extFields3");
            return (Criteria) this;
        }

        public Criteria andExtFields3GreaterThanOrEqualTo(String value) {
            addCriterion("EXT_FIELDS3 >=", value, "extFields3");
            return (Criteria) this;
        }

        public Criteria andExtFields3LessThan(String value) {
            addCriterion("EXT_FIELDS3 <", value, "extFields3");
            return (Criteria) this;
        }

        public Criteria andExtFields3LessThanOrEqualTo(String value) {
            addCriterion("EXT_FIELDS3 <=", value, "extFields3");
            return (Criteria) this;
        }

        public Criteria andExtFields3Like(String value) {
            addCriterion("EXT_FIELDS3 like", value, "extFields3");
            return (Criteria) this;
        }

        public Criteria andExtFields3NotLike(String value) {
            addCriterion("EXT_FIELDS3 not like", value, "extFields3");
            return (Criteria) this;
        }

        public Criteria andExtFields3In(List<String> values) {
            addCriterion("EXT_FIELDS3 in", values, "extFields3");
            return (Criteria) this;
        }

        public Criteria andExtFields3NotIn(List<String> values) {
            addCriterion("EXT_FIELDS3 not in", values, "extFields3");
            return (Criteria) this;
        }

        public Criteria andExtFields3Between(String value1, String value2) {
            addCriterion("EXT_FIELDS3 between", value1, value2, "extFields3");
            return (Criteria) this;
        }

        public Criteria andExtFields3NotBetween(String value1, String value2) {
            addCriterion("EXT_FIELDS3 not between", value1, value2, "extFields3");
            return (Criteria) this;
        }

        public Criteria andAuditResultIsNull() {
            addCriterion("AUDIT_RESULT is null");
            return (Criteria) this;
        }

        public Criteria andAuditResultIsNotNull() {
            addCriterion("AUDIT_RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andAuditResultEqualTo(String value) {
            addCriterion("AUDIT_RESULT =", value, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditResultNotEqualTo(String value) {
            addCriterion("AUDIT_RESULT <>", value, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditResultGreaterThan(String value) {
            addCriterion("AUDIT_RESULT >", value, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditResultGreaterThanOrEqualTo(String value) {
            addCriterion("AUDIT_RESULT >=", value, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditResultLessThan(String value) {
            addCriterion("AUDIT_RESULT <", value, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditResultLessThanOrEqualTo(String value) {
            addCriterion("AUDIT_RESULT <=", value, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditResultLike(String value) {
            addCriterion("AUDIT_RESULT like", value, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditResultNotLike(String value) {
            addCriterion("AUDIT_RESULT not like", value, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditResultIn(List<String> values) {
            addCriterion("AUDIT_RESULT in", values, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditResultNotIn(List<String> values) {
            addCriterion("AUDIT_RESULT not in", values, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditResultBetween(String value1, String value2) {
            addCriterion("AUDIT_RESULT between", value1, value2, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditResultNotBetween(String value1, String value2) {
            addCriterion("AUDIT_RESULT not between", value1, value2, "auditResult");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdIsNull() {
            addCriterion("AUDIT_STAFF_ID is null");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdIsNotNull() {
            addCriterion("AUDIT_STAFF_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdEqualTo(Long value) {
            addCriterion("AUDIT_STAFF_ID =", value, "auditStaffId");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdNotEqualTo(Long value) {
            addCriterion("AUDIT_STAFF_ID <>", value, "auditStaffId");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdGreaterThan(Long value) {
            addCriterion("AUDIT_STAFF_ID >", value, "auditStaffId");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdGreaterThanOrEqualTo(Long value) {
            addCriterion("AUDIT_STAFF_ID >=", value, "auditStaffId");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdLessThan(Long value) {
            addCriterion("AUDIT_STAFF_ID <", value, "auditStaffId");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdLessThanOrEqualTo(Long value) {
            addCriterion("AUDIT_STAFF_ID <=", value, "auditStaffId");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdIn(List<Long> values) {
            addCriterion("AUDIT_STAFF_ID in", values, "auditStaffId");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdNotIn(List<Long> values) {
            addCriterion("AUDIT_STAFF_ID not in", values, "auditStaffId");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdBetween(Long value1, Long value2) {
            addCriterion("AUDIT_STAFF_ID between", value1, value2, "auditStaffId");
            return (Criteria) this;
        }

        public Criteria andAuditStaffIdNotBetween(Long value1, Long value2) {
            addCriterion("AUDIT_STAFF_ID not between", value1, value2, "auditStaffId");
            return (Criteria) this;
        }

        public Criteria andAuditDtIsNull() {
            addCriterion("AUDIT_DT is null");
            return (Criteria) this;
        }

        public Criteria andAuditDtIsNotNull() {
            addCriterion("AUDIT_DT is not null");
            return (Criteria) this;
        }

        public Criteria andAuditDtEqualTo(Date value) {
            addCriterionForJDBCDate("AUDIT_DT =", value, "auditDt");
            return (Criteria) this;
        }

        public Criteria andAuditDtNotEqualTo(Date value) {
            addCriterionForJDBCDate("AUDIT_DT <>", value, "auditDt");
            return (Criteria) this;
        }

        public Criteria andAuditDtGreaterThan(Date value) {
            addCriterionForJDBCDate("AUDIT_DT >", value, "auditDt");
            return (Criteria) this;
        }

        public Criteria andAuditDtGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("AUDIT_DT >=", value, "auditDt");
            return (Criteria) this;
        }

        public Criteria andAuditDtLessThan(Date value) {
            addCriterionForJDBCDate("AUDIT_DT <", value, "auditDt");
            return (Criteria) this;
        }

        public Criteria andAuditDtLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("AUDIT_DT <=", value, "auditDt");
            return (Criteria) this;
        }

        public Criteria andAuditDtIn(List<Date> values) {
            addCriterionForJDBCDate("AUDIT_DT in", values, "auditDt");
            return (Criteria) this;
        }

        public Criteria andAuditDtNotIn(List<Date> values) {
            addCriterionForJDBCDate("AUDIT_DT not in", values, "auditDt");
            return (Criteria) this;
        }

        public Criteria andAuditDtBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("AUDIT_DT between", value1, value2, "auditDt");
            return (Criteria) this;
        }

        public Criteria andAuditDtNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("AUDIT_DT not between", value1, value2, "auditDt");
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