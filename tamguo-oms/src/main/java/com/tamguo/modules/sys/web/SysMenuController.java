package com.tamguo.modules.sys.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.tamguo.modules.sys.model.SysMenuEntity;
import com.tamguo.modules.sys.model.condition.SysMenuCondition;
import com.tamguo.modules.sys.service.ISysMenuService;

@Controller
@RequestMapping(path="sys/menu")
public class SysMenuController {

	private final String MENU_INDEX_PAGE = "modules/sys/menu/index";

	@Autowired
	private ISysMenuService iSysMenuService;

	@RequestMapping(path="index")
	public String index() {
		return MENU_INDEX_PAGE;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public List<SysMenuEntity> listData(SysMenuCondition condition) {
		List<SysMenuEntity> list = iSysMenuService.listData(condition);
		return list;
	}
	
	@RequestMapping(path="treeData")
	@ResponseBody
	public JSONArray treeData(String excludeId , String sysCode , String isShowNameOrig) {
		return iSysMenuService.treeData(excludeId);
	}
	
}
