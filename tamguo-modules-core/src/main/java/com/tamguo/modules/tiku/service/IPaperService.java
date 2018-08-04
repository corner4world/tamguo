package com.tamguo.modules.tiku.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.tiku.model.PaperEntity;

public interface IPaperService extends IService<PaperEntity>{

	List<PaperEntity> findHistoryPaper();

	List<PaperEntity> findSimulationPaper();

	List<PaperEntity> findHotPaper(String areaId);

}
