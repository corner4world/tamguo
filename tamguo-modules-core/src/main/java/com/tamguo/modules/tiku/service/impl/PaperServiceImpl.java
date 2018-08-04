package com.tamguo.modules.tiku.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.config.redis.CacheService;
import com.tamguo.modules.tiku.dao.PaperMapper;
import com.tamguo.modules.tiku.model.PaperEntity;
import com.tamguo.modules.tiku.service.IPaperService;

@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, PaperEntity> implements IPaperService{
	
	@Autowired
	private PaperMapper paperMapper;
	@Autowired
	private CacheService cacheService;
	@SuppressWarnings("unchecked")
	@Override
	public List<PaperEntity> findHistoryPaper() {
		List<PaperEntity> paperList = (List<PaperEntity>) cacheService.getObject(SystemConstant.HISTORY_PAPER);
		if(paperList == null){
			Page<PaperEntity> page = new Page<>(1 , 6);
			paperList = paperMapper.selectPage(page, Condition.create().eq("type", SystemConstant.HISTORY_PAPER).eq("area_id", SystemConstant.BEIJING_AREA_ID));
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
			paperList = paperMapper.selectPage(page, Condition.create().eq("type", SystemConstant.SIMULATION_PAPER).eq("area_id", SystemConstant.BEIJING_AREA_ID));
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
			paperList = paperMapper.selectPage(page, Condition.create().eq("area_id", areaId));
			cacheService.setObject(SystemConstant.HOT_PAPER, paperList , 2 * 60 * 60);
		}
		return paperList;
	}

}
