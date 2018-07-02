package com.tamguo.model.vo;

import java.util.List;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;

@PageSelect(cssQuery = "body")
public class CourseVo {

	@PageFieldSelect(cssQuery = ".course-item")
	private List<String> name;

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}
	
}
