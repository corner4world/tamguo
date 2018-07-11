package com.tamguo.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.model.QuestionEntity;

public interface IQuestionService {

	Page<QuestionEntity> findPage(String chapterId , Page<QuestionEntity> page);
}
