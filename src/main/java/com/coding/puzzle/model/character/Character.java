package com.coding.puzzle.model.character;

import java.util.Map;

public abstract class Character {
    protected String name;

    protected Integer maxHp;
    protected Integer currentHp;

    protected Integer baseDamage;
    protected Integer baseDefense;

    protected Map<Stat,Integer> stats;

    public abstract Integer getDamage();
    public abstract Integer getArmor();
}
