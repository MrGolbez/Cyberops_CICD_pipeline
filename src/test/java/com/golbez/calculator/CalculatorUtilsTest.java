package com.golbez.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorUtilsTest {
    @Test
    void testAddition() {
        assertEquals(5, CalculatorUtils.calculate(2, 3, "+"));
    }

    @Test
    void testModulus() {
        assertEquals(1, CalculatorUtils.calculate(10, 3, "mod"));
    }

    @Test
    void testSqrt() {
        assertEquals(4, CalculatorUtils.calculate(16, 0, "sqrt"), 0.0001);
    }

    @Test
    void testCos() {
        assertEquals(1, CalculatorUtils.calculate(0, 0, "cos"), 0.0001);
    }

    @Test
    void testPow() {
        assertEquals(8, CalculatorUtils.calculate(2, 3, "pow"));
    }
}
