import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/**
 *Geoffery Blackard COSC 2436 Program Set 3
 * Just a Moment..." Just a Moment... Accessed December 2, 2023. https://www.baeldung.com/java-binary-tree
 */
public class FamilyGenerationsGB {

    // A map to store the family tree. The key is a child's name and the value is the parent's name.
    static Map<String, String> familyTree = new HashMap<>();

// Function to convert a number to its ordinal form (1st, 2nd, 3rd, etc.)
    static String ordinal(int n) {
        // Special case for numbers between 11 and 13, as they always end with "th"
        if (n >= 11 && n <= 13) {
            return n + "th";
        }
        // For other numbers, determine the suffix based on the last digit
        switch (n % 10) {
            case 1:
                return n + "st";
            case 2:
                return n + "nd";
            case 3:
                return n + "rd";
            default:
                return n + "th";
        }
    }

    public static void main(String[] args) {
        try {
            // Create a scanner to read user input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter filename: ");
            String filename = scanner.nextLine();

            // Open the file with the given filename
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);

            // Read the number of family relationships and queries from the file
            int t = fileScanner.nextInt();
            if (t>100) {
            System.out.print("T parameter in file is greater the 100 Plaease fix the file and run again");
                
            }
            int p = fileScanner.nextInt();
            if (p>1000) {
            System.out.print("P parameter in file is greater the 1000 Plaease fix the file and run again"); 
            }
            fileScanner.nextLine(); // consume newline

            // Create a set to store the names of all children
            Set<String> children = new HashSet<>();
            for (int i = 0; i < t; i++) {
                // Read each line, split it into words, and add the relationships to the family tree
                String[] line = fileScanner.nextLine().split(" ");
                String parent = line[0];
                for (int j = 2; j < line.length; j++) {
                    familyTree.put(line[j], parent);
                    children.add(line[j]);
                }
            }

            // Find the root of the family tree (the person who is not a child of anyone else)
            String root = null;
            for (String person : familyTree.keySet()) {
                if (!children.contains(person)) {
                    root = person;
                    break;
                }
            }

            // Process each query and print the relationship between the two people
            for (int i = 0; i < p; i++) {
                String person1 = fileScanner.next();
                String person2 = fileScanner.next();
                findRelationship(root, person1, person2);
            }

            // Ask the user if they want to run the program again
            System.out.print("Run again (Y/N): ");
            String runAgain = scanner.nextLine();
            if (runAgain.equalsIgnoreCase("Y")) {
                main(args);
            }

        } catch (FileNotFoundException e) {
            // Handle the case where the file is not found
            System.out.println("File not found: " + e.getMessage());
        } catch (NoSuchElementException e) {
            // Handle the case where the scanner runs out of input
            System.out.println("Error reading from scanner: " + e.getMessage());
        }
    }

    static void findRelationship(String root, String person1, String person2) {
        // Create a list of ancestors for person1
        List<String> ancestors1 = new ArrayList<>();
        ancestors1.add(person1);
        // Keep adding the parent of the last person in the list until we reach the root
        while (familyTree.get(ancestors1.get(ancestors1.size() - 1)) != null && !ancestors1.get(ancestors1.size() - 1).equals(root)) {
            ancestors1.add(familyTree.get(ancestors1.get(ancestors1.size() - 1)));
        }

        // Do the same for person2
        List<String> ancestors2 = new ArrayList<>();
        ancestors2.add(person2);
        while (familyTree.get(ancestors2.get(ancestors2.size() - 1)) != null && !ancestors2.get(ancestors2.size() - 1).equals(root)) {
            ancestors2.add(familyTree.get(ancestors2.get(ancestors2.size() - 1)));
        }

        // Find the first common ancestor of person1 and person2
        String commonAncestor = null;
        for (String ancestor : ancestors1) {
            if (ancestors2.contains(ancestor)) {
                commonAncestor = ancestor;
                break;
            }
        }

        // Find how many generations each person is removed from the common ancestor
        int generationsRemoved1 = ancestors1.indexOf(commonAncestor);
        int generationsRemoved2 = ancestors2.indexOf(commonAncestor);

        // Print the relationship based on the number of generations removed
        // The conditions cover different cases like siblings, cousins, parent-child, and grandparent-grandchild
        // The ordinal function is used to convert numbers to ordinal form (1st, 2nd, 3rd, etc.)
        // The logic for determining the relationship is based on the rules of genealogy
        // ...
        if (generationsRemoved1 == 0) {
            if (generationsRemoved2 == 1) {
                System.out.println(person2 + " is the child of " + person1);
            } else if (generationsRemoved2 > 1) {
                System.out.println(person2 + " is the " + ordinal(generationsRemoved2 - 2) + "grandchild of " + person1);
            }
        } else if (generationsRemoved1 > 0 && generationsRemoved1 == generationsRemoved2) {
            if (generationsRemoved1 == 1) {
                System.out.println(person1 + " and " + person2 + " are siblings");
            } else if (generationsRemoved1 > 1) {
                System.out.println(person1 + " and " + person2 + " are " + ordinal(generationsRemoved1 - 1) + "cousins");
            }
        } else if (generationsRemoved1 > 0 && generationsRemoved1 < generationsRemoved2) {
            if (generationsRemoved1 == 1 && generationsRemoved2 == 2) {
                System.out.println(person1 + " and " + person2 + " are " + ordinal(generationsRemoved1 - 1) + " cousins " + ordinal(generationsRemoved2 - 1) + " times removed");
            } else {
                System.out.println(person1 + " and " + person2 + " are " + ordinal(generationsRemoved1 - 1) + " cousins " + ordinal(generationsRemoved2 - generationsRemoved1) + " times removed");
            }
        } else if (generationsRemoved1 > 1) {
            int greats = generationsRemoved1 - 2;
            String strGreat = "";
            for (int i = 0; i < greats; i++) {
                strGreat += "great ";
            }
            System.out.println(person1 + " is the " + (strGreat) + "grandchild of " + person2);

        }
    }
}
