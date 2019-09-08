package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.lux.calculator.logic.CalculatePlus;

class CalculatePlusTest {
	private static CalculatePlus calculatorplus;

	@BeforeAll
	static void init() {
		calculatorplus = new CalculatePlus();
	}

	@Test
	void addition() {
		assertEquals(2, calculatorplus.act(1, 1));
	}

	@Test
	void addition1() {
		assertEquals(52, calculatorplus.act(55, -3));
	}

	@Test
	void addition2() {
		assertEquals(100, calculatorplus.act(100, 0));
	}

}
