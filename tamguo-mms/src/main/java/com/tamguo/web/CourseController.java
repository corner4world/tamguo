package com.tamguo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.service.IBookService;
import com.tamguo.modules.tiku.service.IChapterService;
import com.tamguo.modules.tiku.service.ICourseService;

@Controller
public class CourseController {
	
	@Autowired
	private ICourseService iCourseService;
	@Autowired
	private IBookService iBookService;
	@Autowired
	private IChapterService iChapterService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path= {"course/{courseId}.html"})
	public ModelAndView list(@PathVariable String courseId , ModelAndView model) {
		CourseEntity course = iCourseService.selectById(courseId);
		List<BookEntity> bookList = iBookService.selectList(Condition.create().eq("course_id", courseId));
		List<ChapterEntity> chapterList = null;
		if(bookList.size() > 0) {
			chapterList = iChapterService.selectList(Condition.create().eq("book_id", bookList.get(0).getId()));
		}
		model.addObject("course", course);
		model.addObject("chapterList", chapterList);
		model.addObject("subjectId", course.getSubjectId());
		model.addObject("courseId", course.getId());
		model.setViewName("course");
		return model;
	}

	public ICourseService getiCourseService() {
		return iCourseService;
	}

	public void setiCourseService(ICourseService iCourseService) {
		this.iCourseService = iCourseService;
	}
}
