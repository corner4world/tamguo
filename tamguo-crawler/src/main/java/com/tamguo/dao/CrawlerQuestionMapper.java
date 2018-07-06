package com.tamguo.dao;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.model.CrawlerQuestionEntity;

public interface CrawlerQuestionMapper extends SuperMapper<CrawlerQuestionEntity>{

	List<CrawlerQuestionEntity> queryPageOrderUid(Pagination page);
	
}
