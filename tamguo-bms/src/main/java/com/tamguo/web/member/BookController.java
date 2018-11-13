package com.tamguo.web.member;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.book.model.DocumentEntity;
import com.tamguo.modules.book.service.IBookService;
import com.tamguo.modules.book.service.IDocumentService;

@Controller
@RequestMapping(value="member/book")
public class BookController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IBookService iBookService;
	@Autowired
	IDocumentService iDocumentService;

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(String bookId , ModelAndView model) {
		model.setViewName("member/book/edit");
		model.addObject("bookId", bookId);
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getDocumentList", method = RequestMethod.POST)
	@ResponseBody
	public Result getDocumentList(String bookId) {
		List<DocumentEntity> documentList = null;
		try {
			documentList = iDocumentService.selectList(Condition.create().eq("book_id", bookId));
		} catch (Exception e) {
			logger.error(e.getMessage() , e );
			return Result.failResult("查询失败！");
		}
		return Result.successResult(documentList);
	}
}
