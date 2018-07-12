package com.tamguo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Controller - 科目
 * 
 * @author tamguo
 *
 */
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.model.ChapterEntity;
import com.tamguo.model.CourseEntity;
import com.tamguo.model.SubjectEntity;
import com.tamguo.service.IChapterService;
import com.tamguo.service.ICourseService;
import com.tamguo.service.ISubjectService;
import com.tamguo.util.Result;

@Controller
public class CourseController {
	
	@Autowired
	IChapterService iChapterService;
	@Autowired
	ICourseService iCourseService;
	@Autowired
	ISubjectService iSubjectService;

	@RequestMapping(value = {"course/{uid}"}, method = RequestMethod.GET)
	public ModelAndView index(@PathVariable String uid , ModelAndView model) {
		CourseEntity course = iCourseService.find(uid);
		SubjectEntity subject = iSubjectService.find(course.getSubjectId());
		List<ChapterEntity> chapterList = iChapterService.findCourseChapter(uid);
		List<CourseEntity> courseList = iCourseService.findBySubjectId(course.getSubjectId());
		
		model.addObject("chapterList", chapterList);
		model.addObject("courseList", courseList);
		model.addObject("course", course);
		model.addObject("subject", subject);
		
		model.setViewName("chapter");
		return model;
	}
	
	@RequestMapping(value = {"course/findChapter"}, method = RequestMethod.GET)
	@ResponseBody
	public List<ChapterEntity> findChapterByCourseId(String courseId){
		return iChapterService.findCourseChapter(courseId);
	}
	
	@RequestMapping(value = {"course/findChapterTreeByCourseId"}, method = RequestMethod.GET)
	@ResponseBody
	public Result findChapterTreeByCourseId(String courseId){
		return Result.successResult(iChapterService.getChapterTree(courseId));
	}
	
}
