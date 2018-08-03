package com.tamguo.modules.sys.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;
import com.tamguo.modules.sys.service.ISysPostService;
import com.tamguo.modules.sys.service.ISysUserService;

@Controller
@RequestMapping(path="sys/user")
public class SysUserController {
	
	/** 用户列表*/
	private final String USER_LIST_PAGE = "modules/sys/user/list";
	/** 修改*/
	private final String USER_UPDATE_PAGE = "modules/sys/user/update";
	/** 新增*/
	private final String USER_ADD_PAGE = "modules/sys/user/add";
	/** 分配角色*/
	private final String USER_ROLE_PAGE = "modules/sys/user/role";
	/** 数据权限*/
	private final String USER_DATA_SCOPE_PAGE = "modules/sys/user/dataScope";
	
	@Autowired
	private ISysUserService iSysUserService;
	@Autowired
	private ISysPostService iSysPostService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="list")
	public ModelAndView list(ModelAndView model) {
		model.setViewName(USER_LIST_PAGE);
		model.addObject("postList", iSysPostService.selectList(Condition.create().eq("status", "normal")));
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="add")
	public ModelAndView add(String userCode , ModelAndView model) {
		model.setViewName(USER_ADD_PAGE);
		model.addObject("postList", iSysPostService.selectList(Condition.create().eq("status", "normal")));
		return model;
	}
	
	@RequestMapping(path="role")
	public ModelAndView role(String userCode , ModelAndView model) {
		model.setViewName(USER_ROLE_PAGE);
		model.addObject("user", iSysUserService.selectById(userCode));
		model.addObject("userRoleList", iSysUserService.findUserRole(userCode));
		return model;
	}
	
	@RequestMapping(path="dataScope")
	public ModelAndView dataScope(String userCode , ModelAndView model) {
		model.setViewName(USER_DATA_SCOPE_PAGE);
		model.addObject("user", iSysUserService.selectById(userCode));
		model.addObject("userDataScopeList", iSysUserService.selectUserDataScope(userCode));
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="update")
	public ModelAndView update(String userCode , ModelAndView model) {
		model.setViewName(USER_UPDATE_PAGE);
		model.addObject("user", iSysUserService.selectById(userCode));
		model.addObject("postList", iSysPostService.selectList(Condition.create().eq("status", "0")));
		model.addObject("userPostCode", iSysUserService.queryUserPostByUserCode(userCode));
		return model;
	}

	/** 列表*/
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysUserCondition condition) {
		try {
			Page<SysUserEntity> page = iSysUserService.listData(condition);
			return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
		} catch (Exception e) {
			ExceptionSupport.resolverResult("查询用户列表", this.getClass(), e);
			return null;
		}
	}
	
	/** 校验登录账号*/
	@RequestMapping(path="checkLoginCode",method=RequestMethod.GET)
	@ResponseBody
	public Boolean checkLoginCode(String oldLoginCode , String loginCode) {
		return iSysUserService.checkLoginCode(oldLoginCode , loginCode);
	}
	
	/** 更新用户*/
	@RequestMapping(path="update",method=RequestMethod.POST)
	@ResponseBody
	public Result update(SysUserEntity user) {
		try {
			iSysUserService.update(user);
			return Result.result(0, null, "保存用户【"+user.getUserName()+"】成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("更新用户", this.getClass(), e);
		}
	}
	
	/** 保存用户*/
	@RequestMapping(path="save",method=RequestMethod.POST)
	@ResponseBody
	public Result save(SysUserEntity user) {
		try {
			iSysUserService.save(user);
			return Result.result(0, null, "保存用户【"+user.getUserName()+"】成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("保存用户", this.getClass(), e);
		}
	}
	
	/** 授权用户角色*/
	@RequestMapping(path="allowUserRole",method=RequestMethod.POST)
	@ResponseBody
	public Result allowUserRole(SysUserEntity user) {
		try {
			iSysUserService.allowUserRole(user);
			return Result.result(0, null, "【"+user.getUserName()+"】分配角色成功！"); 
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("分配角色", this.getClass(), e);
		}
	}
	
	/** 保存用户数据权限*/
	@RequestMapping(path="saveUserDataScope",method=RequestMethod.POST)
	@ResponseBody
	public Result saveUserDataScope(SysUserEntity user) {
		try {
			iSysUserService.saveUserDataScope(user);
			return Result.result(0, null, "【"+user.getUserName()+"】保存数据权限成功！"); 
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("保存数据权限", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="disable",method=RequestMethod.POST)
	@ResponseBody
	public Result disable(String userCode) {
		try {
			return iSysUserService.disable(userCode);
		} catch (Exception e) {
			ExceptionSupport.resolverResult("停用用户", this.getClass(), e);
			return null;
		}
	}
	
	@RequestMapping(path="enable",method=RequestMethod.POST)
	@ResponseBody
	public Result enable(String userCode) {
		try {
			return iSysUserService.enable(userCode);
		} catch (Exception e) {
			ExceptionSupport.resolverResult("激活用户", this.getClass(), e);
			return null;
		}
	}
	
	@RequestMapping(path="delete",method=RequestMethod.POST)
	@ResponseBody
	public Result delete(String userCode) {
		try {
			return iSysUserService.delete(userCode);
		} catch (Exception e) {
			ExceptionSupport.resolverResult("删除用户", this.getClass(), e);
			return null;
		}
	}
	
}
