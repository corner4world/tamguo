package com.tamguo.dao;

import java.util.List;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.model.MenuEntity;

public interface MenuMapper extends SuperMapper<MenuEntity>{

	
	public List<MenuEntity> findMenuByParentId(String parentId);

	public List<MenuEntity> findAllFatherMenus();

}
