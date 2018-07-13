package com.tamguo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.model.ChapterEntity;
import com.tamguo.model.CourseEntity;
import com.tamguo.model.QuestionEntity;
import com.tamguo.model.SubjectEntity;
import com.tamguo.model.enums.QuestionType;
import com.tamguo.service.IChapterService;
import com.tamguo.service.ICourseService;
import com.tamguo.service.IQuestionService;
import com.tamguo.service.ISubjectService;
import com.tamguo.util.Result;

@Controller
public class QuestionContrller {

	@Autowired
	private IQuestionService iQuestionService;
	@Autowired
	private IChapterService iChapterService;
	@Autowired
	private ISubjectService iSubjectService;
	@Autowired
	private ICourseService iCourseService;
	
	@RequestMapping(value = {"questionlist/{chapterId}-{offset}-{limit}"}, method = RequestMethod.GET)
	public ModelAndView questionList(@PathVariable String chapterId , @PathVariable Integer offset , 
			@PathVariable Integer limit , ModelAndView model){
		model.setViewName("questionList");

		ChapterEntity chapter = iChapterService.findById(chapterId);
		CourseEntity course = iCourseService.find(chapter.getCourseId());
		SubjectEntity subject = iSubjectService.find(course.getSubjectId());
		ChapterEntity parentChapter = iChapterService.findById(chapter.getParentId());
		ChapterEntity nextChapter = iChapterService.findNextPoint(chapter.getParentId() , chapter.getOrders());
		
		Page<QuestionEntity> page = new Page<>();
		page.setCurrent(offset);
		page.setSize(limit);
		Page<QuestionEntity> questionList = iQuestionService.findByChapterId(chapterId , page);
		model.addObject("subject", subject);
		model.addObject("course", course);
		model.addObject("chapter", chapter);
		model.addObject("parentChapter" , parentChapter);
		model.addObject("nextChapter" , nextChapter);
		model.addObject("questionList", questionList);
		model.addObject("subjectId", course.getSubjectId());
		model.addObject("courseId", course.getUid());
		return model;
	}
	
	/**
	 * 直接访问题目
	 * @param uid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/question/{uid}.html"}, method = RequestMethod.GET)
	public ModelAndView question(@PathVariable String uid , ModelAndView model){
		model.setViewName("question");
		QuestionEntity question = iQuestionService.findNormalQuestion(uid);
		question.setQuestionType(QuestionType.getQuestionType(question.getQuestionType()).getDesc());
		model.addObject("question", question);
		
		// 推荐试题
		model.addObject("featuredQuestionList", iQuestionService.featuredQuestion(question.getSubjectId(),question.getCourseId()));
		return model;
	}
	
	@RequestMapping(value = {"/question/getQuestion/{uid}.html"}, method = RequestMethod.GET)
	@ResponseBody
	public Result getQuestion(@PathVariable String uid , ModelAndView model){
		return Result.successResult(iQuestionService.select(uid));
	}
	
}
