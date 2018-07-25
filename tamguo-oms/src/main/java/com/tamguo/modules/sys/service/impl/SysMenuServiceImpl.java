package com.tamguo.modules.sys.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
