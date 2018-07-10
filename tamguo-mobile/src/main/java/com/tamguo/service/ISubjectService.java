package com.tamguo.service;

import java.util.List;
import com.tamguo.model.CourseEntity;

public interface ISubjectService {

	public List<CourseEntity> findCourseList(String subjectId);
	
}
