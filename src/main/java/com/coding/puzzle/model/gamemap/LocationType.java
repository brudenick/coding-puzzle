package com.coding.puzzle.model.gamemap;

import java.util.*;

public enum LocationType {
    CITY("City"),
    CASTLE("Castle"),
    FOREST("Forest");

    private String name;

    private static final List<LocationType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    LocationType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public static LocationType getRandomLocationType()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
