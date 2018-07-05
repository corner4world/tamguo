package com.tamguo.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;



/**
 * The persistent class for the crawler_question database table.
 * 
 */
@TableName(value="crawler_question")
public class CrawlerQuestionEntity extends SuperEntity<CrawlerQuestionEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String questionUrl;
	
	private String chapterId;
	
	private String status;
	
	public String getQuestionUrl() {
		return questionUrl;
	}



	public void setQuestionUrl(String questionUrl) {
		this.questionUrl = questionUrl;
	}



	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}