package com.coding.puzzle;

import com.coding.puzzle.controller.*;
import com.coding.puzzle.view.drawer.ConsoleDrawer;
import com.coding.puzzle.view.drawer.Drawer;
import com.coding.puzzle.view.inputreader.ConsoleUserInputReader;
import com.coding.puzzle.view.inputreader.UserInputReader;

public class Main {

    private static GameController gameController;

    public static void main(String[] args) {
        //Initial Configuration
        Drawer drawer = new ConsoleDrawer();
        UserInputReader userInputReader = new ConsoleUserInputReader();
        GameObjectives gameObjectives = new GameObjectivesMock(150);
        GameEventGenerator gameEventGenerator = new RandomGameEventGenerator(0.25);
        gameController = new GameController(drawer,userInputReader,gameObjectives,gameEventGenerator);

        //Game Start
        gameController.startGame();
    }

}