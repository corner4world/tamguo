package com.tamguo.modules.tiku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.tiku.dao.SubjectMapper;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.model.condition.SubjectCondition;
import com.tamguo.modules.tiku.model.enums.SysSubjectStatusEnum;
import com.tamguo.modules.tiku.service.ISubjectService;

@Service
public class SubjectService extends ServiceImpl<SubjectMapper, SubjectEntity> implements ISubjectService{
	@Autowired
	private SubjectMapper subjectMapper;

	@Transactional(readOnly=false)
	@SuppressWarnings("unchecked")
	@Override
	public Page<SubjectEntity> listData(SubjectCondition condition) {
		Page<SubjectEntity> page = new Page<>(condition.getPageNo(), condition.getPageSize());
		Condition query = Condition.create();
		if(!StringUtils.isEmpty(condition.getUid())) {
			query.eq("uid", condition.getUid());
		}else if(!StringUtils.isEmpty(condition.getName())) {
			query.eq("name", condition.getName());
		}else if(!StringUtils.isEmpty(condition.getStatus())) {
			query.eq("status", condition.getStatus());
		}
		return page.setRecords(subjectMapper.selectPage(page, query));
	}

	@Transactional(readOnly=false)
	@Override
	public void save(SubjectEntity subject) {
		subject.setStatus(SysSubjectStatusEnum.NORMAL);
		subjectMapper.insert(subject);
	}

}
