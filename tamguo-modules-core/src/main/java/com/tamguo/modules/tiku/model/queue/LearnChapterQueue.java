package com.tamguo.modules.tiku.model.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.tamguo.modules.tiku.model.ChapterEntity;

public class LearnChapterQueue {

	// 章节
	private static BlockingQueue<ChapterEntity> chapterQueue = new ArrayBlockingQueue<ChapterEntity>(5);

	public static void add(ChapterEntity chapter) throws InterruptedException {
		if(chapterQueue.size() == 5) {
			chapterQueue.take();
		}
		chapterQueue.put(chapter);
	}

	public static BlockingQueue<ChapterEntity> getChapterQueue() {
		return chapterQueue;
	}

	public static void setChapterQueue(BlockingQueue<ChapterEntity> chapterQueue) {
		LearnChapterQueue.chapterQueue = chapterQueue;
	}
}
