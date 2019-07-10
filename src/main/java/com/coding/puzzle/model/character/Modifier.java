package com.coding.puzzle.model.character;

import java.util.Arrays;

public enum Modifier {
    ORC("Orc",1.25,1.1),
    HUMAN("Human",1.1,1.25);
    //Here we can add another modifiers, as character class or buff/debuff.

    Modifier(String name, Double damageMultiplyer, Double defenseMultiplyer) {
        this.name = name;
        this.damageMultiplyer = damageMultiplyer;
        this.defenseMultiplyer = defenseMultiplyer;
    }

    private String name;
    private Double damageMultiplyer;
    private Double defenseMultiplyer;

    public String getName() {
        return name;
    }

    public Double getDamageMultiplyer() {
        return damageMultiplyer;
    }

    public Double getDefenseMultiplyer() {
        return defenseMultiplyer;
    }
}
