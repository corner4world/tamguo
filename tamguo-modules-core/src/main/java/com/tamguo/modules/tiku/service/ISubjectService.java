package com.tamguo.modules.tiku.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.model.condition.SubjectCondition;

public interface ISubjectService extends IService<SubjectEntity>{

	Page<SubjectEntity> listData(SubjectCondition condition);

}
