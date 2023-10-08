
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Geoffery Blackard 
 * COSC 2436 
 * Extra Credit Quiz 1 
 * "Find Four Elements That Sum
 * to a Given Value (4Sum) | Set 2." GeeksforGeeks. Last modified September 11,
 * 2023.
 * https://www.geeksforgeeks.org/find-four-elements-that-sum-to-a-given-value-set-2/#.
 *
 *
 * "Four Fours." Wikipedia, the Free Encyclopedia. Last modified August 28,
 * 2023. https://en.wikipedia.org/wiki/Four_fours.
 *
 *
 * "Guidance on Algorithmic Thinking (4 Fours Equation)." Stack Overflow.
 * Accessed September 30, 2023.
 * https://stackoverflow.com/questions/30594502/guidance-on-algorithmic-thinking-4-fours-equation.
 *
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
