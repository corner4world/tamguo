package com.tamguo.modules.tiku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.tiku.dao.BookMapper;
import com.tamguo.modules.tiku.dao.CourseMapper;
import com.tamguo.modules.tiku.dao.SubjectMapper;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.model.condition.BookCondition;
import com.tamguo.modules.tiku.model.enums.BookStatusEnum;
import com.tamguo.modules.tiku.model.enums.CourseStatusEnum;
import com.tamguo.modules.tiku.model.enums.SubjectStatusEnum;
import com.tamguo.modules.tiku.service.IBookService;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, BookEntity> implements IBookService{

	@Autowired
	BookMapper bookMapper;
	@Autowired
	CourseMapper courseMapper;
	@Autowired
	SubjectMapper subjectMapper;
	
	@Transactional(readOnly=false)
	@Override
	public Page<BookEntity> listData(BookCondition condition) {
		Page<BookEntity> page = new Page<>(condition.getPageNo() , condition.getPageSize());
		return page.setRecords(bookMapper.listData(page, condition));
	}

	@Transactional(readOnly=false)
	@Override
	public void save(BookEntity book) {
		CourseEntity course = courseMapper.selectById(book.getCourseId());
		
		book.setStatus(BookStatusEnum.NORMAL);
		book.setSubjectId(course.getSubjectId());
		bookMapper.insert(book);
	}

	@Transactional(readOnly=false)
	@Override
	public void update(BookEntity book) {
		CourseEntity course = courseMapper.selectById(book.getCourseId());
		BookEntity entity = bookMapper.selectById(book.getId());
		
		entity.setName(book.getName());
		entity.setPointNum(book.getPointNum());
		entity.setQuestionNum(book.getQuestionNum());
		entity.setRemarks(book.getRemarks());
		entity.setPublishingHouse(book.getPublishingHouse());
		entity.setSort(book.getSort());
		entity.setCourseId(course.getId());
		
		bookMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void delete(String id) {
		BookEntity book = bookMapper.selectById(id);
		book.setStatus(BookStatusEnum.DELETE);
		bookMapper.updateById(book);
	}

	@Transactional(readOnly=false)
	@Override
	public void enable(String id) {
		BookEntity book = bookMapper.selectById(id);
		book.setStatus(BookStatusEnum.NORMAL);
		bookMapper.updateById(book);
	}

	@Transactional(readOnly=false)
	@Override
	public void disabled(String id) {
		BookEntity book = bookMapper.selectById(id);
		book.setStatus(BookStatusEnum.DISABLED);
		bookMapper.updateById(book);
	}

	@Transactional(readOnly=false)
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray treeData() {
		List<SubjectEntity> subjectList = subjectMapper.selectList(Condition.create().eq("status", SubjectStatusEnum.NORMAL.getValue()));
		List<CourseEntity> courseList = courseMapper.selectList(Condition.create().eq("status", CourseStatusEnum.NORMAL.getValue()));
		List<BookEntity> bookList = bookMapper.selectList(Condition.create().eq("status", BookStatusEnum.NORMAL.getValue()));
		return transform(subjectList, courseList , bookList);
	}

	private JSONArray transform(List<SubjectEntity> subjectList , List<CourseEntity> courseList , List<BookEntity> bookList) {
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
		for(int i=0 ; i<bookList.size() ; i++) {
			JSONObject entity = new JSONObject();
			entity.put("id", bookList.get(i).getId());
			entity.put("name", bookList.get(i).getName());
			entity.put("pId", bookList.get(i).getCourseId());
			entitys.add(entity);
		}
		return entitys;
	}
}
