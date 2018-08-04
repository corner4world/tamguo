package com.tamguo.modules.tiku.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.config.redis.CacheService;
import com.tamguo.modules.tiku.dao.PaperMapper;
import com.tamguo.modules.tiku.dao.SchoolMapper;
import com.tamguo.modules.tiku.model.PaperEntity;
import com.tamguo.modules.tiku.model.SchoolEntity;
import com.tamguo.modules.tiku.service.ISchoolService;

@Service
public class SchoolService extends ServiceImpl<SchoolMapper, SchoolEntity> implements ISchoolService {

	@Autowired
	private SchoolMapper schoolMapper;
	@Autowired
	private PaperMapper paperMapper;
	@Autowired
	private CacheService cacheService;

	@SuppressWarnings("unchecked")
	@Override
	public List<SchoolEntity> findEliteSchoolPaper(String shcoolId) {
		List<SchoolEntity> schoolList = (List<SchoolEntity>) cacheService.getObject(SystemConstant.ELITE_SCHOOL_PAPER);
		// 获取名校试卷
		if(schoolList == null || schoolList.isEmpty()){
			Page<SchoolEntity> page = new Page<>(1 , 3);
			schoolList = schoolMapper.selectPage(page, Condition.create().eq("id", shcoolId));
			for(SchoolEntity school : schoolList){
				Page<PaperEntity> p = new Page<>(1 , 3);
				List<PaperEntity> paperList = paperMapper.selectPage(p, Condition.create().eq("school_id", school.getId()));
				school.setPaperList(paperList);
			}
			cacheService.setObject(SystemConstant.ELITE_SCHOOL_PAPER, schoolList , 2 * 60 * 60);
		}
		return schoolList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchoolEntity> findEliteSchool() {
		List<SchoolEntity> schoolList = (List<SchoolEntity>) cacheService.getObject(SystemConstant.ELITE_PAPER);
		if(schoolList == null || schoolList.isEmpty()){
			RowBounds row = new RowBounds(1 , 6);
			schoolList = schoolMapper.selectPage(row, Condition.EMPTY);
			cacheService.setObject(SystemConstant.ELITE_PAPER, schoolList , 2 * 60 * 60);
		}
		return schoolList;
	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SchoolEntity> findSchoolByAreaId(String areaId) {
		return schoolMapper.selectList(Condition.create().in("area_id", Arrays.asList(areaId.split(","))));
	}
	
}
