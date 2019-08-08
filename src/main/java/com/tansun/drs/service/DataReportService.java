package com.tansun.drs.service;

import org.springframework.web.multipart.MultipartFile;

public interface DataReportService {

	/**
	 * 上传excel导入数据库
	 * @param file
	 * @return
	 */
	boolean impExcelToDatabase(MultipartFile file);
}
