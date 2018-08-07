package com.tamguo.web.tiku;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.service.IBookService;
import com.tamguo.modules.tiku.service.IChapterService;
import com.tamguo.modules.tiku.service.ICourseService;
import com.tamguo.modules.tiku.service.ISubjectService;

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
	@RequestMapping(value = {"book/{uid}.html"}, method = RequestMethod.GET)
	public ModelAndView index(@PathVariable String uid , ModelAndView model) {
		try {
			BookEntity book = iBookService.selectById(uid);
			SubjectEntity subject = iSubjectService.selectById(book.getSubjectId());
			List<CourseEntity> courseList = iCourseService.selectList(Condition.create().eq("subject_id", subject.getId()).orderAsc(Arrays.asList("sort")));
			List<BookEntity> bookList = iBookService.selectList(Condition.create().eq("course_id", book.getCourseId()));
			CourseEntity course = iCourseService.selectById(book.getCourseId());
			List<ChapterEntity> chapterList = iChapterService.findChapterTree(book.getId());
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
