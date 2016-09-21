package com.kohler.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.kohler.dto.PIMModel;
import com.kohler.excel.data.ExcelColumn;

public class AnnotationReader {

	public static String[] readAnnotations(){
		
		Annotation[] annotationsField;
		ExcelColumn column;
		String cols[] = new String[PIMModel.class.getDeclaredFields().length];
		int i = 0;

		
		for (Field field : PIMModel.class.getDeclaredFields()) {

			annotationsField = field.getAnnotations(); 
			column = (ExcelColumn) annotationsField[0];
			String s = "";
			int j = 0;
			for(String str : column.colNameAlias()){
				s = (column.colNameAlias().length > 1 && j>0) ? s +"," +str : s+str;
			
			j++;
			}
			cols[i] = column.colName() + "," + s;
			System.out.println(column.colName() + "," + s);
			i++;
		}
		return cols;
	}
}
