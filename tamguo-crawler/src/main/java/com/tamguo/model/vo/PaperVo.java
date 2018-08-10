package com.tamguo.model.vo;

import java.util.List;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;

@PageSelect(cssQuery = "body")
public class PaperVo {
	
	// 试卷URL
	@PageFieldSelect(cssQuery = ".paperlist .paper-title a", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:href")
	private List<String> paperUrls;
	
	@PageFieldSelect(cssQuery = ".title-bar .title-inner .paper-title .title")
	private String paperName;
	
	@PageFieldSelect(cssQuery = ".quelist-wrap .ques-container .ques-info-wrap .que-type")
	private List<String> questionInfoTypes;
	
	@PageFieldSelect(cssQuery = ".quelist-wrap .ques-container .ques-info-wrap .que-info")
	private List<String> questionInfoTitles;
	
	@PageFieldSelect(cssQuery = ".view-analyse .view-link", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:href")
	private List<String> questionUrls;

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public List<String> getQuestionInfoTypes() {
		return questionInfoTypes;
	}

	public void setQuestionInfoTypes(List<String> questionInfoTypes) {
		this.questionInfoTypes = questionInfoTypes;
	}

	public List<String> getPaperUrls() {
		return paperUrls;
	}

	public void setPaperUrls(List<String> paperUrls) {
		this.paperUrls = paperUrls;
	}

	public List<String> getQuestionInfoTitles() {
		return questionInfoTitles;
	}

	public void setQuestionInfoTitles(List<String> questionInfoTitles) {
		this.questionInfoTitles = questionInfoTitles;
	}

	public List<String> getQuestionUrls() {
		return questionUrls;
	}

	public void setQuestionUrls(List<String> questionUrls) {
		this.questionUrls = questionUrls;
	}
	
}
