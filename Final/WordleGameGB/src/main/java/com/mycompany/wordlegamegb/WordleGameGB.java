/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.wordlegamegb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author gblac
 */
class Game {

    Integer trys = 0;
    String secretWord;
    String unusedChars;

    public Game(String secret, Integer trys, String aplphabet, String word) {
        this.unusedChars = aplphabet;
        this.secretWord = secret;
        this.trys = trys;

    }

    public Game() {
    }
;

}

public class WordleGameGB {

    private static final int MAX_GUESSES = 6;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> words = new ArrayList();
        // Create a Scanner to read input from the user
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to Wordle!");
            // Prompt the user to enter the file name
            System.out.println("Enter the file name:");
            String fileName = scanner.nextLine();
            // Create a File object for the file
            File file = new File(fileName);
            // Create a Scanner to read from the file
            Scanner fs = new Scanner(file);
            while (fs.hasNext()) {
                words.add(fs.nextLine());
            }
            // Create a Random object
            Random rand = new Random();

            // Get a random index in the range of array length
            int randomIndex = rand.nextInt(words.size());

            // While the user wants to continue
            while (true) {
                Game game = new Game();
                game.unusedChars = ALPHABET;
                int trys = game.trys;
                // Prompt the user to enter the actor's name
                System.out.println("There are two modes for this program: demo and game.");
                System.out.println("Enter [D/d]-demo mode or [G/g]-game mode:");
                String gameMode = scanner.nextLine();
                if (gameMode.equalsIgnoreCase("d")) {
                    System.out.println("Enter secret word:");
                    game.secretWord = scanner.nextLine();

                    while (trys < MAX_GUESSES) {
                        System.out.println("Guess #" + (trys + 1) + ":");
                        String guess = scanner.nextLine();
                        int attempts = proccessGuess(words, game, guess, trys);
                        trys = attempts;

                    }

                } else {
                    String word = words.get(randomIndex);
                    game.secretWord = word;
                    while (trys < MAX_GUESSES) {
                        System.out.println("Guess #" + (trys + 1) + ":");
                        String guess = scanner.nextLine();
                        int attempts = proccessGuess(words, game, guess, trys);
                        trys = attempts;

                    }

                }

                // Ask the user if they want to run the program again
                System.out.println("Do you want to run the program again? (yes/no)");
                String response = scanner.nextLine();
                // If the user does not want to run the program again, break the loop
                if (!response.equalsIgnoreCase("yes")) {
                    break;
                }
            }

        }
    }

    public static Integer proccessGuess(ArrayList<String> wordList, Game game, String guess, Integer trys) {
        if (!wordList.contains(guess)) {
            System.out.println(guess + "  is not in the wordfile");
            return trys;
        } else if (guess.equals(game.secretWord)) {
            System.out.println("You won in " + (trys + 1) + " guesses.");
            return 6;
        } else {
            StringBuilder feedback = new StringBuilder();
            for (int j = 0; j < guess.length(); j++) {
                char c = guess.charAt(j);
                game.unusedChars = game.unusedChars.replace(String.valueOf(c), "");
                if (c == game.secretWord.charAt(j)) {
                    feedback.append(Character.toUpperCase(c));
                } else if (game.secretWord.contains(String.valueOf(c))) {
                    feedback.append('_');
                } else {
                    feedback.append('*');
                }
            }
            System.out.println(feedback + " " + game.unusedChars);
            trys++;
            game.trys = trys;
            if (game.trys == 6) {
                System.out.println("Secret word was:" + game.secretWord);
            }
            return trys;
        }
    }

}
