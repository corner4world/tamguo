package com.tamguo.modules.tiku.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.modules.tiku.dao.ChapterMapper;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.service.IChapterService;

@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, ChapterEntity> implements IChapterService{

	@Transactional(readOnly=false)
	@SuppressWarnings("unchecked")
	@Override
	public List<ChapterEntity> findChapterTree(String bookId) {
		List<ChapterEntity> chapterList = baseMapper.selectList(Condition.create().eq("book_id", bookId));
		
		// 获取根chapter UID
		String rootUid = StringUtils.EMPTY;
		for(int i=0 ; i<chapterList.size() ; i++){
			ChapterEntity chapter = chapterList.get(i);
			if(chapter.getParentId().equals(SystemConstant.CHAPTER_DEFAULT_ROOT_UID)){
				rootUid = chapter.getId();
			}
		}
		// 获取第一层结构
		List<ChapterEntity> entitys = new ArrayList<>();
		for(int i=0 ; i<chapterList.size() ; i++){
			ChapterEntity chapter = chapterList.get(i);
			if(rootUid.equals(chapter.getParentId())){
				entitys.add(chapter);
			}
		}
		for(int i=0 ; i<entitys.size() ; i++){
			ChapterEntity entity = entitys.get(i);
			List<ChapterEntity> childs = new ArrayList<>();
			for(int k=0 ; k<chapterList.size() ; k++){
				ChapterEntity chapter = chapterList.get(k);
				if(entity.getId().equals(chapter.getParentId())){
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
					if(child.getId().equals(chapter.getParentId())){
						tmpChilds.add(chapter);
					}
				}
				child.setChildChapterList(tmpChilds);
			}
		}
		return entitys;
	}
	
}
