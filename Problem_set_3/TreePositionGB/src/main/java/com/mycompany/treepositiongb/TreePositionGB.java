/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.treepositiongb;

import java.util.Scanner;

/**
 * Geoffery Blackard COSC 2436 Program Set 3 "Binary Tree (Array
 * Implementation)." GeeksforGeeks. Last modified April 6, 2023.
 * https://www.geeksforgeeks.org/binary-tree-array-implementation/# "Construct
 * Binary Tree from Given Parent Array Representation." GeeksforGeeks. Last
 * modified October 20, 2023.
 * https://www.geeksforgeeks.org/construct-a-binary-tree-from-parent-array-representation/.
 */
public class TreePositionGB {

    public static void main(String[] args) {
        // Create a Scanner to read input from the user
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Prompt the user to enter a binary tree triple
            System.out.println("Enter binary tree triple (parent left right):");
            String input = scanner.nextLine().trim();
            // If the user enters "exit", break the loop and end the program
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            // Get the root of the tree
            Character root = getRoot(input);
            // Find the position of 'A' in the tree
            findAPostition(root, input);

            // Ask the user if they want to enter another triple
            System.out.println("Do you want to enter another triple? (yes/no)");
            String continueInput = scanner.nextLine().trim();
            // If the user enters "no", break the loop and end the program
            if (continueInput.equalsIgnoreCase("no")) {
                break;
            }
        }
    }

// Method to get the root of the tree
    public static Character getRoot(String input) {
        // Split the input into parts
        String[] splitInput = input.split(" ");
        // Create an array to store the count of each character
        Integer[] countArray = new Integer[splitInput.length];
        int count = 0;
        // Count the occurrence of each character
        for (int i = 0; i < splitInput.length; i++) {
            Character root = splitInput[i].charAt(0);
            for (String string : splitInput) {
                if (string.contains("" + root)) {
                    count++;
                }
            }
            countArray[i] = count;
            count = 0;
        }
        // The root is the character that occurs only once
        for (int i = 0; i < countArray.length; i++) {
            if (countArray[i] == 1) {
                return splitInput[i].charAt(0);
            }
        }
        return null;
    }

    public static void findAPostition(Character root, String input) {
        // Split the input into parts
        String[] splitInput = input.split(" ");
        // Calculate the number of nodes, height, and size of the tree
        int numberOfNodes = (splitInput.length * 3);
        int height = (int) (Math.ceil(Math.log(numberOfNodes + 1) / Math.log(2)) - 1);
        int size = (int) Math.pow(2, height + 1) - 1;
        // Create an array to represent the tree
        Character[] binArray = new Character[(size)];
        // Fill the array with dashes
        for (int i = 0; i < binArray.length; i++) {
            binArray[i] = '-';
        }
        // Set the root of the tree
        binArray[0] = root;
        int pos = 0;
        int counter = 0;

        // Loop until all nodes have been added to the array
        while (true) {
            // For each part in the input
            for (String string : splitInput) {
                // If the current position in the array is the parent in the part
                if (binArray[pos].equals(string.charAt(0))) {
                    // Add the left child to the array
                    int index = (pos * 2) + 1;
                    binArray[index] = string.charAt(1);
                    // Add the right child to the array
                    index = (pos * 2) + 2;
                    binArray[index] = string.charAt(2);
                    counter++;
                }
            }
            pos++;
            // If all nodes have been added to the array, break the loop
            if (counter == splitInput.length) {
                break;
            }
        }
        // Print the position of 'A' in the array
        for (int i = 0; i < binArray.length; i++) {
            if (binArray[i].equals('A')) {
                System.out.println("Position of A: " + (i));
            }
        }
    }
}
