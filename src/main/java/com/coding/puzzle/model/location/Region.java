package com.coding.puzzle.model.location;

import java.util.*;
import java.util.stream.Collectors;

public class Region implements Location {

    private List<Location> locations;

    public Region (){
        locations = new ArrayList<>();
    }

    @Override
    public Integer getSize() {
        return locations.stream().collect(Collectors.summingInt(Location::getSize));
    }

    @Override
    public String getSprite() {
        return null;
    }

    public void addLocation(Location location){
        locations.add(location);
    }

    public void removeLocation(Location location){
        locations.remove(location);
    }


    //TODO: Move to tests
    public static void main (String[] args){

        StringBuilder content = new StringBuilder().append("The region has: \n");

        Region r1 = new Region();
        r1.addLocation(new SimpleLocation(LocationName.CITY,""));
        r1.addLocation(new SimpleLocation(LocationName.CITY,""));
        r1.addLocation(new SimpleLocation(LocationName.CITY,""));
        Region r2 = new Region();
        r2.addLocation(new SimpleLocation(LocationName.CITY,""));
        r2.addLocation(new SimpleLocation(LocationName.CITY,""));
        r2.addLocation(new SimpleLocation(LocationName.CITY,""));
        r1.addLocation(r2);

        System.out.println("r1 Size :" + r1.getSize());
        System.out.println("r2 Size :" + r2.getSize());

    }


}
