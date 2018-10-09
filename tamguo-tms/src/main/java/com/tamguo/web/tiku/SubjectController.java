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

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.sys.service.ISysAreaService;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.PaperEntity;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.service.IBookService;
import com.tamguo.modules.tiku.service.IChapterService;
import com.tamguo.modules.tiku.service.ICourseService;
import com.tamguo.modules.tiku.service.IPaperService;
import com.tamguo.modules.tiku.service.ISubjectService;
import com.tamguo.utils.BrowserUtils;

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
	@Autowired
	private IPaperService iPaperService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"subject/{subjectId}.html"}, method = RequestMethod.GET)
    public ModelAndView indexAction(@PathVariable String subjectId , HttpServletRequest request , ModelAndView model) {
		// request url 
		logger.info("request url :{} " , request.getRequestURI() );
		SubjectEntity subject = iSubjectService.selectById(subjectId);
		List<CourseEntity> courseList = iCourseService.selectList(Condition.create().eq("subject_id", subjectId).orderAsc(Arrays.asList("sort")));
		// 获取第一个科目
		CourseEntity course = courseList.get(0);
		// 获取第一本书
		List<BookEntity> bookList = iBookService.selectList(Condition.create().eq("course_id", course.getId()));
		List<ChapterEntity> chapterList = null;
		if(bookList.size() > 0) {
			BookEntity book = bookList.get(0);
			chapterList = iChapterService.selectList(Condition.create().eq("book_id", book.getId()));
		}
		// 获取最新的试卷
		Page<PaperEntity> paperPage = iPaperService.selectPage(new Page<>(1, 15) , Condition.create().eq("subject_id", subjectId).orderDesc(Arrays.asList("id")));
    	model.addObject("subject", subject);
    	model.addObject("course" , course);
    	model.addObject("courseList", courseList);
    	model.addObject("chapterList" , chapterList);
    	model.addObject("areaList", iSysAreaService.selectList(Condition.create().eq("tree_level", "0")));
    	model.addObject("paperList", paperPage.getRecords());
    	if(BrowserUtils.isMobile(request.getHeader("user-agent"))) {
    		model.setViewName("mobile/subject");
    	}else {
    		model.setViewName("subject");
    	}
        return model;
    }
	
	// [{"value":"11","label":"北京市","children":[{"value":"1101","label":"市辖区"}]}]
	@RequestMapping(value = {"subject/getCourseCascaderTree.html"}, method = RequestMethod.GET)
	@ResponseBody
	public Result getCourseCascaderTree() {
		JSONArray list = iSubjectService.getCourseCascaderTree();
		return Result.successResult(list);
	}
	
	// [{"value":"11","label":"北京市"}]
	@RequestMapping(value = {"subject/getSubjectTree.html"}, method = RequestMethod.GET)
	@ResponseBody
	public Result getSubjectTree() {
		JSONArray list = iSubjectService.getSubjectTree();
		return Result.successResult(list);
	}
}
