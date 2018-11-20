package com.tamguo.web;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.book.model.DocumentEntity;
import com.tamguo.modules.book.model.enums.DocumentStatusEnum;
import com.tamguo.modules.book.service.IDocumentService;

@Controller
@RequestMapping(value="document")
public class HistoryDocController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IDocumentService iDocumentService;

	/**
	 * 历史记录
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "history" , method = RequestMethod.POST)
	public ModelAndView history(String id , ModelAndView model) {
		model.setViewName("book/history");
		DocumentEntity document = iDocumentService.selectById(id);
		model.addObject("document", document);
		model.addObject("historyList", iDocumentService.selectList(Condition.create().eq("batch_no", document.getBatchNo()).eq("status", DocumentStatusEnum.HISTORY.getValue()).orderDesc(Arrays.asList("create_date"))));
		return model;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "history/delete" , method = RequestMethod.POST)
	@ResponseBody
	public Result delete(String id) {
		try {
			iDocumentService.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			return Result.failResult("删除失败！");
		}
		return Result.successResult("删除成功！");
	}
	
	/**
	 * 恢复
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "history/restore" , method = RequestMethod.POST)
	@ResponseBody
	public Result restore(String id) {
		try {
			DocumentEntity history = iDocumentService.selectById(id);
			
			String content = history.getContent();
			String markdown = history.getMarkdown();
			DocumentEntity document = iDocumentService.selectOne(Condition.create().eq("batch_no", history.getBatchNo()).eq("status", DocumentStatusEnum.NORMAL.getValue()));
			document.setContent(content);
			document.setMarkdown(markdown);
			document.setCover("no");
			iDocumentService.modify(document);
			return Result.successResult(document);
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			return Result.failResult("恢复失败！");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "history/compare/{id}" , method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView compare(@PathVariable String id , ModelAndView model) {
		model.setViewName("book/compare");
		DocumentEntity history = iDocumentService.selectById(id);
		model.addObject("history", history);
		model.addObject("document", iDocumentService.selectOne(Condition.create().eq("status", DocumentStatusEnum.NORMAL.getValue()).eq("batch_no", history.getBatchNo())));
		return model;
	}
}
