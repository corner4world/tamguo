package com.tamguo.modules.sys.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.tamguo.modules.sys.model.SysOfficeEntity;
import com.tamguo.modules.sys.model.condition.SysOfficeCondition;

public interface ISysOfficeService {

	List<SysOfficeEntity> listData(SysOfficeCondition condition);

	JSONArray treeData(String excludeId);

	void save(SysOfficeEntity office);

}
