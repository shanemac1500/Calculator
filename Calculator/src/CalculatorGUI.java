import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame implements ActionListener {
    // Create components
    private JTextField display;
    private StringBuilder currentInput;
    private double firstOperand;
    private String operator;

    public CalculatorGUI() {
        // Set up the frame
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the display field
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.LIGHT_GRAY);
        add(display, BorderLayout.NORTH);

        // Create buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "√", "±", ""
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setBackground(Color.CYAN);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        
        // Initialize variables
        currentInput = new StringBuilder();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "C":
                clear();
                break;
            case "=":
                calculateResult();
                break;
            case "√":
                calculateSquareRoot();
                break;
            case "±":
                toggleSign();
                break;
            default:
                handleInput(command);
                break;
        }
    }

    private void clear() {
        currentInput.setLength(0);
        display.setText("");
        firstOperand = 0;
        operator = null;
    }

    private void handleInput(String command) {
        if ("+-*/".contains(command)) {
            if (currentInput.length() > 0) {
                try {
                    firstOperand = Double.parseDouble(currentInput.toString());
                    operator = command;
                    currentInput.setLength(0);
                } catch (NumberFormatException ex) {
                    displayError("Invalid Input");
                }
            }
        } else {
            currentInput.append(command);
        }
        display.setText(currentInput.toString());
    }

    private void calculateResult() {
        if (operator != null && currentInput.length() > 0) {
            try {
                double secondOperand = Double.parseDouble(currentInput.toString());
                double result = 0;
                switch (operator) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "/":
                        if (secondOperand != 0) {
                            result = firstOperand / secondOperand;
                        } else {
                            displayError("Cannot divide by zero");
                            return;
                        }
                        break;
                }
                display.setText(String.valueOf(result));
                currentInput.setLength(0);
                operator = null;
            } catch (NumberFormatException ex) {
                displayError("Invalid Input");
            }
        } else {
            displayError("Incomplete Expression");
        }
    }

    private void calculateSquareRoot() {
        if (currentInput.length() > 0) {
            try {
                double number = Double.parseDouble(currentInput.toString());
                if (number >= 0) {
                    double result = Math.sqrt(number);
                    display.setText(String.valueOf(result));
                    currentInput.setLength(0);
                } else {
                    displayError("Cannot calculate square root of negative number");
                }
            } catch (NumberFormatException ex) {
                displayError("Invalid Input");
            }
        } else {
            displayError("No Input");
        }
    }

    private void toggleSign() {
        if (currentInput.length() > 0) {
            try {
                double number = Double.parseDouble(currentInput.toString());
                number = -number;
                display.setText(String.valueOf(number));
                currentInput.setLength(0);
                currentInput.append(number);
            } catch (NumberFormatException ex) {
                displayError("Invalid Input");
            }
        } else {
            displayError("No Input");
        }
    }

    private void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorGUI calculator = new CalculatorGUI();
            calculator.setVisible(true);
        });
    }
}