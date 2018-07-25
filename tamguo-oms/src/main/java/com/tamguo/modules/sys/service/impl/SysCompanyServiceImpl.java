package com.tamguo.modules.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
	public JSONArray treeData(String excludeId) {
		List<SysCompanyEntity> companyList = null;
		if(StringUtils.isEmpty(excludeId)) {
			companyList = sysCompanyMapper.selectList(Condition.EMPTY);
		}else {
			companyList = sysCompanyMapper.selectList(Condition.create().notLike("parent_codes", excludeId).ne("company_code", excludeId));
		}
		return turnZTreeData(companyList);
	}

	@Override
	public List<SysCompanyEntity> listData(SysCompanyCondition condition) {
		return sysCompanyMapper.listData(condition);
	}

	@Override
	public SysCompanyEntity selectByCode(String code) {
		return sysCompanyMapper.selectByCode(code);
	}
	
	private JSONArray turnZTreeData(List<SysCompanyEntity> companyList) {
		if(companyList != null) {
			JSONArray nodes = new JSONArray();
			for(int i=0 ; i<companyList.size() ; i++) {
				JSONObject node = new JSONObject();
				
				SysCompanyEntity company = companyList.get(i);
				node.put("name", company.getCompanyName());
				node.put("id", company.getId());
				node.put("pId", company.getParentCode());
				node.put("title", company.getFullName());
				nodes.add(node);
			}
			return nodes;
		}
		return null;
	}
	
}
