package com.tamguo.modules.sys.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.modules.sys.model.SysCompanyEntity;
import com.tamguo.modules.sys.model.condition.SysCompanyCondition;
import com.tamguo.modules.sys.service.ISysCompanyService;

@Controller
@RequestMapping(path="sys/company")
public class CompanyController {
	
	private final String COMPANY_INDEX_PAGE = "modules/sys/company/index";

	@Autowired
	ISysCompanyService iSysCompanyService;
	
	@RequestMapping(path="index")
	public String index(ModelAndView model) {
		return COMPANY_INDEX_PAGE;
	}
	
	@RequestMapping(path="listData")
	@ResponseBody
	public List<SysCompanyEntity> listData(SysCompanyCondition condition) {
		return iSysCompanyService.listData(condition);
	}

	@RequestMapping(path="treeData")
	@ResponseBody
	public List<SysCompanyEntity> treeData() {
		return iSysCompanyService.treeData();
	}
}
