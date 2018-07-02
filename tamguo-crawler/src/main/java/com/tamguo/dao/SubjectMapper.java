package com.tamguo.dao;

import org.apache.ibatis.annotations.Param;

import com.tamguo.config.dao.SuperMapper;
import com.tamguo.model.SubjectEntity;

public interface SubjectMapper extends SuperMapper<SubjectEntity>{

	SubjectEntity findByName(@Param(value="name")String name);

}
