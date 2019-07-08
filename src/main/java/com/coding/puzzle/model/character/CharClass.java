package com.coding.puzzle.model.character;

public enum CharClass {
    WARRIOR("Warrior"),
    ROGUE("Rogue");

    private String name;

    CharClass (String name){
        this.name = name;
    }

    public String toString(){
        return this.name;
    }
}
