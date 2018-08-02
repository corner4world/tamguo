package com.tamguo.modules.sys.service.impl;

import java.math.BigDecimal;
import java.util.Date;
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
import com.tamguo.modules.sys.model.enums.SysMenuStatusEnum;
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

	@Transactional(readOnly=false)
	@Override
	public void save(SysMenuEntity menu) {
		menu.setCreateDate(new Date());
		menu.setUpdateDate(new Date());
		menu.setStatus(SysMenuStatusEnum.NORMAL);
		this.handleTreeData(menu);
		sysMenuMapper.insert(menu);	
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	@Override
	public void update(SysMenuEntity menu) {
		SysMenuEntity entity = sysMenuMapper.selectById(menu.getMenuCode());
		String oldParentCode = entity.getParentCode();
		
		entity.setUpdateDate(new Date());
		entity.setIsShow(menu.getIsShow());
		entity.setMenuCode(menu.getMenuCode());
		entity.setMenuColor(menu.getMenuColor());
		entity.setMenuHref(menu.getMenuHref());
		entity.setMenuIcon(menu.getMenuIcon());
		entity.setMenuName(menu.getMenuName());
		entity.setMenuTarget(menu.getMenuTarget());
		entity.setMenuType(menu.getMenuType());
		entity.setPermission(menu.getPermission());
		entity.setRemarks(menu.getRemarks());
		entity.setWeight(menu.getWeight());
		entity.setParentCode(menu.getParentCode());
		this.handleTreeData(entity);
		sysMenuMapper.updateById(entity);
		
		// 更新旧的节点
		Integer count = sysMenuMapper.selectCount(Condition.create().eq("parent_code", oldParentCode).ne("menu_code", oldParentCode));
		if(count == 0) {
		 	SysMenuEntity oldParentMenu = sysMenuMapper.selectById(oldParentCode);
		 	oldParentMenu.setTreeLeaf(true);
		 	sysMenuMapper.updateById(oldParentMenu);
		}
	}
	
	// 处理树形结构
	@SuppressWarnings("unchecked")
	private SysMenuEntity handleTreeData(SysMenuEntity menu) {
		if(StringUtils.isEmpty(menu.getParentCode())) {
			menu.setParentCode(SysMenuEntity.ROOT_MENU_CODE);
			menu.setParentCodes(SysMenuEntity.ROOT_MENU_CODE + SysMenuEntity.TREE_CODE_MENU_SEPARATE);
			menu.setTreeLeaf(true);
			menu.setTreeLevel(new BigDecimal(0));
			menu.setTreeNames(menu.getMenuName());
			menu.setTreeSorts(menu.getTreeSort().multiply(new BigDecimal(10000000)).toString() + SysMenuEntity.TREE_CODE_MENU_SEPARATE);
		} else {
			SysMenuEntity parentMenu = sysMenuMapper.selectById(menu.getParentCode());
			
			menu.setParentCodes(parentMenu.getParentCodes() + parentMenu.getMenuCode() + SysMenuEntity.TREE_CODE_MENU_SEPARATE);
			menu.setTreeLeaf(true);
			
			menu.setTreeLevel(parentMenu.getTreeLevel().add(new BigDecimal(1)));
			menu.setTreeNames(parentMenu.getTreeNames() + SysMenuEntity.TREE_NAME_MENU_SEPARATE + parentMenu.getMenuName());
			menu.setTreeSorts(parentMenu.getTreeSorts() + menu.getTreeSort().multiply(new BigDecimal(10000000)).toString() + SysMenuEntity.TREE_CODE_MENU_SEPARATE);
			
			if(parentMenu.getTreeLeaf()) {
				parentMenu.setTreeLeaf(false);
				sysMenuMapper.updateById(parentMenu);
			}
		}
		Integer count = sysMenuMapper.selectCount(Condition.create().eq("parent_code", menu.getMenuCode()));
		if(count > 0) {
			menu.setTreeLeaf(false);
		}else {
			menu.setTreeLeaf(true);
		}
		return menu;
	}

}
