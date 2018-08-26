package com.tamguo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.model.CrawlerPaperEntity;

public interface CrawlerPaperMapper extends SuperMapper<CrawlerPaperEntity>{

	@Select("SELECT c.* FROM crawler_paper c WHERE c.paper_id IN (SELECT p.id FROM tiku_paper p WHERE p.area_id = #{areaId}) order by id,queindex asc;")
	List<CrawlerPaperEntity> selectPageByAreaId(Page<CrawlerPaperEntity> questionPage,@Param(value="areaId") String areaId);

}
