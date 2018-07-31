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
import com.tamguo.modules.sys.dao.SysCompanyMapper;
import com.tamguo.modules.sys.model.SysCompanyEntity;
import com.tamguo.modules.sys.model.condition.SysCompanyCondition;
import com.tamguo.modules.sys.service.ISysCompanyService;
import com.tamguo.modules.sys.utils.ShiroUtils;

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

	@Transactional(readOnly=false)
	@Override
	public void save(SysCompanyEntity company) {
		
		company.setCreateBy(ShiroUtils.getUserCode());
		company.setCreateDate(new Date());
		company.setUpdateBy(ShiroUtils.getUserCode());
		company.setUpdateDate(new Date());
		company.setCompanyCode(company.getViewCode());
		
		// 更新树形结构
		this.handleTreeData(company);
		
		sysCompanyMapper.insert(company);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	@Override
	public void update(SysCompanyEntity company) {
		SysCompanyEntity entity = sysCompanyMapper.selectByCode(company.getCompanyCode());
		
		String oldParentCode = entity.getParentCode();
		
		entity.setUpdateBy(ShiroUtils.getUserCode());
		entity.setUpdateDate(new Date());
		entity.setAreaCode(company.getAreaCode());
		entity.setCompanyCode(company.getCompanyCode());
		entity.setCompanyName(company.getCompanyName());
		entity.setCorpCode(company.getCorpCode());
		entity.setCorpName(company.getCorpName());
		entity.setFullName(company.getFullName());
		entity.setParentCode(company.getParentCode());
		
		// 更新树形结构
		this.handleTreeData(company);
		
		sysCompanyMapper.updateById(entity);
		
		// 更新旧的节点
		Integer count = sysCompanyMapper.selectCount(Condition.create().eq("parent_code", oldParentCode).ne("company_code", oldParentCode));
		if(count == 0) {
		 	SysCompanyEntity oldParentCompany = sysCompanyMapper.selectById(oldParentCode);
		 	oldParentCompany.setTreeLeaf(true);
		 	sysCompanyMapper.updateById(oldParentCompany);
		}
	}
	
	// 处理树形结构
	@SuppressWarnings("unchecked")
	private SysCompanyEntity handleTreeData(SysCompanyEntity company) {
		if(StringUtils.isEmpty(company.getParentCode())) {
			company.setParentCode(SysCompanyEntity.ROOT_COMPANY_CODE);
			company.setParentCodes(SysCompanyEntity.ROOT_COMPANY_CODE + SysCompanyEntity.TREE_CODE_COMPANY_SEPARATE);
			company.setTreeLeaf(true);
			company.setTreeLevel(new BigDecimal(0));
			company.setTreeNames(company.getCompanyName());
			company.setTreeSorts("00000000" + company.getTreeSort() + SysCompanyEntity.ROOT_COMPANY_CODE);
		} else {
			SysCompanyEntity parentCompany = sysCompanyMapper.selectById(company.getParentCode());
			
			company.setParentCodes(parentCompany.getParentCodes() + parentCompany.getCompanyCode() + SysCompanyEntity.TREE_CODE_COMPANY_SEPARATE);
			company.setTreeLeaf(true);
			
			company.setTreeLevel(parentCompany.getTreeLevel().add(new BigDecimal(1)));
			company.setTreeNames(parentCompany.getTreeNames() + SysCompanyEntity.TREE_NAME_COMPANY_SEPARATE + parentCompany.getCompanyName());
			company.setTreeSorts(parentCompany.getTreeSorts() + ("00000000" +company.getTreeSort()) + SysCompanyEntity.TREE_CODE_COMPANY_SEPARATE);
			
			if(parentCompany.getTreeLeaf()) {
				parentCompany.setTreeLeaf(false);
				sysCompanyMapper.updateById(parentCompany);
			}
		}
		Integer count = sysCompanyMapper.selectCount(Condition.create().eq("parent_code", company.getCompanyCode()));
		if(count > 0) {
			company.setTreeLeaf(false);
		}else {
			company.setTreeLeaf(true);
		}
		return company;
	}

	
}
