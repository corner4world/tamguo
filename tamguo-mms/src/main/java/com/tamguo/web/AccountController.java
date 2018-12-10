package com.tamguo.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.common.utils.DateUtils;
import com.tamguo.common.utils.Result;
import com.tamguo.common.utils.Status;
import com.tamguo.common.utils.UploaderMessage;
import com.tamguo.config.redis.CacheService;
import com.tamguo.modules.member.service.IMemberService;
import com.tamguo.utils.ShiroUtils;

@Controller
public class AccountController {

	@Value("${file.storage.path}")
	String fileStoragePath;
	@Value("${tamguo.domain.name}")
	String tamguoDomainName;
	@Autowired
	IMemberService iMemberService;
	@Autowired
	CacheService cacheService;

	private static final String AVATOR_NO_FORMAT = "00000";
	private static final String AVATOR_PREFIX = "MTX";
	
	@RequestMapping(value = {"account.html"}, method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("account");
		model.addObject("member", iMemberService.findByUid(ShiroUtils.getMemberId()));
		return model;
	}
	
	@RequestMapping(value = {"getCurrentMember"}, method = RequestMethod.GET)
	@ResponseBody
	public Result getCurrentMember() {
		return Result.result(0, iMemberService.findByUid(ShiroUtils.getMemberId()), "success");
	}
	
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public UploaderMessage uploadFileHandler(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IOException {

		if (!file.isEmpty()) {
			InputStream in = null;
			OutputStream out = null;
			
			try {
				String path = fileStoragePath + DateUtils.format(new Date(), "yyyyMMdd");
				File dir = new File(path);
				if (!dir.exists())
					dir.mkdirs();
				String avatorName = this.getAvatorNo() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				in = file.getInputStream();
				out = new FileOutputStream(path + "/" + avatorName);
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = in.read(b)) > 0) {
					out.write(b, 0, len);
				}
				out.close();
				in.close();

				UploaderMessage msg = new UploaderMessage();
				msg.setStatus(Status.SUCCESS);
				msg.setStatusMsg("File upload success");
				msg.setFilePath("files/" + DateUtils.format(new Date(), "yyyyMMdd") + "/" + avatorName);
				msg.setFileDomain(tamguoDomainName);
				return msg;
			} catch (Exception e) {
				UploaderMessage msg = new UploaderMessage();
				msg.setStatus(Status.ERROR);
				msg.setError("File upload file");
				return msg;
			} finally {
				if (out != null) {
					out.close();
					out = null;
				}

				if (in != null) {
					in.close();
					in = null;
				}
			}
		} else {
			UploaderMessage msg = new UploaderMessage();
			msg.setStatus(Status.ERROR);
			msg.setError("File is empty");
			return msg;
		}
	}

	private String getAvatorNo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String format = sdf.format(new Date());
		DecimalFormat df = new DecimalFormat(AVATOR_NO_FORMAT);
		String key = AVATOR_PREFIX + format;
		Long incr = cacheService.incr(key);
		String avatorNo = AVATOR_PREFIX + df.format(incr);
		return avatorNo;
	}
}
