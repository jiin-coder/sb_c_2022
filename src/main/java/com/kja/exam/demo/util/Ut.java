package com.kja.exam.demo.util;

public class Ut {
	public static boolean empty(Object obj) {
		if ( obj == null ) {
			return true;
		}
		
		if (obj instanceof String == false) {
			return true;
		}
		
		String str = (String) obj;
		
		return str.trim().length() == 0;
	}

	public static String f(String format, Object... args) {
		return String.format(format, args);
	}
}
 