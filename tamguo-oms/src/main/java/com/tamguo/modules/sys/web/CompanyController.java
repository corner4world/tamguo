package com.tamguo.modules.sys.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tamguo.modules.sys.model.SysCompanyEntity;
import com.tamguo.modules.sys.service.ISysCompanyService;

@Controller
@RequestMapping(path="sys/company")
public class CompanyController {
	
	@Autowired
	ISysCompanyService iSysCompanyService;

	@RequestMapping(path="treeData")
	@ResponseBody
	public List<SysCompanyEntity> treeData() {
		return iSysCompanyService.treeData();
	}
}
