package com.tamguo.modules.tiku.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.condition.ChapterCondition;

public interface IChapterService extends IService<ChapterEntity>{
	
	// 获取科目章节
	public List<ChapterEntity> findChapterTree(String bookId);

	// 章节列表
	public List<ChapterEntity> listData(ChapterCondition condition);

	// 章节树形数据
	public JSONArray treeData(String courseId, String excludeId);

	// 保存章节
	public void save(ChapterEntity chapter);

	// 修改章节
	public void update(ChapterEntity chapter);

	// 删除章节
	public void delete(String id);

	// 科目章节
	public List<ChapterEntity> findCourseChapter(String courseId);

	// 获取下一章节
	public ChapterEntity selectNextChapter(String parentCode , String id);

}
