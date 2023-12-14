package com.soham.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScientificCalculator extends JFrame {
    private JTextField display;

    public ScientificCalculator() {
        super("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sin", "cos", "tan", "sqrt"
        };

        for (String button : buttons) {
            JButton btn = new JButton(button);
            btn.addActionListener(new ButtonClickListener());
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            switch (buttonText) {
                case "=":
                    evaluateExpression();
                    break;
                case "sqrt":
                    calculateSquareRoot();
                    break;
                case "sin":
                    calculateSin();
                    break;
                case "cos":
                    calculateCos();
                    break;
                case "tan":
                    calculateTan();
                    break;
                default:
                    appendToDisplay(buttonText);
            }
        }

        private void evaluateExpression() {
            String expression = display.getText();
            try {
                // Evaluate the expression using a suitable library or parser
                double result = evaluate(expression);
                display.setText(Double.toString(result));
            } catch (Exception ex) {
                display.setText("Error");
            }
        }

        private void calculateSquareRoot() {
            double number = Double.parseDouble(display.getText());
            display.setText(Double.toString(Math.sqrt(number)));
        }

        private void calculateSin() {
            double angle = Double.parseDouble(display.getText());
            display.setText(Double.toString(Math.sin(Math.toRadians(angle))));
        }

        private void calculateCos() {
            double angle = Double.parseDouble(display.getText());
            display.setText(Double.toString(Math.cos(Math.toRadians(angle))));
        }

        private void calculateTan() {
            double angle = Double.parseDouble(display.getText());
            display.setText(Double.toString(Math.tan(Math.toRadians(angle))));
        }

        private void appendToDisplay(String text) {
            String currentText = display.getText();
            display.setText(currentText + text);
        }
    }

    private double evaluate(String expression) {
        // Implement an expression evaluator or use a library
        // Here, we are using a simple method for demonstration purposes
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = expression.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "sin":
                            x = Math.sin(x);
                            break;
                        case "cos":
                            x = Math.cos(x);
                            break;
                        case "tan":
                            x = Math.tan(x);
                            break;
                        default:
                            throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor());

                return x;
            }
        }.parse();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScientificCalculator::new);
    }
}
