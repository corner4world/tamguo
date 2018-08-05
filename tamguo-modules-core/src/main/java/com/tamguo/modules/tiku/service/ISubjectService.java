package com.tamguo.modules.tiku.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.model.condition.SubjectCondition;

public interface ISubjectService extends IService<SubjectEntity>{

	Page<SubjectEntity> listData(SubjectCondition condition);

	/** 保存分类*/
	void save(SubjectEntity subject);

	/** 修改分类*/
	void update(SubjectEntity subject);

	/** 激活分类*/
	void enable(String uid);
	
	/** 停用分类*/
	void disabled(String uid);

	/** 删除分类*/
	void delete(String uid);

	JSONArray getCourseCascaderTree();

}
