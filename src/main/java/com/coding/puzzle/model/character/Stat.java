package com.coding.puzzle.model.character;

public enum Stat {
    STRENGTH("Strength"),
    DEXTERITY("Intellect");

    private String name;


    Stat (String name){
        this.name = name;
    }

    public String toString(){
        return this.name;
    }


}
