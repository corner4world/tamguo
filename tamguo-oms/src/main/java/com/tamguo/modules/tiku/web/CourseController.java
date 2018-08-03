package com.tamguo.modules.tiku.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.condition.SubjectCondition;
import com.tamguo.modules.tiku.service.ICourseService;

@Controller
@RequestMapping(path="tiku/course")
public class CourseController {

	/** 题库科目*/
	private final String COURSE_INDEX_PAGE = "modules/tiku/course/list";
	@Autowired
	private ICourseService iCourseService;
	
	@RequestMapping(path="list")
	public ModelAndView index(ModelAndView model) {
		model.setViewName(COURSE_INDEX_PAGE);
		return model;
	}

	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SubjectCondition condition) {
		Page<CourseEntity> page = iCourseService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
}
