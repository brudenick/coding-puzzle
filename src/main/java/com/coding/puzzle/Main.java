package com.coding.puzzle;

import com.coding.puzzle.controller.GameController;
import com.coding.puzzle.view.*;

public class Main {

    private static GameController gameController;

    public static void main(String[] args) {
        //Initial Configuration
        Drawer drawer = new ConsoleDrawer();
        UserInputReader userInputReader = new ConsoleUserInputReader();
        gameController = new GameController(drawer,userInputReader);

        //Game Start
        gameController.startGame();
    }

}