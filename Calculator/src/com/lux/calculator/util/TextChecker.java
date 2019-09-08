package com.lux.calculator.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextChecker {
	private static final String DOUBLE_REGULAR_EXPRESSION = "[-+]?[0-9]+(\\.)?+([0-9]+)?";

	private TextChecker() {
	}
	
	public static boolean checker(String text) {
		if (checkTextFild(text, DOUBLE_REGULAR_EXPRESSION)) {
			return true;
		}
		return false;
	}

	private static boolean checkTextFild(String value, String expresion) {
		Pattern p = Pattern.compile(expresion);
		Matcher m = p.matcher(value);
		if (m.find() && m.group().equals(value)) {
			return true;
		}
		return false;
	}

}
