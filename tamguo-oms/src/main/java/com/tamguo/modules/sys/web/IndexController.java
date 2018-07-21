package com.tamguo.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@RequestMapping(path="sysIndex")
	public String sysLogin(ModelAndView model) {
		return "sysIndex";
	}
	
}
