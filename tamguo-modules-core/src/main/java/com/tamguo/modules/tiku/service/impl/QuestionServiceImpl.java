package com.tamguo.modules.tiku.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.tiku.dao.QuestionMapper;
import com.tamguo.modules.tiku.model.QuestionEntity;
import com.tamguo.modules.tiku.service.IQuestionService;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, QuestionEntity> implements IQuestionService{
	
}
