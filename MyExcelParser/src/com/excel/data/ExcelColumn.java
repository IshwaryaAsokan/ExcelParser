package com.excel.data;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
	//basic predominant column name
	String colName() default "";
	
	//possible other column names
	String[] colNameAlias() default "";
}
