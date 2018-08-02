package com.tamguo.modules.sys.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.sys.model.SysOfficeEntity;
import com.tamguo.modules.sys.model.condition.SysOfficeCondition;
import com.tamguo.modules.sys.service.ISysOfficeService;

/**
 * Controller - 组织
 * 
 * @author tamguo
 *
 */
@Controller
@RequestMapping(path="sys/office")
public class SysOfficeController {
	
	private final String OFFICE_ADD_PAGE = "modules/sys/office/add";
	private final String OFFICE_UPDATE_PAGE = "modules/sys/office/update";

	@Autowired
	private ISysOfficeService iSysOfficeService; 
	
	@RequestMapping(path="add")
	public ModelAndView add(String parentCode , ModelAndView model) {
		model.setViewName(OFFICE_ADD_PAGE);
		model.addObject("parentOffice", iSysOfficeService.selectById(parentCode));
		return model;
	}
	
	@RequestMapping(path="update")
	public ModelAndView update(String officeCode , ModelAndView model) {
		model.setViewName(OFFICE_UPDATE_PAGE);
		SysOfficeEntity office = iSysOfficeService.selectById(officeCode);
		SysOfficeEntity parentOffice = iSysOfficeService.selectById(office.getParentCode());
		model.addObject("office", office);
		model.addObject("parentOffice" , parentOffice);
		return model;
	}
	
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
	
	@RequestMapping(path="update",method=RequestMethod.POST)
	@ResponseBody
	public Result update(SysOfficeEntity office) {
		try {
			iSysOfficeService.update(office);
			return Result.result(0, null, "修改机构【"+office.getOfficeName()+"】成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("修改机构", this.getClass(), e);
		}
	}
	
}
