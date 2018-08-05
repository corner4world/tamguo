package com.tamguo.modules.tiku.service;

import java.util.List;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.tiku.model.PaperEntity;

public interface IPaperService extends IService<PaperEntity>{

	List<PaperEntity> findHistoryPaper();

	List<PaperEntity> findSimulationPaper();

	List<PaperEntity> findHotPaper(String areaId);
	
	void addPaperQuestionInfo(String paperId, String title, String name, String type);

	void updatePaperQuestionInfo(String paperId, String title, String name,
			String type, String uid);
	
	Result deletePaper(String deletePaper , String paperId);
	
	Result deletePaperQuestionInfoBtn(String currMemberId , String paperId, String cuid);
	
	void addPaper(PaperEntity paper);

	Result updatePaper(PaperEntity paper , String currMemberId);
}
