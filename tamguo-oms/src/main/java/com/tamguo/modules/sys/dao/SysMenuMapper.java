package com.tamguo.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.modules.sys.model.SysMenuEntity;

public interface SysMenuMapper extends SuperMapper<SysMenuEntity>{

	List<SysMenuEntity> queryList(Map<String, Object> hashMap , Pagination page);

	List<SysMenuEntity> queryNotButtonList();

	SysMenuEntity queryByParentId(String parentId);

	SysMenuEntity queryByUid(String uid);

}
