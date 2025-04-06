package com.golbez.calculator;

import static java.lang.Math.*;

public class CalculatorUtils {
    public static double calculate(double a, double b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            case "mod" -> a % b;
            case "pow" -> pow(a, b);
            case "sqrt" -> sqrt(a);
            case "sin" -> sin(toRadians(a));
            case "cos" -> cos(toRadians(a));
            case "tan" -> tan(toRadians(a));
            default -> throw new IllegalArgumentException("Unsupported operation");
        };
    }
}
