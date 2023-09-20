package com.gblackard.landplotgb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author Adams Laptop
 */
public class LandPlotGB {

    public static void main(String[] args) {
        // Using scanner to read in user input
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.print("Enter the file name (including extension): ");
                String fileName = scanner.nextLine();

                try {
                    String surveys = surveyReport(fileName);
                    System.out.println("Survey " + surveys);
                } catch (IOException e) {
                    System.err.println("Error reading the file: " + e.getMessage());
                }

                System.out.print("Do you want to run the program again? (yes/no): ");
            } while (scanner.nextLine().equalsIgnoreCase("yes"));

            System.out.println("Program terminated.");
        }
    }

    public static String surveyReport(String fileName) throws IOException {
        // Instantiate readers
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder surveyBuilder = new StringBuilder();
        int numSurveys = 0;
        String strLine;
        ArrayList<String> surveyStringArray = new ArrayList<>();
        while ((strLine = reader.readLine()) != null) {
            if (strLine.toCharArray().length == 1) {
                numSurveys = Integer.parseInt(strLine);
            }
            for (int i = 0; i < numSurveys; i++) {
                if (!strLine.isEmpty()) {
                    if (strLine.toCharArray().length != 1) {
                        surveyBuilder.append(strLine).append("\n");
                    }

                } else {
                    surveyStringArray.add(surveyBuilder.toString().trim());
                }

            }
        }
        //Time to add the strings to 2d arrays
        for (String surveyString : surveyStringArray) {
            int surveyRows = surveyString.split("\n").length;
            int surveyCols = surveyString.split("\n")[0].length();

            char[][] surveyArray = new char[surveyRows][surveyCols];

            // add shape to multidominsional array
            for (int i = 0; i < surveyRows; i++) {
                for (int j = 0; j < surveyCols; j++) {
                    surveyArray[i][j] = surveyString.charAt(i * (surveyCols + 1) + j);
                }
            }
            List<Coordinate> unclaimedPlots = new ArrayList();
            
            
            for (int i = 0; i < surveyRows; i++) {
                for (int j = 0; j < surveyCols; j++) {
                    if (surveyArray[i][j] == '*') {
                        unclaimedPlots.add(new Coordinate(i, j));
                        
                    } else {
                        
                    }
                    
                }
                
            }
            
            
            
             
        }

        return "" + numSurveys;
    }
}
