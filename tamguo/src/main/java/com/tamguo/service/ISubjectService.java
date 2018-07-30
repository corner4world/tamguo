package com.tamguo.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.model.SubjectEntity;

public interface ISubjectService extends IService<SubjectEntity>{

	public SubjectEntity find(String uid);
	
	public JSONArray getCourseTree();

	public JSONArray getCourseCascaderTree();
	
}
