package com.tamguo.web.tiku;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.modules.sys.model.SysAreaEntity;
import com.tamguo.modules.sys.service.ISysAreaService;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.PaperEntity;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.service.ICourseService;
import com.tamguo.modules.tiku.service.IPaperService;
import com.tamguo.modules.tiku.service.IQuestionService;
import com.tamguo.modules.tiku.service.ISubjectService;
import com.tamguo.utils.BrowserUtils;
import com.tamguo.utils.PageUtils;

/**
 * Controller - 试卷
 * 
 * @author candy.tam
 *
 */
@Controller
public class PaperController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ICourseService iCourseService;
	@Autowired
	private ISysAreaService iSysAreaService;
	@Autowired
	private IPaperService iPaperService;
	@Autowired
	private IQuestionService iQuestionService;
	@Autowired
	private ISubjectService iSubjectService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"paperlist/{subjectId}-{courseId}-{paperType}-{year}-{area}-{pageNum}.html"}, method = RequestMethod.GET)
    public ModelAndView indexAction(HttpServletRequest request , @PathVariable String subjectId , @PathVariable String courseId , @PathVariable String paperType,
    		@PathVariable String year , @PathVariable String area , @PathVariable Integer pageNum, ModelAndView model) {
    	try {
			// request url 
    		logger.info("request url :{}" , request.getRequestURI());
        	CourseEntity course = iCourseService.selectById(courseId);
			List<CourseEntity> courseList = iCourseService.selectList(Condition.create().eq("subject_id", subjectId));
        	SubjectEntity subject = iSubjectService.selectById(subjectId);
        	List<SysAreaEntity> areaList = iSysAreaService.selectList(Condition.create().eq("tree_level", "0"));
        	
        	Page<PaperEntity> page = new Page<>(pageNum , 10);
        	Condition condition = Condition.create();
        	if(!StringUtils.isEmpty(subjectId) && !"0".equals(subjectId)) {
        		condition.eq("subject_id", subjectId);
        	}
        	if(!StringUtils.isEmpty(paperType) && !"0".equals(paperType)) {
        		condition.eq("type", paperType);
        	}
        	if(!StringUtils.isEmpty(year) && !"0".equals(year)) {
        		condition.eq("year", year);
        	}
        	if(!StringUtils.isEmpty(area) && !"0".equals(area)) {
        		condition.eq("area_id", area);
        	}
        	PageUtils result = PageUtils.getPage(iPaperService.selectPage(page , condition));
        	if(courseList.size() > 0) {
        		course = courseList.get(0);
        	}
        	Integer total = iPaperService.selectCount(Condition.EMPTY);
        	model.addObject("courseList", courseList);
        	model.addObject("subject", subject);
        	model.addObject("course", course);
        	model.addObject("areaList", areaList);
        	model.addObject("paperPage" , result);
        	model.addObject("total" , total);
        	model.addObject("courseId", courseId);
        	model.addObject("paperType", paperType);
        	model.addObject("year", year);
        	model.addObject("area", area);
        	

			if(BrowserUtils.isMobile(request.getHeader("user-agent"))) {
	    		model.setViewName("mobile/paperlist");
	    	}else {
	    		model.setViewName("paperlist");
	    	}
            return model;
		} catch (Exception e) {
			model.setViewName("404");
			return model;
		}
		
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/paper/{paperId}.html"}, method = RequestMethod.GET)
	public ModelAndView indexAction(HttpServletRequest request , @PathVariable String paperId , ModelAndView model){
		try {
			// request url 
    		logger.info("request url :{}" , request.getRequestURI());
			model.setViewName("paper");
			PaperEntity paper = iPaperService.selectById(paperId);
			model.addObject("paper", paper);
			model.addObject("subject", StringUtils.isEmpty(paper.getSubjectId()) ? null : iSubjectService.selectById(paper.getSubjectId()));
			model.addObject("course", StringUtils.isEmpty(paper.getCourseId()) ? null : iCourseService.selectById(paper.getCourseId()));
			model.addObject("questionList", iQuestionService.selectList(Condition.create().eq("paper_id", paperId)));

	    	// 获取推荐试卷
			model.addObject("zhentiPaperList", iPaperService.selectPage(new Page<>(1, 5) , Condition.create().eq("subject_id", paper.getSubjectId()).eq("type",SystemConstant.ZHENGTI_PAPER_ID)).getRecords());
			model.addObject("moniPaperList", iPaperService.selectPage(new Page<>(1, 5) , Condition.create().eq("subject_id", paper.getSubjectId()).eq("type",SystemConstant.MONI_PAPER_ID)).getRecords());
			model.addObject("yatiPaperList", iPaperService.selectPage(new Page<>(1, 5) , Condition.create().eq("subject_id", paper.getSubjectId()).eq("type",SystemConstant.YATI_PAPER_ID)).getRecords());
			model.addObject("hotPaperList", iPaperService.selectPage(new Page<>(1, 5) , Condition.create().eq("subject_id", paper.getSubjectId()).eq("course_id", paper.getCourseId())).getRecords());
			

			if(BrowserUtils.isMobile(request.getHeader("user-agent"))) {
	    		model.setViewName("mobile/paper");
	    	}else {
	    		model.setViewName("paper");
	    	}
			return model;
		} catch (Exception e) {
			model.setViewName("404");
			return model;
		}
		
	}
	
}
