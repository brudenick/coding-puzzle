package com.coding.puzzle.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUserInputReader implements UserInputReader {

    @Override
    public Integer getOptionSelected(Integer optionsSize) {
        Scanner scanner = new Scanner(System.in);
        Integer optionSelected = -1;
        while (optionSelected.equals(-1)){
            try{
                //TODO: this can be done prettier
                System.out.print("Selection: ");
                optionSelected = Integer.parseInt(scanner.nextLine());
                if (optionSelected<1 || optionSelected>=optionsSize){
                    optionSelected = -1;
                    throw new InputMismatchException();
                }
            }catch (InputMismatchException | NumberFormatException ex){
                System.out.println("The selected option doesn't exist, select a number within the options.");
            }
        }
        return optionSelected;
    }

    @Override
    public String getString(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
