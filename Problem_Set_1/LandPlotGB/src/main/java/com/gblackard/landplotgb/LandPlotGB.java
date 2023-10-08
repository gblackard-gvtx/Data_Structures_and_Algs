package com.gblackard.landplotgb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/**
 * Geoffery Blackard
 * COSC 2436
 * Program Set 3
 * Resources
 * "Depth First Traversal ( DFS ) on a 2D Array." GeeksforGeeks. 
 * Last modified July 27, 2021. https://www.geeksforgeeks.org/depth-first-traversal-dfs-on-a-2d-array/.
 */
public class LandPlotGB {

    public static void main(String[] args) {
        // Using scanner to read in user input
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.print("Enter the file name (including extension): ");
                String fileName = scanner.nextLine();

                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int numSurveys = Integer.parseInt(reader.readLine().trim());
                    if (numSurveys<2||numSurveys>50) {
                        System.out.println("The number of surveys on the file must be ge 2 or le 50");
                        numSurveys = 0;
                    }

            for (int surveyNum = 1; surveyNum <= numSurveys; surveyNum++) {
                char[][] grid = new char[8][16];

                // Read the section grid
                for (int i = 0; i < 8; i++) {
                    String line = reader.readLine().trim();
                    grid[i] = line.toCharArray();
                }

                // Skip the blank line
                reader.readLine();

                List<Integer> unclaimedAcres = calculateUnclaimedAcres(grid);
                Collections.sort(unclaimedAcres, Collections.reverseOrder());

                System.out.print("Survey " + surveyNum + ": ");
                for (int i = 0; i < unclaimedAcres.size(); i++) {
                    System.out.print(unclaimedAcres.get(i));
                    if (i < unclaimedAcres.size() - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
                    System.out.println("Unable to load file. Check the file name and try again.");
        }

                System.out.print("Do you want to run the program again? (yes/no): ");
            } while (scanner.nextLine().equalsIgnoreCase("yes"));

            System.out.println("Program terminated.");
        }
    }
     private static List<Integer> calculateUnclaimedAcres(char[][] grid) {
        List<Integer> unclaimedAcres = new ArrayList<>();
        int currentOwner = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++) {
                if (grid[i][j] == '*') {
                    int size = dfs(grid, i, j, currentOwner);
                    unclaimedAcres.add(size*5);
                    currentOwner++;
                }
            }
        }

        return unclaimedAcres;
    }
     // this method preforms a depth first search recursivly. I didnt see this untill later in the book. I have used this a work for large datasets. 
    private static int dfs(char[][] grid, int x, int y, int currentOwner) {
        //It checks if the current (x, y) coordinates are out of bounds
        //or if the plot at (x, y) is not an unclaimed plot. 
        //If any of these conditions are met, the function returns 0.
        if (x < 0 || x >= 8 || y < 0 || y >= 16 || grid[x][y] != '*') {
            return 0;
        }
        //Changing the * to and A+n
        grid[x][y] = (char) ('A' + currentOwner);
        int size = 1;

        size += dfs(grid, x - 1, y, currentOwner); //This step explores the left neighbor plot
        size += dfs(grid, x + 1, y, currentOwner);//This explores the right neighbor plot
        size += dfs(grid, x, y - 1, currentOwner);//This explores the upper neighbor plot
        size += dfs(grid, x, y + 1, currentOwner);//This explores the lower neighbor plot

        return size;
    }
}
