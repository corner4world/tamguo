package com.tamguo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.model.QuestionEntity;
import com.tamguo.service.IQuestionService;
import com.tamguo.util.Result;

@Controller
public class QuestionController {
	
	@Autowired
	IQuestionService iQuestionService;
	
	@RequestMapping(path= {"question/{subjectId}/{chapterId}-{current}-{size}"})
	public String index(@PathVariable String subjectId , @PathVariable String chapterId ,
			@PathVariable String current ,@PathVariable String size ,
			ModelAndView model) {
		model.addObject("chapterId", chapterId);
		model.addObject("current" , current);
		model.addObject("size", size);
		return "question";
	}
	
	@RequestMapping(path= {"question/{subjectId}/{chapterId}-{current}-{size}"} , method=RequestMethod.POST)
	@ResponseBody
	public Result getQuestion(@PathVariable String chapterId ,
			@PathVariable Integer current ,@PathVariable Integer size ,
			ModelAndView model) {
		Page<QuestionEntity> page =  new Page<QuestionEntity>(current, size);
		return Result.successResult(iQuestionService.findPage(chapterId, page));
	}
	
}
