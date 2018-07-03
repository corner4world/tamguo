package com.tamguo.model.vo;

import java.util.List;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;

@PageSelect(cssQuery = "body")
public class SubjectVo {

	@PageFieldSelect(cssQuery = ".all-list-li")
	private List<String> name;
	
	// 类型名称
	@PageFieldSelect(cssQuery=".submenu-contain .contain-title")
	private String subjectName;
	
	// 科目信息
	@PageFieldSelect(cssQuery=".course-list-container .course-list .course-item")
	private List<String> courseName;
	
	// 带采集的科目URLs
	@PageFieldSelect(cssQuery = ".all-list-li a", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:href")
	private List<String> courseUrls;

	
	@PageFieldSelect(cssQuery=".screening .selected a")
	private String chapterPageCourseName;
	
	@PageFieldSelect(cssQuery=".screening .selected a")
	private String chapterCurrName;
	
	// 带采集的章节URLs缓存
	@PageFieldSelect(cssQuery = ".main-submenu .contain-ul .contain-li:eq(1) a", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:href")
	private List<String> chapterUrlsTemp;
	
	// 待采集的章节URLs
	@PageFieldSelect(cssQuery = ".screening .sc-subject li:not(.selected) a", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:href")
	private List<String> chapterUrls;
	
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

	public List<String> getCourseUrls() {
		return courseUrls;
	}

	public void setCourseUrls(List<String> courseUrls) {
		this.courseUrls = courseUrls;
	}

	public List<String> getChapterUrls() {
		return chapterUrls;
	}

	public void setChapterUrls(List<String> chapterUrls) {
		this.chapterUrls = chapterUrls;
	}

	public List<String> getChapterUrlsTemp() {
		return chapterUrlsTemp;
	}

	public void setChapterUrlsTemp(List<String> chapterUrlsTemp) {
		this.chapterUrlsTemp = chapterUrlsTemp;
	}

	public String getChapterPageCourseName() {
		return chapterPageCourseName;
	}

	public void setChapterPageCourseName(String chapterPageCourseName) {
		this.chapterPageCourseName = chapterPageCourseName;
	}

	public String getChapterCurrName() {
		return chapterCurrName;
	}

	public void setChapterCurrName(String chapterCurrName) {
		this.chapterCurrName = chapterCurrName;
	}

}
