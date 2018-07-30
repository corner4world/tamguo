package com.tamguo.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value="tiku_subject")
public class SubjectEntity extends Model<SubjectEntity> implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId
	private String uid;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	protected Serializable pkVal() {
		return getUid();
	}

}
