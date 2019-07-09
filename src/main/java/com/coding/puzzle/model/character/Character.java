package com.coding.puzzle.model.character;

import java.util.HashMap;
import java.util.Map;

public class Character {
    private String name;

    private Integer maxHp = 100;
    private Integer currentHp = 100;
    private Integer currentExperience = 0;

    private Double baseDamage = 10.0;
    private Double baseDefense = 10.0;

    private CharacterType characterType;

    private Map<String,Modifier> modifiers = new HashMap<>();

    public Character (String name, String race, CharacterType characterType){
        this.name = name;
        this.characterType = characterType;
        this.addModifier(Modifier.getModifierByName(race));
    }

    public Integer getDamage(){
        return Math.toIntExact(Math.round(modifiers.values().stream().map(Modifier::getDamageMultiplyer).reduce(this.baseDamage, (a, b) -> a * b)));
    }

    public Integer getDefense(){
        return Math.toIntExact(Math.round(modifiers.values().stream().map(Modifier::getDefenseMultiplyer).reduce(this.baseDefense, (a, b) -> a * b)));
    }

    public static void main(String[] args){
        Character c = new Character("name",Modifier.ORC.getName(),CharacterType.HERO);
        System.out.println(c.getDamage());
    }

    public void addModifier(Modifier modifier){
        modifiers.put(modifier.getName(),modifier);
    }

    public void removeModifier(Modifier modifier) { modifiers.remove(modifier.getName()); }

    public void increaseExperience(Integer expGained){
        currentExperience+=expGained;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(Integer maxHp) {
        this.maxHp = maxHp;
    }

    public Integer getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(Integer currentHp) {
        this.currentHp = currentHp;
    }

    public void setBaseDamage(Double baseDamage) {
        this.baseDamage = baseDamage;
    }

    public void setBaseDefense(Double baseDefense) {
        this.baseDefense = baseDefense;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }
}


