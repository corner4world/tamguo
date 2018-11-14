package com.tamguo.web.member;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.common.utils.DateUtil;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.book.model.DocumentEntity;
import com.tamguo.modules.book.model.FileEntity;
import com.tamguo.modules.book.service.IDocumentService;
import com.tamguo.modules.book.service.IFileEntityService;
import com.tamguo.utils.FileMd5Utils;

@Controller
@RequestMapping(value="member/document")
public class DocumentController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IDocumentService iDocumentService;
	@Autowired
	IFileEntityService iFileEntityService;
	
	@Value("${file.storage.path}")
	private String fileStoragePath;
	@Value("${domain.name}")
	private String domainName;
	
	@RequestMapping(value = "{id}" , method = RequestMethod.GET)
	@ResponseBody
	public Result getDocument(@PathVariable String id) {
		DocumentEntity document = null;
		try {
			document = iDocumentService.selectById(id);
		} catch (Exception e) {
			logger.error(e.getMessage() , e );
			return Result.failResult("查询失败");
		}
		return Result.successResult(document);
	}
	
	/**
	 * 编辑内容
	 */
	@RequestMapping(value = "modify" , method = RequestMethod.POST)
	@ResponseBody
	public Result modify(DocumentEntity document) {
		try {
			iDocumentService.modify(document);
		} catch (Exception e) {
			logger.error(e.getMessage() , e );
			return Result.failResult("保存失败");
		}
		return Result.successResult("保存成功");
	}
	
	/**
	 * 创建文档
	 */
	@RequestMapping(value = "create" , method = RequestMethod.POST)
	@ResponseBody
	public Result create(DocumentEntity document) {
		try {
			iDocumentService.create(document);
		} catch (Exception e) {
			logger.error(e.getMessage() , e );
			return Result.failResult("保存失败");
		}
		return Result.successResult(document);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "uploadImage" , method = RequestMethod.POST)
	@ResponseBody
	public Result uploadImage(@RequestParam("editormd-image-file") MultipartFile file, String bookId , HttpServletRequest request) {
		try {
			String fileMd5 = FileMd5Utils.getMD5((FileInputStream)file.getInputStream());
			FileEntity sysFile = iFileEntityService.selectOne(Condition.create().eq("file_md5", fileMd5));
			if(sysFile != null) {
				sysFile.setFilePath(domainName + "files/" + sysFile.getFilePath());
				return Result.successResult(sysFile);
			}
			String filePath  = fileStoragePath + "book/" + DateUtil.fomatDate(new Date(), "yyyyMM") + "/" + bookId;
			File dest = new File(filePath);
			if(!dest.exists()) {
				dest.mkdirs();
			}
			// save 文件
			FileUtils.writeByteArrayToFile(new File(filePath + "/" + file.getOriginalFilename()) , file.getBytes());
			
			FileEntity fileEntity = new FileEntity();
			fileEntity.setFileContentType(file.getContentType());
			fileEntity.setFileExtension(file.getOriginalFilename());
			fileEntity.setFileMd5(FileMd5Utils.getMD5((FileInputStream)file.getInputStream()));
			fileEntity.setFileSize(file.getSize());
			fileEntity.setFilePath("book/" + DateUtil.fomatDate(new Date(), "yyyyMM") + "/" + bookId + "/" + file.getOriginalFilename());
			iFileEntityService.insert(fileEntity);
			
			fileEntity.setFilePath(domainName + "files/" + fileEntity.getFilePath());
			
			return Result.successResult(fileEntity);
		} catch (IOException e) {
			e.printStackTrace();
			return Result.failResult("上传失败");
		}
	}
}
