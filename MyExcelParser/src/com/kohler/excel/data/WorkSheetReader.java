package com.kohler.excel.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kohler.dto.PIMModel;

public class WorkSheetReader {

	public XSSFSheet getWorkSheet(InputStream stream){
		XSSFSheet sheet = null;
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(stream);

			sheet = workbook.getSheetAt(0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sheet;
	}
}
