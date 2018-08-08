package com.tamguo.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.QuestionEntity;
import com.tamguo.modules.tiku.service.IChapterService;
import com.tamguo.modules.tiku.service.IQuestionService;

@Controller
public class ChapterController {

	@Autowired
	private IChapterService iChapterService;
	@Autowired
	private IQuestionService iQuestionService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="chapter/{chapterId}-{current}-{size}.html")
	public ModelAndView list(@PathVariable String chapterId , @PathVariable Integer current 
			, @PathVariable Integer size, ModelAndView model) {
		ChapterEntity chapter = iChapterService.selectById(chapterId);
		Page<QuestionEntity> page = iQuestionService.selectPage(new Page<>(current, size) , Condition.create().eq("chapter_id", chapterId).orderDesc(Arrays.asList("id")));
		model.addObject("chapter", chapter);
		model.addObject("page", page);
		model.addObject("nextPage", current == page.getPages() ? page.getPages() : current+1);
		model.addObject("prePage", current == 1 ? 1 : current - 1);
		model.setViewName("question");
		return model;
	}
}
