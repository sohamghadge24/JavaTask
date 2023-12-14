package CurrencyConvertor.src.com.convertor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrencyConverter extends JFrame {
    private JTextField amountField;
    private JComboBox<String> fromCurrencyBox;
    private JComboBox<String> toCurrencyBox;
    private JLabel resultLabel;

    private static final String[] CURRENCIES = {"USD", "EUR", "GBP", "JPY"};

    public CurrencyConverter() {
        super("Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(5, 2));

        amountField = new JTextField();
        fromCurrencyBox = new JComboBox<>(CURRENCIES);
        toCurrencyBox = new JComboBox<>(CURRENCIES);
        resultLabel = new JLabel("Result:");

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        add(new JLabel("Amount:"));
        add(amountField);
        add(new JLabel("From Currency:"));
        add(fromCurrencyBox);
        add(new JLabel("To Currency:"));
        add(toCurrencyBox);
        add(new JLabel()); // Empty label for spacing
        add(convertButton);
        add(new JLabel("Result:"));
        add(resultLabel);

        setVisible(true);
    }

    private void convertCurrency() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String fromCurrency = (String) fromCurrencyBox.getSelectedItem();
            String toCurrency = (String) toCurrencyBox.getSelectedItem();

            // In a real application, you would fetch the exchange rate from an API.
            // Here, we assume a simple conversion rate for demonstration purposes.
            double conversionRate = getConversionRate(fromCurrency, toCurrency);
            double result = amount * conversionRate;

            resultLabel.setText("Result: " + result + " " + toCurrency);
        } catch (NumberFormatException e) {
            resultLabel.setText("Result: Invalid input");
        }
    }

    private double getConversionRate(String fromCurrency, String toCurrency) {
        // In a real application, you would fetch the exchange rate from an API.
        // Here, we use a simple mapping for demonstration purposes.
        // You should replace this with actual exchange rate data.
        // Note: This mapping is not accurate and is just for demonstration purposes.
        // Real-world applications should use a reliable API for currency conversion.
        // For example, you can use a service like Open Exchange Rates, Fixer.io, etc.
        // Consult their documentation for usage details and API key requirements.

        String mappingKey = fromCurrency + "_" + toCurrency;
        switch (mappingKey) {
            case "USD_EUR":
                return 0.85;
            case "USD_GBP":
                return 0.75;
            case "USD_JPY":
                return 110.0;
            case "EUR_USD":
                return 1.18;
            case "EUR_GBP":
                return 0.88;
            case "EUR_JPY":
                return 130.0;
            case "GBP_USD":
                return 1.33;
            case "GBP_EUR":
                return 1.14;
            case "GBP_JPY":
                return 150.0;
            case "JPY_USD":
                return 0.0091;
            case "JPY_EUR":
                return 0.0077;
            case "JPY_GBP":
                return 0.0067;
            default:
                throw new IllegalArgumentException("Invalid currency pair");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurrencyConverter::new);
    }
}
