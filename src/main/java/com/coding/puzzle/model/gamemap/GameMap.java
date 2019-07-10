package com.coding.puzzle.model.gamemap;

import com.coding.puzzle.model.options.Direction;

import java.io.IOException;
import java.util.List;

public interface GameMap {

    /**
     * Builds a map with random locations and random initial location.
     * @param size
     */
    void buildMap(Integer size);

    /**
     * Returns the current location.
     * @return
     */
    Location getCurrentLocation();

    /**
     * Returns a list with the allowed directions the player can take from current position in the map.
     * @return
     */
    List<Direction> getAllowedDirections();

    /**
     * Updates player current position in map depending on the selected direction.
     * @param direction
     */
    void movePlayer(Direction direction);

    /**
     * Saves the map in a file named like the character.
     * @param characterName
     * @throws IOException
     */
    void saveMap(String characterName) throws IOException;

    /**
     * Load the map from a filePath.
     * @param mapName
     * @throws IOException
     */
    void loadMap(String mapName) throws IOException;
}