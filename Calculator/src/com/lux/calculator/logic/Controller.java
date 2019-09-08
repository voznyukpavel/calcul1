package com.lux.calculator.logic;

import com.lux.calculator.util.TextChecker;

public class Controller {
	
	private static final String ARENT_VALID = " are`nt valid: ";
	private static final String DIVIDING_BY_ZERO_IS_FORBIDDEN = "Divding by zero is forbidden";
	private static final String IN_THE_BOTH_FIELDS_ARGUMENTS="In the both fields arguments";
	private static final String ISNT_VALID = " is`nt valid: ";
	private static final String THE_FIRST_ARGUMENT = "The first argument ";
	private static final String THE_FIRST_FIELD = "\t the first field: ";
	private static final String THE_SECOND_ARGUMENT = "The second argument ";
	private static final String THE_SECOND_FIELD = "\t the second field: ";
	
	public String isValid(String arg1, String arg2) {
		boolean result1 = TextChecker.checker(arg1);
		boolean result2 = TextChecker.checker(arg2);
		return createMessage(arg1, arg2, result1, result2);
	}

	private String createMessage(String arg1, String arg2, boolean result1, boolean result2) {
		String lineSeparator = System.lineSeparator();
		if (!result1 && !result2) {
			return IN_THE_BOTH_FIELDS_ARGUMENTS + lineSeparator + ARENT_VALID + lineSeparator + THE_FIRST_FIELD
					+ arg1 + lineSeparator + THE_SECOND_FIELD + arg2;
		} else if (!result1) {
			return THE_FIRST_ARGUMENT + lineSeparator + ISNT_VALID + arg1;
		} else if (!result2) {
			return THE_SECOND_ARGUMENT + lineSeparator + ISNT_VALID + arg2;
		}
		return "";
	}

	public String checkDividingByZero(String text, String actionTitle) {
		if (actionTitle.equals(Action.DIV.getTitle()) && getDouble(text) == 0) {
			return DIVIDING_BY_ZERO_IS_FORBIDDEN;
		}
		return "";
	}

	public String callCalculate(String arg1, String arg2, String actionTitle) {
		Action action = Action.getActionByTitle(actionTitle);
		return action.calcExecute(getDouble(arg1), getDouble(arg2)) + "";
	}

	private double getDouble(String text) {
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException ex) {
		}
		return 0;
	}
}
