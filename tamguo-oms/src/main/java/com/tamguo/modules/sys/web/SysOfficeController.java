package com.tamguo.modules.sys.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.tamguo.modules.sys.model.SysOfficeEntity;
import com.tamguo.modules.sys.model.condition.SysOfficeCondition;
import com.tamguo.modules.sys.service.ISysOfficeService;

/**
 * Controller - 组织
 * 
 * @author tamguo
 *
 */
@Controller
@RequestMapping(path="sys/office")
public class SysOfficeController {

	@Autowired
	private ISysOfficeService iSysOfficeService; 
	
	@RequestMapping(path="listData")
	@ResponseBody
	public List<SysOfficeEntity> listData(SysOfficeCondition condition) {
		return iSysOfficeService.listData(condition);
	}
	
	@RequestMapping(path="treeData")
	@ResponseBody
	public JSONArray treeData(String excludeId) {
		return iSysOfficeService.treeData(excludeId);
	}

	
}
