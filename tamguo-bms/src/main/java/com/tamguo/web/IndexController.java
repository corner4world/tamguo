package com.tamguo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller - 首页
 * 
 * @author tamguo
 *
 */
@Controller
public class IndexController {

	@RequestMapping(path= {"index" , "/"})
	public ModelAndView index(ModelAndView model) {
		model.setViewName("index");
		return model;
	}
	
}
