package com.coding.puzzle.controller;

import com.coding.puzzle.model.character.Character;

import java.util.ArrayList;
import java.util.List;

public class GameObjectivesMock implements GameObjectives {

    private Integer experienceObjective;
    private List<String> objectives = new ArrayList<>();

    public GameObjectivesMock(Integer experienceObjective){
        this.experienceObjective = experienceObjective;
    }

    @Override
    public boolean objectivesCompleted(Character player) {
        return player.getCurrentExperience()>experienceObjective;
    }

    @Override
    public List<String> getObjectives() {
        objectives.add("GAIN "+experienceObjective+" XP.");
        return objectives;
    }
}
