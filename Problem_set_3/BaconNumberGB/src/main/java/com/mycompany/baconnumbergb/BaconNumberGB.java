/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.baconnumbergb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 * Geoffery Blackard COSC 2436 Program Set 3 "Graph Algorithms Crash Course
 * (with Java)." YouTube. October 26, 2022.
 * https://youtu.be/dS44jZyj5gU?si=47WzxaSQB5z4sYum.
 */
// Define the Actor class
class Actor {

    // Declare a String variable to store the name of the actor
    String name;
    // Declare a Map to store other actors this actor is connected to
    // The keys are Actor objects and the values are Strings
    Map<Actor, String> connectedActors = new HashMap<>();

    // Define the constructor for the Actor class
    public Actor(String name) {
        // Initialize the name of the actor
        this.name = name;
    }
}

public class BaconNumberGB {

// Declare a Map to store all actors
// The keys are actor names and the values are Actor objects
    static Map<String, Actor> actors = new HashMap<>();

// Method to add a connection between two actors
    static void addConnection(String actor1Name, String actor2Name, String movie) {
        // Get the Actor objects for the two actors, or create new ones if they don't exist
        Actor actor1 = actors.getOrDefault(actor1Name, new Actor(actor1Name));
        Actor actor2 = actors.getOrDefault(actor2Name, new Actor(actor2Name));
        // Add the connection between the two actors
        actor1.connectedActors.put(actor2, movie);
        actor2.connectedActors.put(actor1, movie);
        // Add the Actor objects to the map of all actors
        actors.put(actor1Name, actor1);
        actors.put(actor2Name, actor2);
    }

// Method to find a path from Kevin Bacon to another actor
    static void findPath(String actorName) {
        // Get the Actor objects for Kevin Bacon and the other actor
        Actor start = actors.get("Kevin Bacon");
        Actor end = actors.get(actorName);
        // If either Actor object is null, print a message and return
        if (start == null || end == null) {
            System.out.println("No Path Found");
            return;
        }

        // Declare a Map to store the previous actor for each actor
        Map<Actor, Actor> prev = new HashMap<>();
        // Declare a Queue to store the actors to visit
        Queue<Actor> queue = new LinkedList<>();
        // Add Kevin Bacon to the queue
        queue.add(start);
        // While the queue is not empty
        while (!queue.isEmpty()) {
            // Remove the next actor from the queue
            Actor actor = queue.poll();
            // If this actor is the end actor, print the path and return
            if (actor == end) {
                printPath(start, end, prev);
                return;
            }
            // For each actor connected to this actor
            for (Actor neighbour : actor.connectedActors.keySet()) {
                // If this actor has not been visited yet
                if (!prev.containsKey(neighbour)) {
                    // Add this actor to the map of previous actors
                    prev.put(neighbour, actor);
                    // Add this actor to the queue
                    queue.add(neighbour);
                }
            }
        }
        // If no path was found, print a message
        System.out.println("No Path Found");
    }

    // Method to print the path from Kevin Bacon to another actor
    static void printPath(Actor start, Actor end, Map<Actor, Actor> prev) {
        // Declare a List to store the path
        List<String> path = new ArrayList<>();
        // For each actor in the path
        for (Actor actor = end; actor != start; actor = prev.get(actor)) {
            // Get the movie that connects this actor to the previous actor
            String movie = actor.connectedActors.get(prev.get(actor));
            // Add a step to the path
            path.add(actor.name + " was in " + movie + " with " + prev.get(actor).name);
        }
        // Print the path
        System.out.println("Path from " + end.name + " to Kevin Bacon:");
        for (String step : path) {
            System.out.println(step);
        }
        // Print the Bacon Number
        System.out.println(end.name + "'s Bacon Number is: " + path.size());
    }

// Main method
    public static void main(String[] args) throws IOException {
        // Create a Scanner to read input from the user
        try (Scanner scanner = new Scanner(System.in)) {
            // Prompt the user to enter the file name
            System.out.println("Enter the file name:");
            String fileName = scanner.nextLine();
            // Create a File object for the file
            File file = new File(fileName);
            // Create a Scanner to read from the file
            Scanner fs = new Scanner(file);
            // While there are more lines in the file
            while (fs.hasNext()) {
                // Split the line into parts
                String[] parts = fs.nextLine().split(";");
                // The first part is the movie
                String movie = parts[0];
                // For each pair of actors in the parts
                for (int i = 1; i < parts.length; i++) {
                    for (int j = i + 1; j < parts.length; j++) {
                        // Add a connection between the actors
                        addConnection(parts[i], parts[j], movie);
                    }
                }
            }

            // While the user wants to continue
            while (true) {
                // Prompt the user to enter the actor's name
                System.out.println("Enter the actor's name:");
                String actorName = scanner.nextLine();
                // Find the path from Kevin Bacon to the actor
                findPath(actorName);

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

}
