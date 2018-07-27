package com.tamguo.modules.sys.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.sys.dao.SysPostMapper;
import com.tamguo.modules.sys.model.SysPostEntity;
import com.tamguo.modules.sys.model.condition.SysPostCondition;
import com.tamguo.modules.sys.model.enums.SysPostStatusEnum;
import com.tamguo.modules.sys.service.ISysPostService;

@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPostEntity> implements ISysPostService{

	@Autowired
	private SysPostMapper sysPostMapper;

	@Override
	public Page<SysPostEntity> listData(SysPostCondition condition) {
		Page<SysPostEntity> page = new Page<>(condition.getPageNo(), condition.getPageSize());
		return page.setRecords(sysPostMapper.listData(condition , page));
	}

	@Transactional(readOnly=false)
	@Override
	public void add(SysPostEntity post) {
		post.setCreateDate(new Date());
		post.setUpdateDate(new Date());
		post.setStatus(SysPostStatusEnum.NORMAL);
		sysPostMapper.insert(post);
	}

	@Transactional(readOnly=false)
	@Override
	public void update(SysPostEntity post) {
		post.setUpdateDate(new Date());
		sysPostMapper.updateById(post);
	}

}
