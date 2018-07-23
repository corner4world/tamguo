package com.tamguo.modules.sys.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
public class OfficeController {
	
	private final String OFFICE_INDEX_PAGE = "modules/sys/office/index";

	@Autowired
	private ISysOfficeService iSysOfficeService; 
	
	@RequestMapping(path="index")
	public String index(ModelAndView model) {
		return OFFICE_INDEX_PAGE;
	}
	
	@RequestMapping(path="listData")
	@ResponseBody
	public List<SysOfficeEntity> listData(SysOfficeCondition condition) {
		return iSysOfficeService.listData(condition);
	}

	
}
