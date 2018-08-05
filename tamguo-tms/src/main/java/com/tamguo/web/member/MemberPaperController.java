package com.tamguo.web.member;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.member.model.MemberEntity;
import com.tamguo.modules.tiku.model.PaperEntity;
import com.tamguo.modules.tiku.model.enums.QuestionTypeEnum;
import com.tamguo.modules.tiku.service.IPaperService;
import com.tamguo.utils.ShiroUtils;

@Controller
public class MemberPaperController {
	
	@Autowired
	private IPaperService iPaperService;
	

	@RequestMapping(value = "/member/paper.html", method = RequestMethod.GET)
	public ModelAndView paper(ModelAndView model, HttpSession session){
		model.setViewName("member/paperList");
		return model;
	}
	
	@RequestMapping(value = "/member/findPaper.html", method = RequestMethod.GET)
	@ResponseBody
	public Result findPaper(String paperId) {
		return Result.successResult(iPaperService.selectById(paperId));
	}
	
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "member/paper/list.html" , method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> paperList(String name , Integer page , Integer limit , HttpSession session){
		MemberEntity member = ((MemberEntity)session.getAttribute("currMember"));
		Page<PaperEntity> list = iPaperService.selectPage(new Page<>(page , limit) , Condition.create().like("name", name).eq("creater_id", member.getId()));
		return Result.jqGridResult(list.getRecords(), list.getTotal(), limit, page, list.getPages());
	}
	
	@RequestMapping(value="member/paperList/addPaperQuestionInfo.html",method=RequestMethod.POST)
	@ResponseBody
	public Result addPaperQuestionInfo(@RequestBody JSONObject data){
		try {
			String paperId ; String title ; String name ;String type;
			paperId = data.getString("uid");
			title = data.getString("title");
			type = data.getString("type");
			name = QuestionTypeEnum.getQuestionType(type).getDesc();
			iPaperService.addPaperQuestionInfo(paperId , title , name , type);
			return Result.result(0, null, "修改成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("添加questionInfo", this.getClass(), e);
		}
	}
	
	@RequestMapping("member/paperList/updatePaperQuestionInfo.html")
	@ResponseBody
	public Result updatePaperQuestionInfo(@RequestBody JSONObject data){
		try {
			String paperId ; String title ; String name ; String type ; String uid;
			paperId = data.getString("uid");
			title = data.getString("title");
			type = data.getString("type");
			name = QuestionTypeEnum.getQuestionType(type).getDesc();
			uid = data.getString("infoUid");
			iPaperService.updatePaperQuestionInfo(paperId , title , name , type , uid);
			return Result.result(0, null, "修改成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("修改questionInfo", this.getClass(), e);
		}
	}
	
	@RequestMapping("member/paperList/deletePaper")
	@ResponseBody
	public Result deletePaper(String paperId){
		try {
			return iPaperService.deletePaper(ShiroUtils.getMemberId() , paperId);
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("删除试卷", this.getClass(), e);
		}
	}
	
	@RequestMapping("member/paperList/deletePaperQuestionInfoBtn.html")
	@ResponseBody
	public Result deletePaperQuestionInfoBtn(String paperId , String uid){
		try {
			return iPaperService.deletePaperQuestionInfoBtn(ShiroUtils.getMemberId() , paperId , uid);
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("删除子卷", this.getClass(), e);
		}
	}
	
	@RequestMapping(value="member/paperList/addPaper.html",method=RequestMethod.POST)
	@ResponseBody
	public Result addPaper(@RequestBody PaperEntity paper,HttpSession session){
		try {
			MemberEntity member = (MemberEntity) session.getAttribute("currMember");
			paper.setCreaterId(member.getId());
			iPaperService.addPaper(paper);
			return  Result.result(Result.SUCCESS_CODE, paper, "添加成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("添加试卷", this.getClass(), e);
		}
	}
	
	@RequestMapping(value="member/paperList/updatePaper.html",method=RequestMethod.POST)
	@ResponseBody
	public Result updatePaper(@RequestBody PaperEntity paper){
		try {
			return iPaperService.updatePaper(paper , ShiroUtils.getMemberId());
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("修改试卷", this.getClass(), e);
		}
	}
}
