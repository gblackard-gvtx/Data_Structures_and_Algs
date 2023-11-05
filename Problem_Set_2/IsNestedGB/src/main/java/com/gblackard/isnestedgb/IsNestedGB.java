/*
 * 
 * Geoffery Blackard
 * COSC 2436
 * Program Set 2
 *Dib, Firas. Regex101. Accessed October 30, 2023. https://regex101.com/.
 *"Validating a Mathematical Expression in Java." Stack Overflow. Accessed October 30, 2023. https://stackoverflow.com/questions/40483468/validating-a-mathemat
 */
package com.gblackard.isnestedgb;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class IsNestedGB {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter a mathematical expression: ");
                String expression = scanner.nextLine();
                
                // Check if the expression contains invalid characters
                if (!isValidExpression(expression)) {
                    System.out.println("Invalid characters in the expression.");
                    continue; // Skip further processing
                }
                
                // Check if the expression is nested correctly
                if (isNested(expression)) {
                    System.out.println("The expression is nested correctly.");
                } else {
                    System.out.println("The expression is not nested correctly.");
                }
                
                System.out.print("Do you want to run the program again? (yes/no): ");
                String again = scanner.nextLine();
                if (!again.equalsIgnoreCase("yes")) {
                    break;
                }
            }
        }
    }

    public static boolean isNested(String expression) {
        Stack<Character> stack = new Stack<>();

        for (char character : expression.toCharArray()) {
            if (character == '(' || character == '[' || character == '{') {
                stack.push(character); // Push left delimiter onto the stack
            } else if (character == ')' || character == ']' || character == '}') {
                if (stack.isEmpty()) {
                    return false; // Right delimiter with no matching left delimiter
                }

                char leftDelimiter = stack.pop();
                if (!isMatchingPair(leftDelimiter, character)) {
                    return false; // Mismatched delimiters
                }
            }
        }

        return stack.isEmpty(); // If stack is empty, all delimiters matched
    }

    public static boolean isMatchingPair(char left, char right) {
        return (left == '(' && right == ')') || (left == '[' && right == ']') || (left == '{' && right == '}');
    }

   public static boolean isValidExpression(String expression) {
       System.out.println("Expression: " + expression);
       
    // Use a regular expression to validate the expression format
    String pattern = "[a-zA-Z0-9+\\-*\\/(){}\\[\\].\\s]+";
    return Pattern.matches(pattern, expression);
   }
}

