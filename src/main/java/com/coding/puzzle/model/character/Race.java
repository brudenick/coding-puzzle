package com.coding.puzzle.model.character;

public enum Race {
    HUMAN("Human"),
    ORC("Orc");

    private String name;

    Race (String name){
        this.name = name;
    }

    public String toString(){
        return this.name;
    }
}
