package com.tamguo.modules.tiku.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.condition.CourseCondition;
import com.tamguo.modules.tiku.model.enums.SubjectStatusEnum;
import com.tamguo.modules.tiku.service.ICourseService;
import com.tamguo.modules.tiku.service.ISubjectService;

@Controller
@RequestMapping(path="tiku/course")
public class CourseController {

	/** 科目*/
	private final String COURSE_INDEX_PAGE = "modules/tiku/course/list";
	private final String COURSE_ADD_PAGE = "modules/tiku/course/add";
	private final String COURSE_UPDATE_PAGE = "modules/tiku/course/update";
	
	@Autowired
	private ICourseService iCourseService;
	@Autowired
	private ISubjectService iSubjectService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="list")
	public ModelAndView index(ModelAndView model) {
		model.setViewName(COURSE_INDEX_PAGE);
		model.addObject("subjectList", iSubjectService.selectList(Condition.create().eq("status", SubjectStatusEnum.NORMAL.getValue())));
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="add")
	public ModelAndView add(ModelAndView model) {
		model.setViewName(COURSE_ADD_PAGE);
		model.addObject("subjectList", iSubjectService.selectList(Condition.create().eq("status", SubjectStatusEnum.NORMAL.getValue())));
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="update")
	public ModelAndView update(String id, ModelAndView model) {
		model.setViewName(COURSE_UPDATE_PAGE);
		model.addObject("subjectList", iSubjectService.selectList(Condition.create().eq("status", SubjectStatusEnum.NORMAL.getValue())));
		model.addObject("course", iCourseService.selectById(id));
		return model;
	}

	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(CourseCondition condition) {
		Page<CourseEntity> page = iCourseService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
	
	@RequestMapping(path="save",method=RequestMethod.POST)
	@ResponseBody
	public Result save(CourseEntity course) {
		try {
			iCourseService.save(course);
			return Result.result(0, null, "保存科目【"+course.getName()+"】成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("保存科目", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="update",method=RequestMethod.POST)
	@ResponseBody
	public Result update(CourseEntity course) {
		try {
			iCourseService.update(course);
			return Result.result(0, null, "修改科目【"+course.getName()+"】成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("修改科目", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="delete",method=RequestMethod.POST)
	@ResponseBody
	public Result delete(String id) {
		try {
			iCourseService.delete(id);
			return Result.result(0, null, "删除科目成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("删除科目", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="enable",method=RequestMethod.POST)
	@ResponseBody
	public Result enable(String id) {
		try {
			iCourseService.enable(id);
			return Result.result(0, null, "激活科目成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("激活科目", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="disabled",method=RequestMethod.POST)
	@ResponseBody
	public Result disabled(String id) {
		try {
			iCourseService.disabled(id);
			return Result.result(0, null, "停用科目成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("停用科目", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="treeData",method=RequestMethod.GET)
	@ResponseBody
	public JSONArray treeData() {
		return iCourseService.treeData();
	}
}
