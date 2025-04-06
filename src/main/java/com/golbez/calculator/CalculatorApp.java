package com.golbez.calculator;

import javax.swing.*;
import java.awt.*;

public class CalculatorApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Scientific Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);

        JTextField input1 = new JTextField();
        JTextField input2 = new JTextField();
        JTextField result = new JTextField();
        result.setEditable(false);

        JComboBox<String> operation = new JComboBox<>(new String[]{
                "+", "-", "*", "/", "mod", "pow", "sqrt", "sin", "cos", "tan"
        });

        JButton calcButton = new JButton("Calculate");
        JButton resetButton = new JButton("Reset");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        panel.add(new JLabel("Input 1:"));
        panel.add(input1);
        panel.add(new JLabel("Input 2:"));
        panel.add(input2);
        panel.add(new JLabel("Operation:"));
        panel.add(operation);
        panel.add(calcButton);
        panel.add(resetButton);
        panel.add(new JLabel("Result:"));
        panel.add(result);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);

        calcButton.addActionListener(e -> {
            try {
                double val1 = input1.getText().isEmpty() ? 0 : Double.parseDouble(input1.getText());
                double val2 = input2.getText().isEmpty() ? 0 : Double.parseDouble(input2.getText());
                String op = (String) operation.getSelectedItem();

                assert op != null;
                double output = CalculatorUtils.calculate(val1, val2, op);
                result.setText(String.valueOf(output));
            } catch (NumberFormatException ex) {
                result.setText("Invalid input");
            } catch (ArithmeticException ae) {
                result.setText("Math Error");
            }
        });

        resetButton.addActionListener(e -> {
            input1.setText("");
            input2.setText("");
            result.setText("");
        });
    }
}
