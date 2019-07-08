package com.coding.puzzle.model.location;

public class SimpleLocation implements Location{

    private LocationName name;
    private String spritePath;

    public SimpleLocation (LocationName name, String spritePath){
        this.name = name;
        this.spritePath = spritePath;
    }

    @Override
    public Integer getSize() {
        return 1;
    }

    @Override
    public String getSprite() {
        return null;
    }
}