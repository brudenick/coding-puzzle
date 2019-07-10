package com.coding.puzzle.controller;

import com.coding.puzzle.model.character.Character;

import java.util.List;

/**
 * This manages the objectives and the completion of them.
 */
public interface GameObjectives {

    /**
     * This function checks if the @param player has completed all the objectives.
     * @param player
     * @return
     */
    boolean objectivesCompleted(Character player);

    /**
     * Returns a list of the current objectives to win the game.
     * @return
     */
    List<String> getObjectives();
}
