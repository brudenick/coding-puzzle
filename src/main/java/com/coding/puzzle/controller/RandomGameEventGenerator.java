package com.coding.puzzle.controller;

import com.coding.puzzle.model.character.Character;

public class RandomGameEventGenerator implements GameEventGenerator {

    private Double spawnEnemyChance;

    public RandomGameEventGenerator (Double enemySpawnChance){
        this.spawnEnemyChance = enemySpawnChance;
    }

    @Override
    public boolean spawnEnemy() {
        return Math.random()>1.0- this.spawnEnemyChance;
    }

    @Override
    public Integer getExperienceDroppedByEnemy(Character hero, Character enemy) {
        //We can specify some logic to determine the experience that the hero should gain.
        return Math.toIntExact(Math.round(Math.random()*50)+50);
    }
}
