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
import com.tamguo.modules.sys.utils.ShiroUtils;

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
		// 父节点
		SysOfficeEntity parent = sysOfficeMapper.selectById(office.getParentCode());
		
		office.setCreateBy(ShiroUtils.getUserCode());
		office.setCreateDate(new Date());
		office.setUpdateBy(ShiroUtils.getUserCode());
		office.setUpdateDate(new Date());
		office.setOfficeCode(office.getViewCode());
		if(StringUtils.isEmpty(office.getParentCode())) {
			office.setParentCode("0");
			office.setParentCodes("0,");
			office.setTreeLeaf(false);
			office.setTreeLevel(BigDecimal.valueOf(0));
		}else {
			office.setParentCodes(parent.getParentCodes() + parent.getOfficeCode() + ",");
			office.setTreeLeaf(true);
			office.setTreeLevel(parent.getTreeLevel().add(BigDecimal.valueOf(1)));
		}
		office.setTreeSorts(office.getTreeSort() + ",");
		office.setTreeNames(office.getOfficeName() + ",");
		sysOfficeMapper.insert(office);
		
		// 更新父节点
		parent.setTreeLeaf(false);
		sysOfficeMapper.updateById(parent);
	}

	@Override
	public void update(SysOfficeEntity office) {
		SysOfficeEntity oldOffice = sysOfficeMapper.selectById(office.getOfficeCode());
		SysOfficeEntity parentOffice = sysOfficeMapper.selectById(office.getParentCode());
		
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
		if(StringUtils.isEmpty(office.getParentCode())) {
			oldOffice.setParentCode("0");
			oldOffice.setParentCodes("0,");
			oldOffice.setTreeLeaf(false);
			oldOffice.setTreeLevel(BigDecimal.valueOf(0));
		}else {
			oldOffice.setParentCodes(parentOffice.getParentCodes() + parentOffice.getOfficeCode() + ",");
			oldOffice.setTreeLeaf(true);
			oldOffice.setTreeLevel(parentOffice.getTreeLevel().add(BigDecimal.valueOf(1)));
		}
		sysOfficeMapper.updateById(oldOffice);
	}

}
