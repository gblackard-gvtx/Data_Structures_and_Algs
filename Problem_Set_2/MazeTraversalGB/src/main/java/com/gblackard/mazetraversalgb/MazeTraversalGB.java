/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.gblackard.mazetraversalgb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Adams Laptop
 */
class Coordinate {

    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

public class MazeTraversalGB {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Enter filename: ");
            String fileName = scanner.nextLine();
            solveMaze(fileName);

        } while (scanner.nextLine().equalsIgnoreCase("Y"));
    }

    public static String solveMaze(String fileName) {
        try {
            //Reading in case information
            Scanner scanner = new Scanner(new File(fileName));
            int numTestCases = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            for (int testCase = 0; testCase < numTestCases; testCase++) {
                int rows = scanner.nextInt();
                int cols = scanner.nextInt();
                char rule = scanner.next().charAt(0);
                scanner.nextLine(); // Consume the newline

                int startRow = scanner.nextInt();
                int startCol = scanner.nextInt();
                int finishRow = scanner.nextInt();
                int finishCol = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                char[][] maze = new char[rows][cols];
                for (int row = 0; row < rows; row++) {
                    String rowStr = scanner.nextLine();
                    String[] tokens = rowStr.split(" ");
                    for (int col = 0; col < cols; col++) {
                        maze[row][col] = tokens[col].charAt(0);
                    }
                }

                if (rule == 'L') {
                    System.out.println("Case " + (testCase + 1) + ": Left");
                    leftHandRule(maze, startRow, startCol, finishRow, finishCol);
                } else {
                    System.out.println("Case " + (testCase + 1) + ": Right");
                    rightHandRule(maze, startRow, startCol, finishRow, finishCol);
                }

            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return null;
    }

    public static void leftHandRule(char[][] maze, int startRow, int startCol, int finishRow, int finishCol) {

        int numRows = maze.length;
        System.out.println("Maze Rows " + numRows);
        int numCols = maze[0].length;
        System.out.println("Maze Cols " + numCols);
        int[][] visitedStatus = new int[numRows][numCols];
        int[] dx = {0, 1, 0, -1}; // Right, Down, Left, Up
        int[] dy = {1, 0, -1, 0};
        Stack<Coordinate> coords = new Stack<>();
        // Create a 2D array to track visited positions
        boolean[][] visited = new boolean[numRows][numCols];
        int currentRow = startRow;
        int currentCol = startCol;
        int currentDirection = 0;
        int totalSteps = 0;
        while (true) {
            //add the starting position
            coords.push(new Coordinate(currentRow, currentCol));
            boolean moved = false;
            boolean foundNewDirection = false;
            for (int i = 0; i < 4; i++) {
                int nextDirection = (currentDirection + i) % 4;
                int nextRow = currentRow + dx[nextDirection];
                int nextCol = currentCol + dy[nextDirection];
                //System.out.println("NextRow:"+nextRow+" NextCols: "+nextCol);

                if (nextRow == finishRow && nextCol == finishCol) {
                    // Maze solved
                    coords.push(new Coordinate(currentRow, currentCol));
                    totalSteps++;
                    printPositions(coords, totalSteps);
                    return;
                }

                if (nextRow >= 0 && nextRow < numRows && nextCol >= 0 && nextCol < numCols
                        && !visited[nextRow][nextCol] && visitedStatus[nextRow][nextCol] < 3) {
                    // Check if the cell is not blocked from three directions
                    currentRow = nextRow;
                    currentCol = nextCol;
                    currentDirection = nextDirection;
                    moved = true;
                    foundNewDirection = true;
                    coords.push(new Coordinate(currentRow, currentCol));
                    totalSteps++;

                    // Mark the position as visited
                    visited[currentRow][currentCol] = true;

                    if (totalSteps % 10 == 0) {
                        printPositions(coords, totalSteps);
                    }
                    break;
                }
            }
            if (!foundNewDirection) {
                // Dead-end or loop detected; backtrack
                if (coords.isEmpty()) {
                    System.out.println("No solution found.");
                    return;
                }
                // If the current cell is blocked from three directions, mark it as 'X'
                if (visitedStatus[currentRow][currentCol] == 3) {
                    maze[currentRow][currentCol] = 'X';
                }
                if (!coords.isEmpty()) {
                    coords.pop();
                    totalSteps--;
                    if (!coords.isEmpty()) {
                        coords.pop();
                    }
                }
            }

            if (currentRow == finishRow && currentCol == finishCol) {
                // Maze solved
                System.out.println("Maze solved!");
                return;
            }
        }

    }

    public static void rightHandRule(char[][] maze, int startRow, int startCol, int finishRow, int finishCol) {
        Stack<Coordinate> coords = new Stack();
        int[] dx = {0, 1, 0, -1}; // Right, Down, Left, Up
        int[] dy = {1, 0, -1, 0};
        char[] directions = {'R', 'D', 'L', 'U'}; // Right, Down, Left, Up
    }

    static void printPositions(Stack<Coordinate> stack, int totalSteps) {
        int count = 0;
        while (!stack.isEmpty()) {
            Coordinate coords = stack.pop();
            System.out.print(coords.toString() + " ");
            count++;

            if (count == 10) {
                System.out.println();
                count = 0;
            }
        }
        System.out.println(); // Ensure a new line after printing
    }

}
