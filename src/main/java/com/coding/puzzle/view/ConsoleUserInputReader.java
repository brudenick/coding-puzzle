package com.coding.puzzle.view;

import java.util.Scanner;

public class ConsoleUserInputReader implements UserInputReader {

    @Override
    public String getInputString() {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        userInput = scanner.nextLine();

        while (userInput.equals("")) {
            System.out.println("Please insert a value.");
            userInput = scanner.nextLine();
        }
        return userInput;
    }
}
