package com.tamguo.web.tiku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.QuestionEntity;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.service.IChapterService;
import com.tamguo.modules.tiku.service.ICourseService;
import com.tamguo.modules.tiku.service.IQuestionService;
import com.tamguo.modules.tiku.service.ISubjectService;

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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"questionlist/{chapterId}-{offset}-{limit}.html"}, method = RequestMethod.GET)
	public ModelAndView questionList(@PathVariable String chapterId , @PathVariable Integer offset , 
			@PathVariable Integer limit , ModelAndView model){
		try {
			model.setViewName("questionList");

			ChapterEntity chapter = iChapterService.selectById(chapterId);
			CourseEntity course = iCourseService.selectById(chapter.getCourseId());
			SubjectEntity subject = iSubjectService.selectById(course.getSubjectId());
			ChapterEntity parentChapter = iChapterService.selectById(chapter.getParentId());
			ChapterEntity nextChapter = iChapterService.selectById(chapter.getParentId());
			
			Page<QuestionEntity> page = new Page<>();
			page.setCurrent(offset);
			page.setSize(limit);
			Page<QuestionEntity> questionList = iQuestionService.selectPage(page , Condition.create().eq("chapter_id", chapterId));
			model.addObject("subject", subject);
			model.addObject("course", course);
			model.addObject("chapter", chapter);
			model.addObject("parentChapter" , parentChapter);
			model.addObject("nextChapter" , nextChapter);
			model.addObject("questionList", questionList);
			model.addObject("subjectId", course.getSubjectId());
			model.addObject("courseId", course.getId());
			return model;
		} catch (Exception e) {
			model.setViewName("404");
			return model;
		}
		
	}
	
	/**
	 * 直接访问题目
	 * @param uid
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/question/{uid}.html"}, method = RequestMethod.GET)
	public ModelAndView question(@PathVariable String uid , ModelAndView model){
		try {
			model.setViewName("question");
			QuestionEntity question = iQuestionService.selectById(uid);
			question.setQuestionType(question.getQuestionType());
			model.addObject("question", question);
			
			// 推荐试题
			model.addObject("featuredQuestionList", iQuestionService.selectList(Condition.create().eq("subject_id", question.getSubjectId()).eq("course_id", question.getCourseId())));
			return model;
		} catch (Exception e) {
			model.setViewName("404");
			return model;
		}
		
	}
	
	@RequestMapping(value = {"question/getQuestion/{uid}.html"}, method = RequestMethod.GET)
	@ResponseBody
	public Result getQuestion(@PathVariable String uid , ModelAndView model){
		return Result.successResult(iQuestionService.selectById(uid));
	}
	
}
