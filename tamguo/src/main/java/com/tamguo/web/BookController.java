package com.tamguo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.model.BookEntity;
import com.tamguo.model.ChapterEntity;
import com.tamguo.model.CourseEntity;
import com.tamguo.model.SubjectEntity;
import com.tamguo.service.IBookService;
import com.tamguo.service.IChapterService;
import com.tamguo.service.ICourseService;
import com.tamguo.service.ISubjectService;

@Controller
public class BookController {

	@Autowired
	IBookService iBookService;
	@Autowired
	IChapterService iChapterService;
	@Autowired
	ISubjectService iSubjectService;
	@Autowired
	ICourseService iCourseService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"book/{uid}"}, method = RequestMethod.GET)
	public ModelAndView index(@PathVariable String uid , ModelAndView model) {
		try {
			BookEntity book = iBookService.selectById(uid);
			SubjectEntity subject = iSubjectService.find(book.getSubjectId());
			List<CourseEntity> courseList = subject.getCourseList();
			List<BookEntity> bookList = iBookService.selectList(Condition.create().eq("course_id", book.getCourseId()));
			CourseEntity course = iCourseService.selectById(book.getCourseId());
			List<ChapterEntity> chapterList = iChapterService.selectList(Condition.create().eq("book_id", uid));
			model.addObject("book", book);
			model.addObject("subject", subject);
			model.addObject("course", course);
			model.addObject("chapterList" , chapterList);
			model.addObject("courseList", courseList);
			model.addObject("bookList", bookList);
			model.setViewName("book");
			return model;
		} catch (Exception e) {
			model.setViewName("404");
			return model;
		}
	}
	
}
