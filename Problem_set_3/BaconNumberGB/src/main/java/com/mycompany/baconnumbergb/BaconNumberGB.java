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
 * Geoffery Blackard
 * COSC 2436
 * Program Set 3
 * "Graph Algorithms Crash Course (with Java)." YouTube. October 26, 2022. https://youtu.be/dS44jZyj5gU?si=47WzxaSQB5z4sYum.
 */
class Actor {
    
    String name;
    Map<Actor, String> connectedActors = new HashMap<>();
    
    public Actor(String name) {
        this.name = name;
        
    }
}

public class BaconNumberGB {
    
    static Map<String, Actor> actors = new HashMap<>();
    
    static void addConnection(String actor1Name, String actor2Name, String movie) {
        Actor actor1 = actors.getOrDefault(actor1Name, new Actor(actor1Name));
        Actor actor2 = actors.getOrDefault(actor2Name, new Actor(actor2Name));
        actor1.connectedActors.put(actor2, movie);
        actor2.connectedActors.put(actor1, movie);
        actors.put(actor1Name, actor1);
        actors.put(actor2Name, actor2);
    }
    
    static void findPath(String actorName) {
        Actor start = actors.get("Kevin Bacon");
        Actor end = actors.get(actorName);
        if (start == null || end == null) {
            System.out.println("No Path Found");
            return;
        }
        
        Map<Actor, Actor> prev = new HashMap<>();
        Queue<Actor> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Actor actor = queue.poll();
            if (actor == end) {
                printPath(start, end, prev);
                return;
            }
            for (Actor neighbour : actor.connectedActors.keySet()) {
                if (!prev.containsKey(neighbour)) {
                    prev.put(neighbour, actor);
                    queue.add(neighbour);
                }
            }
        }
        System.out.println("No Path Found");
    }
    
    static void printPath(Actor start, Actor end, Map<Actor, Actor> prev) {
        List<String> path = new ArrayList<>();
        for (Actor actor = end; actor != start; actor = prev.get(actor)) {
            String movie = actor.connectedActors.get(prev.get(actor));
            path.add(actor.name + " was in " + movie + " with " + prev.get(actor).name);
        }
        System.out.println("Path from " + end.name + " to Kevin Bacon:");
        for (String step : path) {
            System.out.println(step);
        }
        System.out.println(end.name+"'s Bacon Number is: "+ path.size());
    }
    
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the file name:");
            String fileName = scanner.nextLine();
             File file = new File(fileName);
             Scanner fs = new Scanner(file);
                    while (fs.hasNext()) {
                        String[] parts = fs.nextLine().split(";");
                        String movie = parts[0];
                        for (int i = 1; i < parts.length; i++) {
                            for (int j = i + 1; j < parts.length; j++) {
                                addConnection(parts[i], parts[j],movie );
                            }
                        }
                    }
            
            while (true) {
                System.out.println("Enter the actor's name:");
                String actorName = scanner.nextLine();
                findPath(actorName);
                
                System.out.println("Do you want to run the program again? (yes/no)");
                String response = scanner.nextLine();
                if (!response.equalsIgnoreCase("yes")) {
                    break;
                }
            }
        }
    }
    
}
