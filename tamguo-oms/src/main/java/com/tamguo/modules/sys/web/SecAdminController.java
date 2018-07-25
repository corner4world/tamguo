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
import com.tamguo.modules.sys.utils.Result;

@Controller
@RequestMapping(path="sys/secAdmin")
public class SecAdminController {
	
	private final String SECADMIN_INDEX_PAGE = "modules/sys/secAdmin/index";
	
	@Autowired
	private ISysUserService iSysUserService;

	@RequestMapping(path="index")
	public String index(ModelAndView model) {
		return SECADMIN_INDEX_PAGE;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysUserCondition condition) {
		Page<SysUserEntity> page = iSysUserService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
}
