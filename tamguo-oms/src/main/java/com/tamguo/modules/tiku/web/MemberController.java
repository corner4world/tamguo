package com.tamguo.modules.tiku.web;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.book.model.BookCategoryEntity;
import com.tamguo.modules.book.model.BookEntity;
import com.tamguo.modules.book.service.IBookCategoryService;
import com.tamguo.modules.book.service.IBookService;
import com.tamguo.modules.member.model.MemberEntity;
import com.tamguo.modules.member.model.condition.MemberCondition;
import com.tamguo.modules.member.service.IMemberService;

@Controller
@RequestMapping(value="tiku/member")
public class MemberController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IMemberService iMemberService;
	@Autowired
	IBookService iBookService;
	@Autowired
	IBookCategoryService iBookCategoryService;

	@RequestMapping(value="list")
	public ModelAndView list(ModelAndView model) {
		model.setViewName("modules/tiku/member/list");
		return model;
	}
	
	@RequestMapping(value="listData" , method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(MemberCondition condition){
		Page<MemberEntity> page = iMemberService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}
	
	@RequestMapping(value="reward" , method=RequestMethod.GET)
	public ModelAndView reward(String id , ModelAndView model) {
		model.setViewName("modules/tiku/member/reward");
		model.addObject("member", iMemberService.selectById(id));
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="bookTreeData",method=RequestMethod.POST)
	@ResponseBody
	public List<BookEntity> bookTreeData(String memberId){
	 	List<BookEntity> bookList = iBookService.selectList(Condition.create().eq("owner", memberId).orderDesc(Arrays.asList("create_date")));
	 	if(!CollectionUtils.isEmpty(bookList)) {
	 		for(int i=0 ; i<bookList.size() ; i++) {
	 			BookEntity book = bookList.get(i);
	 			BookCategoryEntity category = iBookCategoryService.selectById(book.getCategoryId().split(",")[0]);
	 			book.setCategoryName(category.getName());
	 		}
	 	}
		return bookList;
	}
	
	@RequestMapping(value="reward" , method=RequestMethod.POST)
	@ResponseBody
	public Result submitReward(String id , String bookId , Integer rewardPoint , BigDecimal rewardMoney) {
		try {
			iMemberService.reward(id , bookId , rewardPoint , rewardMoney);
			return Result.result(0, null, "奖励成功！");
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			return Result.result(1, null, "奖励失败！");
		}
	}
	
}
