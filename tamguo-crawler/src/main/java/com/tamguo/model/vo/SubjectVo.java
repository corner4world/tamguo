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
	
	// 待采集的问题URLs
	@PageFieldSelect(cssQuery = ".list-right .detail-chapter .detail-kpoint-1 .detail-kpoint-2 .mask a", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:href")
	private List<String> questionUrlsTemp;
	
	// 待采集问题URLs
	@PageFieldSelect(cssQuery = ".bd-content .question-box .question-box-inner .view-analyse a", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:href")
	private List<String> questionUrls;
	
	// 单个题目数据
	@PageFieldSelect(cssQuery=".question-box-inner .questem-inner", selectType = XxlCrawlerConf.SelectType.HTML)
	private String content;
	
	@PageFieldSelect(cssQuery=".exam-answer-content", selectType = XxlCrawlerConf.SelectType.HTML)
	private List<String> answer;
	
	@PageFieldSelect(cssQuery=".exam-analysis .exam-analysis-content", selectType = XxlCrawlerConf.SelectType.HTML)
	private String analysis;
	
	@PageFieldSelect(cssQuery=".que-title span:eq(0)",selectType = XxlCrawlerConf.SelectType.TEXT)
	private String questionType;

	@PageFieldSelect(cssQuery=".que-title span:eq(1)",selectType = XxlCrawlerConf.SelectType.TEXT)
	private String score;

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

	public List<String> getQuestionUrlsTemp() {
		return questionUrlsTemp;
	}

	public void setQuestionUrlsTemp(List<String> questionUrlsTemp) {
		this.questionUrlsTemp = questionUrlsTemp;
	}

	public List<String> getQuestionUrls() {
		return questionUrls;
	}

	public void setQuestionUrls(List<String> questionUrls) {
		this.questionUrls = questionUrls;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<String> getAnswer() {
		return answer;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}

}
