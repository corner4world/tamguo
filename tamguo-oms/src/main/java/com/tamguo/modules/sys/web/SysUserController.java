package com.tamguo.modules.sys.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;
import com.tamguo.modules.sys.service.IPostService;
import com.tamguo.modules.sys.service.ISysUserService;
import com.tamguo.modules.sys.utils.Result;

@Controller
@RequestMapping(path="sys/user")
public class SysUserController {
	
	private final String USER_LIST_PAGE = "modules/sys/user/list";
	private final String USER_DETAIL_PAGE = "modules/sys/user/update";
	
	@Autowired
	private ISysUserService iSysUserService;
	@Autowired
	private IPostService iPostService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="list")
	public ModelAndView list(ModelAndView model) {
		model.setViewName(USER_LIST_PAGE);
		model.addObject("postList", iPostService.selectList(Condition.create().eq("status", "0")));
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="update")
	public ModelAndView update(String userCode , ModelAndView model) {
		model.setViewName(USER_DETAIL_PAGE);
		model.addObject("user", iSysUserService.selectById(userCode));
		model.addObject("postList", iPostService.selectList(Condition.create().eq("status", "0")));
		model.addObject("userPostCode", iSysUserService.queryUserPostByUserCode(userCode));
		return model;
	}

	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(SysUserCondition condition) {
		Page<SysUserEntity> page = iSysUserService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
	
	@RequestMapping(path="checkLoginCode",method=RequestMethod.GET)
	@ResponseBody
	public Boolean checkLoginCode(String oldLoginCode , String loginCode) {
		return iSysUserService.checkLoginCode(oldLoginCode , loginCode);
	}
	
	@RequestMapping(path="update",method=RequestMethod.POST)
	@ResponseBody
	public Result update(SysUserEntity user) {
		iSysUserService.update(user);
		return Result.result(0, null, "保存用户【"+user.getUserCode()+"】成功！");
	}
}
