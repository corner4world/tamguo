package com.tamguo.modules.sys.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;
import com.tamguo.modules.sys.service.ISysUserService;
import com.tamguo.modules.sys.utils.Result;

@Controller
@RequestMapping(path="sys/user")
public class SysUserController {
	
	@Autowired
	private ISysUserService iSysUserService;

	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysUserCondition condition) {
		Page<SysUserEntity> page = iSysUserService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
	
}
