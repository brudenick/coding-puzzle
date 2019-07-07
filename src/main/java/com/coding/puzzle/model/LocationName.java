package com.coding.puzzle.model;

public enum LocationName {
    CITY("City"),
    CASTLE("Castle"),
    FOREST("Forest");

    private String name;

    LocationName (String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}