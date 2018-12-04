package com.tamguo.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.common.utils.DateUtil;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.book.model.FileEntity;
import com.tamguo.modules.book.service.IFileEntityService;
import com.tamguo.utils.FileMd5Utils;

@Controller
public class FileController {
	
	@Value("${file.storage.path}")
	private String fileStoragePath;
	@Value("${tamguo.domain.name}")
	private String domainName;
	@Autowired
	private IFileEntityService iFileEntityService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "uploadImage" , method = RequestMethod.POST)
	@ResponseBody
	public Result uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			String fileMd5 = FileMd5Utils.getMD5((FileInputStream)file.getInputStream());
			FileEntity sysFile = iFileEntityService.selectOne(Condition.create().eq("file_md5", fileMd5));
			if(sysFile != null) {
				sysFile.setFilePath(domainName + "files/" + sysFile.getFilePath());
				return Result.successResult(sysFile);
			}
			String filePath  = fileStoragePath + DateUtil.fomatDate(new Date(), "yyyyMM") + "/avatar";
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
			fileEntity.setFilePath(DateUtil.fomatDate(new Date(), "yyyyMM") + "/avatar/" + file.getOriginalFilename());
			iFileEntityService.insert(fileEntity);
			
			fileEntity.setFilePath(domainName + "files/" + fileEntity.getFilePath());
			
			return Result.successResult(fileEntity);
		} catch (IOException e) {
			e.printStackTrace();
			return Result.failResult("上传失败");
		}
	}
	
}
