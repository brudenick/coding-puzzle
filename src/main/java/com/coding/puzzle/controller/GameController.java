package com.coding.puzzle.controller;

import com.coding.puzzle.model.gamemap.MapSize;
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
    private static final List<MapSize> mapSizeOptions = new ArrayList<>();

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

        //Static build of MapSize options
        mapSizeOptions.add(MapSize.SMALL);
        mapSizeOptions.add(MapSize.MEDIUM);
        mapSizeOptions.add(MapSize.BIG);
        mapSizeOptions.add(MapSize.HUGE);
    }

    public GameController(Drawer drawer, UserInputReader userInputReader, GameObjectives gameObjectives, GameEventGenerator gameEventGenerator){
        this.gameObjectives = gameObjectives;
        this.drawer = drawer;
        this.userInputReader = userInputReader;
        this.gameEventGenerator = gameEventGenerator;
    }

    /**
     * Only public method of the controller. It starts the game, displaying the main menu.
     */
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
                drawer.displayMessage(">To save the game at any point, just type 'SAVE' and press Enter\n>To exit the game at any point, just type 'EXIT' and press Enter");
                this.startGame();
                break;
            case EXIT:
                System.exit(0);
                break;
        }
    }

    /**
     * Checks if the user selection is a valid option.
     * @param userInput
     * @param optionsSize
     * @return
     */
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

    /**
     * Asks the user for an input until it enters a valid one.
     * @param options
     * @return
     */
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

    /**
     * Displays options to choose character name and race. Then a random_map is created and the gameplay starts.
     */
    private void newGame(){
        drawer.displayMessage("Choose a name for your character: ");
        String name = userInputReader.getInputString();

        drawer.displayMessage("Choose the race of your character:");
        drawer.displayOptions(raceMenu.stream().map(Modifier::getName).collect(Collectors.toList()));
        Integer raceSelectedIndex = this.getValidUserSelection(raceMenu.stream().map(Modifier::getName).collect(Collectors.toList()));
        String race = raceMenu.get(raceSelectedIndex).toString();
        drawer.displayMessage("Race Selected: " + race);

        drawer.displayMessage("Choose the size of the map:");
        drawer.displayOptions(mapSizeOptions.stream().map(MapSize::getDescription).collect(Collectors.toList()));
        Integer mapSizeSelectedIndex = this.getValidUserSelection(mapSizeOptions.stream().map(MapSize::getDescription).collect(Collectors.toList()));
        Integer mapSize = mapSizeOptions.get(mapSizeSelectedIndex).getSize();
        drawer.displayMessage("Map Size Selected: " + mapSize);

        this.createCharacter(name,race, CharacterType.HERO);

        this.gameMap = new MatrixGameMap();
        gameMap.buildMap(mapSize);

        this.play();
    }

    //This and all the "playing" methods could be moved to a GamePlayController.
    private void play(){
        drawer.displayMessage("GAME OBJECTIVES:");
        gameObjectives.getObjectives().stream().forEach(s -> drawer.displayMessage("> "+s));
        List<Direction> allowedDirections;
        while (!gameObjectives.objectivesCompleted(playerCharacter) && this.playerCharacter.isAlive()){
            //Asks the gameMap for the movements that can be done from currentLocation.
            allowedDirections = gameMap.getAllowedDirections();
            allowedDirectionsMenu = allowedDirections.stream().map(direction -> "Move to the "+ direction.getName()).collect(Collectors.toList());

            drawer.displayMessage("Current Location: "+gameMap.getCurrentLocation().getType().getName());
            drawer.displayMessage("Choose where to move: ");
            drawer.displayOptions(allowedDirectionsMenu);

            //Updates player location.
            gameMap.movePlayer(allowedDirections.get(this.getValidUserSelection(allowedDirectionsMenu)));

            if (gameEventGenerator.spawnEnemy()){
                this.spawnEnemy();
            }
        }

        if (!this.playerCharacter.isAlive()){
            drawer.displayMessage("YOU DIED! GAME OVER :(");
        }else{//gameObjectives.objectivesCompleted==TRUE
            drawer.displayMessage("OBJECTIVES COMPLETED! YOU WIN!");
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

    /**
     * Saves the player Character into a file in the root directory.
     * @throws IOException
     */
    private void saveCharacter() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("./"+this.playerCharacter.getName()+".codingpuzzlecharacter"));
        writer.write(this.playerCharacter.toString());
        writer.close();
    }

    /**
     * Load a character from the fileSystem, searching for a file in the root directory.
     * @param characterName
     * @throws IOException
     */
    private void loadCharacter(String characterName) throws IOException {

        BufferedReader br = Files.newBufferedReader(Paths.get("./"+characterName+".codingpuzzlecharacter"));
        List<String> loadedCharacter = br.lines().collect(Collectors.toList());
        br.close();

        //Initializes the playerCharacter with the values loaded from the file.
        this.playerCharacter = new Character(loadedCharacter);
    }

    /**
     *
     */
    private void loadGame(){

        drawer.displayMessage("Choose a save file to load:");

        //Returns the files with extension '.codingpuzzlecharacter' existing in the root directory.
        File[] files = new File("./")
                .listFiles((dir, filename) -> filename.endsWith(".codingpuzzlecharacter"));

        //Builds a list of 'saveGames' available to be loaded.
        List<String> savedGames = Arrays.stream(files)
                                        .map(File::getName)
                                        .map(fileName -> fileName.split("\\.codingpuzzlecharacter")[0])
                                        .collect(Collectors.toList());

        if (savedGames.isEmpty()){
            drawer.displayMessage("No saved games to load.");
            this.startGame();
        }else{
            //Adds the 'back' option in case the player wants to return to the main menu.
            savedGames.add("Back");
            drawer.displayOptions(savedGames);
            Integer optionSelected = this.getValidUserSelection(savedGames);
            //If the optionSelected is the last one in the list ('Back'). The game goes back to the beginning.
            if (optionSelected.equals(savedGames.size()-1)){
                this.startGame();
            }else{
                drawer.displayMessage("Saved game Selected: " + savedGames.get(optionSelected));

                this.gameMap = new MatrixGameMap();
                try {
                    //The map of the saved game selected is loaded.
                    gameMap.loadMap(savedGames.get(optionSelected));
                    //The character of the saved game selected is loaded.
                    this.loadCharacter(savedGames.get(optionSelected));
                } catch (IOException e) {
                    drawer.displayMessage("An error ocurred while loading the game!");
                }
                this.play();
            }
        }

    }

    /**
     * Creates a HERO Character with the given name and race.
     * @param name
     * @param race
     * @param characterType
     */
    private void createCharacter(String name, String race, CharacterType characterType){
        this.playerCharacter = new Character(name,race,characterType);
    }

    /**
     * Creates en ENEMY Character with random attributes.
     */
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

    /**
     * Simulates the fight between player's Character and an ENEMY character, until one dies.
     * @param enemy
     */
    private void simulateFight(Character enemy){
        //Randomly selects who's has the first turn (that is, who will attack first).
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

        //If the enemy is dead, then the playerCharacter should earn experience.
        if (!enemy.isAlive()){
            Integer experienceGained = this.playerCharacter.getExperienceDroppedByEnemy(enemy);
            this.playerCharacter.increaseExperience(experienceGained);
            drawer.displayMessage("XP gained: "+experienceGained);
        }
    }
}