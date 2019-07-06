package com.coding.puzzle.view;

import com.coding.puzzle.Main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConsoleInterface extends UserInterface {
    @Override
    public void showMainMenu() {
        this.printSpriteFromFile("/ascii-sprites/infinite");

    }

    public void printSpriteFromFile(String path){
        try {
            System.out.println(this.getClass().getResource(path).toURI());
            Files.lines(Paths.get(this.getClass().getResource(path).toURI()), StandardCharsets.US_ASCII).forEachOrdered(s1 -> System.out.println(s1));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
