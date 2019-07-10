package com.coding.puzzle.controller;

public class RandomGameEventGenerator implements GameEventGenerator {

    private Double spawnEnemyChance;

    public RandomGameEventGenerator (Double enemySpawnChance){
        this.spawnEnemyChance = enemySpawnChance;
    }

    @Override
    public boolean spawnEnemy() {
        return Math.random()>1.0- this.spawnEnemyChance;
    }
}
