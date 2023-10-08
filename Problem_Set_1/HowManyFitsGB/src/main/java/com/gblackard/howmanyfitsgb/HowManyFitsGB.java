package com.gblackard.howmanyfitsgb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Geoffery Blackard
 * COSC 2436
 * Program Set 1
 * Resources
"Complete Guide On 2D Array (Matrix) Rotations - Data Structure and Algorithms Tutorial - 
* GeeksforGeeks." GeeksforGeeks. Last modified August 22, 2023.
* https://www.geeksforgeeks.org/complete-guide-on-2d-array-matrix-rotations/#.
 */
public class HowManyFitsGB {

    public static void main(String[] args) {
        // Using scanner to read in user input
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.print("Enter the file name (including extension): ");
                String fileName = scanner.nextLine();

                try {
                    int ways = countWaysToFitShapeInGrid(fileName);
                    System.out.println("Number of ways the shape can fit into the grid: " + ways);
                } catch (IOException e) {
                    System.err.println("Error reading the file: " + e.getMessage());
                }

                System.out.print("Do you want to run the program again? (yes/no): ");
            } while (scanner.nextLine().equalsIgnoreCase("yes"));

            System.out.println("Program terminated.");
        }
    }

    public static int countWaysToFitShapeInGrid(String fileName) throws IOException {
        int count = 0;
        // Instantiate readers
        try{BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder shapeBuilder = new StringBuilder();
        StringBuilder gridBuilder = new StringBuilder();
        boolean readingShape = true;
        // Read in shape and grid 
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                readingShape = false;
                continue;
            }
            if (readingShape) {
                shapeBuilder.append(line).append("\n");
            } else {
                gridBuilder.append(line).append("\n");
            }
        }
        // clean up string
        String shapeString = shapeBuilder.toString().trim();
        String gridString = gridBuilder.toString().trim();
        // Set size vars
        int shapeRows = shapeString.split("\n").length;
        int shapeCols = shapeString.split("\n")[0].length();

        int gridRows = gridString.split("\n").length;
        int gridCols = gridString.split("\n")[0].length();

        char[][] shapeArray = new char[shapeRows][shapeCols];
        char[][] gridArray = new char[gridRows][gridCols];

        // add shape to multidominsional array
        for (int i = 0; i < shapeRows; i++) {
            for (int j = 0; j < shapeCols; j++) {
                shapeArray[i][j] = shapeString.charAt(i * (shapeCols + 1) + j);
            }
        }
        //  add grid to multidominsinal array
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                gridArray[i][j] = gridString.charAt(i * (gridCols + 1) + j);
            }

        }

        int shapeHeight = shapeArray.length;
        int shapeWidth = shapeArray[0].length;
        int gridHeight = gridArray.length;
        int gridWidth = gridArray[0].length;
        // going to try and use some illimination if the top row 0 matches the grid then we will test for fit. vs testing for full fit each loop
        for (int rotations = 0; rotations < 4; rotations++) {
            char[] inverse = getInverseArray(shapeArray);
            for (int i = 0; i <= gridHeight - shapeHeight; i++) {
                for (int j = 0; j <= gridWidth - shapeWidth; j++) {
                    if (gridArray[i][j] == inverse[0] && gridArray[i][j + 1] == inverse[1] && gridArray[i][j + 2] == inverse[2]) {
                        int matchs = 0;
                        for (int si = 0; si < shapeHeight; si++) {
                            for (int sj = 0; sj < shapeWidth; sj++) {
                                if (shapeArray[si][sj] == '*' && gridArray[i + si][j + sj] != '*') {
                                    matchs++;
                                }
                            }
                        }
                        if (matchs == 5) {
                            count++;
                        }
                    }
                }

            }
            shapeArray = rotateShape90(shapeArray);
        }
        }catch (IOException e) {
            System.out.println("Unable to load file. Check the file name and try again.");
        }
        return count;
    }
    //This rotates the shape 90 degrees
    public static char[][] rotateShape90(char[][] shape) {
        int numRows = shape.length;
        int numCols = shape[0].length;
        char[][] rotatedShape = new char[numCols][numRows];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                rotatedShape[j][numRows - 1 - i] = shape[i][j];
            }
        }
        return rotatedShape;
    }
    // This creates the inverse of the top row
    public static char[] getInverseArray(char[][] shapeArray) {
        char[] inverse = new char[3];
        for (int i = 0; i < 3; i++) {
            if (shapeArray[0][i] == '*') {
                inverse[i] = '-';

            } else {
                inverse[i] = '*';
            }

        }
        return inverse;
    }

}
