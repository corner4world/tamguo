package com.tamguo.modules.tiku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.tiku.dao.CourseMapper;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.condition.SubjectCondition;
import com.tamguo.modules.tiku.service.ICourseService;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements ICourseService{
	
	@Autowired
	public CourseMapper courseMapper;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public Page<CourseEntity> listData(SubjectCondition condition) {
		Page<CourseEntity> page = new Page<>();
		Condition query = Condition.create();
		return page.setRecords(courseMapper.selectPage(page , query));
	}

}
