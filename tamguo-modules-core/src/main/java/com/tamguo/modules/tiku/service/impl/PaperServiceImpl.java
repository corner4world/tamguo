package com.tamguo.modules.tiku.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.common.utils.Result;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.config.redis.CacheService;
import com.tamguo.modules.tiku.dao.PaperMapper;
import com.tamguo.modules.tiku.dao.QuestionMapper;
import com.tamguo.modules.tiku.model.PaperEntity;
import com.tamguo.modules.tiku.service.IPaperService;

@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, PaperEntity> implements IPaperService{
	
	@Autowired
	private PaperMapper paperMapper;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private QuestionMapper questionMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaperEntity> findHistoryPaper() {
		List<PaperEntity> paperList = (List<PaperEntity>) cacheService.getObject(SystemConstant.HISTORY_PAPER);
		if(paperList == null){
			Page<PaperEntity> page = new Page<>(1 , 6);
			paperList = paperMapper.selectPage(page, Condition.create().eq("type", SystemConstant.ZHENGTI_PAPER_ID).eq("area_id", SystemConstant.BEIJING_AREA_ID).orderDesc(Arrays.asList("id")));
			cacheService.setObject(SystemConstant.HISTORY_PAPER, paperList , 2 * 60 * 60);
		}
		return paperList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaperEntity> findSimulationPaper() {
		List<PaperEntity> paperList = (List<PaperEntity>) cacheService.getObject(SystemConstant.SIMULATION_PAPER);
		if(paperList == null){
			Page<PaperEntity> page = new Page<>(1 , 6);
			paperList = paperMapper.selectPage(page, Condition.create().eq("type", SystemConstant.MONI_PAPER_ID).eq("area_id", SystemConstant.BEIJING_AREA_ID).orderDesc(Arrays.asList("id")));
			cacheService.setObject(SystemConstant.SIMULATION_PAPER, paperList , 2 * 60 * 60);
		}
		return paperList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaperEntity> findHotPaper(String areaId) {
		List<PaperEntity> paperList = (List<PaperEntity>) cacheService.getObject(SystemConstant.HOT_PAPER);
		if(paperList == null){
			Page<PaperEntity> page = new Page<>(1 , 10);
			paperList = paperMapper.selectPage(page, Condition.create().eq("area_id", areaId).orderDesc(Arrays.asList("id")));
			cacheService.setObject(SystemConstant.HOT_PAPER, paperList , 2 * 60 * 60);
		}
		return paperList;
	}

	@Transactional(readOnly=false)
	@Override
	public void addPaperQuestionInfo(String paperId, String title, String name, String type) {
		PaperEntity paper = paperMapper.selectById(paperId);
		String questionInfo = paper.getQuestionInfo();
		
		JSONArray qList = JSONArray.parseArray(questionInfo);
		JSONObject entity = new JSONObject();
		entity.put("name", name);
		entity.put("title", title);
		entity.put("type", type);
		entity.put("id", UUID.randomUUID().toString());
		qList.add(entity);
		
		paper.setQuestionInfo(qList.toString());
		paperMapper.updateById(paper);
	}

	@Transactional(readOnly=false)
	@Override
	public void updatePaperQuestionInfo(String paperId, String title, String name, String type, String uid) {
		PaperEntity paper = paperMapper.selectById(paperId);
		JSONArray qList = JSONArray.parseArray(paper.getQuestionInfo());
		for(int i =0 ; i<qList.size() ; i++){
			JSONObject q = qList.getJSONObject(i);
			if(q.getString("id").equals(uid)){
				q.put("name", name);
				q.put("title", title);
				q.put("type", type);
			}
		}
		
		paper.setQuestionInfo(qList.toString());
		paperMapper.updateById(paper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result deletePaper(String currMemberId , String paperId) {
		PaperEntity paper = paperMapper.selectById(paperId);
		if(!currMemberId.equals(paper.getCreaterId())) {
			return Result.result(501, null , "不能删除其他人的试卷！");
		}
		paperMapper.deleteById(paperId);
		// 删除试题
		questionMapper.delete(Condition.create().eq("paper_id", paperId));
		return Result.result(Result.SUCCESS_CODE, null , "删除成功！");
	}

	@Transactional(readOnly=false)
	@Override
	public Result deletePaperQuestionInfoBtn(String currMemberId , String paperId, String cuid) {
		PaperEntity paper = paperMapper.selectById(paperId);
		if(!paper.getCreaterId().equals(currMemberId)) {
			return Result.failResult("试卷属于当前用户，不能修改！");
		}
		JSONArray qList = JSONArray.parseArray(paper.getQuestionInfo());
		for(int i =0 ; i<qList.size() ; i++){
			JSONObject q = qList.getJSONObject(i);
			if(q.getString("uid").equals(cuid)){
				qList.remove(i);
			}
		}
		paper.setQuestionInfo(qList.toString());
		paperMapper.updateById(paper);
		return Result.result(Result.SUCCESS_CODE, null, "删除子卷成功");
	}

	@Transactional(readOnly=false)
	@Override
	public void addPaper(PaperEntity paper) {
		paper.setDownHits(0);
		paper.setOpenHits(0);
		paper.setQuestionInfo("[]");
		
		// 写入seo信息
		paper.setSeoTitle(paper.getName());
		paper.setSeoKeywords(paper.getName());
		paper.setSeoDescription(paper.getName());
		paperMapper.insert(paper);
	}

	@Override
	public Result updatePaper(PaperEntity paper ,String currMemberId) {
		PaperEntity entity = paperMapper.selectById(paper.getId());
		if(!entity.getCreaterId().equals(currMemberId)) {
			return Result.failResult("试卷属于当前用户，不能修改！");
		}
		paper.setCreaterId(currMemberId);
		paperMapper.updateById(paper);
		return Result.result(Result.SUCCESS_CODE, paper, "修改成功");
	}

}
