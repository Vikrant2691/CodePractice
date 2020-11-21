package com.adventure;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.Map;

public class Location {

    private String description;
    private int locationID;
    private Map<String, Integer> exits;

    public Location(int locationID, String description, Map<String, Integer> exits) {
        this.description = description;
        this.locationID = locationID;
        if (exits != null)
            this.exits = exits;
        else
            this.exits = new HashMap<String, Integer>();
    }

    public Map<String, Integer> getExits() {
        return new HashMap<>(exits);
    }

    public String getDescription() {
        return description;
    }

    public int getLocationID() {
        return locationID;
    }

    public void addExits(String direction, Integer location) {
        if (!exits.containsKey(direction)) {
            this.exits.put(direction, location);
        } else {
            System.out.println("This exit already exists");
        }
    }
}
