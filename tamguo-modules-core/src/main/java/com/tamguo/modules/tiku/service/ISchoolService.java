package com.tamguo.modules.tiku.service;

import java.util.List;
import com.tamguo.modules.tiku.model.SchoolEntity;

public interface ISchoolService {

	public List<SchoolEntity> findEliteSchoolPaper(String shcoolId);
	
	public List<SchoolEntity> findEliteSchool();

	public List<SchoolEntity> findSchoolByAreaId(String areaId);
	
}
