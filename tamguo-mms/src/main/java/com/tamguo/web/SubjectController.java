package com.tamguo.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.modules.tiku.service.ICourseService;
import com.tamguo.modules.tiku.service.ISubjectService;

@Controller
public class SubjectController {
	
	@Autowired
	private ICourseService iCourseService;
	@Autowired
	private ISubjectService iSubjectService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path= {"subject/{subjectId}.html"})
	public ModelAndView list(@PathVariable String subjectId , ModelAndView model) {
		model.setViewName("subject");
		model.addObject("subject", iSubjectService.selectById(subjectId));
		model.addObject("courseList", iCourseService.selectList(Condition.create().eq("subject_id", subjectId).orderAsc(Arrays.asList("sort"))));
		return model;
	}
	
}
