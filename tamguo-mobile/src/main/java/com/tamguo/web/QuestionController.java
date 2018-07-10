package com.tamguo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuestionController {

	@RequestMapping(path= {"question","/"})
	public String index() {
		return "question";
	}
	
}
