package com.tamguo.modules.sys.dao;

import java.util.List;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;

public interface SysUserMapper extends SuperMapper<SysUserEntity>{

	List<SysUserEntity> listData(SysUserCondition condition, Pagination page);

}
