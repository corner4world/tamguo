package com.tamguo.web.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tamguo.common.utils.Result;
import com.tamguo.modules.book.model.DocumentEntity;
import com.tamguo.modules.book.service.IDocumentService;

@Controller
@RequestMapping(value="member/document")
public class DocumentController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IDocumentService iDocumentService;
	
	@RequestMapping(value = "{id}" , method = RequestMethod.GET)
	@ResponseBody
	public Result getDocument(@PathVariable String id) {
		DocumentEntity document = null;
		try {
			document = iDocumentService.selectById(id);
		} catch (Exception e) {
			logger.error(e.getMessage() , e );
			return Result.failResult("查询失败");
		}
		return Result.successResult(document);
	}
	
	/**
	 * 编辑内容
	 */
	@RequestMapping(value = "modify" , method = RequestMethod.POST)
	@ResponseBody
	public Result modify(DocumentEntity document) {
		try {
			iDocumentService.modify(document);
		} catch (Exception e) {
			logger.error(e.getMessage() , e );
			return Result.failResult("保存失败");
		}
		return Result.successResult("保存成功");
	}
	
	/**
	 * 创建文档
	 */
	@RequestMapping(value = "create" , method = RequestMethod.POST)
	@ResponseBody
	public Result create(DocumentEntity document) {
		try {
			iDocumentService.create(document);
		} catch (Exception e) {
			logger.error(e.getMessage() , e );
			return Result.failResult("保存失败");
		}
		return Result.successResult(document);
	}
}
