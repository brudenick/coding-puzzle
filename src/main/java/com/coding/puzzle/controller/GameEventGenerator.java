package com.coding.puzzle.controller;

/**
 * It generates the random events that may occur during gamePlay, such as the spawning of an enemy.
 */
public interface GameEventGenerator {

    /**
     * This function decides whether an enemy should spawn or not.
     * @return boolean : if true, an enemy should spawn
     */
    boolean spawnEnemy();
}
