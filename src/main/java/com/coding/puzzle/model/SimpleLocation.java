package com.coding.puzzle.model;

public class SimpleLocation implements Location{

    private LocationName name;
    private String spritePath;

    public SimpleLocation (LocationName name, String spritePath){
        this.name = name;
        this.spritePath = spritePath;
    }

    @Override
    public String getContent() {
        return name.toString();
    }
}