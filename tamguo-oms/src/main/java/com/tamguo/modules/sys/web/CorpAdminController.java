package com.tamguo.modules.sys.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;
import com.tamguo.modules.sys.service.ISysUserService;
import com.tamguo.modules.sys.utils.ExceptionSupport;
import com.tamguo.modules.sys.utils.Result;

@Controller
@RequestMapping(path="sys/corpAdmin")
public class CorpAdminController {

	private final String CORPADMIN_INDEX_PAGE = "modules/sys/corpAdmin/index";
	private final String CORPADMIN_UPDATE_PAGE = "modules/sys/corpAdmin/update";
	
	@Autowired
	private ISysUserService iSysUserService;

	@RequestMapping(path="index")
	public String index(ModelAndView model) {
		return CORPADMIN_INDEX_PAGE;
	}
	
	@RequestMapping(path="update")
	public ModelAndView update(String userCode , ModelAndView model) {
		model.addObject("user", iSysUserService.selectById(userCode));
		model.setViewName(CORPADMIN_UPDATE_PAGE);
		return model;
	}
	
	@RequestMapping(path="save",method=RequestMethod.POST)
	@ResponseBody
	public Result save(SysUserEntity user) {
		try {
			iSysUserService.saveAdmin(user);
			return Result.result(0, null, "管理员【"+user.getUserName()+"】添加成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("添加管理员错误", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="update",method=RequestMethod.POST)
	@ResponseBody
	public Result update(SysUserEntity user) {
		try {
			iSysUserService.updateAdmin(user);
			return Result.result(0, null, "管理员【"+user.getUserName()+"】修改成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("修改管理员错误", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysUserCondition condition) {
		Page<SysUserEntity> page = iSysUserService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
	
}
