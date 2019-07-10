package com.coding.puzzle.model.options;

public enum MainMenuOption {
    NEW_GAME("New Game"),
    LOAD_GAME("Load Game"),
    INSTRUCTIONS("Instructions"),
    EXIT("Exit");

    private String name;

    MainMenuOption (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
