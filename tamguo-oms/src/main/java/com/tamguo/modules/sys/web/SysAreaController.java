package com.tamguo.modules.sys.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.tamguo.modules.sys.model.SysAreaEntity;
import com.tamguo.modules.sys.model.condition.SysAreaCondition;
import com.tamguo.modules.sys.service.ISysAreaService;
import com.tamguo.modules.sys.utils.ExceptionSupport;
import com.tamguo.modules.sys.utils.Result;

@Controller
@RequestMapping(path="sys/area")
public class SysAreaController {

	private final String AREA_INDEX_PAGE = "modules/sys/area/index";
	private final String AREA_ADD_PAGE = "modules/sys/area/add";

	@Autowired
	private ISysAreaService iSysAreaService; 

	
	@RequestMapping(path="index")
	public String index() {
		return AREA_INDEX_PAGE;
	}
	
	@RequestMapping(path="add")
	@ResponseBody
	public ModelAndView add(String parentCode , ModelAndView model) {
		model.setViewName(AREA_ADD_PAGE);
		model.addObject("parentArea", iSysAreaService.selectById(parentCode));
		return model;
	}
	
	@RequestMapping(path="save")
	@ResponseBody
	public Result save(SysAreaEntity area) {
		try {
			iSysAreaService.save(area);
			return Result.result(0, null, "保存【"+area.getAreaName()+"】地区成功！");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("保存地区", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public List<SysAreaEntity> listData(SysAreaCondition condition) {
		List<SysAreaEntity> list = iSysAreaService.listData(condition);
		return list;
	}
	
	@RequestMapping(path="treeData")
	@ResponseBody
	public JSONArray treeData(String excludeId) {
		return iSysAreaService.treeData(excludeId);
	}
	
	
}
