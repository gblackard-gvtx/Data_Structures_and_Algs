/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.gblackard.checkpalingb;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

/**
 * Geoffery Blackard
 * COSC 2436
 * Program Set 3
 * Resources
 * "Find All Distinct Palindromic Sub-strings of a Given String." GeeksforGeeks. Last modified October 21, 2023. https://www.geeksforgeeks.org/find-number-distinct-palindromic-sub-strings-given-string/.
 *"In Search of Palindromes." Khoury College of Computer Sciences. Accessed October 31, 2023. https://www.khoury.northeastern.edu/home/sbratus/com1100/Palindromes.html#:~:text=To%20check%20whether%20a%20sentence,a%20string%20of%20lowercase%20letters.
 *"Parsing a String That Represents a Chemical Reaction and Verify if the Reaction is Possible." Stack Overflow. Accessed October 31, 2023. https://stackoverflow.com/questions/41129245/parsing-a-string-that-represents-a-chemical-reaction-and-verify-if-the-reaction.
 *"Remove All the Palindromic Words from the Given Sentence." GeeksforGeeks. Last modified March 3, 2023. https://www.geeksforgeeks.org/remove-palindromic-words-given-sentence/.
 *"Sentence Palindrome (Palindrome After Removing Spaces, Dots, .. Etc)." GeeksforGeeks. Last modified September 15, 2023. https://www.geeksforgeeks.org/sentence-palindrome-palindrome-removing-spaces-dots-etc/.
 */
public class CheckPalinGB {

    

    public static void main(String[] args) {
        
        // declare variables
        Stack<String> stackPal = new Stack<>();
        int longestLength;
        String inputPalindrome;
        //instansiate scanner class
        Scanner input = new Scanner(System.in);
        do{
        // get input from the user
        System.out.print("Enter a palindrome: ");

        inputPalindrome = input.nextLine();

        
        // Get the list of palindromes
        stackPal = findAllPalindromeSubstrings(inputPalindrome.toLowerCase());

        //get the longest length of palindrome
        longestLength = findLongest(stackPal);

        //get the list of palindromes that are equal to length of longestLength
        Stack<Integer> indexes = getLongestPalindromes(
                inputPalindrome.toLowerCase(), longestLength, stackPal);

        System.out.print("\n" + inputPalindrome);

        if (!indexes.isEmpty()) {

            System.out.println();

            Iterator<Integer> palinIterator = indexes.iterator();

            // loop until there are no more elements in the stack
            // get the index values of each palindrome
            // loop till index value present in indexes
            while (palinIterator.hasNext()) {
                int start = palinIterator.next();
                int end = palinIterator.next();
                for (int k = 0; k < start; k++) {

                    System.out.print(" ");

                }
                System.out.print("[");
                for (int j = 1; j < end - 1; j++) {
                    System.out.print(" ");
                }
                System.out.println("]");

            }

        }
        System.out.print("Run again (Y/N): ");
        }while(input.nextLine().equalsIgnoreCase("Y"));

    }
    //method to create the list of palindromes from the given input and returns a stack
    public static Stack<String> findPalindromesInSubString(String input,
            int start, int end, Stack<String> stackPal) {

        // loop until start and end condition is true.loop until valid character is found from the start and end index
        while (start >= 0 && end < input.length()) {
            while (!Character.isAlphabetic(input.charAt(start)) && start >= 0) {
                start--;
            }
            while (!Character.isAlphabetic(input.charAt(end))
                    && end < input.length() - 1) {
                end++;
            }
            if (start >= 0 && end < input.length()) {
                if (input.charAt(start) != input.charAt(end)) {

                    break;

                } // if true, get the substring, push the substring into the stack and inc/dec the start and the end indexes
                else {
                    String palin = input.substring(start, end + 1);
                    stackPal.push(palin);
                    --start;
                    ++end;
                }
            }
        }
        return stackPal;

    }

// findAllPalindromeSubstrings() method to return main stack containing
// the list of palindromes
    public static Stack<String> findAllPalindromeSubstrings(String userInput) {
        Stack<String> stackPal = new Stack<>();

        // if the length of the input String is 2,Check if its a palindrome
        if (userInput.length() == 2) {
            if (userInput.charAt(0) == userInput.charAt(1)) {
                stackPal.push(userInput);

            }
        } else {

            // loop through all the characters in the string userInput
            for (int i = 0; i < userInput.length(); ++i) {

                stackPal = findPalindromesInSubString(userInput, i - 1, i + 1,
                        stackPal);
            }
        }
        return stackPal;

    }
// return the longest length of palindromepresent in the Stack

    public static int findLongest(Stack<String> palindromeList) {
        int max = 0;
            // user iterator to iterate through the Stack
           // loop until there are no more elements in the stack

        for (String val : palindromeList) {
            // get the stack element
            // remove the characters that are not alphabets
            // and get the length
            int len = val.replaceAll("[^A-Za-z]", "").length();
            // if greater than max, update max
            if (len > max) {
                max = len;
            }
        }
        return max;

    }

    public static Stack<Integer> getLongestPalindromes(String originalStr,
            int longestLength, Stack<String> stackPal) {

        // the starting indexes are stored in the ArrayList
        Stack<Integer> indexes = new Stack<>();

            // loop through each Stack value
        for (String palVal : stackPal) {

            // get the index of the palVal from the original
            int index = originalStr.indexOf(palVal);

            // remove all other special characters from the palVal
            int palLength = palVal.replaceAll("[^A-Za-z]", "").length();

            // check if palLength is equal to longestLength
            if (palLength == longestLength) {
                indexes.push(index);
                indexes.push(palVal.length());
            }
        }
        return indexes;

    }

}
