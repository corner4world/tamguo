package com.tamguo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

	@RequestMapping(value = {"account.html"}, method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("account");
		return model;
	}
}
