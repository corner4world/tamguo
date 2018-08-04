package com.tamguo.web.tiku;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.modules.sys.service.ISysAreaService;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.service.IBookService;
import com.tamguo.modules.tiku.service.IChapterService;
import com.tamguo.modules.tiku.service.ICourseService;
import com.tamguo.modules.tiku.service.ISubjectService;

/**
 * Controller - 考试（高考，建造师，医药师）
 * 
 * @author candy.tam
 *
 */
@Controller
public class SubjectController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IChapterService iChapterService;
	@Autowired
	private ISysAreaService iSysAreaService;
	@Autowired
	private ISubjectService iSubjectService;
	@Autowired
	private ICourseService iCourseService;
	@Autowired
	private IBookService iBookService;
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"subject/{subjectId}.html"}, method = RequestMethod.GET)
    public ModelAndView indexAction(@PathVariable String subjectId , ModelAndView model) {
		try {
			SubjectEntity subject = iSubjectService.selectById(subjectId);
			List<CourseEntity> courseList = iCourseService.selectList(Condition.create().eq("subject_id", subjectId));
			// 获取第一个科目
			CourseEntity course = courseList.get(0);
			// 获取第一本书
			List<BookEntity> bookList = iBookService.selectList(Condition.create().eq("course_id", course.getId()));
			List<ChapterEntity> chapterList = null;
			if(bookList.size() > 0) {
				BookEntity book = bookList.get(0);
				chapterList = iChapterService.selectList(Condition.create().eq("book_id", book.getId()));
			}
	    	model.setViewName("subject");
	    	model.addObject("subject", subject);
	    	model.addObject("course" , course);
	    	model.addObject("courseList", courseList);
	    	model.addObject("chapterList" , chapterList);
	    	model.addObject("areaList", iSysAreaService.selectList(Condition.create().eq("tree_level", "0")));
	        return model;
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			model.setViewName("500");
			return model;
		}
		
    }
}
