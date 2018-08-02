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
import com.tamguo.modules.sys.dao.SysAreaMapper;
import com.tamguo.modules.sys.model.SysAreaEntity;
import com.tamguo.modules.sys.model.condition.SysAreaCondition;
import com.tamguo.modules.sys.model.enums.SysAreaStatusEnum;
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

	@Transactional(readOnly=false)
	@Override
	public void save(SysAreaEntity area) {
		area.setCreateDate(new Date());
		area.setUpdateDate(new Date());
		area.setStatus(SysAreaStatusEnum.NORMAL);
		
		this.handleTreeData(area);
		
		sysAreaMapper.insert(area);
	}
	
	// 处理树形结构
	@SuppressWarnings("unchecked")
	private SysAreaEntity handleTreeData(SysAreaEntity area) {
		if(StringUtils.isEmpty(area.getParentCode())) {
			area.setParentCode(SysAreaEntity.ROOT_AREA_CODE);
			area.setParentCodes(SysAreaEntity.ROOT_AREA_CODE + SysAreaEntity.TREE_CODE_AREA_SEPARATE);
			area.setTreeLeaf(true);
			area.setTreeLevel(new BigDecimal(0));
			area.setTreeNames(area.getAreaName());
			area.setTreeSorts(area.getTreeSort().multiply(new BigDecimal(10000000)).toString() + SysAreaEntity.TREE_CODE_AREA_SEPARATE);
		} else {
			SysAreaEntity parentOffice = sysAreaMapper.selectById(area.getParentCode());
			
			area.setParentCodes(parentOffice.getParentCodes() + parentOffice.getAreaCode() + SysAreaEntity.TREE_CODE_AREA_SEPARATE);
			area.setTreeLeaf(true);
			
			area.setTreeLevel(parentOffice.getTreeLevel().add(new BigDecimal(1)));
			area.setTreeNames(parentOffice.getTreeNames() + SysAreaEntity.TREE_CODE_AREA_SEPARATE + parentOffice.getAreaName());
			area.setTreeSorts(parentOffice.getTreeSorts() + area.getTreeSort().multiply(new BigDecimal(10000000)).toString() + SysAreaEntity.TREE_CODE_AREA_SEPARATE);
			
			if(parentOffice.getTreeLeaf()) {
				parentOffice.setTreeLeaf(false);
				sysAreaMapper.updateById(parentOffice);
			}
		}
		Integer count = sysAreaMapper.selectCount(Condition.create().eq("parent_code", area.getAreaCode()));
		if(count > 0) {
			area.setTreeLeaf(false);
		}else {
			area.setTreeLeaf(true);
		}
		return area;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	@Override
	public void update(SysAreaEntity area) {
		SysAreaEntity entity = sysAreaMapper.selectById(area.getAreaCode());
		
		String oldParentCode = entity.getParentCode();
		
		entity.setUpdateDate(new Date());
		entity.setParentCode(area.getParentCode());
		entity.setAreaName(area.getAreaName());
		entity.setAreaType(area.getAreaType());
		entity.setTreeSort(area.getTreeSort());
		entity.setRemarks(area.getRemarks());
		
		this.handleTreeData(area);
		sysAreaMapper.updateById(entity);
		
		// 更新旧的节点
		Integer count = sysAreaMapper.selectCount(Condition.create().eq("parent_code", oldParentCode).ne("area_code", oldParentCode));
		if(count == 0) {
		 	SysAreaEntity oldParentOffice = sysAreaMapper.selectById(oldParentCode);
		 	oldParentOffice.setTreeLeaf(true);
		 	sysAreaMapper.updateById(oldParentOffice);
		}
	}
}
