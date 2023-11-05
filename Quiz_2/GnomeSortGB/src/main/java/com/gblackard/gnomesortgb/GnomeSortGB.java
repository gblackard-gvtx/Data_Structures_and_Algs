/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.gblackard.gnomesortgb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Geoffery Blackard
 * COSC 2436
 * EC Quiz 2
 *  
 */
public class GnomeSortGB {

    public static void main(String[] args) throws FileNotFoundException {
        // Using scanner to read in user input
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.print("Enter the file name (including extension): ");
                String fileName = scanner.nextLine();
                File file = new File(fileName);
                Scanner fileScanner = new Scanner(file);

                int numGardens = 1;
                while (true) {
                    int n = fileScanner.nextInt();
                    if (n == 0) {
                        break;
                    } if (n > 1500 || n < 0) {
                        System.out.println("The number of pots is greater than 1500 or less then 0");
                        break;
                    }

                    int[] pots = new int[n];
                    for (int i = 0; i < n; i++) {
                        pots[i] = fileScanner.nextInt();
                    }

                    System.out.println("Garden " + numGardens + ":");
                    gnomeSort(pots);
                    System.out.println();
                    numGardens++;

                }

                System.out.print("Do you want to run the program again? (yes/no): ");
            } while (scanner.nextLine().equalsIgnoreCase("yes"));

            System.out.println("Program terminated.");
        }
    }

    public static void gnomeSort(int[] pots) {
        int n = pots.length;
        int index = 0;

        while (index < n) {
            if (index == 0 || pots[index] >= pots[index - 1]) {
                index++;
            } else {
                // Swap the pots
                int temp = pots[index];
                pots[index] = pots[index - 1];
                pots[index - 1] = temp;
                index--;

                // Print the swap
                System.out.println("The gnome swaps the values at positions " + (index + 1) + " and " + (index + 2) + ".");
            }
        }

        System.out.println("Sorted!");
    }
}
