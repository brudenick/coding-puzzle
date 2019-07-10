package com.coding.puzzle.controller;

import com.coding.puzzle.model.character.Character;

public interface GameEventGenerator {

    boolean spawnEnemy();

    Integer getExperienceDroppedByEnemy(Character hero, Character enemy);

}
