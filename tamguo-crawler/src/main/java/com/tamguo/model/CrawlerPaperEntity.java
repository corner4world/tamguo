package com.tamguo.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;

@TableName(value="crawler_paper")
public class CrawlerPaperEntity extends SuperEntity<CrawlerChapterEntity>{

	private static final long serialVersionUID = 1L;

	private String questionUrl;
	
	private String paperId;
	
	private Integer queindex;
	
	public String getQuestionUrl() {
		return questionUrl;
	}

	public void setQuestionUrl(String questionUrl) {
		this.questionUrl = questionUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public Integer getQueindex() {
		return queindex;
	}

	public void setQueindex(Integer queindex) {
		this.queindex = queindex;
	}
}
