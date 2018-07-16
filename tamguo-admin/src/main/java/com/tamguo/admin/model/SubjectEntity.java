package com.tamguo.admin.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.admin.config.dao.SuperEntity;

@TableName(value="tiku_subject")
public class SubjectEntity extends SuperEntity<SubjectEntity> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	@TableField(exist=false)
	private List<CourseEntity> courseList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CourseEntity> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<CourseEntity> courseList) {
		this.courseList = courseList;
	}
}
