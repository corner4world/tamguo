package com.tamguo.modules.sys.dao;

import java.util.List;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.modules.sys.model.SysRoleEntity;

public interface SysRoleMapper extends SuperMapper<SysRoleEntity>{

	List<SysRoleEntity> selectPageByName(SysRoleEntity sysRoleEntity, Pagination page);

}
