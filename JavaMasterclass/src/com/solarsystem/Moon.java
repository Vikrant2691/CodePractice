package com.solarsystem;

public final class Moon extends HeavenlyBody{


    public Moon(String name, double orbitalPeriod) {
        super(name, orbitalPeriod, Moon.class.getName());
    }
}
