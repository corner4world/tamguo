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
import com.tamguo.modules.sys.model.SysOfficeEntity;
import com.tamguo.modules.sys.model.condition.SysAreaCondition;
import com.tamguo.modules.sys.model.enums.SysAreaStatusEnum;
import com.tamguo.modules.sys.service.ISysAreaService;
import com.tamguo.modules.sys.utils.ShiroUtils;

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
		area.setCreateBy(ShiroUtils.getUserCode());
		area.setCreateDate(new Date());
		area.setUpdateBy(ShiroUtils.getUserCode());
		area.setUpdateDate(new Date());
		area.setStatus(SysAreaStatusEnum.NORMAL);
		
		this.handleTreeData(area);
		
		sysAreaMapper.insert(area);
	}
	
	// 处理树形结构
	@SuppressWarnings("unchecked")
	private SysAreaEntity handleTreeData(SysAreaEntity area) {
		if(StringUtils.isEmpty(area.getParentCode())) {
			area.setParentCode(SysOfficeEntity.ROOT_OFFICE_CODE);
			area.setParentCodes(SysOfficeEntity.ROOT_OFFICE_CODE + SysOfficeEntity.TREE_CODE_OFFICE_SEPARATE);
			area.setTreeLeaf(true);
			area.setTreeLevel(new BigDecimal(0));
			area.setTreeNames(area.getAreaName());
			area.setTreeSorts(area.getTreeSort().multiply(new BigDecimal(10000000)).toString() + SysOfficeEntity.TREE_CODE_OFFICE_SEPARATE);
		} else {
			SysAreaEntity parentOffice = sysAreaMapper.selectById(area.getParentCode());
			
			area.setParentCodes(parentOffice.getParentCodes() + parentOffice.getAreaCode() + SysOfficeEntity.TREE_CODE_OFFICE_SEPARATE);
			area.setTreeLeaf(true);
			
			area.setTreeLevel(parentOffice.getTreeLevel().add(new BigDecimal(1)));
			area.setTreeNames(parentOffice.getTreeNames() + SysOfficeEntity.TREE_NAME_OFFICE_SEPARATE + parentOffice.getAreaName());
			area.setTreeSorts(parentOffice.getTreeSorts() + area.getTreeSort().multiply(new BigDecimal(10000000)).toString() + SysOfficeEntity.TREE_CODE_OFFICE_SEPARATE);
			
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
}
