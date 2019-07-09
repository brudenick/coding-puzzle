package com.coding.puzzle.controller;

import com.coding.puzzle.model.Direction;
import com.coding.puzzle.model.character.Character;
import com.coding.puzzle.model.character.CharacterType;
import com.coding.puzzle.model.character.Modifier;
import com.coding.puzzle.model.gamemap.GameMap;
import com.coding.puzzle.model.gamemap.MatrixGameMap;
import com.coding.puzzle.model.options.MainMenuOption;
import com.coding.puzzle.view.Drawer;
import com.coding.puzzle.view.UserInputReader;

import java.util.*;

public final class GameController {

    private Drawer drawer;
    private UserInputReader userInputReader;
    private static final List<String> mainMenu = new ArrayList<>();
    private static final List<String> raceMenu = new ArrayList<>();

    private Character player;

    static {
        //Static building of Main Menu
        mainMenu.add("Main Menu:");
        mainMenu.add(MainMenuOption.NEW_GAME);
        mainMenu.add(MainMenuOption.LOAD_GAME);
        mainMenu.add(MainMenuOption.INSTRUCTIONS);
        mainMenu.add(MainMenuOption.EXIT);

        //Static building of Race Menu
        raceMenu.add("Select a race:");
        raceMenu.add(Modifier.ORC.getName());
        raceMenu.add(Modifier.HUMAN.getName());
    }

    public GameController(Drawer drawer, UserInputReader userInputReader){
        this.drawer = drawer;
        this.userInputReader = userInputReader;
    }

    public void startGame(){
        drawer.displayOptions(mainMenu);
        Integer optionSelected = this.getValidUserSelection(mainMenu);
        drawer.displayMessage("Option Selected: " + mainMenu.get(optionSelected));

        switch(mainMenu.get(optionSelected)){
            case MainMenuOption.NEW_GAME:
                this.newGame();
                break;
            case MainMenuOption.LOAD_GAME:
                this.loadGame();//TODO
                break;
            case MainMenuOption.INSTRUCTIONS:
                drawer.displayMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
                break;
            case MainMenuOption.EXIT:
                System.exit(0);
                break;
        }
    }

    private boolean isValidUserSelection(String userInput, Integer optionsSize){
        try{
            Integer integerInput = Integer.parseInt(userInput);
            if (integerInput<1 || integerInput>=optionsSize){
                throw new InputMismatchException();
            }
        }catch (NumberFormatException | InputMismatchException ex){
            drawer.displayMessage("The selected option doesn't exist, select a number within the options.");
            return false;
        }
        return true;
    }

    private Integer getValidUserSelection(List<String> options){
        String userInput = userInputReader.getInputString();
        while (!isValidUserSelection(userInput,options.size())){
            userInput = userInputReader.getInputString();
        }
        return Integer.parseInt(userInput);
    }

    public void newGame(){
        drawer.displayMessage("Choose a name for your character: ");
        String name = userInputReader.getInputString();

        drawer.displayOptions(raceMenu);
        Integer optionSelected = this.getValidUserSelection(raceMenu);
        String race = raceMenu.get(optionSelected);
        drawer.displayMessage("Race Selected: " + race);

        this.createCharacter(name,race, CharacterType.HERO);

        GameMap gameMap = new MatrixGameMap();
        gameMap.buildMap(5,"random_map");
    }

    public void loadGame(){
        //TODO
        GameMap gameMap = new MatrixGameMap();
    }

    public void createCharacter(String name, String race, CharacterType characterType){
        this.player = new Character(name,race,characterType);
    }

    public void spawnEnemy(){
    }

    public void moveCharacter(Direction direction){
    }

    public Character getPlayer() {
        return player;
    }

    public void setPlayer(Character player) {
        this.player = player;
    }
}
