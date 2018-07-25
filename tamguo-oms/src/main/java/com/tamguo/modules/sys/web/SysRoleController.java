package com.tamguo.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="sys/role")
public class SysRoleController {

	private final String ROLE_INDEX_PAGE = "modules/sys/role/index";

	@RequestMapping(path="index")
	public String index() {
		return ROLE_INDEX_PAGE;
	}
	
}
