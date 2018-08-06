package com.tamguo.modules.tiku.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.condition.ChapterCondition;
import com.tamguo.modules.tiku.service.IChapterService;

@Controller
@RequestMapping(path="tiku/chapter")
public class ChapterController {
	
	@Autowired
	private IChapterService iChapterService;

	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public List<ChapterEntity> listData(ChapterCondition condition) {
		return iChapterService.listData(condition);
	}
}
