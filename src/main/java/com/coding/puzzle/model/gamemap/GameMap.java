package com.coding.puzzle.model.gamemap;

import com.coding.puzzle.model.Direction;

import java.util.List;

public interface GameMap {

    //Builds a map with random locations and random initial location.
    void buildMap(Integer size,String mapName);

    //Builds a map with given list of locations and initial location. Used to load previous game.
    void buildMap(List<Location> locations, Location initialLocation, String mapName);

    //Returns the current location.
    Location getCurrentLocation();

    //Returns a list with the allowed directions the player can take from current position in the map.
    List<Direction> getAllowedDirections();

    //Updates player current position in map depending on the selected direction.
    void movePlayer(Direction direction);

    //Saves the map in a file.
    void saveMap();
}