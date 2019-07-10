package com.coding.puzzle.controller;

import com.coding.puzzle.model.options.Direction;
import com.coding.puzzle.model.character.Character;
import com.coding.puzzle.model.character.CharacterType;
import com.coding.puzzle.model.character.Modifier;
import com.coding.puzzle.model.gamemap.GameMap;
import com.coding.puzzle.model.gamemap.MatrixGameMap;
import com.coding.puzzle.model.options.EncounterOption;
import com.coding.puzzle.model.options.MainMenuOption;
import com.coding.puzzle.view.drawer.Drawer;
import com.coding.puzzle.view.inputreader.UserInputReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public final class GameController {

    private Drawer drawer;
    private UserInputReader userInputReader;
    private GameObjectives gameObjectives;
    private GameEventGenerator gameEventGenerator;
    private static final List<MainMenuOption> mainMenu = new ArrayList<>();
    private static final List<Modifier> raceMenu = new ArrayList<>();
    private List<String> allowedDirectionsMenu = new ArrayList<>();
    private static final List<EncounterOption> encounterOptions = new ArrayList<>();

    private Character playerCharacter;
    private GameMap gameMap;

    static {
        //Static build of Main Menu
        mainMenu.add(MainMenuOption.NEW_GAME);
        mainMenu.add(MainMenuOption.LOAD_GAME);
        mainMenu.add(MainMenuOption.INSTRUCTIONS);
        mainMenu.add(MainMenuOption.EXIT);

        //Static build of Race Menu
        raceMenu.add(Modifier.ORC);
        raceMenu.add(Modifier.HUMAN);

        //Static build of Encounter options
        encounterOptions.add(EncounterOption.FIGHT);
        encounterOptions.add(EncounterOption.RUN);
    }

    public GameController(Drawer drawer, UserInputReader userInputReader, GameObjectives gameObjectives, GameEventGenerator gameEventGenerator){
        this.gameObjectives = gameObjectives;
        this.drawer = drawer;
        this.userInputReader = userInputReader;
        this.gameEventGenerator = gameEventGenerator;
    }

    public void startGame(){
        drawer.displayMessage("Choose an option:");
        drawer.displayOptions(mainMenu.stream().map(MainMenuOption::getName).collect(Collectors.toList()));
        Integer optionSelected = this.getValidUserSelection(mainMenu.stream().map(MainMenuOption::getName).collect(Collectors.toList()));
        drawer.displayMessage("Option Selected: " + mainMenu.get(optionSelected));

        switch(mainMenu.get(optionSelected)){
            case NEW_GAME:
                this.newGame();
                break;
            case LOAD_GAME:
                this.loadGame();//TODO
                break;
            case INSTRUCTIONS:
                drawer.displayMessage(">To save the game at any point, just write 'SAVE'\n>To exit the game at any point, just write 'EXIT'");
                this.startGame();
                break;
            case EXIT:
                System.exit(0);
                break;
        }
    }

    private boolean isValidUserSelection(String userInput, Integer optionsSize){
        try{
            Integer integerInput = Integer.parseInt(userInput);
            if (integerInput<1 || integerInput>optionsSize){
                throw new InputMismatchException();
            }
        }catch (NumberFormatException | InputMismatchException ex){
            drawer.displayMessage("The selected option doesn't exist, select a number within the options.");
            return false;
        }
        return true;
    }

    private Integer getValidUserSelection(List<String> options){
        String userInput;
        do {
            userInput = userInputReader.getInputString();

            if (userInput.equalsIgnoreCase("EXIT")){
                System.exit(0);
            }else if (userInput.equalsIgnoreCase("SAVE")){
                this.saveGame();
                drawer.displayMessage("Game SAVED successfuly!\n");
                drawer.displayOptions(options);
                userInput = userInputReader.getInputString();
            }
        }while (!isValidUserSelection(userInput,options.size()));
        return Integer.parseInt(userInput)-1;
    }

    private void newGame(){
        drawer.displayMessage("Choose a name for your character: ");
        String name = userInputReader.getInputString();

        drawer.displayMessage("Choose the race of your character:");
        drawer.displayOptions(raceMenu.stream().map(Modifier::getName).collect(Collectors.toList()));
        Integer optionSelected = this.getValidUserSelection(raceMenu.stream().map(Modifier::getName).collect(Collectors.toList()));
        String race = raceMenu.get(optionSelected).toString();
        drawer.displayMessage("Race Selected: " + race);

        this.createCharacter(name,race, CharacterType.HERO);

        this.gameMap = new MatrixGameMap();
        gameMap.buildMap(5,"random_map");

        this.startGameplay();
    }

    private void startGameplay(){
        this.play();
    }

    //This could be moved to a GamePlayController.
    private void play(){
        List<Direction> allowedDirections;
        while (!gameObjectives.objectivesCompleted(playerCharacter) && this.playerCharacter.isAlive()){
            allowedDirections = gameMap.getAllowedDirections();
            allowedDirectionsMenu = allowedDirections.stream().map(direction -> "Move to the "+ direction.getName()).collect(Collectors.toList());

            drawer.displayMessage("Current Location: "+gameMap.getCurrentLocation().getType().getName());
            drawer.displayMessage("Choose where to move: ");
            drawer.displayOptions(allowedDirectionsMenu);

            gameMap.movePlayer(allowedDirections.get(this.getValidUserSelection(allowedDirectionsMenu)));

            if (gameEventGenerator.spawnEnemy()){
                this.spawnEnemy();
            }
        }

        if (!this.playerCharacter.isAlive()){
            drawer.displayMessage("YOU DIED! GAME OVER :(");
        }else{//gameObjectives.objectivesCompleted==TRUE
            drawer.displayMessage("YOU COMPLETED ALL THE OBJECTIVES! YOU WIN!");
        }
    }

    private void saveGame(){
        if (this.gameMap!=null && this.playerCharacter!=null){
            try {
                this.gameMap.saveMap(this.playerCharacter.getName());
                this.saveCharacter();
            } catch (IOException e) {
                drawer.displayMessage("An error ocurred while saving the game!");
            }
        }else{
            drawer.displayMessage("An error ocurred while saving the game!");
        }
    }

    private void saveCharacter() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("./"+this.playerCharacter.getName()+".codingpuzzlecharacter"));
        writer.write(this.playerCharacter.toString());
        writer.close();
    }

    private void loadCharacter(String characterName) throws IOException {

        BufferedReader br = Files.newBufferedReader(Paths.get("./"+characterName+".codingpuzzlecharacter"));
        List<String> loadedCharacter = br.lines().collect(Collectors.toList());
        br.close();

        this.playerCharacter = new Character(loadedCharacter);
    }

    private void loadGame(){

        drawer.displayMessage("Choose a file to load:");

        File[] files = new File("./").listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename){
                return filename.endsWith(".codingpuzzlecharacter"); }
        } );

        List<String> savedGames = Arrays.stream(files)
                                        .map(File::getName)
                                        .map(fileName -> fileName.split("\\.codingpuzzlecharacter")[0])
                                        .collect(Collectors.toList());

        if (savedGames.isEmpty()){
            drawer.displayMessage("No saved games to load.");
            this.startGame();
        }else{
            drawer.displayOptions(savedGames);
            Integer optionSelected = this.getValidUserSelection(savedGames);
            drawer.displayMessage("Saved game Selected: " + savedGames.get(optionSelected));

            this.gameMap = new MatrixGameMap();
            try {
                gameMap.loadMap(savedGames.get(optionSelected));
                this.loadCharacter(savedGames.get(optionSelected));
            } catch (IOException e) {
                drawer.displayMessage("An error ocurred while loading the game!");
            }
            this.startGameplay();
        }

    }

    private void createCharacter(String name, String race, CharacterType characterType){
        this.playerCharacter = new Character(name,race,characterType);
    }

    private void spawnEnemy(){
        Character enemy = new Character("Random_Enemy","",CharacterType.ENEMY);
        drawer.displayMessage(enemy.getName()+" appeared just in front of you!");
        drawer.displayMessage("Choose an option:");
        drawer.displayOptions(encounterOptions.stream().map(EncounterOption::getName).collect(Collectors.toList()));
        switch(encounterOptions.get(this.getValidUserSelection(encounterOptions.stream().map(EncounterOption::getName).collect(Collectors.toList())))){
            case FIGHT:
                this.simulateFight(enemy);
                break;
            case RUN:
                //Continue to the selected direction.
                break;
        }

    }

    private void simulateFight(Character enemy){
        CharacterType turn = Arrays.stream(CharacterType.values()).skip(new Random().nextInt(CharacterType.values().length-1)).findFirst().get();
        while (this.playerCharacter.isAlive() && enemy.isAlive()){
            drawer.displayMessage("Player HP: "+this.playerCharacter.getCurrentHp()+" ; "+"Enemy HP: "+enemy.getCurrentHp());
            switch (turn){
                case HERO:
                    enemy.receiveDamage(this.playerCharacter.getDamage());
                    turn = CharacterType.ENEMY;
                    break;
                case ENEMY:
                    this.playerCharacter.receiveDamage(enemy.getDamage());
                    turn = CharacterType.HERO;
                    break;
            }
        }

        if (!enemy.isAlive()){
            Integer experienceGained = gameEventGenerator.getExperienceDroppedByEnemy(this.playerCharacter,enemy);
            this.playerCharacter.increaseExperience(experienceGained);
            drawer.displayMessage("XP gained: "+experienceGained);
        }
    }
}