package com.tamguo.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tamguo.model.ChapterEntity;
import com.tamguo.service.IChapterService;
import com.tamguo.util.Result;


@Controller
public class ChapterController {
	
	@Autowired
	private IChapterService iChapterService;

	@RequestMapping(value = {"/chapter/findChapter/{courseId}.html"}, method = RequestMethod.GET)
	@ResponseBody
	public List<ChapterEntity> findChapterByCourseId(@PathVariable String courseId){
		return iChapterService.findCourseChapter(courseId);
	}
	
	@RequestMapping(value = {"/chapter/findChapterTreeByCourseId.html"}, method = RequestMethod.GET)
	@ResponseBody
	public Result findChapterTreeByCourseId(String courseId){
		return Result.successResult(iChapterService.getChapterTree(courseId));
	}
}
