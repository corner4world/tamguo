package com.tamguo.modules.sys.dao;

import java.util.List;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.modules.sys.model.SysPostEntity;
import com.tamguo.modules.sys.model.condition.SysPostCondition;

public interface SysPostMapper extends SuperMapper<SysPostEntity>{

	List<SysPostEntity> listData(SysPostCondition condition , Pagination page);

}
