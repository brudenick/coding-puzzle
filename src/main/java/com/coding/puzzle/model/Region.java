package com.coding.puzzle.model;

import java.util.*;
import java.util.stream.Collectors;

public class Region implements Location {

    private String name;
    private List<Location> locations;

    @Override
    public String getContent() {
        StringBuilder content = new StringBuilder().append("The region has: ");

        Map<String, Long> map = locations.stream()
                                         .collect(Collectors.groupingBy(Location::getContent, Collectors.counting()));


        map.forEach((k, v) -> content.append((">" +k + ": " + v)));

        return content.toString();
    }

    public void addLocation(Location location){
        locations.add(location);
    }

    public void removeLocation(Location location){
        locations.remove(location);
    }

    public static void main (String[] args){

        StringBuilder content = new StringBuilder().append("The region has: \n");

        List<Location> locations = new ArrayList<>();

        locations.add(new SimpleLocation(LocationName.CASTLE,""));
        locations.add(new SimpleLocation(LocationName.CASTLE,""));
        locations.add(new SimpleLocation(LocationName.CASTLE,""));
        locations.add(new SimpleLocation(LocationName.CITY,""));
        locations.add(new SimpleLocation(LocationName.CITY,""));
        locations.add(new SimpleLocation(LocationName.CITY,""));
        locations.add(new SimpleLocation(LocationName.CASTLE,""));
        locations.add(new SimpleLocation(LocationName.FOREST,""));
        locations.add(new SimpleLocation(LocationName.FOREST,""));

        Map<String, Long> map = locations.stream().collect(Collectors.groupingBy(Location::getContent, Collectors.counting()));

        map.forEach((k, v) -> content.append((">" +k + ": " + v + "\n")));

        System.out.println(content.toString());

    }


}
