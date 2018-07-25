package com.tamguo.modules.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.sys.model.SysMenuEntity;
import com.tamguo.modules.sys.model.condition.SysMenuCondition;

public interface ISysMenuService extends IService<SysMenuEntity>{

	List<SysMenuEntity> listData(SysMenuCondition condition);

}
