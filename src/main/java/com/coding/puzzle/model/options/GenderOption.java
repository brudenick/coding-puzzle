package com.coding.puzzle.model.options;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenderOption {
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";

    public static List<String> getValues(){
        List<String> values = new ArrayList<>();
        List<Field> fields = Arrays.stream(RaceOption.class.getDeclaredFields()).collect(Collectors.toList());
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
        RaceOption.getValues().stream().forEach(System.out::println);
    }
}
