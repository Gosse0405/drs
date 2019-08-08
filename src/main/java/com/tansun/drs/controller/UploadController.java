package com.tansun.drs.controller;

import com.tansun.drs.service.DataReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	private DataReportService dataReportService;

	@GetMapping("/upload")
	public void uploadPage() {}

	@PostMapping("/uploadFile")
	@ResponseBody
	public Object uploadFile(@RequestParam("file") MultipartFile file){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "上传成功");

		dataReportService.impExcelToDatabase(file);

		return map;
	}

}
