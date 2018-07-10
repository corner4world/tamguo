package com.tamguo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.model.ChapterEntity;
import com.tamguo.model.CourseEntity;
import com.tamguo.service.ICourseService;
import com.tamguo.service.ISubjectService;
import com.tamguo.util.ExceptionSupport;
import com.tamguo.util.Result;

@Controller
public class ChapterController {
	
	@Autowired
	private ISubjectService iSubjectService;
	@Autowired
	private ICourseService iCourseService;
	
	@RequestMapping(path= {"chapter/{subjectId}/{courseId}"})
	public String chapter(@PathVariable String subjectId , @PathVariable String courseId , ModelAndView model) {
		model.addObject("subjectId", subjectId);
		model.addObject("courseId", courseId);
		return "chapter";
	}
	
	
	@RequestMapping(path="chapter/findCourseList",method=RequestMethod.POST)
	@ResponseBody
	public Result findAllMenus(String subjectId) {
		try {
			List<CourseEntity> courseList = iSubjectService.findCourseList(subjectId);
			return Result.successResult(courseList);
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("查询科目", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="chapter/findChapterList",method=RequestMethod.POST)
	@ResponseBody
	public Result findChapterList(String courseId) {
		try {
			List<ChapterEntity> chapterList = iCourseService.findCourseChapter(courseId);
			return Result.successResult(chapterList);
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("查询章节", this.getClass(), e);
		}
	}
	
}
