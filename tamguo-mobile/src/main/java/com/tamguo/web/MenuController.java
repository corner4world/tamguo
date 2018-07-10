package com.tamguo.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tamguo.model.MenuEntity;
import com.tamguo.service.IMenuService;
import com.tamguo.util.ExceptionSupport;
import com.tamguo.util.Result;

@Controller
public class MenuController {
	
	@Resource
	private IMenuService iMenuService;

	@RequestMapping(path="menu/findAllMenus",method=RequestMethod.POST)
	@ResponseBody
	public Result findAllMenus() {
		// 获取全部菜单
		try {
			List<MenuEntity> allMenuList = iMenuService.findAllMenus();
			return Result.successResult(allMenuList);
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("查询所有菜单", this.getClass(), e);
		}
		
	}
		
}
