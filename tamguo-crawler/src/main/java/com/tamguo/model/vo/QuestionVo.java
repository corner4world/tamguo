package com.tamguo.model.vo;

import java.util.List;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;

public class QuestionVo {

	// 单个题目数据
	@PageFieldSelect(cssQuery=".question-box-inner .questem-inner", selectType = XxlCrawlerConf.SelectType.HTML)
	private String content;
	
	@PageFieldSelect(cssQuery=".exam-answer-content", selectType = XxlCrawlerConf.SelectType.HTML)
	private String answer;
	
	@PageFieldSelect(cssQuery = ".exam-answer-content img", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:src")
    private List<String> answerImages;
	
	@PageFieldSelect(cssQuery=".exam-analysis .exam-analysis-content", selectType = XxlCrawlerConf.SelectType.HTML)
	private String analysis;
	
	@PageFieldSelect(cssQuery = ".exam-analysis .exam-analysis-content img", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:src")
    private List<String> analysisImages;
	
	@PageFieldSelect(cssQuery=".que-title span:eq(0)",selectType = XxlCrawlerConf.SelectType.TEXT)
	private String questionType;

	@PageFieldSelect(cssQuery=".que-title span:eq(1)",selectType = XxlCrawlerConf.SelectType.TEXT)
	private String score;
	
	@PageFieldSelect(cssQuery=".que-title span:eq(2)",selectType = XxlCrawlerConf.SelectType.TEXT)
	private String year;
	
	@PageFieldSelect(cssQuery=".kpoint-contain point point-item",selectType = XxlCrawlerConf.SelectType.TEXT)
	private List<String> reviewPoint;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<String> getReviewPoint() {
		return reviewPoint;
	}

	public void setReviewPoint(List<String> reviewPoint) {
		this.reviewPoint = reviewPoint;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<String> getAnswerImages() {
		return answerImages;
	}

	public void setAnswerImages(List<String> answerImages) {
		this.answerImages = answerImages;
	}

	public List<String> getAnalysisImages() {
		return analysisImages;
	}

	public void setAnalysisImages(List<String> analysisImages) {
		this.analysisImages = analysisImages;
	}
	
}
