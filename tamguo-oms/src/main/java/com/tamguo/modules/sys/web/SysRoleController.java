package com.tamguo.modules.sys.web;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.model.SysRoleEntity;
import com.tamguo.modules.sys.model.condition.SysRoleCondition;
import com.tamguo.modules.sys.service.ISysRoleDataScopeService;
import com.tamguo.modules.sys.service.ISysRoleService;
import com.tamguo.modules.sys.utils.Result;

@Controller
@RequestMapping(path="sys/role")
public class SysRoleController {

	// 分配角色菜单权限
	private final String ROLE_MENU_INDEX_PAGE = "modules/sys/role/menu";
	// 数据权限
	private final String ROLE_DATA_INDEX_PAGE = "modules/sys/role/dataScope";

	@Autowired
	private ISysRoleService iSysRoleService;
	@Autowired
	private ISysRoleDataScopeService iSysRoleDataScopeService;

	/** 角色授权功能菜单*/
	@RequestMapping(path="menu")
	public ModelAndView menu(String roleCode , ModelAndView model) {
		model.addObject("role", iSysRoleService.selectById(roleCode));
		model.setViewName(ROLE_MENU_INDEX_PAGE);
		return model;
	}
	
	/** 数据权限 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path="dataScope")
	public ModelAndView dataScope(String roleCode , ModelAndView model) {
		model.addObject("role", iSysRoleService.selectById(roleCode));
		model.addObject("roleDataScopeList" , iSysRoleDataScopeService.selectList(Condition.create().eq("role_code", roleCode)));
		model.setViewName(ROLE_DATA_INDEX_PAGE);
		return model;
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

	/** 角色授权功能菜单 */
	@RequestMapping(path="allowMenuPermission",method=RequestMethod.POST)
	@ResponseBody
	public Result allowMenuPermission(SysRoleEntity role) {
		iSysRoleService.allowMenuPermission(role);
		return Result.result(0, null, "保存角色【"+role.getRoleName()+"】成功！");
	}
	
	/** 数据权限 */
	@RequestMapping(path="allowDataScope",method=RequestMethod.POST)
	@ResponseBody
	public Result allowDataScope(SysRoleEntity role) {
		iSysRoleService.allowDataScope(role);
		return Result.result(0, null, "保存角色【"+role.getRoleName()+"】成功！");
	}
	
	/** 授权用户 */
	@RequestMapping(path="allowUser",method=RequestMethod.POST)
	@ResponseBody
	public Result allowUser(SysRoleEntity role) {
		iSysRoleService.allowDataScope(role);
		return Result.result(0, null, "保存角色【"+role.getRoleName()+"】成功！");
	}
	
	/** 角色树结构*/
	@RequestMapping(path="treeData",method=RequestMethod.POST)
	@ResponseBody
	public List<SysRoleEntity> treeData(String userType){
		return iSysRoleService.treeDate(userType);
	}
	
}
