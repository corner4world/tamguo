package com.tamguo.web;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.tamguo.modules.book.model.BookEntity;
import com.tamguo.modules.book.model.DocumentEntity;
import com.tamguo.modules.book.model.enums.DocumentStatusEnum;
import com.tamguo.modules.book.service.IBookService;
import com.tamguo.modules.book.service.IDocumentService;

@Controller
public class BookController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IBookService iBookService;
	@Autowired
	private IDocumentService iDocumentService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value="book/{id}.html" , method=RequestMethod.GET)
	public ModelAndView book(@PathVariable String id ,  ModelAndView model) {
		BookEntity book = iBookService.selectById(id);
		model.addObject("book", book);
		model.setViewName("book/book");
		
		// 查询第一章
		List<DocumentEntity> documentList = iDocumentService.selectList(Condition.create().eq("book_id", id).eq("status", DocumentStatusEnum.NORMAL.getValue()).eq("parent_id", "0").orderAsc(Arrays.asList("create_date")));
		if(!CollectionUtils.isEmpty(documentList)) {
			model.addObject("document", documentList.get(0));
		}
		model.addObject("documentList", iDocumentService.selectList(Condition.create().eq("book_id", id).eq("status", DocumentStatusEnum.NORMAL.getValue()).orderAsc(Arrays.asList("create_date"))));
		return model;
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="document/{id}.html" , method=RequestMethod.GET)
	public ModelAndView document(@PathVariable String id ,  ModelAndView model) {
		model.setViewName("book/document");
		DocumentEntity document = iDocumentService.selectById(id);
		List<DocumentEntity> documentList = iDocumentService.selectList(Condition.create().eq("book_id", document.getBookId()).eq("status", DocumentStatusEnum.NORMAL.getValue()).orderAsc(Arrays.asList("create_date")));
		model.addObject("documentList", documentList);
		model.addObject("document", document);
		model.addObject("book", iBookService.selectById(document.getBookId()));
		return model;
	}
	
	@RequestMapping(value="document/{id}.html" , method=RequestMethod.POST)
	@ResponseBody
	public Result getDocument(@PathVariable String id) {
		try {
			DocumentEntity document =  iDocumentService.selectById(id);
			return Result.result(0, document, "查询成功！");
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			return Result.result(1, null, "查询失败！");
		}
	}
}
