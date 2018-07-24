package com.tamguo.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
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
	public SysUserEntity queryByLoginCode(String loginCode) {
		return sysUserMapper.queryByLoginCode(loginCode);
	}


	@Transactional(readOnly=false)
	@Override
	public Page<SysUserEntity> listData(SysUserCondition condition) {
		if(!StringUtils.isEmpty(condition.getCompanyCode())) {
			condition.setCompanyCode("%" + condition.getCompanyCode() + "%");
		}
		if(!StringUtils.isEmpty(condition.getName())) {
			condition.setName("%" + condition.getName() + "%");
		}
		if(!StringUtils.isEmpty(condition.getNickName())) {
			condition.setNickName("%" + condition.getNickName() + "%");
		}
		Page<SysUserEntity> page = new Page<>(condition.getPageNo() , condition.getPageSize());
		return page.setRecords(sysUserMapper.listData(condition , page));
	}

}
