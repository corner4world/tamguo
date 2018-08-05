package com.tamguo.modules.tiku.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.tiku.model.ChapterEntity;

public interface IChapterService extends IService<ChapterEntity>{
	
	// 获取科目章节
	public List<ChapterEntity> findChapterTree(String bookId);

}
