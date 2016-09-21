package com.excel.data;

import java.util.LinkedHashMap;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelHeaders {

	public static LinkedHashMap<Integer, String> mapHeaders(
			String[] outColumns, XSSFSheet sheet) {
		XSSFRow row = sheet.getRow(0);
		Integer icol = null;
		LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
		
		for (String outColumn : outColumns) {
			if (outColumn != null) {
				String cols[] = outColumn.split(",");
				String cellValue;
				outer: for (String col : cols) {

					for (int i = row.getFirstCellNum(); i < row
							.getLastCellNum(); i++) {
						cellValue = row.getCell(i).getStringCellValue();
						if (cellValue.equalsIgnoreCase(col)) {

							icol = new Integer(i);

							map.put(icol, cols[0]);

							break outer;
						}
					}

				}
			}
		}
		return map;
	}

}
