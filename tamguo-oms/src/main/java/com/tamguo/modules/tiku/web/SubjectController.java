package com.tamguo.modules.tiku.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.model.condition.SubjectCondition;
import com.tamguo.modules.tiku.service.ISubjectService;

@Controller
@RequestMapping(path="tiku/subject")
public class SubjectController {

	/** 题库分类*/
	private final String SUBJECT_INDEX_PAGE = "modules/tiku/subject/list";
	private final String SUBJECT_UPDATE_PAGE = "modules/tiku/subject/update";
	
	@Autowired
	private ISubjectService iSubjectService;

	@RequestMapping(path="list")
	public ModelAndView list(ModelAndView model) {
		model.setViewName(SUBJECT_INDEX_PAGE);
		return model;
	}
	
	@RequestMapping(path="update")
	public ModelAndView update(String uid ,  ModelAndView model) {
		model.setViewName(SUBJECT_UPDATE_PAGE);
		model.addObject("subject", iSubjectService.selectById(uid));
		return model;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SubjectCondition condition) {
		Page<SubjectEntity> page = iSubjectService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="checkSubjectCode",method=RequestMethod.GET)
	@ResponseBody
	public Boolean checkSubjectCode(String uid) {
		Integer count = iSubjectService.selectCount(Condition.create().eq("uid", uid));
		if(count > 0) {
			return false;
		}else {
			return true;
		}
	}
	
	@RequestMapping(path="save",method=RequestMethod.POST)
	@ResponseBody
	public Result save(SubjectEntity subject) {
		try {
			iSubjectService.save(subject);
			return Result.result(0, null, "保存分类【"+subject.getName()+"】成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("保存分类", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="update",method=RequestMethod.POST)
	@ResponseBody
	public Result update(SubjectEntity subject) {
		try {
			iSubjectService.update(subject);
			return Result.result(0, null, "修改分类【"+subject.getName()+"】成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("修改分类", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="enable",method=RequestMethod.POST)
	@ResponseBody
	public Result enable(String uid) {
		try {
			iSubjectService.enable(uid);
			return Result.result(0, null, "激活分类成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("激活分类", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="disabled",method=RequestMethod.POST)
	@ResponseBody
	public Result disabled(String uid) {
		try {
			iSubjectService.disabled(uid);
			return Result.result(0, null, "停用分类成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("停用分类", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="delete",method=RequestMethod.POST)
	@ResponseBody
	public Result delete(String uid) {
		try {
			iSubjectService.delete(uid);
			return Result.result(0, null, "删除分类成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("删除分类", this.getClass(), e);
		}
	}
	
}
