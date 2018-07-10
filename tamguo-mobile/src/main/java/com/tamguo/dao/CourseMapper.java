package com.tamguo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tamguo.config.dao.SuperMapper;
import com.tamguo.model.ChapterEntity;
import com.tamguo.model.CourseEntity;

public interface CourseMapper extends SuperMapper<CourseEntity>{

	List<CourseEntity> findBySubjectId(String uid);

	List<ChapterEntity> findByCourseId(@Param(value="courseId") String courseId);

}
