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
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.model.condition.SubjectCondition;
import com.tamguo.modules.tiku.service.ISubjectService;

@Controller
@RequestMapping(path="tk/subject")
public class SubjectController {

	/** 题库分类*/
	private final String SUBJECT_INDEX_PAGE = "modules/tiku/subject/index";
	
	@Autowired
	private ISubjectService iSubjectService;

	@RequestMapping(path="list")
	public ModelAndView list(ModelAndView model) {
		model.setViewName(SUBJECT_INDEX_PAGE);
		return model;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SubjectCondition condition) {
		Page<SubjectEntity> page = iSubjectService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
}
