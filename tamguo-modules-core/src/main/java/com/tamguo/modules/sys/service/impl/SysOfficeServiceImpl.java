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
import com.tamguo.modules.sys.dao.SysOfficeMapper;
import com.tamguo.modules.sys.model.SysOfficeEntity;
import com.tamguo.modules.sys.model.condition.SysOfficeCondition;
import com.tamguo.modules.sys.service.ISysOfficeService;

@Service
public class SysOfficeServiceImpl extends ServiceImpl<SysOfficeMapper, SysOfficeEntity> implements ISysOfficeService {
	
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

	@Transactional(readOnly=false)
	@Override
	public void save(SysOfficeEntity office) {
		office.setCreateDate(new Date());
		office.setUpdateDate(new Date());
		office.setOfficeCode(office.getViewCode());
		// 处理属性结构
		this.handleTreeData(office);
		
		sysOfficeMapper.insert(office);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	@Override
	public void update(SysOfficeEntity office) {
		SysOfficeEntity oldOffice = sysOfficeMapper.selectById(office.getOfficeCode());
		
		String oldParentCode = oldOffice.getParentCode();
		
		oldOffice.setAddress(office.getAddress());
		oldOffice.setCorpCode(office.getCorpCode());
		oldOffice.setCorpName(office.getCorpName());
		oldOffice.setEmail(office.getEmail());
		oldOffice.setFullName(office.getFullName());
		oldOffice.setLeader(office.getLeader());
		oldOffice.setOfficeCode(office.getOfficeCode());
		oldOffice.setOfficeName(office.getOfficeName());
		oldOffice.setOfficeType(office.getOfficeType());
		oldOffice.setParentCode(office.getParentCode());
		oldOffice.setRemarks(office.getRemarks());
		oldOffice.setPhone(office.getPhone());
		
		// 处理属性结构
		this.handleTreeData(oldOffice);
		sysOfficeMapper.updateById(oldOffice);
		
		// 更新旧的节点
		Integer count = sysOfficeMapper.selectCount(Condition.create().eq("parent_code", oldParentCode).ne("office_code", oldParentCode));
		if(count == 0) {
		 	SysOfficeEntity oldParentOffice = sysOfficeMapper.selectById(oldParentCode);
		 	oldParentOffice.setTreeLeaf(true);
			sysOfficeMapper.updateById(oldParentOffice);
		}
	}
	
	// 处理树形结构
	@SuppressWarnings("unchecked")
	private SysOfficeEntity handleTreeData(SysOfficeEntity office) {
		if(StringUtils.isEmpty(office.getParentCode())) {
			office.setParentCode(SysOfficeEntity.ROOT_OFFICE_CODE);
			office.setParentCodes(SysOfficeEntity.ROOT_OFFICE_CODE + SysOfficeEntity.TREE_CODE_OFFICE_SEPARATE);
			office.setTreeLeaf(true);
			office.setTreeLevel(new BigDecimal(0));
			office.setTreeNames(office.getOfficeName());
			office.setTreeSorts(office.getTreeSort().multiply(new BigDecimal(10000000)).toString() + SysOfficeEntity.TREE_CODE_OFFICE_SEPARATE);
		} else {
			SysOfficeEntity parentOffice = sysOfficeMapper.selectById(office.getParentCode());
			
			office.setParentCodes(parentOffice.getParentCodes() + parentOffice.getOfficeCode() + SysOfficeEntity.TREE_CODE_OFFICE_SEPARATE);
			office.setTreeLeaf(true);
			
			office.setTreeLevel(parentOffice.getTreeLevel().add(new BigDecimal(1)));
			office.setTreeNames(parentOffice.getTreeNames() + SysOfficeEntity.TREE_NAME_OFFICE_SEPARATE + parentOffice.getOfficeName());
			office.setTreeSorts(parentOffice.getTreeSorts() + office.getTreeSort().multiply(new BigDecimal(10000000)).toString() + SysOfficeEntity.TREE_CODE_OFFICE_SEPARATE);
			
			if(parentOffice.getTreeLeaf()) {
				parentOffice.setTreeLeaf(false);
				sysOfficeMapper.updateById(parentOffice);
			}
		}
		Integer count = sysOfficeMapper.selectCount(Condition.create().eq("parent_code", office.getOfficeCode()));
		if(count > 0) {
			office.setTreeLeaf(false);
		}else {
			office.setTreeLeaf(true);
		}
		return office;
	}

}
