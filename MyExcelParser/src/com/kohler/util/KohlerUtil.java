package com.kohler.util;

public class KohlerUtil {

	public static String removeChars(String value) {
		String newValue = value;
		if (value.indexOf('_') != -1) {
			newValue = value.replaceAll("_", "");
		}
		if (value.indexOf('-') != -1) {
			newValue = value.replaceAll("-", "");
		}
		if (value.indexOf(' ') != -1) {
			newValue = value.replaceAll(" ", "");
		}
		return newValue;
	}
	
	public static String capitalize(String string) {
		String capital = string.substring(0, 1).toUpperCase();
		return capital + string.substring(1);
	}

	public static String decapitalize(String string) {
		String capital = string.substring(0, 1).toLowerCase();
		return capital + string.substring(1);
	}

}
