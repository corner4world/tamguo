package com.tamguo.modules.tiku.service.impl;

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
import com.tamguo.modules.tiku.model.condition.SubjectCondition;
import com.tamguo.modules.tiku.model.enums.SubjectStatusEnum;
import com.tamguo.modules.tiku.service.ISubjectService;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, SubjectEntity> implements ISubjectService{
	
	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
	private CourseMapper courseMapper;

	@Transactional(readOnly=false)
	@SuppressWarnings("unchecked")
	@Override
	public Page<SubjectEntity> listData(SubjectCondition condition) {
		Page<SubjectEntity> page = new Page<>(condition.getPageNo(), condition.getPageSize());
		Condition query = Condition.create();
		if(!StringUtils.isEmpty(condition.getUid())) {
			query.eq("uid", condition.getUid());
		}else if(!StringUtils.isEmpty(condition.getName())) {
			query.eq("name", condition.getName());
		}else if(!StringUtils.isEmpty(condition.getStatus())) {
			query.eq("status", condition.getStatus());
		}
		query.ne("status", SubjectStatusEnum.DELETE.getValue());
		return page.setRecords(subjectMapper.selectPage(page, query));
	}

	@Transactional(readOnly=false)
	@Override
	public void save(SubjectEntity subject) {
		subject.setStatus(SubjectStatusEnum.NORMAL);
		subjectMapper.insert(subject);
	}

	@Transactional(readOnly=false)
	@Override
	public void update(SubjectEntity subject) {
		SubjectEntity entity = subjectMapper.selectById(subject.getId());
		entity.setName(subject.getName());
		entity.setRemarks(subject.getRemarks());
		entity.setSort(subject.getSort());
		entity.setSeoTitle(subject.getSeoTitle());
		entity.setSeoKeywords(subject.getSeoKeywords());
		entity.setSeoDescription(subject.getSeoDescription());
		subjectMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void enable(String uid) {
		SubjectEntity entity = subjectMapper.selectById(uid);
		entity.setStatus(SubjectStatusEnum.NORMAL);
		subjectMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void disabled(String uid) {
		SubjectEntity entity = subjectMapper.selectById(uid);
		entity.setStatus(SubjectStatusEnum.DISABLED);
		subjectMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void delete(String uid) {
		SubjectEntity entity = subjectMapper.selectById(uid);
		entity.setStatus(SubjectStatusEnum.DELETE);
		subjectMapper.updateById(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getCourseCascaderTree() {
		JSONArray courseTree = new JSONArray();
		List<SubjectEntity> subjectList = subjectMapper.selectList(Condition.EMPTY);
		for(int i=0 ; i<subjectList.size() ; i++){
			SubjectEntity subject = subjectList.get(i);
			JSONObject node = new JSONObject();
			node.put("value", subject.getId());
			node.put("label", subject.getName());
			
			JSONArray children = new JSONArray();
			List<CourseEntity> courseList = courseMapper.selectList(Condition.create().eq("subject_id", subject.getId()));
			for(int k=0 ; k<courseList.size() ; k++){
				CourseEntity course = courseList.get(k);
				
				JSONObject no = new JSONObject();
				no.put("value", course.getId());
				no.put("label", course.getName());
				children.add(no);
			}
			node.put("children", children);
			courseTree.add(node);
		}
		return courseTree;
	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getSubjectTree() {
		JSONArray courseTree = new JSONArray();
		List<SubjectEntity> subjectList = subjectMapper.selectList(Condition.EMPTY);
		for(int i=0 ; i<subjectList.size() ; i++){
			SubjectEntity subject = subjectList.get(i);
			JSONObject node = new JSONObject();
			node.put("value", subject.getId());
			node.put("label", subject.getName());
			courseTree.add(node);
		}
		return courseTree;
	}

}
