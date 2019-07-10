package com.coding.puzzle.model.gamemap;

public enum MapSize {
    SMALL("Small (9 locations)",3),
    MEDIUM("Medium (25 locations)",5),
    BIG("Big (49 locations)",7),
    HUGE("Huge (100 locations)",10);

    private String description;
    private Integer size;

    MapSize(String description, Integer size) {
        this.description = description;
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
