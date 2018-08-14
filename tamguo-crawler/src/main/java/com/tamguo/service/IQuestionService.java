package com.tamguo.service;

import com.baomidou.mybatisplus.service.IService;
import com.tamguo.model.QuestionEntity;

public interface IQuestionService extends IService<QuestionEntity>{
	/**
	 * 爬取章节数据
	 */
	void crawlerQuestion();
}
