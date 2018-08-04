package com.tamguo.modules.tiku.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.config.redis.CacheService;
import com.tamguo.modules.tiku.dao.AdMapper;
import com.tamguo.modules.tiku.model.AdEntity;
import com.tamguo.modules.tiku.service.IAdService;

@Service
public class AdServiceService extends ServiceImpl<AdMapper, AdEntity> implements IAdService{
	
	@Autowired
	AdMapper adMapper;
	@Autowired
	CacheService cacheService;

	@SuppressWarnings("unchecked")
	@Override
	public List<AdEntity> findAll() {
		List<AdEntity> adList = (List<AdEntity>) cacheService.getObject(SystemConstant.ALL_AD);
		if(adList == null){
			adList = adMapper.selectList(Condition.EMPTY);
			cacheService.setObject(SystemConstant.ALL_AD, adList , 2 * 60 * 60);
		}
		return adList;
	}
	
}
