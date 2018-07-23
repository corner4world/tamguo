package com.tamguo.modules.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tamguo.modules.sys.dao.SysOfficeMapper;
import com.tamguo.modules.sys.model.SysOfficeEntity;
import com.tamguo.modules.sys.model.condition.SysOfficeCondition;
import com.tamguo.modules.sys.service.ISysOfficeService;

@Service
public class SysOfficeServiceImpl implements ISysOfficeService {
	
	@Autowired
	private SysOfficeMapper sysOfficeMapper;

	@Override
	public List<SysOfficeEntity> listData(SysOfficeCondition condition) {
		return sysOfficeMapper.listData(condition);
	}

}
