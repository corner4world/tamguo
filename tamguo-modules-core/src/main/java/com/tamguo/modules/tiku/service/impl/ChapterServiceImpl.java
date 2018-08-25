package com.tamguo.modules.tiku.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.modules.tiku.dao.BookMapper;
import com.tamguo.modules.tiku.dao.ChapterMapper;
import com.tamguo.modules.tiku.dao.CourseMapper;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.condition.ChapterCondition;
import com.tamguo.modules.tiku.model.enums.ChapterStatusEnum;
import com.tamguo.modules.tiku.service.IChapterService;

@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, ChapterEntity> implements IChapterService{
	
	@Autowired
	ChapterMapper chapterMapper;
	@Autowired
	CourseMapper courseMapper;
	@Autowired
	BookMapper bookMapper;

	@Transactional(readOnly=false)
	@SuppressWarnings("unchecked")
	@Override
	public List<ChapterEntity> findChapterTree(String bookId) {
		List<ChapterEntity> chapterList = baseMapper.selectList(Condition.create().eq("book_id", bookId));
		
		// 获取根chapter UID
		String rootUid = StringUtils.EMPTY;
		for(int i=0 ; i<chapterList.size() ; i++){
			ChapterEntity chapter = chapterList.get(i);
			if(chapter.getParentCode().equals(SystemConstant.CHAPTER_DEFAULT_ROOT_UID)){
				rootUid = chapter.getId();
			}
		}
		// 获取第一层结构
		List<ChapterEntity> entitys = new ArrayList<>();
		for(int i=0 ; i<chapterList.size() ; i++){
			ChapterEntity chapter = chapterList.get(i);
			if(rootUid.equals(chapter.getParentCode())){
				entitys.add(chapter);
			}
		}
		for(int i=0 ; i<entitys.size() ; i++){
			ChapterEntity entity = entitys.get(i);
			List<ChapterEntity> childs = new ArrayList<>();
			for(int k=0 ; k<chapterList.size() ; k++){
				ChapterEntity chapter = chapterList.get(k);
				if(entity.getId().equals(chapter.getParentCode())){
					childs.add(chapter);
				}
			}
			entity.setChildChapterList(childs);
		}
		for(int i=0 ; i<entitys.size() ; i++){
			List<ChapterEntity> childs = entitys.get(i).getChildChapterList();
			for(int k=0 ; k<childs.size() ; k++){
				ChapterEntity child = childs.get(k);
				List<ChapterEntity> tmpChilds = new ArrayList<>();
				for(int n=0 ; n<chapterList.size() ; n++){
					ChapterEntity chapter = chapterList.get(n);
					if(child.getId().equals(chapter.getParentCode())){
						tmpChilds.add(chapter);
					}
				}
				child.setChildChapterList(tmpChilds);
			}
		}
		return entitys;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChapterEntity> listData(ChapterCondition condition) {
		Condition query = Condition.create();
		if(!StringUtils.isEmpty(condition.getParentCode())) {
			query.eq("parent_code", condition.getParentCode());
		}else {
			query.eq("tree_level", "0");
		}
		if(!StringUtils.isEmpty(condition.getId())) {
			query.eq("id", condition.getId());
		}
		if(!StringUtils.isEmpty(condition.getName())) {
			query.like("name", condition.getName());
		}
		if(!StringUtils.isEmpty(condition.getBookId())) {
			query.andNew().eq("course_id", condition.getBookId()).or().eq("book_id", condition.getBookId()).or().eq("subject_id", condition.getBookId());;
		}
		return chapterMapper.selectList(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray treeData(String courseId, String excludeId) {
		List<ChapterEntity> chapterList = null;
		if(StringUtils.isEmpty(excludeId)) {
			chapterList = chapterMapper.selectList(Condition.EMPTY);
		} else {
			chapterList = chapterMapper.selectList(Condition.create().notLike("parent_codes", excludeId).eq("id", excludeId));
		}
		return turnZTreeData(chapterList);
	}
	
	private JSONArray turnZTreeData(List<ChapterEntity> chapterList) {
		if(chapterList != null) {
			JSONArray nodes = new JSONArray();
			for(int i=0 ; i<chapterList.size() ; i++) {
				JSONObject node = new JSONObject();
				
				ChapterEntity office = chapterList.get(i);
				node.put("name", office.getName());
				node.put("id", office.getId());
				node.put("pId", office.getParentCode());
				node.put("title", office.getName());
				nodes.add(node);
			}
			return nodes;
		}
		return null;
	}

	@Transactional(readOnly=false)
	@Override
	public void save(ChapterEntity chapter) {
		ChapterEntity parentChapter = chapterMapper.selectById(chapter.getParentCode());
		parentChapter.setTreeLeaf(false);
		chapterMapper.updateById(parentChapter);
		
		chapter.setStatus(ChapterStatusEnum.NORMAL);
		chapter.setTreeLeaf(true);
		chapter.setTreeLevel(parentChapter.getTreeLevel() + 1);
		chapter.setBookId(parentChapter.getBookId());
		chapterMapper.insert(chapter);
		
		chapter.setParentCodes(parentChapter.getParentCodes() + chapter.getId() + ",");
		chapterMapper.updateById(chapter);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	@Override
	public void update(ChapterEntity chapter) {
		ChapterEntity entity = chapterMapper.selectById(chapter.getId());
		ChapterEntity parentChapter = chapterMapper.selectById(chapter.getParentCode());
		parentChapter.setTreeLeaf(false);
		chapterMapper.updateById(parentChapter);
		
		entity.setName(chapter.getName());
		entity.setParentCode(chapter.getParentCode());
		entity.setParentCodes(parentChapter.getParentCodes() + entity.getId() + ",");
		entity.setQuestionNum(chapter.getQuestionNum());
		entity.setPointNum(chapter.getPointNum());
		entity.setOrders(chapter.getOrders());
		entity.setTreeLevel(entity.getParentCodes().split(",").length - 1);
		
		// 更新子集章节
		List<ChapterEntity> chapterList = chapterMapper.selectList(Condition.create().like("parent_codes", entity.getId()));
		for(int i=0 ; i<chapterList.size() ; i++) {
			ChapterEntity child = chapterList.get(i);
			
			String subParentCodes = child.getParentCodes().substring(child.getParentCodes().indexOf(entity.getId()) + entity.getId().length() + 1);
			child.setParentCodes(entity.getParentCodes() + subParentCodes);
			child.setTreeLevel(child.getParentCodes().split(",").length - 1);
			chapterMapper.updateById(child);
		}
		chapterMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@SuppressWarnings("unchecked")
	@Override
	public void delete(String id) {
		ChapterEntity chapter = chapterMapper.selectById(id);
		chapter.setStatus(ChapterStatusEnum.DELETE);
		chapterMapper.updateById(chapter);
		// 删除子章节
		List<ChapterEntity> childs = chapterMapper.selectList(Condition.create().like("parent_codes", id));
		for(int i=0 ; i<childs.size() ; i++) {
			ChapterEntity child = childs.get(i);
			child.setStatus(ChapterStatusEnum.DELETE);
			chapterMapper.updateById(child);
		}
	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ChapterEntity> findCourseChapter(String courseId) {
		List<BookEntity> bookList = bookMapper.selectList(Condition.create().eq("course_id", courseId));
		if(bookList.size() == 0) {
			return null;
		}
		Condition condition = Condition.create();
		condition.eq("tree_level", 1);
		condition.eq("book_id", bookList.get(0).getId());
		List<ChapterEntity> list = chapterMapper.selectPage(new Page<>(1, 5), condition);
		return list;
	}

	@Transactional(readOnly=true)
	@Override
	public ChapterEntity selectNextChapter(String parentCode , String id) {
		return chapterMapper.selectNextChapter(parentCode , id);
	}
}
