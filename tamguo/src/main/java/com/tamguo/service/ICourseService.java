package com.tamguo.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.tamguo.model.CourseEntity;

public interface ICourseService extends IService<CourseEntity>{

	/** 根据考试获取科目 */
	List<CourseEntity> findBySubjectId(String subjectId);
	
	/** 获取科目 */
	CourseEntity find(String uid);

}
