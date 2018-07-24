package com.tamguo.modules.sys.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.tamguo.modules.sys.model.SysCompanyEntity;
import com.tamguo.modules.sys.model.condition.SysCompanyCondition;
import com.tamguo.modules.sys.service.ISysCompanyService;
import com.tamguo.modules.sys.utils.Result;

@Controller
@RequestMapping(path="sys/company")
public class CompanyController {
	
	private final String COMPANY_INDEX_PAGE = "modules/sys/company/index";
	private final String COMPANY_DETAIL_PAGE = "modules/sys/company/detail";

	@Autowired
	ISysCompanyService iSysCompanyService;
	
	@RequestMapping(path="index")
	public String index(ModelAndView model) {
		return COMPANY_INDEX_PAGE;
	}
	
	@RequestMapping(path="detail")
	public ModelAndView detail(String id , ModelAndView model) {
		model.setViewName(COMPANY_DETAIL_PAGE);
		model.addObject("id", id);
		return model;
	}
	
	@RequestMapping(path="info/{code}")
	@ResponseBody
	public Result info(@PathVariable("code") String code) {
		return Result.successResult(iSysCompanyService.selectByCode(code));
	}
	
	@RequestMapping(path="listData")
	@ResponseBody
	public List<SysCompanyEntity> listData(SysCompanyCondition condition) {
		return iSysCompanyService.listData(condition);
	}

	@RequestMapping(path="treeData")
	@ResponseBody
	public JSONArray treeData(String excludeId) {
		return iSysCompanyService.treeData(excludeId);
	}
}
