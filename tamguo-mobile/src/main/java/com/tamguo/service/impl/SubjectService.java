package com.tamguo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tamguo.dao.CourseMapper;
import com.tamguo.model.CourseEntity;
import com.tamguo.service.ISubjectService;

@Service
public class SubjectService implements ISubjectService{
	
	@Autowired
	CourseMapper courseMapper;

	@Override
	public List<CourseEntity> findCourseList(String subjectId) {
		return courseMapper.findBySubjectId(subjectId);
	}

}
