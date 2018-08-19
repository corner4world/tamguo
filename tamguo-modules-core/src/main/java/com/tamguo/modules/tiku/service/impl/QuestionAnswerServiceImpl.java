package com.tamguo.modules.tiku.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.tiku.dao.QuestionAnswerMapper;
import com.tamguo.modules.tiku.model.QuestionAnswerEntity;
import com.tamguo.modules.tiku.service.IQuestionAnswerService;

@Service
public class QuestionAnswerServiceImpl extends ServiceImpl<QuestionAnswerMapper, QuestionAnswerEntity> implements IQuestionAnswerService{

	@Transactional(readOnly=false)
	@Override
	public void sendAnswer(QuestionAnswerEntity entity) {
		entity.setCreateDate(new Date());
		entity.setAgreeNum(0);
		entity.setDisagreeNum(0);
		baseMapper.insert(entity);
	}

}
