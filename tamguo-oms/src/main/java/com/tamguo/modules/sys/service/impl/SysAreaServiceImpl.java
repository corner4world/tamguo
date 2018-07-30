package com.tamguo.modules.sys.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
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

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray treeData(String excludeId) {
		List<SysAreaEntity> areaList = null;
		if(StringUtils.isEmpty(excludeId)) {
			areaList = sysAreaMapper.selectList(Condition.EMPTY);
		}else {
			areaList = sysAreaMapper.selectList(Condition.create().notLike("parent_codes", excludeId).ne("area_code", excludeId));
		}
		return turnZTreeData(areaList);
	}

	private JSONArray turnZTreeData(List<SysAreaEntity> areaList) {
		if(areaList != null) {
			JSONArray nodes = new JSONArray();
			for(int i=0 ; i<areaList.size() ; i++) {
				JSONObject node = new JSONObject();
				
				SysAreaEntity area = areaList.get(i);
				node.put("name", area.getAreaName());
				node.put("id", area.getId());
				node.put("pId", area.getParentCode());
				node.put("title", area.getAreaName());
				nodes.add(node);
			}
			return nodes;
		}
		return null;
	}
}
