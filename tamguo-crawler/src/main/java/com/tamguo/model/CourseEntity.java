package com.tamguo.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;

/**
 * The persistent class for the tiku_course database table.
 * 
 */
@TableName(value="tiku_course")
public class CourseEntity extends SuperEntity<CourseEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	private String subjectId;
	
	private Integer pointNum;
	
	private Integer questionNum;

	private Integer sort;
	
	
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

	public Integer getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	public Integer getPointNum() {
		return pointNum;
	}

	public void setPointNum(Integer pointNum) {
		this.pointNum = pointNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}