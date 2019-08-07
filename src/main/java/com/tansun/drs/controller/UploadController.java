package com.tansun.drs.controller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
		try {
			// 像磁盘中写入文件
			file.transferTo(dest);
			map.put("code", 0);
			map.put("msg", "上传成功");
			LOGGER.info("Excel文件上传成功");
		} catch (IOException e) {
			LOGGER.error("Excel文件上传失败", e.getMessage());
		}

		// ********************* 读取Excel *********************
		XSSFWorkbook xssfWorkbook = null;
		try {
			// 构造一个XSSFWorkbook对象，将整个流缓冲到内存中
			xssfWorkbook = new XSSFWorkbook(OPCPackage.open(filePath));
			// 获取第一个工作簿
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {
				XSSFRow xssfRow = xssfSheet.getRow(i);
				int minCell = xssfRow.getFirstCellNum();
				int maxCell = xssfRow.getLastCellNum();
			}
			System.out.println("aaaaaa");
		} catch (IOException e) {
			LOGGER.error("IO异常：" + e.getMessage());
		} catch (InvalidFormatException e) {
			LOGGER.error("路径不存在异常：" + e.getMessage());
		}

		return map;
	}
}
