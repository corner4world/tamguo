package com.tamguo.modules.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.model.SysPostEntity;
import com.tamguo.modules.sys.model.condition.SysPostCondition;

public interface IPostService {

	Page<SysPostEntity> listData(SysPostCondition condition);

}
