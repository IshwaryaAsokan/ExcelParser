package com.kohler.excel.file;

import java.io.InputStream;


public class ExcelUtil {

	public InputStream readFile(String fileName){
		
		InputStream inputStream = ExcelUtil.class.getClassLoader()
				.getResourceAsStream(fileName);
		
		return inputStream;
		
	}
	
	
}
