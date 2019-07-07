package com.coding.puzzle.view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConsoleInterface extends UserInterface {

    @Override
    public void showMainMenu() {
        this.printSpriteFromFile("ascii-sprites/infinite");
    }

    public void printSpriteFromFile(String path){

        InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);

        if (resourceStream != null) {
            BufferedReader resourceReader = new BufferedReader(new InputStreamReader(resourceStream));
            resourceReader.lines().forEachOrdered(s -> System.out.println(s));
        }

    }
}