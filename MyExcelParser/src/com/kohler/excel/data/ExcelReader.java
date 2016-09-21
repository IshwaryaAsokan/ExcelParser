package com.kohler.excel.data;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public interface ExcelReader {
		
	public void validateExcel();

	
	public <T> List<T> readExcel(XSSFSheet sheet);

}
