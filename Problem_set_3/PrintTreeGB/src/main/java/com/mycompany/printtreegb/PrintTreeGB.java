/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.printtreegb;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Geoffery Blackard COSC 2436 Program Set 3 "Coding a Basic Pretty Printer for
 * Trees in Java." Stack Overflow. Accessed November 29, 2023.
 * https://stackoverflow.com/questions/8964279/coding-a-basic-pretty-printer-for-trees-in-java.
 * "Find the Maximum Depth or Height of Given Binary Tree." GeeksforGeeks. Last
 * modified October 3, 2023.
 * https://www.geeksforgeeks.org/find-the-maximum-depth-or-height-of-a-tree/.
 * "Java : How Do I Implement a Generic Binary Search Tree?" Stack Overflow.
 * Accessed November 27, 2023.
 * https://stackoverflow.com/questions/11263244/java-how-do-i-implement-a-generic-binary-search-tree.
 */
// Node class for the Binary Search Tree
class Node<T extends Comparable<T>> {

    T value; // Value of the node
    Node<T> left; // Left child of the node
    Node<T> right; // Right child of the node

    // Constructor for the Node class
    Node(T value) {
        this.value = value;
    }
}

// Binary Search Tree class
class BinarySearchTree<T extends Comparable<T>> {

    Node<T> root; // Root of the tree

    // Method to insert a value into the tree
    void insert(T value) {
        root = insertRec(root, value);
    }

    // Recursive method to insert a value into the tree
    Node<T> insertRec(Node<T> root, T value) {
        // If the tree is empty, create a new node
        if (root == null) {
            root = new Node<>(value);
            return root;
        }

        // If the value is less than or equal to the root's value, insert it into the left subtree
        if (value.compareTo(root.value) <= 0) {
            root.left = insertRec(root.left, value);
        } else { // Otherwise, insert it into the right subtree
            root.right = insertRec(root.right, value);
        }

        return root;
    }

    // Method to search for a value in the tree
    boolean search(T value) {
        return searchRec(root, value) != null;
    }

    // Recursive method to search for a value in the tree
    Node<T> searchRec(Node<T> root, T value) {
        // If the tree is empty or the root's value is the value we're searching for, return the root
        if (root == null || root.value.equals(value)) {
            return root;
        }

        // If the value is less than the root's value, search the left subtree
        if (value.compareTo(root.value) < 0) {
            return searchRec(root.left, value);
        }

        // Otherwise, search the right subtree
        return searchRec(root.right, value);
    }

    // Method to get the height of the tree
    int getHeight(Node root) {
        if (root == null) {
            return 0;
        } else {
            int leftHeight = getHeight(root.left);
            int rightHeight = getHeight(root.right);

            return 1 + Math.max(leftHeight, rightHeight);
        }
    }

    // Method to clear the tree
    void clear() {
        root = null;
    }
}

public class PrintTreeGB {

    public static void main(String[] args) {

        // Create two Binary Search Trees, one for integers and one for characters
        BinarySearchTree<Integer> intTree = new BinarySearchTree<>();
        BinarySearchTree<Character> charTree = new BinarySearchTree<>();

        // Create a Scanner to read input from the user
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Prompt the user to enter up to 20 integers or a string up to 20 letters long
            System.out.println("Enter up to 20 integers or a string up to 20 letters long: ");
            String input = scanner.nextLine();
            // If the user enters "exit", break the loop and end the program
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            // Split the input into parts
            String[] parts = input.split(" ");
            System.out.println(parts[0]);

            // If the first part of the input is a number, insert the parts into the integer tree
            if (parts[0].matches("\\d+")) {
                for (String part : parts) {
                    intTree.insert(Integer.valueOf(part));
                }
                // Print the integer tree
                printTree(intTree);
                // Clear the integer tree
                intTree.clear();
            } else { // If the first part of the input is not a number, insert the characters into the character tree
                char[] inputChars = input.toCharArray();
                for (char inputChar : inputChars) {
                    charTree.insert(inputChar);
                }
                // Print the character tree
                printTree(charTree);
                // Clear the character tree
                charTree.clear();
            }
            // Ask the user if they want to run the program again
            System.out.println("Run Again? (yes/no)");
            String continueInput = scanner.nextLine().trim();
            // If the user enters "no", break the loop and end the program
            if (continueInput.equalsIgnoreCase("no")) {
                break;
            }
        }
    }
// Maximum width for the output
    static final int MAX_WIDTH = 80;

// Method to print the Binary Search Tree
    static void printTree(BinarySearchTree<?> tree) {
        // Get the height of the tree
        int height = tree.getHeight(tree.root);
        // Create a 2D array to store the output
        char[][] output = new char[height * 2][MAX_WIDTH];
        // Fill the output array with spaces
        for (char[] row : output) {
            Arrays.fill(row, ' ');
        }

        // Populate the output array with the tree's nodes
        populateOutput(tree.root, output, 0, MAX_WIDTH / 2, MAX_WIDTH / 4);

        // Print the output array
        for (char[] row : output) {
            System.out.println(row);
        }
    }

// Method to populate the output array with the tree's nodes
    static void populateOutput(Node<?> node, char[][] output, int level, int pos, int offset) {
        // If the node is null or the position is out of bounds, return
        if (node == null || pos < 0 || pos >= MAX_WIDTH) {
            return;
        }

        // Convert the node's value to a string
        String valueStr = node.value.toString();
        // Add the node's value to the output array
        for (int i = 0; i < valueStr.length(); i++) {
            if (pos + i < MAX_WIDTH) {
                output[level * 2][pos + i] = valueStr.charAt(i);
            }
        }
        // If the node has a left child, add a '/' to the output array and recursively populate the output with the left child
        if (node.left != null && pos - offset / 2 >= 0) {
            output[level * 2 + 1][pos - offset / 2] = '/';
            populateOutput(node.left, output, level + 1, pos - offset, offset / 2);
        }
        // If the node has a right child, add a '\' to the output array and recursively populate the output with the right child
        if (node.right != null) {
            output[level * 2 + 1][pos + offset / 2] = '\\';
            populateOutput(node.right, output, level + 1, pos + offset, offset / 2);
        }
    }

}
