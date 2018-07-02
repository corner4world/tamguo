package com.tamguo.model.vo;

import java.util.List;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;

@PageSelect(cssQuery = "body")
public class SubjectVo {

	@PageFieldSelect(cssQuery = ".all-list-li")
	private List<String> name;
	
	@PageFieldSelect(cssQuery=".course-list-container .course-list .course-item")
	private List<String> courseName;
	
	@PageFieldSelect(cssQuery=".submenu-contain .contain-title")
	private String subjectName;
	
	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}

	public List<String> getCourseName() {
		return courseName;
	}

	public void setCourseName(List<String> courseName) {
		this.courseName = courseName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

}
