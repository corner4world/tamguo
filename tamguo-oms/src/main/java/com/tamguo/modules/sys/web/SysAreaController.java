package com.tamguo.modules.sys.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.tamguo.modules.sys.model.SysAreaEntity;
import com.tamguo.modules.sys.model.condition.SysAreaCondition;
import com.tamguo.modules.sys.service.ISysAreaService;

@Controller
@RequestMapping(path="sys/area")
public class SysAreaController {

	private final String AREA_INDEX_PAGE = "modules/sys/area/index";

	@Autowired
	private ISysAreaService iSysAreaService; 

	
	@RequestMapping(path="index")
	public String index() {
		return AREA_INDEX_PAGE;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public List<SysAreaEntity> listData(SysAreaCondition condition) {
		List<SysAreaEntity> list = iSysAreaService.listData(condition);
		return list;
	}
	
	@RequestMapping(path="treeData")
	@ResponseBody
	public JSONArray treeData(String excludeId) {
		return iSysAreaService.treeData(excludeId);
	}
	
	
}
