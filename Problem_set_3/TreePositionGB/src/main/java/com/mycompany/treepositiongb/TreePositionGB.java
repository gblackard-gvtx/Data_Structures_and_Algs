/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.treepositiongb;

import java.util.Scanner;

/**
 *
 * @author gblac
 */

class TreeNode {
    char data;
    TreeNode left, right;

    public TreeNode(char data) {
        this.data = data;
        this.left = this.right = null;
    }
}
    
public class TreePositionGB {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter binary tree triple (parent left right):");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
                Character root = getRoot(input);
                findAPostition(root, input);
                

            System.out.println("Do you want to enter another triple? (yes/no)");
            String continueInput = scanner.nextLine().trim();
            if (continueInput.equalsIgnoreCase("no")) {
                break;
            }
        }
    }
    public static Character getRoot(String input){
    String[] splitInput = input.split(" ");
    Integer[] countArray = new Integer[splitInput.length];
        int count = 0;
        for (int i = 0; i < splitInput.length; i++) {
        Character root = splitInput[i].charAt(0);
            for (String string : splitInput) {
                if (string.contains(""+root)) {
                    count++;
                    
                    
                }
                
            }
            countArray[i] = count;
            count = 0;
        }
        for (int i = 0; i < countArray.length; i++) {
            if (countArray[i] == 1) {
                
              return splitInput[i].charAt(0);    
            }
        }
        
        return null;
    }
    public static void findAPostition(Character root, String input){
       String[] splitInput = input.split(" ");
       int numberOfNodes= (splitInput.length*3);
       int height = (int) (Math.ceil(Math.log(numberOfNodes + 1) / Math.log(2)) - 1);
       int size = (int) Math.pow(2, height + 1) - 1;
       Character[] binArray = new Character[(size)];
       // fill array will dashes
        for (int i = 0; i < binArray.length; i++) {
            binArray[i]= '-';
        }
        binArray[0] = root;
        int pos = 0;
	int counter = 0;

        
        while (true) {
            System.out.println(binArray[pos]);
            for (String string : splitInput) {
                if (binArray[pos].equals(string.charAt(0))) {
                    //add left to array 
                    int index = (pos*2)+1;
                    binArray[index] = string.charAt(1);
                    //System.out.println(binArray[index]);
                    //set right
                    index = (pos*2)+2;
                    binArray[index] = string.charAt(2);
                    //System.out.println(binArray[index]);
                    counter++;
                }
                
            }
            pos++;
            if (counter == splitInput.length) {
                break;
            }
            
        }
    }
}
