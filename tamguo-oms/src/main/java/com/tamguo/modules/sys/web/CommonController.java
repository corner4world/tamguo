package com.tamguo.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {
	
	private final String TREE_SELECT_PAGE = "modules/sys/common/treeselect";
	private final String TREE_ICON_PAGE = "modules/sys/common/iconselect";
	private final String SYS_DESKTOP_PAGE = "modules/sys/common/desktop";

	@RequestMapping(path="sys/treeselect")
	public ModelAndView index(ModelAndView model , String url) {
		model.addObject("url" , url);
		model.setViewName(TREE_SELECT_PAGE);
		return model;
	}
	
	@RequestMapping(path="sys/iconselect")
	public ModelAndView iconselect(ModelAndView model) {
		model.setViewName(TREE_ICON_PAGE);
		return model;
	}

	@RequestMapping(path="sys/desktop")
	public ModelAndView index(ModelAndView model) {
		model.setViewName(SYS_DESKTOP_PAGE);
		return model;
	}
}
