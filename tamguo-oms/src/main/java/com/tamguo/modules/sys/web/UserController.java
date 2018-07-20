package com.tamguo.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path="sys/user")
public class UserController {
	
	private final String USER_INDEX_PAGE = "modules/sys/user/index";
	private final String USER_LIST_PAGE = "modules/sys/user/list";

	@RequestMapping(path="index")
	public String index(ModelAndView model) {
		return USER_INDEX_PAGE;
	}
	
	@RequestMapping(path="list")
	public String list(ModelAndView model) {
		return USER_LIST_PAGE;
	}
	
}
