package com.tamguo.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.dao.SysRoleMapper;
import com.tamguo.modules.sys.model.SysRoleEntity;
import com.tamguo.modules.sys.model.condition.SysRoleCondition;
import com.tamguo.modules.sys.service.ISysRoleService;

@Service
public class SysRoleServiceImpl implements ISysRoleService{
	
	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Transactional(readOnly=true)
	@Override
	public Page<SysRoleEntity> listData(SysRoleCondition condition) {
		Page<SysRoleEntity> page = new Page<>(condition.getPageNo(), condition.getPageSize());
		return page.setRecords(sysRoleMapper.listData(condition , page));
	}

}
