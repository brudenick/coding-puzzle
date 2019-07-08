package com.coding.puzzle.view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleDrawer implements Drawer {

    public void printSpriteFromFile(String path){

        InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);

        if (resourceStream != null) {
            BufferedReader resourceReader = new BufferedReader(new InputStreamReader(resourceStream));
            resourceReader.lines().forEachOrdered(s -> System.out.println(s));
        }
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayOptions(List<String> options) {
        System.out.println("\n"+options.get(0));
        options.stream().skip(1).forEachOrdered(option -> System.out.println(options.indexOf(option)+"-"+option));
    }

    public Integer getUserInput(){
        return null;
    }
}