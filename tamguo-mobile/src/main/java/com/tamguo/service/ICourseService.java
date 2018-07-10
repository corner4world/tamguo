package com.tamguo.service;

import java.util.List;
import com.tamguo.model.ChapterEntity;

public interface ICourseService {

	// 获取科目章节
	public List<ChapterEntity> findCourseChapter(String courseId);

}
