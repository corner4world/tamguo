package com.tamguo.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;

import java.math.BigInteger;


/**
 * The persistent class for the tiku_course database table.
 * 
 */
@TableName(value="tiku_course")
public class CourseEntity extends SuperEntity<CourseEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	private String subjectId;
	
	private BigInteger pointNum;
	
	private BigInteger questionNum;

	private String icon;
	
	private Integer orders;
	
	private String seoTitle;
	
	private String seoKeywords;
	
	private String seoDescription;
	
	public CourseEntity() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public BigInteger getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(BigInteger questionNum) {
		this.questionNum = questionNum;
	}

	public BigInteger getPointNum() {
		return pointNum;
	}

	public void setPointNum(BigInteger pointNum) {
		this.pointNum = pointNum;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}


	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}