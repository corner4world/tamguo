package com.tamguo.modules.tiku.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.tiku.dao.CourseMapper;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.condition.CourseCondition;
import com.tamguo.modules.tiku.model.enums.CourseStatusEnum;
import com.tamguo.modules.tiku.service.ICourseService;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements ICourseService{
	
	@Autowired
	public CourseMapper courseMapper;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public Page<CourseEntity> listData(CourseCondition condition) {
		Page<CourseEntity> page = new Page<>(condition.getPageNo() , condition.getPageSize());
		Condition query = Condition.create();
		if(!StringUtils.isEmpty(condition.getUid())) {
			query.eq("uid", condition.getUid());
		}
		if(!StringUtils.isEmpty(condition.getName())) {
			query.like("name", condition.getName());
		}
		if(!StringUtils.isEmpty(condition.getSubjectId())) {
			query.eq("subject_id", condition.getSubjectId());
		}
		if(!StringUtils.isEmpty(condition.getStatus())) {
			query.eq("status", condition.getStatus());
		}
		query.orderAsc(Arrays.asList("sort"));
		return page.setRecords(courseMapper.selectPage(page , query));
	}

	@Transactional(readOnly=false)
	@Override
	public void save(CourseEntity course) {
		course.setStatus(CourseStatusEnum.NORMAL);
		courseMapper.insert(course);
	}

	@Transactional(readOnly=false)
	@Override
	public void update(CourseEntity course) {
		courseMapper.updateById(course);
	}

}
