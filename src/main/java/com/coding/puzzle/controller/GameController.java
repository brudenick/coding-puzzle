package com.coding.puzzle.controller;

import com.coding.puzzle.model.Direction;
import com.coding.puzzle.model.character.CharClass;
import com.coding.puzzle.model.character.Race;
import com.coding.puzzle.model.options.MainMenuOption;
import com.coding.puzzle.view.Drawer;
import com.coding.puzzle.view.UserInputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class GameController {

    private Drawer drawer;
    private UserInputReader userInputReader;
    private CharacterFactory characterFactory = new CharacterFactory();
    private static final List<String> mainMenu = new ArrayList<>();
    private static final List<String> raceMenu = new ArrayList<>();
    private static final List<String> classMenu = new ArrayList<>();

    static {
        mainMenu.add("Main Menu:");
        mainMenu.add(MainMenuOption.NEW_GAME);
        mainMenu.add(MainMenuOption.LOAD_GAME);
        mainMenu.add(MainMenuOption.INSTRUCTIONS);
        mainMenu.add(MainMenuOption.EXIT);

        raceMenu.add("Select a race:");
        //An option is added to the menu for each value defined in the enum Race.
        raceMenu.addAll(Arrays.stream(Race.values()).map(Race::toString).collect(Collectors.toList()));

        classMenu.add("Select a class:");
        //An option is added to the menu for each value defined in the enum CharClass.
        classMenu.addAll(Arrays.stream(CharClass.values()).map(CharClass::toString).collect(Collectors.toList()));

    }

    public GameController(Drawer drawer, UserInputReader userInputReader){
        this.drawer = drawer;
        this.userInputReader = userInputReader;
    }

    public void startGame(){
        drawer.displayOptions(mainMenu);
        Integer optionSelected = userInputReader.getOptionSelected(mainMenu.size());
        drawer.displayMessage("Option Selected: " + mainMenu.get(optionSelected));

        switch(mainMenu.get(optionSelected)){
            case MainMenuOption.NEW_GAME:
                this.newGame();
                break;
            case MainMenuOption.LOAD_GAME:
                this.loadGame();
                break;
            case MainMenuOption.INSTRUCTIONS:
                break;
            case MainMenuOption.EXIT:
                System.exit(0);
                break;
        }
    }

    public void newGame(){
        drawer.displayMessage("\nChoose a name for your character: ");
        String name = userInputReader.getString();

        drawer.displayOptions(raceMenu);
        String race = raceMenu.get(userInputReader.getOptionSelected(raceMenu.size()));
        drawer.displayMessage("Race Selected: " + race);

        drawer.displayOptions(classMenu);
        String charClass = classMenu.get(userInputReader.getOptionSelected(classMenu.size()));
        drawer.displayMessage("Class Selected: " + charClass);

        this.createCharacter(name,race,charClass);
    }

    public void loadGame(){
        //TODO
    }

    public void createCharacter(String name,String race,String charClass){
        //Character character = characterFactory.getCharacter(name,race,charClass);
    }

    public void spawnEnemy(){
    }

    public void moveCharacter(Direction direction){

    }
}
