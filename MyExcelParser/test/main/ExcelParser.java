package main;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.dto.PIMModel;

public class ExcelParser {
	static Logger logger = Logger.getLogger(ExcelParser.class.getName());
	private static List<String> fieldNames = new ArrayList<String>();
	private static Workbook workbook = null;
	private String workbookName = "workbook.xls";

	public ExcelParser(String workbookName) {
		setWorkbookName(workbookName);
		initialize();
	}

	private void initialize() {
		setWorkbook(new HSSFWorkbook());
	}

	public void closeWorksheet() {
		// Write the output to a file
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(getWorkbookName());
			getWorkbook().write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean setupFieldsForClass(Class<?> clazz) throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fieldNames.add(fields[i].getName());
		}
		return true;
	}
	
	public static void main(String args[]){
		
		try {
			readData(PIMModel.class.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static Sheet getSheetWithName(String name) {
		Sheet sheet = null;
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			//if (name.compareTo(workbook.getSheetName(i)) == 0) {
				sheet = workbook.getSheetAt(i);
				//break;
			}
		
		return sheet;
	}

	private static void initializeForRead() throws InvalidFormatException, IOException {
		logger.info("Open file");
		InputStream inp = TestParser.class.getClassLoader()
				.getResourceAsStream("RESI_TEST.xlsx");
		//InputStream inp = new FileInputStream(getWorkbookName());
		logger.info("create wb");
		workbook = WorkbookFactory.create(inp);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> readData(String classname) throws Exception {
		
		initializeForRead();
		Sheet sheet = getSheetWithName(classname);

		logger.info("The class is: " + workbook.getSheetName(0));
		Class clazz = Class.forName(PIMModel.class.getName());
		setupFieldsForClass(clazz);
		List<T> result = new ArrayList<T>();
		Row row;
		for (int rowCount = 1; rowCount < 4; rowCount++) {
			T one = (T) clazz.newInstance();
			row = sheet.getRow(rowCount);
			if(null != row){int colCount = 0;
			result.add(one);
			for (Cell cell : row) {
				int type = cell.getCellType();
				String fieldName = fieldNames.get(colCount++);
				logger.info("Method: set"+capitalize(fieldName));
				logger.info("Cell type: " + type);
				
				Method method = constructMethod(clazz, fieldName);
				
				if (type ==  1) {
					String value = cell.getStringCellValue();
					Object[] values = new Object[1];
					values[0] = value;
					method.invoke(one, values);
				} else if (type == 0) {
					Double num = cell.getNumericCellValue();
                    Class<?> returnType = getGetterReturnClass(clazz,fieldName);
                    if(returnType == Integer.class){
                    	method.invoke(one, num.intValue());
                    } else if(returnType == Double.class){
                    	method.invoke(one, num);
                    } else if(returnType == Float.class){
                    	method.invoke(one, num.floatValue());
                    }

				} else if (type == 3) {
					double num = cell.getNumericCellValue();
					Object[] values = new Object[1];
					values[0] =num;
					method.invoke(one, values);
				}
			}}
		}

		logger.info("The result set contains: " + result.size()
				+ " items.");
		return result;
	}

	private static Class<?> getGetterReturnClass(Class<?> clazz, String fieldName) {
		String methodName = "get"+capitalize(fieldName);
		Class<?> returnType = null;
		for (Method method : clazz.getMethods()) {
			if(method.getName().equals(methodName)){
				returnType = method.getReturnType();
				break;
			}
		}
		return returnType;
	}
	@SuppressWarnings("unchecked")
	private static Method constructMethod(Class clazz, String fieldName) throws SecurityException, NoSuchMethodException {
		Class<?> fieldClass = getGetterReturnClass(clazz, fieldName);
		return clazz.getMethod("set"+ capitalize(fieldName),fieldClass);
	}

	public static String capitalize(String string) {
		String capital = string.substring(0, 1).toUpperCase();
		return capital + string.substring(1);
	}

	public String getWorkbookName() {
		return workbookName;
	}

	public void setWorkbookName(String workbookName) {
		this.workbookName = workbookName;
	}


	void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	Workbook getWorkbook() {
		return workbook;
	}

}

