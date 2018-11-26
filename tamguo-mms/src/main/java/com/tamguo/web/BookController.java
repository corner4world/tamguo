package com.tamguo.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.book.model.BookEntity;
import com.tamguo.modules.book.model.DocumentEntity;
import com.tamguo.modules.book.model.enums.DocumentStatusEnum;
import com.tamguo.modules.book.service.IBookService;
import com.tamguo.modules.book.service.IDocumentService;
import com.tamguo.utils.ShiroUtils;

@Controller
public class BookController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IBookService iBookService;
	@Autowired
	private IDocumentService iDocumentService;
	
	@RequestMapping(value = "editBook/{bookId}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String bookId , ModelAndView model) {
		model.setViewName("book/edit");
		model.addObject("bookId", bookId);
		return model;
	}

	@RequestMapping(value = {"booklist.html"}, method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("booklist");
		return model;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getDocumentList", method = RequestMethod.POST)
	@ResponseBody
	public Result getDocumentList(String id) {
		Map<String, Object> map = new HashMap<>();
		try {
			BookEntity book = iBookService.selectById(id);
			List<DocumentEntity> documentList = iDocumentService.selectList(Condition.create().eq("book_id", id).eq("status", DocumentStatusEnum.NORMAL.getValue()));
			
			map.put("documentList", this.processDocumentList(documentList));
			map.put("book", book);
		} catch (Exception e) {
			logger.error(e.getMessage() , e );
			return Result.failResult("查询失败");
		}
		return Result.successResult(map);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"getBookList.html"}, method = RequestMethod.POST)
	@ResponseBody
	public Result getBookList() {
		try {
			List<BookEntity> bookList = iBookService.selectList(Condition.create().eq("owner", ShiroUtils.getMemberId()).orderDesc(Arrays.asList("create_date")));
			return Result.successResult(bookList);
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			return Result.failResult("查询失败！");
		}
	}
	
	@RequestMapping(value = {"saveBook"}, method = RequestMethod.POST)
	@ResponseBody
	public Result saveBook(@RequestBody BookEntity book) {
		try {
			book.setOwner(ShiroUtils.getMemberId());
			book.setCategoryId(StringUtils.join(book.getCategoryIds(), ","));
			iBookService.saveBook(book);
			return Result.result(0, null, "保存成功");
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			return Result.result(1, null, "保存失败");
		}
	}
	
	private JSONArray processDocumentList(List<DocumentEntity> documentList) {
		JSONArray entitys = new JSONArray();
		for(int i=0 ; i<documentList.size() ; i ++) {
			DocumentEntity doc = documentList.get(i);
			JSONObject entity = new JSONObject();
			
			entity.put("id", doc.getId());
			entity.put("text", doc.getName());
			entity.put("parent", "0".equals(doc.getParentId()) ? "#" : doc.getParentId());
			entity.put("identify", doc.getId());
			entity.put("version", doc.getCreateDate().getTime());
			JSONObject attr = new JSONObject();
			attr.put("is_open", "0".equals(doc.getIsOpen()) ? false : true);
			entity.put("a_attr", attr);
			entitys.add(entity);
		}
		return entitys;
	}
	
	@RequestMapping(value="book/{id}.html" , method=RequestMethod.GET)
	@ResponseBody
	public Result book(@PathVariable String id) {
		try {
			BookEntity book =  iBookService.selectById(id);
			
			String categoryIds = book.getCategoryId();
		 	book.setCategoryIds(Arrays.asList(categoryIds));
			// 获取categoryIds
			return Result.result(0, book, "查询成功！");
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			return Result.result(1, null, "查询失败！");
		}
	}
}
