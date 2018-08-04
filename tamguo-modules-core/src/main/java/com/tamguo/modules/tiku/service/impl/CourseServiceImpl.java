package com.tamguo.modules.tiku.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.tiku.dao.CourseMapper;
import com.tamguo.modules.tiku.dao.SubjectMapper;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.model.condition.CourseCondition;
import com.tamguo.modules.tiku.model.enums.CourseStatusEnum;
import com.tamguo.modules.tiku.model.enums.SubjectStatusEnum;
import com.tamguo.modules.tiku.service.ICourseService;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements ICourseService{
	
	@Autowired
	public CourseMapper courseMapper;
	@Autowired
	public SubjectMapper subjectMapper;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public Page<CourseEntity> listData(CourseCondition condition) {
		Page<CourseEntity> page = new Page<>(condition.getPageNo() , condition.getPageSize());
		Condition query = Condition.create();
		if(!StringUtils.isEmpty(condition.getUid())) {
			query.eq("uid", condition.getUid());
		}
		if(!StringUtils.isEmpty(condition.getName())) {
			query.like("name", condition.getName());
		}
		if(!StringUtils.isEmpty(condition.getSubjectId())) {
			query.eq("subject_id", condition.getSubjectId());
		}
		if(!StringUtils.isEmpty(condition.getStatus())) {
			query.eq("status", condition.getStatus());
		}
		query.orderAsc(Arrays.asList("sort"));
		return page.setRecords(courseMapper.selectPage(page , query));
	}

	@Transactional(readOnly=false)
	@Override
	public void save(CourseEntity course) {
		course.setStatus(CourseStatusEnum.NORMAL);
		courseMapper.insert(course);
	}

	@Transactional(readOnly=false)
	@Override
	public void update(CourseEntity course) {
		CourseEntity entity = courseMapper.selectById(course.getId());
		entity.setName(course.getName());
		entity.setSubjectId(course.getSubjectId());
		entity.setSort(course.getSort());
		entity.setRemarks(course.getRemarks());
		courseMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void delete(String uid) {
		CourseEntity entity = courseMapper.selectById(uid);
		entity.setStatus(CourseStatusEnum.DELETE);
		courseMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void enable(String uid) {
		CourseEntity entity = courseMapper.selectById(uid);
		entity.setStatus(CourseStatusEnum.NORMAL);
		courseMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void disabled(String uid) {
		CourseEntity entity = courseMapper.selectById(uid);
		entity.setStatus(CourseStatusEnum.DISABLED);
		courseMapper.updateById(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray treeData() {
		List<SubjectEntity> subjectList = subjectMapper.selectList(Condition.create().eq("status", SubjectStatusEnum.NORMAL.getValue()));
		List<CourseEntity> courseList = courseMapper.selectList(Condition.create().eq("status", CourseStatusEnum.NORMAL.getValue()));
		return transform(subjectList, courseList);
	}
	
	private JSONArray transform(List<SubjectEntity> subjectList , List<CourseEntity> courseList) {
		JSONArray entitys = new JSONArray();
		for(int i=0 ; i<subjectList.size() ; i++) {
			JSONObject entity = new JSONObject();
			entity.put("id", subjectList.get(i).getId());
			entity.put("name", subjectList.get(i).getName());
			entity.put("pId", "0");
			entitys.add(entity);
		}
		for(int i=0 ; i<courseList.size() ; i++) {
			JSONObject entity = new JSONObject();
			entity.put("id", courseList.get(i).getId());
			entity.put("name", courseList.get(i).getName());
			entity.put("pId", courseList.get(i).getSubjectId());
			entitys.add(entity);
		}
		return entitys;
	}

}
