package com.tamguo.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面视图
 */
@Controller
public class SysPageController {
	
	@RequestMapping("sys/{fn}/{page}")
	public String sys(@PathVariable("fn") String fn , @PathVariable("page") String page){
		return "modules/sys/" + fn + "/" + page;
	}
	
	@RequestMapping("tiku/{fn}/{page}")
	public String ti(@PathVariable("fn") String fn , @PathVariable("page") String page){
		return "modules/tiku/" + fn + "/" + page;
	}
	
}
