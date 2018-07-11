package com.tamguo.dao;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.model.QuestionEntity;

public interface QuestionMapper extends SuperMapper<QuestionEntity>{

	List<QuestionEntity> findPage(String chapterId, Pagination page);


}
