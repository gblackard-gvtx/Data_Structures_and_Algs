package com.gblackard.frogmazegb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/**
 * Geoffery Blackard
 * COSC 2436
 * Program Set 3
 * Resources
 * "Depth First Traversal ( DFS ) on a 2D Array." GeeksforGeeks. 
 * Last modified July 27, 2021. https://www.geeksforgeeks.org/depth-first-traversal-dfs-on-a-2d-array/.
 * "Rat in a Maze." GeeksforGeeks. Last modified January 12, 2023. https://www.geeksforgeeks.org/rat-in-a-maze/#.
 * "Store the Path for Maze Solving Using Breadth First Search Algorithm." 
* Stack Overflow. Accessed September 23, 2023. https://stackoverflow.com/questions/64240760/store-the-path-for-maze-solving-using-breadth-first-search-algorithm.
 */
public class FrogMazeGB {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.print("Enter filename: ");
                String fileName = scanner.nextLine();

                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    int numMazes = Integer.parseInt(reader.readLine().trim());
                    if (numMazes < 2 || numMazes > 50) {
                        System.out.println("The number of surveys on the file must be ge 2 or le 50");
                        numMazes = 0;
                    }

                    for (int surveyNum = 1; surveyNum <= numMazes; surveyNum++) {
                        String[] gridSize = reader.readLine().trim().split(" ");
                        int numRow = Integer.parseInt(gridSize[0]);
                        int numCol = Integer.parseInt(gridSize[1]);
                        if (numRow < 3 && numRow > 8 || numCol < 3 && numCol > 8) {
                            System.out.println("The Row and Column variables in the input file do not meet the program specifications. \n Please examine the file and try again.");
                        } else {
                            char[][] maze = new char[numRow][numCol];
                            int startRow = -1;
                            int startCol = -1;
                            // Read the section grid
                            for (int i = 0; i < numRow; i++) {
                                String line = reader.readLine().trim();
                                maze[i] = line.toCharArray();
                                if (line.contains("$")) {
                                    startRow = i;
                                    startCol = line.indexOf("$");

                                }
                            }
                            int minVisits = mazeRunner(maze, numRow, numCol, startRow, startCol);
                            System.out.println("Maze " + surveyNum + ": " + minVisits + " cells");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading the file: " + e.getMessage());
                }

                System.out.print("Run again (Y/N): ");
            } while (scanner.nextLine().equalsIgnoreCase("Y"));

            System.out.println("Program terminated.");
        }
    }

    private static int mazeRunner(char[][] maze, int numRows, int numCols, int startRow, int startCol) {
        boolean[][][] visited = new boolean[numRows][numCols][5]; // Additional dimension for frog jump count
        return dfs(maze, numRows, numCols, startRow, startCol, 0, visited);
    }

    private static int dfs(char[][] maze, int numRows, int numCols, int row, int col, int jumps, boolean[][][] visited) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols || maze[row][col] == '*' || visited[row][col][jumps]) {
            return -1;
        }

        if (maze[row][col] == '@') {
            return 0;
        }

        visited[row][col][jumps] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int minVisits = -1;

        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];

            int visits = dfs(maze, numRows, numCols, newRow, newCol, jumps, visited);

            if (visits != -1) {
                if (Character.isDigit(maze[row][col])) {
                    int jumpDistance = Character.getNumericValue(maze[row][col]);
                    if (jumps < jumpDistance) {
                        jumps++;
                    }
                }
                if (minVisits == -1 || visits < minVisits) {
                    minVisits = visits;
                }
            }
        }

        visited[row][col][jumps] = false;
        return minVisits == -1 ? -1 : minVisits + 1;
    }
}
