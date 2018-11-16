package com.tamguo.web.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.tamguo.modules.book.service.IBookCategoryService;
import com.tamguo.modules.book.service.IBookService;
import com.tamguo.modules.book.service.IDocumentService;

@Controller(value="memberBookController")
@RequestMapping(value="member/book")
public class BookController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IBookService iBookService;
	@Autowired
	IBookCategoryService iBookCategoryService;
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
}
