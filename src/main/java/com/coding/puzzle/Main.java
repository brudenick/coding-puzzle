package com.coding.puzzle;

import com.coding.puzzle.view.ConsoleInterface;
import com.coding.puzzle.view.UserInterface;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {

    private static final UserInterface cui = new ConsoleInterface();

    public static void main(String[] args) throws URISyntaxException, IOException {
        String s;
        Scanner in = new Scanner(System.in);
        System.out.println("Quieres ir al castillo? SI/NO");
        s = in.nextLine();
        System.out.println("Elegiste:"+ s);

        cui.showMainMenu();

//        if (s.equalsIgnoreCase("si")){
//            Files.lines(Paths.get(Main.class.getResource("/ascii-sprites/castle_1").toURI()),StandardCharsets.US_ASCII).forEachOrdered(s1 -> System.out.println(s1));
//        }
    }

}