package com.tamguo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.modules.tiku.service.ISubjectService;

@Controller
public class IndexController {
	
	@Autowired
	private ISubjectService iSubjectService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path= {"index","/"})
	public ModelAndView index(ModelAndView model) {
		model.setViewName("index");
		model.addObject("subjectList", iSubjectService.selectList(Condition.EMPTY));
		return model;
	}
	
}
