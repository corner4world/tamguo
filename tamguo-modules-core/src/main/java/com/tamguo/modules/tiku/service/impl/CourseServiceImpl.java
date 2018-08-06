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
import com.tamguo.modules.tiku.dao.SubjectMapper;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.condition.CourseCondition;
import com.tamguo.modules.tiku.model.enums.CourseStatusEnum;
import com.tamguo.modules.tiku.service.ICourseService;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements ICourseService{
	
	@Autowired
	public CourseMapper courseMapper;
	@Autowired
	public SubjectMapper subjectMapper;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public Page<CourseEntity> listData(CourseCondition condition) {
		Page<CourseEntity> page = new Page<>(condition.getPageNo() , condition.getPageSize());
		Condition query = Condition.create();
		if(!StringUtils.isEmpty(condition.getId())) {
			query.eq("id", condition.getId());
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
		CourseEntity entity = courseMapper.selectById(course.getId());
		entity.setName(course.getName());
		entity.setSubjectId(course.getSubjectId());
		entity.setSort(course.getSort());
		entity.setRemarks(course.getRemarks());
		courseMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void delete(String uid) {
		CourseEntity entity = courseMapper.selectById(uid);
		entity.setStatus(CourseStatusEnum.DELETE);
		courseMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void enable(String uid) {
		CourseEntity entity = courseMapper.selectById(uid);
		entity.setStatus(CourseStatusEnum.NORMAL);
		courseMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void disabled(String uid) {
		CourseEntity entity = courseMapper.selectById(uid);
		entity.setStatus(CourseStatusEnum.DISABLED);
		courseMapper.updateById(entity);
	}

}
