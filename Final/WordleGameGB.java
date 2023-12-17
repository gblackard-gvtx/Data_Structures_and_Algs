import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * Geoffery Blackard COSC 2436
 */
class Game {

    // Declare an Integer variable to keep track of the number of tries
    Integer trys;

    // Declare a String variable to store the secret word
    String secretWord;

    // Declare a String variable to store the unused characters
    String unusedChars;

    // Define a constructor for the Game class
    public Game(String secret, Integer trys, String alphabet, String word) {
        // Initialize the unusedChars variable with the value of the alphabet parameter
        this.unusedChars = alphabet;

        // Initialize the secretWord variable with the value of the secret parameter
        this.secretWord = secret;

        // Initialize the trys variable with the value of the trys parameter
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

    // Define a static method named proccessGuess
    public static Integer proccessGuess(ArrayList<String> wordList, Game game, String guess, Integer trys) {
        // Check if the guessed word is in the word list
        if (!wordList.contains(guess)) {
            // If the guessed word is not in the word list, print a message and return the current number of tries
            System.out.println(guess + "  is not in the wordfile");
            return trys;
        } // Check if the guessed word is the secret word
        else if (guess.equals(game.secretWord)) {
            // If the guessed word is the secret word, print a winning message and return 6
            System.out.println("You won in " + (trys + 1) + " guesses.");
            return 6;
        } // If the guessed word is in the word list but is not the secret word
        else {
            // Initialize a StringBuilder to build the feedback string
            StringBuilder feedback = new StringBuilder();
            // Iterate over each character in the guessed word
            for (int j = 0; j < guess.length(); j++) {
                char c = guess.charAt(j);
                // Remove the current character from the unused characters string
                game.unusedChars = game.unusedChars.replace(String.valueOf(c), "");
                // Check if the current character matches the character in the same position in the secret word
                if (c == game.secretWord.charAt(j)) {
                    // If it matches, append the uppercase version of the character to the feedback string
                    feedback.append(Character.toUpperCase(c));
                } // Check if the current character is in the secret word
                else if (game.secretWord.contains(String.valueOf(c))) {
                    // If it is in the secret word but in a different position, append an underscore to the feedback string
                    feedback.append('_');
                } // If the current character is not in the secret word
                else {
                    // Append an asterisk to the feedback string
                    feedback.append('*');
                }
            }
            // Print the feedback string and the unused characters string
            System.out.println(feedback + " " + game.unusedChars);
            // Increment the number of tries
            trys++;
            // Update the number of tries in the game object
            game.trys = trys;
            // If the player has made 6 tries without guessing the secret word
            if (game.trys == 6) {
                // Print a losing message
                System.out.println("Secret word was:" + game.secretWord);
            }
            // Return the number of tries
            return trys;
        }
    }

}