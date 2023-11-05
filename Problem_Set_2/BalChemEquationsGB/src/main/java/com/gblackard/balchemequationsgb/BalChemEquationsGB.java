/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.gblackard.balchemequationsgb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * Geoffery Blackard
 * COSC 2436
 * Program Set 3
 */
public class BalChemEquationsGB {

    public static void main(String[] args) throws FileNotFoundException {
        // Get file name from user
        Scanner input = new Scanner(System.in);

        do{
        System.out.print("Enter file name: ");

        String fileName = input.nextLine();

        // Read equations from file
        File file = new File(fileName);

        Scanner fileInput = new Scanner(file);

        int numEquations = fileInput.nextInt();

        fileInput.nextLine();

        for (int i = 0; i < numEquations; i++) {

            String equation = fileInput.nextLine();

             String balancedEquation = balanceEquation(equation);
            System.out.println("EQ"+ (i+1)+": " + balancedEquation);

        }
        System.out.print("Run again (Y/N): ");
        }while(input.nextLine().equalsIgnoreCase("Y"));

    }

    public static String balanceEquation(String equation) {
        // Split the equation into reactants and products
        String[] parts = equation.split("=");

        if (parts.length != 2) {
            return "Invalid equation format";
        }

        String reactants = parts[0].trim();
        String products = parts[1].trim();

        // Parse the reactants and products into their respective compounds
        Map<String, Integer> reactantMap = parseCompound(reactants);
        Map<String, Integer> productMap = parseCompound(products);

        // Balance the equation by adjusting the coefficients
        for (String element : productMap.keySet()) {
            int reactantCount = reactantMap.getOrDefault(element, 0);
            int productCount = productMap.get(element);
            int diff = productCount - reactantCount;

            if (diff != 0) {
                for (String compound : reactantMap.keySet()) {
                    int coeff = reactantMap.get(compound);
                    reactantMap.put(compound, coeff * (productCount - reactantCount));
                }
            }
        }

        // Build the balanced equation string
        StringBuilder balancedEquation = new StringBuilder();
        for (String compound : reactantMap.keySet()) {
            int coeff = reactantMap.get(compound);
            if (balancedEquation.length() > 0) {
                balancedEquation.append(" + ");
            }
            balancedEquation.append(coeff);
            balancedEquation.append(compound);
        }
        balancedEquation.append(" = ");
        balancedEquation.append(products);

        return balancedEquation.toString();
    }

    // Helper method to parse a compound into its elements and coefficients
    private static Map<String, Integer> parseCompound(String compound) {
        Map<String, Integer> compoundMap = new HashMap<>();
        String[] elements = compound.split("\\s+");
        for (String element : elements) {
            int coeff = 1;
            if (Character.isDigit(element.charAt(0))) {
                int endIndex = 1;
                while (endIndex < element.length() && Character.isDigit(element.charAt(endIndex))) {
                    endIndex++;
                }
                coeff = Integer.parseInt(element.substring(0, endIndex));
                element = element.substring(endIndex);
            }
            compoundMap.put(element, coeff);
        }
        return compoundMap;
    }

}
