package com.tamguo.modules.tiku.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.tiku.model.QuestionEntity;

public interface IQuestionService extends IService<QuestionEntity>{
	
	public Result addQuestion(QuestionEntity question , String currMemberId);

	public Page<QuestionEntity> queryQuestionList(String questionType, String uid, String content, String paperId, String currMemberId , 
			Page<QuestionEntity> p);
	
	public Result updateQuestion(QuestionEntity question, String currMemberId);

	public Result delete(String uid, String currMemberId);
}
