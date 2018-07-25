package com.tamguo.modules.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.sys.model.SysAreaEntity;
import com.tamguo.modules.sys.model.condition.SysAreaCondition;

public interface ISysAreaService extends IService<SysAreaEntity>{

	List<SysAreaEntity> listData(SysAreaCondition condition);

}
