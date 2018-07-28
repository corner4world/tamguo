package com.tamguo.modules.sys.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.tamguo.modules.sys.model.SysOfficeEntity;
import com.tamguo.modules.sys.model.condition.SysOfficeCondition;
import com.tamguo.modules.sys.service.ISysOfficeService;
import com.tamguo.modules.sys.utils.ExceptionSupport;
import com.tamguo.modules.sys.utils.Result;

/**
 * Controller - 组织
 * 
 * @author tamguo
 *
 */
@Controller
@RequestMapping(path="sys/office")
public class SysOfficeController {

	@Autowired
	private ISysOfficeService iSysOfficeService; 
	
	@RequestMapping(path="listData")
	@ResponseBody
	public List<SysOfficeEntity> listData(SysOfficeCondition condition) {
		return iSysOfficeService.listData(condition);
	}
	
	@RequestMapping(path="treeData")
	@ResponseBody
	public JSONArray treeData(String excludeId) {
		return iSysOfficeService.treeData(excludeId);
	}
	
	@RequestMapping(path="save")
	@ResponseBody
	public Result save(SysOfficeEntity office) {
		try {
			iSysOfficeService.save(office);
			return Result.result(0, null, "新增机构【"+office.getOfficeName()+"】成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("新增机构", this.getClass(), e);
		}
	}

	
}
