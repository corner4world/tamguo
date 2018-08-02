package com.tamguo.modules.tiku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.tiku.dao.SubjectMapper;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.model.condition.SubjectCondition;
import com.tamguo.modules.tiku.service.ISubjectService;

@Service
public class SubjectService extends ServiceImpl<SubjectMapper, SubjectEntity> implements ISubjectService{
	@Autowired
	private SubjectMapper subjectMapper;

	@SuppressWarnings("unchecked")
	@Override
	public Page<SubjectEntity> listData(SubjectCondition condition) {
		Page<SubjectEntity> page = new Page<>(condition.getPageNo(), condition.getPageSize());
		return page.setRecords(subjectMapper.selectPage(page, Condition.EMPTY));
	}

}
