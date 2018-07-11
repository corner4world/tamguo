package com.tamguo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.dao.QuestionMapper;
import com.tamguo.model.QuestionEntity;
import com.tamguo.service.IQuestionService;

@Service
public class QuestionService implements IQuestionService{
	
	@Autowired
	QuestionMapper questionMapper;
	
	@Override
	public Page<QuestionEntity> findPage(String chapterId, Page<QuestionEntity> page) {
		return page.setRecords(questionMapper.findPage(chapterId , page));
	}

}
