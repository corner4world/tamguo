package com.tamguo.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.dao.SysPostMapper;
import com.tamguo.modules.sys.model.SysPostEntity;
import com.tamguo.modules.sys.model.condition.SysPostCondition;
import com.tamguo.modules.sys.service.IPostService;

@Service
public class PostServiceImpl implements IPostService{
	
	@Autowired
	private SysPostMapper sysPostMapper;

	@Override
	public Page<SysPostEntity> listData(SysPostCondition condition) {
		Page<SysPostEntity> page = new Page<>(condition.getPageNo(), condition.getPageSize());
		return page.setRecords(sysPostMapper.listData(condition , page));
	}

}
