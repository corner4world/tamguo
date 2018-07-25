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

	private final String ROLE_INDEX_PAGE = "modules/sys/role/index";
	
	@Autowired
	private ISysRoleService iSysRoleService;

	@RequestMapping(path="index")
	public String index() {
		return ROLE_INDEX_PAGE;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysRoleCondition condition) {
		Page<SysRoleEntity> page = iSysRoleService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
	
}
