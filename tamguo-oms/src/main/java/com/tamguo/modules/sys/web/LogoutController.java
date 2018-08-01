package com.tamguo.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.modules.sys.utils.ShiroUtils;

@Controller
public class LogoutController {

	@RequestMapping(path="logout")
	public ModelAndView logout(ModelAndView model) {
		ShiroUtils.logout();
		model.setViewName("login");
		return model;
	}
	
}
