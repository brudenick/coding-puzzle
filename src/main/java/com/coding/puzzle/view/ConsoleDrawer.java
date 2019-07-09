package com.coding.puzzle.view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleDrawer implements Drawer {

    public void printFromFile(String path){

        InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);

        if (resourceStream != null) {
            BufferedReader resourceReader = new BufferedReader(new InputStreamReader(resourceStream));
            resourceReader.lines().forEachOrdered(s -> System.out.println(s));
        }
    }

    @Override
    public void displayMessage(String message) {
        System.out.println();
        System.out.println(message);
    }

    @Override
    public void displayOptions(List<String> options) {
        options.stream().forEachOrdered(option -> System.out.println(options.indexOf(option)+1+"-"+option));
    }

    public Integer getUserInput(){
        return null;
    }
}