package com.tamguo.modules.sys.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.sys.model.SysMenuEntity;
import com.tamguo.modules.sys.model.condition.SysMenuCondition;
import com.tamguo.modules.sys.service.ISysMenuService;

@Controller
@RequestMapping(path="sys/menu")
public class SysMenuController {

	private final String MENU_INDEX_PAGE = "modules/sys/menu/index";
	private final String MENU_UPDATE_PAGE = "modules/sys/menu/update";

	@Autowired
	private ISysMenuService iSysMenuService;

	@RequestMapping(path="index")
	public String index() {
		return MENU_INDEX_PAGE;
	}
	
	@RequestMapping(path="update")
	public ModelAndView update(String menuCode , ModelAndView model) {
		model.setViewName(MENU_UPDATE_PAGE);
		SysMenuEntity menu = iSysMenuService.selectById(menuCode);
		SysMenuEntity parentMenu = iSysMenuService.selectById(menu.getParentCode());
		model.addObject("menu", menu);
		model.addObject("parentMenu", parentMenu);
		return model;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public List<SysMenuEntity> listData(SysMenuCondition condition) {
		List<SysMenuEntity> list = iSysMenuService.listData(condition);
		return list;
	}
	
	@RequestMapping(path="save")
	@ResponseBody
	public Result save(SysMenuEntity menu) {
		try {
			iSysMenuService.save(menu);
			return Result.result(0, null, "新增菜单【"+menu.getMenuName()+"】成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("新增菜单", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="update" , method=RequestMethod.POST)
	@ResponseBody
	public Result post(SysMenuEntity menu) {
		try {
			iSysMenuService.update(menu);
			return Result.result(0, null, "修改菜单【"+menu.getMenuName()+"】成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("修改菜单", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="treeData")
	@ResponseBody
	public JSONArray treeData(String excludeId , String sysCode , String isShowNameOrig) {
		return iSysMenuService.treeData(excludeId);
	}
	
}
