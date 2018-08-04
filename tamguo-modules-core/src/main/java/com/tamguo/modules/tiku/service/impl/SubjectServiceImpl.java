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
import com.tamguo.modules.tiku.model.enums.SubjectStatusEnum;
import com.tamguo.modules.tiku.service.ISubjectService;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, SubjectEntity> implements ISubjectService{
	
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
		query.ne("status", SubjectStatusEnum.DELETE.getValue());
		return page.setRecords(subjectMapper.selectPage(page, query));
	}

	@Transactional(readOnly=false)
	@Override
	public void save(SubjectEntity subject) {
		subject.setStatus(SubjectStatusEnum.NORMAL);
		subjectMapper.insert(subject);
	}

	@Transactional(readOnly=false)
	@Override
	public void update(SubjectEntity subject) {
		SubjectEntity entity = subjectMapper.selectById(subject.getUid());
		entity.setName(subject.getName());
		entity.setRemarks(subject.getRemarks());
		entity.setSort(subject.getSort());
		subjectMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void enable(String uid) {
		SubjectEntity entity = subjectMapper.selectById(uid);
		entity.setStatus(SubjectStatusEnum.NORMAL);
		subjectMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void disabled(String uid) {
		SubjectEntity entity = subjectMapper.selectById(uid);
		entity.setStatus(SubjectStatusEnum.DISABLED);
		subjectMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void delete(String uid) {
		SubjectEntity entity = subjectMapper.selectById(uid);
		entity.setStatus(SubjectStatusEnum.DELETE);
		subjectMapper.updateById(entity);
	}

}
