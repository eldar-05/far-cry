import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField resultField;
    private double[] numbers = {0, 0};
    private char operator;
    private double result = 0;

    public Calculator() {
        setTitle("Calculator");
        setSize(300, 450);
        setLayout(new BorderLayout());

        resultField = new JTextField();
        resultField.setEditable(false);
        add(resultField, BorderLayout.NORTH);

        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(4, 3, 5, 5));
        JPanel symbolPanel = new JPanel();
        symbolPanel.setLayout(new BoxLayout(symbolPanel, BoxLayout.Y_AXIS));

        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        for (String number : numbers) {
            JButton numberButton = new JButton(number);
            numberButton.addActionListener(this);
            numberPanel.add(numberButton);
        }
        add(numberPanel, BorderLayout.CENTER);

        String[] symbols = {"-", "+", "*", "/", "=", "C"};
        for (String symbol : symbols) {
            JButton symbolButton = new JButton(symbol);
            symbolButton.addActionListener(this);
            symbolPanel.add(symbolButton);
        }
        add(symbolPanel, BorderLayout.EAST);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
            resultField.setText(resultField.getText() + command);
        } else if (command.charAt(0) == 'C') {
            resultField.setText("");
            numbers[0] = 0;
            numbers[1] = 0;
            operator = '\0';
            result = 0;
        } else if (command.charAt(0) == '=') {
            try {
                numbers[1] = Double.parseDouble(resultField.getText());
            } catch (NumberFormatException ex) {
                resultField.setText("Error");
                return;
            }

            switch (operator) {
                case '+':
                    result = numbers[0] + numbers[1];
                    break;
                case '-':
                    result = numbers[0] - numbers[1];
                    break;
                case '*':
                    result = numbers[0] * numbers[1];
                    break;
                case '/':
                    if (numbers[1] == 0) {
                        resultField.setText("Divide by 0");
                        return;
                    }
                    result = numbers[0] / numbers[1];
                    break;
                default:
                    result = numbers[1];
                    break;
            }
            resultField.setText("" + result);
            numbers[0] = result;
        } else {
            try {
                numbers[0] = Double.parseDouble(resultField.getText());
            } catch (NumberFormatException ex) {
                resultField.setText("Error");
                return;
            }
            operator = command.charAt(0);
            resultField.setText("");
        }
    }
    public static void run() {
        new Calculator();
    }
}
