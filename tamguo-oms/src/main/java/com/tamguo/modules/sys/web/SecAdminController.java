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
import com.tamguo.modules.sys.model.enums.SysUserMgrTypeEnum;
import com.tamguo.modules.sys.service.ISysUserService;
import com.tamguo.modules.sys.utils.Result;

@Controller
@RequestMapping(path="sys/secAdmin")
public class SecAdminController {
	
	private final String SECADMIN_INDEX_PAGE = "modules/sys/secAdmin/index";
	private final String SECADMIN_ADD_PAGE = "modules/sys/secAdmin/add";
	
	@Autowired
	private ISysUserService iSysUserService;

	@RequestMapping(path="index")
	public String index(ModelAndView model) {
		return SECADMIN_INDEX_PAGE;
	}
	
	@RequestMapping(path="add")
	public ModelAndView add(String userCode , ModelAndView model) {
		model.setViewName(SECADMIN_ADD_PAGE);
		model.addObject("user", iSysUserService.selectById(userCode));
		model.addObject("userDataScopeList", iSysUserService.selectUserDataScope(userCode));
		return model;
	}
	
	@RequestMapping(path="save")
	@ResponseBody
	public Result save(SysUserEntity user , ModelAndView model) {
		iSysUserService.saveUserDataScope(user , SysUserMgrTypeEnum.SEC_ADMIN);
		return Result.result(0, null, "【"+user.getUserName()+"】保存数据权限成功！"); 
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysUserCondition condition) {
		Page<SysUserEntity> page = iSysUserService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
}
