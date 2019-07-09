package com.coding.puzzle.model.gamemap;

import com.coding.puzzle.model.Direction;

import java.util.ArrayList;
import java.util.List;

public class MatrixGameMap implements GameMap {

    private String name; //Map name.

    private Location[][] map;
    private Integer width;//columns
    private Integer height;//rows

    private Integer currentX,currentY;//Current player coordinates.

    @Override
    public void buildMap(Integer size,String mapName) {
        this.name = name;
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

    @Override
    public void buildMap(List<Location> locations, Location initialLocation, String mapName){
        this.name = name;
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

    public void saveMap(){
        //TODO
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

    //TODO: Move to tests
    public static void main (String[] args){
        Location l1 = new Location(LocationType.getRandomLocationType());
        Location l2 = new Location(LocationType.getRandomLocationType());
        Location l3 = new Location(LocationType.getRandomLocationType());
        Location l4 = new Location(LocationType.getRandomLocationType());

        List<Location> locations = new ArrayList<>();
        locations.add(l1);
        locations.add(l2);
        locations.add(l3);
        locations.add(l4);

        GameMap gameMap = new MatrixGameMap();
        gameMap.buildMap(locations,l3,"map_1");

        gameMap.getAllowedDirections().stream().forEach(System.out::println);
    }
}
