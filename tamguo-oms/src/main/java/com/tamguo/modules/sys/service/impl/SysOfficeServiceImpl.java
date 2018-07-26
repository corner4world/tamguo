package com.tamguo.modules.sys.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
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

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray treeData(String excludeId) {
		List<SysOfficeEntity> officeList = null;
		if(StringUtils.isEmpty(excludeId)) {
			officeList = sysOfficeMapper.selectList(Condition.EMPTY);
		}else {
			officeList = sysOfficeMapper.selectList(Condition.create().notLike("parent_codes", excludeId).ne("office_code", excludeId));
		}
		return turnZTreeData(officeList);
	}
	
	private JSONArray turnZTreeData(List<SysOfficeEntity> companyList) {
		if(companyList != null) {
			JSONArray nodes = new JSONArray();
			for(int i=0 ; i<companyList.size() ; i++) {
				JSONObject node = new JSONObject();
				
				SysOfficeEntity office = companyList.get(i);
				node.put("name", office.getOfficeName());
				node.put("id", office.getId());
				node.put("pId", office.getParentCode());
				node.put("title", office.getFullName());
				nodes.add(node);
			}
			return nodes;
		}
		return null;
	}

}
