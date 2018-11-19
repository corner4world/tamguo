package com.tamguo.web.tiku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tamguo.common.utils.Result;
import com.tamguo.modules.sys.service.ISysAreaService;

@Controller
public class AreaController {

	@Autowired
	private ISysAreaService iSysAreaService;

	@RequestMapping(value = {"area/findAreaTree.html"}, method = RequestMethod.GET)
	@ResponseBody
	public Result findAreaTree() {
		return iSysAreaService.findAreaTree();
	}
	
	
}
