package com.tamguo.modules.sys.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.model.SysPostEntity;
import com.tamguo.modules.sys.model.condition.SysPostCondition;
import com.tamguo.modules.sys.service.IPostService;
import com.tamguo.modules.sys.utils.Result;

@Controller
@RequestMapping(path="sys/post")
public class PostController {

	private final String POST_INDEX_PAGE = "modules/sys/post/index";
	
	@Autowired
	private IPostService iPostService;

	@RequestMapping(path="index")
	public String index() {
		return POST_INDEX_PAGE;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysPostCondition condition) {
		Page<SysPostEntity> page = iPostService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
}
