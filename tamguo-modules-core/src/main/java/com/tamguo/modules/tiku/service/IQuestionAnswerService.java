package com.tamguo.modules.tiku.service;

import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.tiku.model.QuestionAnswerEntity;

public interface IQuestionAnswerService extends IService<QuestionAnswerEntity>{

	void sendAnswer(QuestionAnswerEntity entity);

}
