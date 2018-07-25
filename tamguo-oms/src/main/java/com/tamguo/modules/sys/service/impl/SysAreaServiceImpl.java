package com.tamguo.modules.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.sys.dao.SysAreaMapper;
import com.tamguo.modules.sys.model.SysAreaEntity;
import com.tamguo.modules.sys.model.condition.SysAreaCondition;
import com.tamguo.modules.sys.service.ISysAreaService;

@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysAreaEntity> implements ISysAreaService{
	
	@Autowired
	SysAreaMapper sysAreaMapper;

	@Override
	public List<SysAreaEntity> listData(SysAreaCondition condition) {
		return sysAreaMapper.listData(condition);
	}

}
