package com.tamguo.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;

@TableName(value="tiku_subject")
public class SubjectEntity extends SuperEntity<SubjectEntity> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String courseId;
	
	private String courseName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

}
