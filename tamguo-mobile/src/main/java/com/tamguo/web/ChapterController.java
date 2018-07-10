package com.tamguo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.service.ISubjectService;

@Controller
public class ChapterController {
	
	@Autowired
	private ISubjectService iSubjectService;

	@RequestMapping(path= {"chapter/{subjectId}/{courseId}"})
	public String chapter(@PathVariable String subjectId , @PathVariable String courseId , ModelAndView model) {
		model.addObject("courseList", iSubjectService.findCourseList(subjectId));
		return "chapter";
	}
	
}
