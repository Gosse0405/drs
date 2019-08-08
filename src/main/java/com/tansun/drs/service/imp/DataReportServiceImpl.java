package com.tansun.drs.service.imp;

import com.tansun.drs.entity.DataReport;
import com.tansun.drs.mapper.DataReportMapper;
import com.tansun.drs.service.DataReportService;
import com.tansun.drs.util.DateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class DataReportServiceImpl implements DataReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataReportServiceImpl.class);

	@Autowired
	private DataReportMapper dataReportMapper;

	@Override
	public boolean impExcelToDatabase(MultipartFile file) {
		// ********************* 文件写入 start *********************
		// 上传路径
		String uploadPath = null;
		try {
			uploadPath = ResourceUtils.getURL("classpath:").getPath() + File.separator + "upload";
		} catch (FileNotFoundException e) {
			LOGGER.error("上传路径异常：" + e.getMessage());
		}
		File upload = new File(uploadPath);
		if(!upload.exists()) {
			upload.mkdirs();
		}
		// 文件后缀
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		// 文件路径
		String filePath = uploadPath + File.separator + UUID.randomUUID().toString() + "." + suffix;
		File dest = new File(filePath);
		try {
			// 像磁盘中写入文件
			file.transferTo(dest);
			LOGGER.info("Excel文件上传成功");
		} catch (IOException e) {
			LOGGER.error("Excel文件上传失败", e.getMessage());
		}
		// ********************* 文件写入 end *********************

		// ********************* 读取Excel start *********************
		if (suffix.equals("xlsx")) {
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

					// 获取每一行的单元格
					XSSFCell reportDate = xssfRow.getCell(1);
					XSSFCell totalAssets = xssfRow.getCell(2);
					XSSFCell ownerEquity = xssfRow.getCell(3);

					// 构造一条数据对象DataReport
					DataReport dataReport = new DataReport(
						UUID.randomUUID().toString(),
						DateUtil.parseStrToDate(getValue(reportDate), DateUtil.DATE_FORMAT_YYYY_MM_DD),
						new BigDecimal(getValue(totalAssets)),
						getValue(ownerEquity)
					);

					// 向数据库存储一条数据
					dataReportMapper.insert(dataReport);

				}
			} catch (IOException e) {
				LOGGER.error("IO异常：" + e.getMessage());
			} catch (InvalidFormatException e) {
				LOGGER.error("路径不存在异常：" + e.getMessage());
			}
		} else {

		}


		return false;
	}

	/**
	 * 判断单元格数据类型，并返回对应的值
	 * @param xssfRow
	 * @return
	 */
	private String getValue(XSSFCell xssfRow) {
		String resultStr = null;

		if (xssfRow != null) {
			switch (xssfRow.getCellType()) {
			case STRING:
				resultStr = xssfRow.getStringCellValue();
				break;
			case NUMERIC:
				if("yyyy\\-mm\\-dd;@".equals(xssfRow.getCellStyle().getDataFormatString())){
					resultStr = new SimpleDateFormat(DateUtil.DATE_FORMAT_YYYY_MM_DD).format(xssfRow.getDateCellValue());
				} else {
					resultStr = new DecimalFormat("0.00").format(xssfRow.getNumericCellValue());
				}
				break;
			case ERROR:
				resultStr = xssfRow.getErrorCellString();
				break;
			}
		}
		return resultStr;
	}
}
