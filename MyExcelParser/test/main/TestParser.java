package main;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dto.PIMModel;
import com.excel.data.ExcelColumn;
import com.util.AppUtil;

public class TestParser {
	private static LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();

	public static LinkedHashMap<Integer, String> mapHeaders(
			String[] outColumns, XSSFSheet sheet) {
		XSSFRow row = sheet.getRow(0);
		Integer icol = null;

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

	@SuppressWarnings("unchecked")
	public static <T> void main(String args[]) throws Exception {
		// PIMModel aClass = PIMModel.class.getName();

		List<T> products = null;
		Annotation[] annotationsField;
		ExcelColumn column;
		String cols[] = new String[PIMModel.class.getDeclaredFields().length];
		int i = 0;

		for (Field field : PIMModel.class.getDeclaredFields()) {

			annotationsField = field.getAnnotations(); // do something to these
			column = (ExcelColumn) annotationsField[0];
			String s = "";
			for(String str : column.colNameAlias()){
				s = column.colNameAlias().length > 1 ? s +"," +str : s+str;
			}
			cols[i] = column.colName() + "," + s;
			System.out.println(column.colName() + "," + s);
			i++;
		}

		String fileName = "required_acc_resi_v1.1.xlsx";
		InputStream inputStream = TestParser.class.getClassLoader()
				.getResourceAsStream(fileName);
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			XSSFSheet sheet = workbook.getSheetAt(0);
			LinkedHashMap<Integer, String> map = mapHeaders(cols, sheet);
			products = readData(sheet, PIMModel.class.getName(), map);

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Excel Report - " + fileName + " \nPIMModel list - "
				+ products.toString());
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
		return classObj.getMethod("set" + AppUtil.capitalize(fieldName), fieldClass);
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



}
