package com.coding.puzzle.model;

public enum Direction {
    UP("North"),
    DOWN("South"),
    LEFT("West"),
    RIGHT("East");

    private String name;

    Direction (String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
