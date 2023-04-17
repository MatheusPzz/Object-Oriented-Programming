import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

// Here we have the import packages and classes to perform and create the GUI and be functional
// The HashMap is to store the variables and their values in a fixed position.

/**
 * This is project for the subject Object Oriented Programming.
 *
 * @Matheus Perazzo
 * @2.0 (15/04/2023)
 * 
 * This project was done with the help of internet a youtube videoclasses, with a lot of effort
 * and time i could manage to do it. It is not a hard code, but also not so easy one, i believe
 * and trust i have used a lot of contents from the lecture itself.
 * 
 * The project consists in a calculator using JFrame HashMap fundamentals integrated and implemented 
 * with Object Oriented Programming learned in class with the teacher.
 */

    // Defining the Smart_Calculator class extending JFrame to the window and GUI
    // Then ActionListener is basically used for the user input events (as clicking a button)
  public class Smart_Calculator extends JFrame implements ActionListener {
    
    // Here i am declaring 3 private instances variables for the first Two Text Fields at the top of teh screen
    // Then we have HashMap for storing the variables and their values
    
    private JTextField insertionField;
    private JTextField resultField;
    private Map <String, Double> variables = new HashMap<>();

    // Now we got the smart_calculator constructor, which starts the window title
    // Sets the program to close the application once you close the window
    // Then i am also setting the window size to 500x500 pixels
    
  public Smart_Calculator() {
    super("This is a Smart Calculator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 500);

    // Here i am creating a JPanel for the inputs at the top and saying that the layout 
    // will be done in a 3 by 3 GridLayout 
    
    JPanel insertionPanel = new JPanel(new GridLayout(3, 3));
    insertionPanel.add(new JLabel("Number:"));
    insertionField = new JTextField();
    insertionPanel.add(insertionField);
    insertionPanel.add(new JLabel("Operation Result:"));
    resultField = new JTextField();
    
    // Cant Edit the result given so box will be always one result.
    resultField.setEditable(false);
    insertionPanel.add(resultField);
    
    insertionPanel.setFont(new Font("Arial", Font.PLAIN,45));
    
    // Here is another JPanel in GridLayout 4 by 4, but this one for the buttons of the calculator
    // I have defined an array with all the Data i want inside each of button
    // HashMap will be used to put them in place once the user input is provided
    
    JPanel buttonsPanel = new JPanel(new GridLayout(4,4));
    JButton button;

    String[] buttons = {
      "7","8","9","/",
      "4","5","6","*",
      "1","2","3","-",
      "","C","=","+"
    };
    
    // Another Array to define the color of each block of the calculator
    
    Color[] colors = {
        Color.GREEN,Color.GREEN,Color.GREEN,Color.YELLOW,
        Color.GREEN,Color.GREEN,Color.GREEN,Color.YELLOW,
        Color.GREEN,Color.GREEN,Color.GREEN,Color.YELLOW,
        Color.WHITE,Color.BLUE,Color.BLUE,Color.YELLOW
    };
    // Logo image for the blank space of teh array 
    ImageIcon logo = new ImageIcon("C:/Users/Matheus Perazzo/smartCalc/Logo (1).png");
    
    
    
    // Adding a new Font which is going to be used to increase the size of the strings inside the buttons
    Font buttonFont = new Font("Arial", Font.BOLD, 25);
    
    // This for loop will create a button for each one of the items in the array
    for(int i = 0; i < buttons.length; i++) {
      button = new JButton(buttons[i]);
      button.addActionListener(this);
      button.setBackground(colors[i]);
      buttonsPanel.add(button);
      button.setFont(buttonFont);
      // Validates if the button equals "" of the array, sets the icon background to out logo.  
      if (buttons[i].equals("")){
          button.setIcon(logo);
      }
    }
    
    // Those two set the input and button panels to the content pane of the window using BorderLayout
    // Setting it to be visible 
    
      getContentPane().add(insertionPanel, BorderLayout.NORTH);
      getContentPane().add(buttonsPanel, BorderLayout.CENTER);

      setVisible(true);
    
    }

    // Thats The main function of the program which will take all the instances
    // of the code and create a new Smart_Calculator
    
    public static void main(String[] args) {
      new Smart_Calculator();
    }
    
    
    
    // In this method i am taking the user number input and returning a double
    
    public double evaluate (String number) throws IllegalArgumentException {
        // In here i am splitting the user input number into this array argument.
        // In case of any character inputed by the user will be followed by any of the operators.
        String [] tokens = number.split("(?=[+\\-*/])|(?<=[+\\-*/])");
        double result = 0;
        String operator = "+";
        
        // Similar to the loop before this one will go through each of the 
        // Elements inside the array tokens
        for(String token : tokens) {
            
          // Here i am checking if the token is an empty string, if it is
          // jumps to the next iteration.
          if (token.isEmpty()){
            continue;
          }
          
          // Here is where each token is analyzed based on the user input
          // it will be updated to the selected.
          // If the token is valid it updates the "Result" variable with the new value
          if(token.matches("\\+|-|\\*|\\/")){
            operator = token;
          } else if(token.matches("[a-zA-Z]+")){
            if (variables.containsKey(token)) {
              result = compute(result, operator, variables.get(token));
              } else {
                  throw new IllegalArgumentException("Variable does not match any: " + token);  
                }
          } else if(token.matches("-?\\d+(\\.\\d+)?")) {
              double value = Double.parseDouble(token);
              result = compute(result, operator, value);
            
            // If user enter "=" option the loops continue to the next iteration
            // Which means the next operation, if none of the tokens match an erro will pop up at the result tab.
          } else if(token.equals("=")){
              continue;
          } else {
              throw new IllegalArgumentException("Invalid token: " + token);
          }
        }
        //Updating the result returning the value to it, this after the tokens have been evaluated.
        return result;
    }
    
    // The ActionEvent method is used to communicate the action of each of the 
    // buttons whenever clicked in the GUI.
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("=")) {
            try {
                double result = evaluate(insertionField.getText());
                resultField.setText(String.valueOf(result));
            } catch (IllegalArgumentException ex) {
                resultField.setText("Error: " + ex.getMessage());
            } catch (Exception ex) {
                resultField.setText("Unkown Error: " + ex.getMessage());
            }
        } else if (command.equals("C")) {
            insertionField.setText("");
            resultField.setText("");
        } else {
            insertionField.setText(insertionField.getText() + command);
        }
    }
    
    // This method here is used to make the mathematical expressions
    // I defined two double variables they will do the operation based on 
    // which user input.
    private double compute(double num1, String operator, double num2) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Unkown Argument:" + operator);
        }
    }
  }