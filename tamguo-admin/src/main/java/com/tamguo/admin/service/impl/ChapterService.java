package com.tamguo.admin.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.admin.dao.ChapterMapper;
import com.tamguo.admin.model.ChapterEntity;
import com.tamguo.admin.service.IChapterService;
import com.tamguo.admin.util.ShiroUtils;
import com.tamguo.admin.util.TamguoConstant;

@Service
public class ChapterService extends ServiceImpl<ChapterMapper, ChapterEntity> implements IChapterService{
	
	@Autowired
	private ChapterMapper chapterMapper;

	@Override
	public ChapterEntity findById(String uid) {
		return chapterMapper.selectById(uid);
	}

	@Override
	public ChapterEntity findNextPoint(String uid , Integer orders) {
		return chapterMapper.findNextPoint(uid , orders);
	}

	@Transactional(readOnly=false)
	@Override
	public List<ChapterEntity> getChapterTree(String bookId) {
		if(StringUtils.isEmpty(bookId) || "null".equals(bookId)){
			return rootChapterNode();
		}
		List<ChapterEntity> list = chapterMapper.findByBookId(bookId);
		if(CollectionUtils.isEmpty(list)) {
			return rootChapterNode();
		}
		return list;
	}
	
	@Transactional(readOnly=false)
	@Override
	public List<ChapterEntity> getChapterTree() {
		List<ChapterEntity> list = chapterMapper.findByBookId(ShiroUtils.getUser().getCourseId());
		if(CollectionUtils.isEmpty(list)) {
			return rootChapterNode();
		}
		return list;
	}


	private List<ChapterEntity> rootChapterNode(){
		ChapterEntity chapter = new ChapterEntity();
		chapter.setCourseId(TamguoConstant.CHAPTER_DEFAULT_ROOT_UID);
		chapter.setOrders(0);
		chapter.setPointNum(0);
		chapter.setQuestionNum(0);
		chapter.setUid("0");
		chapter.setName("一级章节");
		chapter.setParentId(TamguoConstant.CHAPTER_DEFAULT_ROOT_UID);
		return Arrays.asList(chapter);
	}

	@Transactional(readOnly=true)
	@Override
	public Page<ChapterEntity> queryPage(String name, Page<ChapterEntity> page) {
		return page.setRecords(chapterMapper.queryPage(name , page));
	}

	@Transactional(readOnly=true)
	@Override
	public ChapterEntity select(String chapterId) {
		return chapterMapper.select(chapterId);
	}

	@Transactional(readOnly=false)
	@Override
	public void deleteByIds(String[] chapterIds) {
		chapterMapper.deleteBatchIds(Arrays.asList(chapterIds));
	}

	@Transactional(readOnly=false)
	@Override
	public void save(ChapterEntity chapter) {
		chapter.setCourseId(ShiroUtils.getUser().getCourseId());
		chapterMapper.insert(chapter);
	}

	@Transactional(readOnly=false)
	@Override
	public void update(ChapterEntity chapter) {
		chapterMapper.updateById(chapter);
	}

}
