package com.lux.calculator.logic;

public class CalculateDiv implements Calculate {
	@Override
	public double act(double a, double b) {
		if (b != 0) {
			return a / b;
		} else {
			return Double.NaN;
		}
	}
}
