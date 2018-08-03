package com.tamguo.modules.tiku.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.condition.SubjectCondition;

public interface ICourseService {

	Page<CourseEntity> listData(SubjectCondition condition);

}
