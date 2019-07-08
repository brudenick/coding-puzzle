package com.coding.puzzle.model.options;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainMenuOption {
    public static final String NEW_GAME = "New Game";
    public static final String LOAD_GAME = "Load Game";
    public static final String INSTRUCTIONS = "Instructions";
    public static final String EXIT = "Exit";
    public static final String BACK = "Back";
    public static final String SELECT_RACE = "Select Race";
    public static final String SELECT_GENDER = "Select Gender";
    public static final String SELECT_MAP = "Select Map";

    public static List<String> getValues(){
        List<String> values = new ArrayList<>();
        List<Field> fields = Arrays.stream(MainMenuOption.class.getDeclaredFields()).collect(Collectors.toList());
        for (Field f : fields){
            try {
                values.add((String)f.get(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return values;
    }

    public static void main(String [] args){
        MainMenuOption.getValues().stream().forEach(System.out::println);
    }

}
