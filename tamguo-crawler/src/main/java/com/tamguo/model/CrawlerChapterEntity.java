package com.tamguo.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;

@TableName(value="crawler_chapter")
public class CrawlerChapterEntity extends SuperEntity<CrawlerChapterEntity>{
	
	private static final long serialVersionUID = 1L;

	private String chapterUid;
	
	private String courseUid;
	
	private String chapterUrl;
	
	private String subjectUid;

	public String getChapterUid() {
		return chapterUid;
	}

	public void setChapterUid(String chapterUid) {
		this.chapterUid = chapterUid;
	}

	public String getCourseUid() {
		return courseUid;
	}

	public void setCourseUid(String courseUid) {
		this.courseUid = courseUid;
	}

	public String getChapterUrl() {
		return chapterUrl;
	}

	public void setChapterUrl(String chapterUrl) {
		this.chapterUrl = chapterUrl;
	}

	public String getSubjectUid() {
		return subjectUid;
	}

	public void setSubjectUid(String subjectUid) {
		this.subjectUid = subjectUid;
	}
}
