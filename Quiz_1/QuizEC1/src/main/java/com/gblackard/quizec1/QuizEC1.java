package com.gblackard.quizec1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Adams Laptop
 */
public class QuizEC1 {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter an integer: ");
                int target = scanner.nextInt();

                if (target < 0) {
                    System.out.println("Input must be a positive integer.");
                    continue;
                }

                String equation = combo(target, 4);
                System.out.println(equation);

                System.out.print("Run again (Y/N): ");
                String choice = scanner.next();
                if (!choice.equalsIgnoreCase("Y")) {
                    break;
                }
            }
        }
    }

    public static String combo(int target, int num) {
        Map<Integer, String>[] solution = new HashMap[target + 1];
        solution[1] = new HashMap();
        solution[1].put(4, "4");
        for (int i = 2; i <= target; i++) {
            solution[i] = new HashMap<>();
            for (int j = 1; j < i; j++) {
                // Iterate over keys
                for (Integer key : solution[j].keySet()) {
                    for (Integer key2 : solution[i - j].keySet()) {
                        solution[i].put(key + key2, "(" + solution[j].get(key) + " + " + solution[i - j].get(key2) + ")");
                        solution[i].put(key - key2, "(" + solution[j].get(key) + " - " + solution[i - j].get(key2) + ")");
                        solution[i].put(key * key2, "(" + solution[j].get(key) + " * " + solution[i - j].get(key2) + ")");
                        if (key2 != 0) {
                            solution[i].put(key / key2, "(" + solution[j].get(key) + " / " + solution[i - j].get(key2) + ")");
                        }

                    }
                }
            }
            // Special case: 4 repeated four times
            solution[i].put(4 * i, "4");
        }
        if (solution[target].containsKey(4)) {
            return solution[target].get(4);
        } else {
            return "No Solution";
        }

    }
}
