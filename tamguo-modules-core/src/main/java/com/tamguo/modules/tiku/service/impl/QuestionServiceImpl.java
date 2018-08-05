package com.tamguo.modules.tiku.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.tiku.dao.PaperMapper;
import com.tamguo.modules.tiku.dao.QuestionMapper;
import com.tamguo.modules.tiku.model.PaperEntity;
import com.tamguo.modules.tiku.model.QuestionEntity;
import com.tamguo.modules.tiku.service.IQuestionService;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, QuestionEntity> implements IQuestionService{
	
	@Autowired
	private PaperMapper paperMapper;
	@Autowired
	private QuestionMapper questionMapper;

	@Override
	public Result addQuestion(QuestionEntity question , String currMemberId) {
		PaperEntity paper = paperMapper.selectById(question.getPaperId().toString());
		if(!currMemberId.equals(paper.getCreaterId())) {
			return Result.result(501, null, "改试卷不属于您！");
		}
		question.setCourseId(paper.getCourseId());
		questionMapper.insert(question);
		return Result.result(0, null, "添加成功！");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<QuestionEntity> queryQuestionList(String questionType, String id, String content, String paperId, String currMemberId , 
			Page<QuestionEntity> p) {
		if(!currMemberId.equals(paperMapper.selectById(paperId).getCreaterId())) {
			return p.setRecords(null);
		}
		Condition condition = new Condition();
		if(!StringUtils.isEmpty(questionType)) {
			condition.eq("question_type", questionType);
		}
		if(!StringUtils.isEmpty(id)) {
			condition.eq("id", id);
		}
		if(!StringUtils.isEmpty(content)) {
			condition.like("content", content);
		}
		condition.eq("paper_id", paperId);
		return p.setRecords(questionMapper.selectPage(p, condition));
	}

	@Transactional(readOnly=false)
	@Override
	public Result updateQuestion(QuestionEntity question , String currMemberId) {
		PaperEntity paper = paperMapper.selectById(question.getPaperId().toString());
		if(!currMemberId.equals(paper.getCreaterId())) {
			return Result.result(501, null, "改试卷不属于您！");
		}
		questionMapper.updateById(question);
		return Result.result(0, null, "修改成功！");
	}

	@Transactional(readOnly=false)
	@Override
	public Result delete(String uid , String currMemberId) {
		QuestionEntity question = questionMapper.selectById(uid);
		PaperEntity paper = paperMapper.selectById(question.getPaperId().toString());
		if(!currMemberId.equals(paper.getCreaterId())) {
			return Result.result(501, null, "改试卷不属于您！");
		}
		questionMapper.deleteById(uid);
		return Result.result(0, null, "删除成功！");
	}
	
}
