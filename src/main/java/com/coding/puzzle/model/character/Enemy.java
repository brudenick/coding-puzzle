package com.coding.puzzle.model.character;

import java.util.Random;

public class Enemy extends Character {

    private Integer baseExperienceDropped;

    @Override
    public Integer getDamage() {
        return null;
    }

    @Override
    public Integer getArmor() {
        return null;
    }

    public Integer getExperienceDropped(){
        return baseExperienceDropped;
    }

}
