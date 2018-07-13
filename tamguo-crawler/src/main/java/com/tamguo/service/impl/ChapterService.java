package com.tamguo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.dao.ChapterMapper;
import com.tamguo.model.ChapterEntity;
import com.tamguo.service.IChapterService;

@Service
public class ChapterService implements IChapterService{
	
	@Autowired
	ChapterMapper chapterMapper;
	
	@Override
	public void modifyQuestionNum() {
		int page = 1;
		int pageSize = 1000;
		while(true) {
			Page<ChapterEntity> chapterPage = new Page<ChapterEntity>(page , pageSize);
			List<ChapterEntity> chapterList = chapterMapper.queryList(chapterPage);
			for(int i=0 ;i<chapterList.size() ; i++) {
				// 处理数据
				ChapterEntity chapter = chapterList.get(i);
				Integer count = chapterMapper.queryCount(chapter.getUid());
				chapter.setQuestionNum(count);
				chapterMapper.updateById(chapter);
			}
			page++;
			if(chapterList.size() < 1000) {
				break;
			}
		}
	}

}
