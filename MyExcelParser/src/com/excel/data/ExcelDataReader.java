package com.excel.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.annotations.AnnotationReader;
import com.dto.PIMModel;
import com.parser.exception.ParsingException;
import com.util.AppUtil;

public class ExcelDataReader implements ExcelReader {

	@Override
	public <T> List<T> readExcel(XSSFSheet sheet) {

		List<T> products = null;
		try {

			String cols[] = AnnotationReader.readAnnotations();
			LinkedHashMap<Integer, String> headersMap = ExcelHeaders
					.mapHeaders(cols, sheet);
			products = readData(sheet, PIMModel.class.getName(), headersMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return products;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> readData(XSSFSheet sheet, String classname,
			LinkedHashMap<Integer, String> map) throws Exception {
		Class classObj = Class.forName(classname);
		List<T> result = new ArrayList<T>();
		Row row;

		for (int rowCount = 1; rowCount < sheet.getLastRowNum(); rowCount++) {
			T one = (T) classObj.newInstance();
			row = sheet.getRow(rowCount);
			result.add(one);

			if (null != row) {
				// outer:
				for (int keys : map.keySet()) {
					String fieldName = map.get(keys);
					fieldName = AppUtil.decapitalize(fieldName);

					Method method = createMethod(classObj,
							AppUtil.removeChars(fieldName));
					Cell cell = row.getCell(keys);

					int type = cell.getCellType();

					if (type == 1) {
						String value = cell.getStringCellValue();
						Object[] values = new Object[1];
						values[0] = value;
						method.invoke(one, values);
					} else if (type == 0) {
						Double num = cell.getNumericCellValue();
						method.invoke(one, num.intValue());

					} else if (type == 3) {
						double num = cell.getNumericCellValue();
						Object[] values = new Object[1];
						values[0] = num;
						method.invoke(one, values);
					}

				}
			}
		}

		return result;
	}

	private static Method createMethod(Class classObj, String fieldName)
			throws SecurityException, NoSuchMethodException {
		Class<?> fieldClass = getterClass(classObj, fieldName);
		return classObj.getMethod("set" + AppUtil.capitalize(fieldName),
				fieldClass);
	}

	private static Class<?> getterClass(Class<?> classObj, String fieldName) {
		String methodName = "get" + AppUtil.capitalize(fieldName);
		Class<?> returnType = null;
		for (Method method : classObj.getMethods()) {
			if (method.getName().equals(methodName)) {
				returnType = method.getReturnType();
				break;

			}
		}
		return returnType;
	}

	@Override
	public void validateExcel() {
		// TODO Auto-generated method stub

	}

}
