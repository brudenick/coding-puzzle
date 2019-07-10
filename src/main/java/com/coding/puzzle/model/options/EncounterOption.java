package com.coding.puzzle.model.options;

public enum EncounterOption {
    FIGHT("Fight"),
    RUN("Run");

    private String name;

    EncounterOption (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
