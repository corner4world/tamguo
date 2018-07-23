package com.tamguo.modules.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.sys.dao.SysCompanyMapper;
import com.tamguo.modules.sys.model.SysCompanyEntity;
import com.tamguo.modules.sys.model.condition.SysCompanyCondition;
import com.tamguo.modules.sys.service.ISysCompanyService;

@Service
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyMapper, SysCompanyEntity> implements ISysCompanyService {

	@Autowired
	private SysCompanyMapper sysCompanyMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysCompanyEntity> treeData() {
		
		List<SysCompanyEntity> companyList = sysCompanyMapper.selectList(Condition.EMPTY);
		return companyList;
	}

	@Override
	public List<SysCompanyEntity> listData(SysCompanyCondition condition) {
		return sysCompanyMapper.listData(condition);
	}
	
}
