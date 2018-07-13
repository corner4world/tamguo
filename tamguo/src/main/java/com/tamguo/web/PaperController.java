package com.tamguo.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.model.AreaEntity;
import com.tamguo.model.CourseEntity;
import com.tamguo.model.PaperEntity;
import com.tamguo.model.SubjectEntity;
import com.tamguo.service.IAreaService;
import com.tamguo.service.ICourseService;
import com.tamguo.service.IPaperService;
import com.tamguo.service.IQuestionService;
import com.tamguo.service.ISubjectService;
import com.tamguo.util.PageUtils;
import com.tamguo.util.TamguoConstant;

/**
 * Controller - 试卷
 * 
 * @author candy.tam
 *
 */
@Controller
public class PaperController {
	
	@Autowired
	private ICourseService iCourseService;
	@Autowired
	private IAreaService iAreaService;
	@Autowired
	private IPaperService iPaperService;
	@Autowired
	private IQuestionService iQuestionService;
	@Autowired
	private ISubjectService iSubjectService;

	@RequestMapping(value = {"paperlist/{subjectId}-{courseId}-{paperType}-{year}-{area}-{pageNum}"}, method = RequestMethod.GET)
    public ModelAndView indexAction(@PathVariable String subjectId , @PathVariable String courseId , @PathVariable String paperType,
    		@PathVariable String year , @PathVariable String area , @PathVariable Integer pageNum, ModelAndView model) {
    	model.setViewName("paperlist");
    	
    	CourseEntity course = iCourseService.find(courseId);
    	List<CourseEntity> courseList = iCourseService.findBySubjectId(subjectId);
    	SubjectEntity subject = iSubjectService.find(subjectId);
    	List<AreaEntity> areaList = iAreaService.findRootArea();
    	PageUtils page = PageUtils.getPage(iPaperService.findList(subjectId , courseId , paperType , year , area , pageNum));
    	if(course == null) {
    		course = courseList.get(0);
    	}
    	Long total = iPaperService.getPaperTotal();
    	model.addObject("courseList", courseList);
    	model.addObject("subject", subject);
    	model.addObject("course", course);
    	model.addObject("areaList", areaList);
    	model.addObject("paperPage" , page);
    	model.addObject("total" , total);
    	model.addObject("courseId", course.getUid());
    	model.addObject("paperType", paperType);
    	model.addObject("year", year);
    	model.addObject("area", area);
        return model;
    }
	
	@RequestMapping(value = {"/paper/{paperId}.html"}, method = RequestMethod.GET)
	public ModelAndView indexAction(@PathVariable String paperId , ModelAndView model){
		model.setViewName("paper");
		PaperEntity paper = iPaperService.find(paperId);
		model.addObject("paper", paper);
		model.addObject("subject", StringUtils.isEmpty(paper.getSubjectId()) ? null : iSubjectService.find(paper.getSubjectId()));
		model.addObject("course", StringUtils.isEmpty(paper.getCourseId()) ? null : iCourseService.find(paper.getCourseId()));
		model.addObject("questionList", iQuestionService.findPaperQuestion(paperId));

    	// 获取推荐试卷
		model.addObject("zhentiPaperList", iPaperService.featuredPaper(TamguoConstant.ZHENGTI_PAPER_ID, paper.getSubjectId()));
		model.addObject("moniPaperList", iPaperService.featuredPaper(TamguoConstant.MONI_PAPER_ID, paper.getSubjectId()));
		model.addObject("yatiPaperList", iPaperService.featuredPaper(TamguoConstant.YATI_PAPER_ID, paper.getSubjectId()));
		model.addObject("hotPaperList", iPaperService.findHotPaper(paper.getSubjectId(), paper.getCourseId()));
		return model;
	}
	
	@RequestMapping(value = {"/paper/area/{areaId}-{type}.html"}, method = RequestMethod.GET)
	@ResponseBody
	public List<PaperEntity> findPaperByAreaId(@PathVariable String areaId ,@PathVariable String type){
		return iPaperService.findPaperByAreaId(areaId , type);
	}
	
}
