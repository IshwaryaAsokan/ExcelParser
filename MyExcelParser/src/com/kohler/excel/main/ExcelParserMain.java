package com.kohler.excel.main;

import java.io.InputStream;import java.util.LinkedHashMap;
import java.util.List;



import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kohler.dto.PIMModel;
import com.kohler.excel.data.ExcelDataReader;
import com.kohler.excel.data.WorkSheetReader;
import com.kohler.excel.file.ExcelUtil;
import com.kohler.parser.exception.ParsingException;

public class ExcelParserMain {

	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws ParsingException{
		
		String fileName = "RESI_TEST.xlsx";
		InputStream stream;
		ExcelUtil excel = new ExcelUtil();
		WorkSheetReader wsReader = new WorkSheetReader();
		
		stream = excel.readFile(fileName);
		XSSFSheet sheet = wsReader.getWorkSheet(stream);
		
		
		List<T> products = new ExcelDataReader().readExcel(sheet);
		System.out.println("Excel Report - " + fileName + " \nPIMModel list - "
				+ products.toString());
		
	}
}
