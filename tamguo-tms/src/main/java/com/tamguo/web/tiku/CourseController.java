package com.tamguo.web.tiku;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.common.utils.DateUtil;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.service.IBookService;
import com.tamguo.modules.tiku.service.IChapterService;
import com.tamguo.modules.tiku.service.ICourseService;
import com.tamguo.modules.tiku.service.ISubjectService;

/**
 * Controller - 科目
 * 
 * @author tamguo
 *
 */
@Controller
public class CourseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IChapterService iChapterService;
	@Autowired
	ICourseService iCourseService;
	@Autowired
	ISubjectService iSubjectService;
	@Autowired
	IBookService iBookService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"course/{uid}.html"}, method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request , @PathVariable String uid , ModelAndView model) {
		try {
			// request url 
    		logger.info("request url :{} , time:{} " , request.getRequestURI() , DateUtil.getCurrentDateYYYYMMDDStr() );
			CourseEntity course = iCourseService.selectById(uid);
			List<BookEntity> bookList = iBookService.selectList(Condition.create().eq("course_id", uid));
			List<ChapterEntity> chapterList = null;
			BookEntity book = null;
			if(bookList.size() > 0) {
				book = bookList.get(0);
				chapterList = iChapterService.findChapterTree(book.getId());
			}
			SubjectEntity subject = iSubjectService.selectById(course.getSubjectId());
			List<CourseEntity> courseList = iCourseService.selectList(Condition.create().eq("subject_id", course.getSubjectId()).orderAsc(Arrays.asList("sort")));
			
			model.addObject("chapterList", chapterList);
			model.addObject("courseList", courseList);
			model.addObject("course", course);
			model.addObject("subject", subject);
			model.addObject("bookList", bookList);
			model.addObject("book" , book);
			model.setViewName("chapter");
			return model;
		} catch (Exception e) {
			model.setViewName("404");
			return model;
		}
	}
	
	@RequestMapping(value = {"course/findChapter.html"}, method = RequestMethod.GET)
	@ResponseBody
	public List<ChapterEntity> findChapterByCourseId(String courseId){
		return iChapterService.findCourseChapter(courseId);
	}
	
}
