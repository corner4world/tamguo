package com.tamguo.modules.tiku.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.tiku.model.KnowPointEntity;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.condition.BookCondition;
import com.tamguo.modules.tiku.service.IKnowPointService;
import com.tamguo.modules.tiku.service.ICourseService;

@Controller
@RequestMapping(path="tiku/knowpoint")
public class KnowPointController {

	/** 书籍*/
	private final String KNOWPOINT_LIST_PAGE = "modules/tiku/knowpoint/list";
	private final String KNOWPOINT_UPDATE_PAGE = "modules/tiku/knowpoint/update";
	
	@Autowired
	private IKnowPointService iKnowPointService;
	@Autowired
	private ICourseService iCourseService;
	
	
	@RequestMapping(path="list")
	public ModelAndView index(ModelAndView model) {
		model.setViewName(KNOWPOINT_LIST_PAGE);
		return model;
	}
	
	@RequestMapping(path="update")
	public ModelAndView update(String id, ModelAndView model) {
		model.setViewName(KNOWPOINT_UPDATE_PAGE);
		KnowPointEntity knowpoint = iKnowPointService.selectById(id);
		CourseEntity course = iCourseService.selectById(knowpoint.getCourseId());
		model.addObject("knowpoint", knowpoint);
		model.addObject("course", course);
		return model;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(BookCondition condition) {
		Page<KnowPointEntity> page = iKnowPointService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}

	@RequestMapping(path="save",method=RequestMethod.POST)
	@ResponseBody
	public Result save(KnowPointEntity book) {
		try {
			iKnowPointService.save(book);
			return Result.result(0, null, "保存书籍【"+book.getName()+"】成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("保存书籍", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="update",method=RequestMethod.POST)
	@ResponseBody
	public Result update(KnowPointEntity book) {
		try {
			iKnowPointService.update(book);
			return Result.result(0, null, "修改书籍【"+book.getName()+"】成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("修改书籍", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="delete",method=RequestMethod.POST)
	@ResponseBody
	public Result delete(String id) {
		try {
			iKnowPointService.delete(id);
			return Result.result(0, null, "删除书籍成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("删除书籍", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="enable",method=RequestMethod.POST)
	@ResponseBody
	public Result enable(String id) {
		try {
			iKnowPointService.enable(id);
			return Result.result(0, null, "激活书籍成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("激活书籍", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="disabled",method=RequestMethod.POST)
	@ResponseBody
	public Result disabled(String id) {
		try {
			iKnowPointService.disabled(id);
			return Result.result(0, null, "停用书籍成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("停用书籍", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="treeData",method=RequestMethod.POST)
	@ResponseBody
	public JSONArray treeData() {
		return iKnowPointService.treeData();
	}
}
