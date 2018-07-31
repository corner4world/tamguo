package com.tamguo.modules.sys.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.sys.dao.SysMenuMapper;
import com.tamguo.modules.sys.model.SysMenuEntity;
import com.tamguo.modules.sys.model.condition.SysMenuCondition;
import com.tamguo.modules.sys.service.ISysMenuService;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements ISysMenuService {

	@Autowired
	SysMenuMapper sysMenuMapper;
	
	@Override
	public List<SysMenuEntity> listData(SysMenuCondition condition) {
		return sysMenuMapper.listData(condition);
	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray treeData(String excludeId) {
		List<SysMenuEntity> menus = null;
		if(StringUtils.isEmpty(excludeId)) {
			menus = sysMenuMapper.selectList(Condition.EMPTY);
		}else {
			menus = sysMenuMapper.selectList(Condition.create().notLike("parent_codes", excludeId).ne("menu_code", excludeId));
		}
		return turnZTreeData(menus);
	}

	private JSONArray turnZTreeData(List<SysMenuEntity> menus) {
		if(menus != null) {
			JSONArray nodes = new JSONArray();
			for(int i=0 ; i<menus.size() ; i++) {
				JSONObject node = new JSONObject();
				
				SysMenuEntity menu = menus.get(i);
				node.put("name", menu.getMenuName());
				node.put("id", menu.getId());
				node.put("pId", menu.getParentCode());
				node.put("title", menu.getMenuName());
				nodes.add(node);
			}
			return nodes;
		}
		return null;
	}

}
