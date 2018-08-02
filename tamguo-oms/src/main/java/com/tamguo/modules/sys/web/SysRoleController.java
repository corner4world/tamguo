package com.tamguo.modules.sys.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.sys.model.SysRoleEntity;
import com.tamguo.modules.sys.model.condition.SysRoleCondition;
import com.tamguo.modules.sys.service.ISysRoleDataScopeService;
import com.tamguo.modules.sys.service.ISysRoleService;

@Controller
@RequestMapping(path="sys/role")
public class SysRoleController {

	// 分配角色菜单权限
	private final String ROLE_MENU_INDEX_PAGE = "modules/sys/role/menu";
	// 数据权限
	private final String ROLE_DATA_INDEX_PAGE = "modules/sys/role/dataScope";
	// 修改角色
	private final String ROLE_MENU_UPDATE_PAGE = "modules/sys/role/update";

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
	
	/** 修改角色*/
	@RequestMapping(path="update")
	public ModelAndView update(String roleCode , ModelAndView model) {
		model.addObject("role", iSysRoleService.selectById(roleCode));
		model.setViewName(ROLE_MENU_UPDATE_PAGE);
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
	
	/** 角色列表数据*/
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysRoleCondition condition) {
		try {
			Page<SysRoleEntity> page = iSysRoleService.listData(condition);
			return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
		} catch (Exception e) {
			ExceptionSupport.resolverResult("角色列表", this.getClass(), e);
			return null;
		}
	}
	
	/** 菜单树*/
	@RequestMapping(path="menuTreeData")
	@ResponseBody
	public Map<String, Object> menuTreeData(String roleCode) {
		try {
			return iSysRoleService.menuTreeData(roleCode);
		} catch (Exception e) {
			ExceptionSupport.resolverResult("菜单树", this.getClass(), e);
			return null;
		}
		
	}

	/** 角色授权功能菜单 */
	@RequestMapping(path="allowMenuPermission",method=RequestMethod.POST)
	@ResponseBody
	public Result allowMenuPermission(SysRoleEntity role) {
		try {
			iSysRoleService.allowMenuPermission(role);
			return Result.result(0, null, "保存角色【"+role.getRoleName()+"】成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("授权功能菜单", this.getClass(), e);
		}
	}
	
	/** 数据权限 */
	@RequestMapping(path="allowDataScope",method=RequestMethod.POST)
	@ResponseBody
	public Result allowDataScope(SysRoleEntity role) {
		try {
			iSysRoleService.allowDataScope(role);
			return Result.result(0, null, "保存角色【"+role.getRoleName()+"】成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("授权数据权限", this.getClass(), e);
		}
	}
	
	/** 授权用户 */
	@RequestMapping(path="allowUser",method=RequestMethod.POST)
	@ResponseBody
	public Result allowUser(SysRoleEntity role) {
		try {
			iSysRoleService.allowDataScope(role);
			return Result.result(0, null, "保存角色【"+role.getRoleName()+"】成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("授权用户", this.getClass(), e);
		}
	}
	
	/** 角色树结构*/
	@RequestMapping(path="treeData",method=RequestMethod.POST)
	@ResponseBody
	public JSONArray treeData(String userType){
		try {
			return iSysRoleService.treeDate(userType);
		} catch (Exception e) {
			ExceptionSupport.resolverResult("角色树", this.getClass(), e);
			return null;
		}
	}
	
	/** 修改角色*/
	@RequestMapping(path="update",method=RequestMethod.POST)
	@ResponseBody
	public Result update(SysRoleEntity role) {
		try {
			iSysRoleService.update(role);
			return Result.result(0, null, "角色【"+role.getRoleName()+"】修改成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("修改角色", this.getClass(), e);
		}
		
	}
	
	/** 新增角色*/
	@RequestMapping(path="save",method=RequestMethod.POST)
	@ResponseBody
	public Result save(SysRoleEntity role) {
		try {
			iSysRoleService.save(role);
			return Result.result(0, null, "角色【"+role.getRoleName()+"】新增成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("新增角色", this.getClass(), e);
		}
	}
	
}
