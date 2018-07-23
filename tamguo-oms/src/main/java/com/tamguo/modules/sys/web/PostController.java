package com.tamguo.modules.sys.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.model.SysPostEntity;
import com.tamguo.modules.sys.model.condition.SysPostCondition;
import com.tamguo.modules.sys.service.IPostService;
import com.tamguo.modules.sys.utils.ExceptionSupport;
import com.tamguo.modules.sys.utils.Result;

@Controller
@RequestMapping(path="sys/post")
public class PostController {

	private final String POST_INDEX_PAGE = "modules/sys/post/index";
	private final String POST_DETAIL_PAGE = "modules/sys/post/detail";
	
	@Autowired
	private IPostService iPostService;

	@RequestMapping(path="index")
	public String index() {
		return POST_INDEX_PAGE;
	}
	
	@RequestMapping(path="detail")
	public ModelAndView detail(ModelAndView model , String id) {
		if(StringUtils.isEmpty(id)) {
			model.addObject("title", "新增岗位");
		}else {
			model.addObject("title", "修改岗位");
			model.addObject("post", iPostService.selectById(id));
		}
		model.setViewName(POST_DETAIL_PAGE);
		return model;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysPostCondition condition) {
		Page<SysPostEntity> page = iPostService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
	
	@RequestMapping(path="save",method=RequestMethod.POST)
	@ResponseBody
	public Result save(SysPostEntity post) {
		try {
			if(StringUtils.isEmpty(post.getId())) {
				iPostService.add(post);
			}else {
				iPostService.update(post);
			}
			return Result.result(0, null, "操作成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("保存岗位", this.getClass(), e);
		}
		
	}
}
