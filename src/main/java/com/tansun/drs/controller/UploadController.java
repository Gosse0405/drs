package com.tansun.drs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UploadController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

	@GetMapping("/upload")
	public void uploadPage() {}

	@PostMapping("/uploadFile")
	@ResponseBody
	public Object uploadFile(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
		Map<String, Object> map = new HashMap<String, Object>();

		// ********************* 读取Excel *********************







		// ********************* 文件写入 *********************
		// 资源路径
		File resPath = new File(ResourceUtils.getURL("classpath:").getPath());
		// 上传路径
		String uploadPath = resPath.getAbsolutePath() + File.separator + "upload";
		File upload = new File(uploadPath);
		if(!upload.exists()) {
			upload.mkdirs();
		}
		String fileName = UUID.randomUUID().toString();
		// 文件路径
		String filePath = uploadPath + File.separator + fileName + "." + fileName.substring(fileName.lastIndexOf(".") + 1);;
		File dest = new File(filePath);
		// 像磁盘中写入文件
		try {
			file.transferTo(dest);
			map.put("code", 0);
			map.put("msg", "上传成功");
			LOGGER.info("Excel文件上传成功");
		} catch (IOException e) {
			map.put("code", 1);
			map.put("msg", "上传失败");
			LOGGER.error("Excel文件上传失败", e.getMessage());
		} finally {
			return map;
		}
	}
}
