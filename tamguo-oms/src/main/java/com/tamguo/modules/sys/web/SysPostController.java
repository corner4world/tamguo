package com.tamguo.modules.sys.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.sys.model.SysPostEntity;
import com.tamguo.modules.sys.model.condition.SysPostCondition;
import com.tamguo.modules.sys.service.ISysPostService;

@Controller
@RequestMapping(path="sys/post")
public class SysPostController {

	private final String POST_INDEX_PAGE = "modules/sys/post/index";
	private final String POST_UPDATE_PAGE = "modules/sys/post/update";
	private final String POST_ADD_PAGE = "modules/sys/post/add";
	
	@Autowired
	private ISysPostService iSysPostService;

	@RequestMapping(path="index")
	public String index() {
		return POST_INDEX_PAGE;
	}
	
	@RequestMapping(path="update")
	public ModelAndView update(ModelAndView model , String id) {
		model.addObject("title", "修改岗位");
		model.addObject("post", iSysPostService.selectById(id));
		model.setViewName(POST_UPDATE_PAGE);
		return model;
	}
	
	@RequestMapping(path="add")
	public String add() {
		return POST_ADD_PAGE;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysPostCondition condition) {
		Page<SysPostEntity> page = iSysPostService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
	
	@RequestMapping(path="save",method=RequestMethod.POST)
	@ResponseBody
	public Result save(SysPostEntity post) {
		try {
			iSysPostService.add(post);
			return Result.result(0, null, "操作成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("保存岗位", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="update",method=RequestMethod.POST)
	@ResponseBody
	public Result update(SysPostEntity post) {
		try {
			iSysPostService.update(post);
			return Result.result(0, null, "操作成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("修改岗位", this.getClass(), e);
		}
		
	}
}
