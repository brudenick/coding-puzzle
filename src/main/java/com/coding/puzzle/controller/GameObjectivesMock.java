package com.coding.puzzle.controller;

import com.coding.puzzle.model.character.Character;

public class GameObjectivesMock implements GameObjectives {

    private Integer experienceObjective;

    public GameObjectivesMock(Integer experienceObjective){
        this.experienceObjective = experienceObjective;
    }

    @Override
    public boolean objectivesCompleted(Character player) {
        return player.getCurrentExperience()>experienceObjective;
    }
}
