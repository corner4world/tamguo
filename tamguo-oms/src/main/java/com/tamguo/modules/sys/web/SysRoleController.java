package com.tamguo.modules.sys.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.model.SysRoleEntity;
import com.tamguo.modules.sys.model.condition.SysRoleCondition;
import com.tamguo.modules.sys.service.ISysRoleService;
import com.tamguo.modules.sys.utils.Result;

@Controller
@RequestMapping(path="sys/role")
public class SysRoleController {

	// 角色列表
	private final String ROLE_INDEX_PAGE = "modules/sys/role/index";
	// 分配角色菜单权限
	private final String ROLE_MENU_INDEX_PAGE = "modules/sys/role/menu";
	
	@Autowired
	private ISysRoleService iSysRoleService;

	/** 角色首页*/
	@RequestMapping(path="index")
	public String index() {
		return ROLE_INDEX_PAGE;
	}
	
	/** 角色授权功能菜单*/
	@RequestMapping(path="menu")
	public String menu() {
		return ROLE_MENU_INDEX_PAGE;
	}
	
	/** 列表数据*/
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysRoleCondition condition) {
		Page<SysRoleEntity> page = iSysRoleService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
	
	@RequestMapping(path="menuTreeData")
	@ResponseBody
	public Map<String, Object> menuTreeData(String roleCode) {
		return iSysRoleService.menuTreeData(roleCode);
	}
	
}
