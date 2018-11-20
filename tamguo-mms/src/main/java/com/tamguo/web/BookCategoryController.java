package com.tamguo.web;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.book.model.BookCategoryEntity;
import com.tamguo.modules.book.service.IBookCategoryService;

@Controller
public class BookCategoryController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IBookCategoryService iBookCategoryService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"getBookCategory.html"}, method = RequestMethod.POST)
	@ResponseBody
	public Result getBookCategory(String parentId) {
		try {
			List<BookCategoryEntity> bookCategoryList = null;
			if(StringUtils.isEmpty(parentId)) {
				bookCategoryList = iBookCategoryService.selectList(Condition.create().eq("parent_id", "0"));
			}else {
				bookCategoryList = iBookCategoryService.selectList(Condition.create().eq("parent_id", parentId));
			}
			
			return Result.successResult(processBookCategoryList(bookCategoryList));
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			return Result.failResult("查询失败！");
		}
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray processBookCategoryList(List<BookCategoryEntity> bookCategoryList) {
		JSONArray entitys = new JSONArray();
		for(int i=0 ; i<bookCategoryList.size() ; i++) {
			BookCategoryEntity bc = bookCategoryList.get(i);
			
			JSONObject entity = new JSONObject();
			entity.put("label", bc.getName());
			entity.put("value", bc.getId());
			Integer count = iBookCategoryService.selectCount(Condition.create().eq("parent_id", bc.getId()));
			if(count > 0) {
				entity.put("children", new JSONArray());	
			}
			entitys.add(entity);
		}
		return entitys;
	}
}
