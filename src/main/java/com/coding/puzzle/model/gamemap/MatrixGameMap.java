package com.coding.puzzle.model.gamemap;

import com.coding.puzzle.model.options.Direction;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MatrixGameMap implements GameMap {

    private String name; //Map name.

    private Location[][] map;
    private Integer width;//columns
    private Integer height;//rows

    private Integer currentX,currentY;//Current player coordinates.

    @Override
    public void buildMap(Integer size) {
        this.width = size;
        this.height = size;
        this.currentX = (int)(Math.random()*size);
        this.currentY = (int)(Math.random()*size);
        this.map = new Location[ height ][ width ];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[ y ][ x ] = new Location(LocationType.getRandomLocationType());
            }
        }
    }

    //Builds a map with given list of locations and initial location. Used to load previous game.
    private void buildMap(List<Location> locations, Location initialLocation){
        Integer size = Math.toIntExact(Math.round(Math.sqrt(locations.size())));
        this.width = size;
        this.height = size;
        this.map = new Location[ height ][ width ];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (locations.get(0).equals(initialLocation)){
                    this.currentX = x; this.currentY = y;
                }
                map[ y ][ x ] = locations.get(0);
                locations.remove(0);
            }
        }
    }

    @Override
    public void loadMap(String saveName) throws IOException {
        //Read all the file.
        BufferedReader br = Files.newBufferedReader(Paths.get("./"+saveName+".codingpuzzlemap"));
        List<String> loadedMap = br.lines().collect(Collectors.toList());
        br.close();

        //Set the initialLocation = first line in the file.
        Location initialLocation = new Location(UUID.fromString(loadedMap.get(0).split(";")[0]),loadedMap.get(0).split(";")[1]);

        //Create the List<Location> that will be used to build the map.
        List<Location> locations = new ArrayList<>();
        for (int i = 1 ; i<loadedMap.size() ; i++){
            locations.add(new Location(UUID.fromString(loadedMap.get(i).split(";")[0]),loadedMap.get(i).split(";")[1]));
        }

        this.buildMap(locations,initialLocation);

    }

    @Override
    public void saveMap(String characterName) throws IOException {
        StringBuilder mapString = new StringBuilder();

        //The first line is the currentLocation.
        mapString.append(this.getCurrentLocation()+"\n");

        //Go through the Matrix, adding one line per map Location in the string,
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                mapString.append(map[ y ][ x ].toString()+"\n");
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("./"+characterName+".codingpuzzlemap"));
        //Remove the last '\n' of the String and write it to the file.
        writer.write(mapString.toString().substring(0,mapString.length()-1));
        writer.close();
    }

    @Override
    public Location getCurrentLocation() {
        return map[currentY][currentX];
    }

    @Override
    public List<Direction> getAllowedDirections() {
        List<Direction> allowedMovements = new ArrayList<>();
        if (currentX+1<width) allowedMovements.add(Direction.RIGHT);
        if (currentX-1>=0) allowedMovements.add(Direction.LEFT);
        if (currentY+1<height) allowedMovements.add(Direction.DOWN);
        if (currentY-1>=0) allowedMovements.add(Direction.UP);
        return allowedMovements;
    }

    @Override
    public void movePlayer(Direction direction) {
        switch (direction){
            case UP:
                currentY--;
                break;
            case DOWN:
                currentY++;
                break;
            case LEFT:
                currentX--;
                break;
            case RIGHT:
                currentX++;
                break;
        }

    }
}
