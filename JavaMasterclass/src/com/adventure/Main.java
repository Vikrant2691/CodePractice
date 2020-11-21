package com.adventure;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Locations locationMap = new Locations();
    private static HashMap<String, String> directions = new HashMap<>();

    public static void main(String[] args) {


        directions.put("East", "E");
        directions.put("West", "W");
        directions.put("North", "N");
        directions.put("South", "S");


        locationMap.get(1).addExits("E", 3);
        locationMap.get(1).addExits("W", 2);
        locationMap.get(1).addExits("N", 5);
        locationMap.get(1).addExits("S", 4);

        locationMap.get(2).addExits("N", 5);
        locationMap.get(2).addExits("S", 4);

        locationMap.get(3).addExits("E", 1);

        locationMap.get(4).addExits("N", 1);
        locationMap.get(4).addExits("E", 2);

        locationMap.get(5).addExits("E", 2);
        locationMap.get(5).addExits("S", 1);


        int loc = 1;

        while (true) {
            System.out.println(locationMap.get(loc).getDescription());

            if (loc == 0) {
                break;
            }

            Map<String, Integer> exits = locationMap.get(loc).getExits();
            System.out.println("Available exits are: ");
            for (String exit : exits.keySet()) {
                System.out.print(exit + " ,");
            }
            System.out.println();

            String input = scanner.nextLine();

            String direction= getDirections(directions,input);

            if (exits.containsKey(direction)) {
                loc = exits.get(direction);
            } else {
                System.out.println("You cannot go that direction");
            }
        }
    }

    private static String getDirections(HashMap<String, String> directions, String input) {

        for (String direction : directions.keySet()) {
            if (input.contains(direction)) {

                return directions.get(direction);
            }
        }
        return null;
    }

}
