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
		if(StringUtils.isEmpty(company.getParentCode())) {
			company.setParentCode("0,");
			company.setParentCodes("0,");
			company.setTreeSorts(company.getTreeSort());
			company.setTreeNames(company.getCompanyCode() + ",");
			company.setTreeLeaf(true);
			company.setTreeLevel(new BigDecimal(0));
		}else {
			SysCompanyEntity condition = new SysCompanyEntity();
			condition.setCompanyCode(company.getParentCode());
			SysCompanyEntity parentCompany = sysCompanyMapper.selectOne(condition);
			
			company.setTreeLeaf(true);
			company.setTreeLevel(parentCompany.getTreeLevel().add(new BigDecimal(1)));
			company.setParentCodes(parentCompany.getParentCodes() + parentCompany.getCompanyCode() + ",");
			company.setTreeSorts(parentCompany.getTreeSorts() + parentCompany.getTreeSort() + ",");
			company.setTreeNames(parentCompany.getTreeNames() + parentCompany.getCompanyName() + ",");

			// 更新
			parentCompany.setTreeLeaf(false);
			parentCompany.setUpdateBy(ShiroUtils.getUserCode());
			parentCompany.setUpdateDate(new Date());
			sysCompanyMapper.updateById(parentCompany);
		}
		sysCompanyMapper.insert(company);
	}

	@Transactional(readOnly=false)
	@Override
	public void update(SysCompanyEntity company) {
		SysCompanyEntity entity = sysCompanyMapper.selectByCode(company.getCompanyCode());
		entity.setUpdateBy(ShiroUtils.getUserCode());
		entity.setUpdateDate(new Date());
		entity.setAreaCode(company.getAreaCode());
		entity.setCompanyCode(company.getCompanyCode());
		entity.setCompanyName(company.getCompanyName());
		entity.setCorpCode(company.getCorpCode());
		entity.setCorpName(company.getCorpName());
		entity.setFullName(company.getFullName());
		if(StringUtils.isEmpty(company.getParentCode())) {
			entity.setParentCode("0");
			entity.setParentCodes("0,");
			entity.setTreeSorts(company.getTreeSort());
			entity.setTreeNames(company.getCompanyCode() + ",");
			entity.setTreeLeaf(true);
			entity.setTreeLevel(new BigDecimal(0));
		}else {
			SysCompanyEntity condition = new SysCompanyEntity();
			condition.setCompanyCode(company.getParentCode());
			SysCompanyEntity parentCompany = sysCompanyMapper.selectOne(condition);
			
			entity.setTreeLeaf(true);
			entity.setTreeLevel(parentCompany.getTreeLevel().add(new BigDecimal(1)));
			entity.setParentCodes(parentCompany.getParentCodes() + parentCompany.getCompanyCode() + ",");
			entity.setTreeSorts(parentCompany.getTreeSorts() + parentCompany.getTreeSort() + ",");
			entity.setTreeNames(parentCompany.getTreeNames() + parentCompany.getCompanyName() + ",");

			// 更新
			parentCompany.setTreeLeaf(false);
			parentCompany.setUpdateBy(ShiroUtils.getUserCode());
			parentCompany.setUpdateDate(new Date());
			sysCompanyMapper.updateById(parentCompany);
		}
		sysCompanyMapper.updateById(entity);
	}
	
}
