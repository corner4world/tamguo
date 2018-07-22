package com.tamguo.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.dao.SysUserMapper;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;
import com.tamguo.modules.sys.service.ISysUserService;

@Service
public class SysUserServiceImpl implements ISysUserService{
	
	@Autowired
	public SysUserMapper sysUserMapper;
	
	@Transactional(readOnly=false)
	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserMapper.queryByUserName(username);
	}


	@Transactional(readOnly=false)
	@Override
	public Page<SysUserEntity> listData(SysUserCondition condition) {
		Page<SysUserEntity> page = new Page<>(condition.getPageNo() , condition.getPageSize());
		return page.setRecords(sysUserMapper.listData(condition , page));
	}

}
