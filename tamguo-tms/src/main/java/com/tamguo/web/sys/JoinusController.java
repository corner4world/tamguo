package com.tamguo.web.sys;

import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.common.utils.Result;
import com.tamguo.modules.sys.model.TeacherEntity;
import com.tamguo.modules.sys.service.ITeacherService;

@Controller
public class JoinusController {
	
	@Autowired
	private ITeacherService iTeacherService;

	@RequestMapping(value = "teacher/joinus.html", method = RequestMethod.GET)
    public ModelAndView register(ModelAndView model , HttpSession session) {
		model.setViewName("teacher/joinus");
		return model;
    }
	
	@RequestMapping(value = "teacher/info.html", method = RequestMethod.POST)
	@ResponseBody
	public Result getTeacher(@RequestBody Map<String, Object> param) {
		String mobile = (String) param.get("mobile");
		String verifyCode = (String) param.get("verifyCode");
		Result result = iTeacherService.getTeacherByMobile(mobile, verifyCode);
		return result;
	}
	
	@RequestMapping(value = "teacher/joinus.html", method = RequestMethod.POST)
	@ResponseBody
	public Result teacherJoinus(@RequestBody TeacherEntity teacher) {
		iTeacherService.joinus(teacher);
		return Result.successResult(null);
	}
}
