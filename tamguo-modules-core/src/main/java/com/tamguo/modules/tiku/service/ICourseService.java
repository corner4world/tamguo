package com.tamguo.modules.tiku.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.condition.CourseCondition;

public interface ICourseService extends IService<CourseEntity>{

	Page<CourseEntity> listData(CourseCondition condition);

	/** 保存科目*/
	void save(CourseEntity course);

	/** 修改科目*/
	void update(CourseEntity course);

}
