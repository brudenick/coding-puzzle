package com.coding.puzzle.model.character;

import java.util.*;

public class Character {
    private String name;

    private Integer maxHp = 100;
    private Integer currentHp = 100;
    private Integer currentExperience = 0;

    private Double baseDamage = (Math.random()*2.5)+7.5;
    private Double baseDefense = (Math.random()*2.5)+2.5;

    private CharacterType characterType;

    private Map<String,Modifier> modifiers = new HashMap<>();

    public Character (String name, String race, CharacterType characterType){
        this.name = name;
        this.characterType = characterType;
        if (characterType.equals(CharacterType.ENEMY)){
            this.maxHp = Math.toIntExact(Math.round(Math.random()*25)+25); //Random HP between 25 and 50;
            this.currentHp = maxHp;
            this.addModifier(Arrays.stream(Modifier.values()).skip(new Random().nextInt(Modifier.values().length-1)).findFirst().get());
        }else{
            this.addModifier(Modifier.valueOf(race));
        }
    }

    //Creates a character with the given list of attributes.*Used when loading a game*
    public Character(List<String> attributes) {
        this.name = attributes.get(0);attributes.remove(0);
        this.maxHp = Integer.parseInt(attributes.get(0));attributes.remove(0);
        this.currentHp = Integer.parseInt(attributes.get(0));attributes.remove(0);
        this.currentExperience = Integer.parseInt(attributes.get(0));attributes.remove(0);
        this.baseDamage = Double.parseDouble(attributes.get(0));attributes.remove(0);
        this.baseDefense = Double.parseDouble(attributes.get(0));attributes.remove(0);
        this.characterType = CharacterType.valueOf(attributes.get(0));attributes.remove(0);
        for(String modifier : attributes){
            this.addModifier(Modifier.valueOf(modifier));
        }
    }

    public Integer getDamage(){
        return Math.toIntExact(Math.round(modifiers.values().stream().map(Modifier::getDamageMultiplyer).reduce(this.baseDamage, (a, b) -> a * b)));
    }

    public Integer getDefense(){
        return Math.toIntExact(Math.round(modifiers.values().stream().map(Modifier::getDefenseMultiplyer).reduce(this.baseDefense, (a, b) -> a * b)));
    }


    public void receiveDamage(Integer damageReceived){
        this.currentHp -= Math.max(0, damageReceived-this.getDefense());
    }

    public void addModifier(Modifier modifier){
        modifiers.put(modifier.getName(),modifier);
    }

    public void removeModifier(Modifier modifier) { modifiers.remove(modifier.getName()); }

    public void increaseExperience(Integer expGained){
        currentExperience+=expGained;
    }

    public Integer getCurrentExperience(){
        return currentExperience;
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

    public boolean isAlive() {
        return currentHp>0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name+"\n");
        stringBuilder.append(maxHp+"\n");
        stringBuilder.append(currentHp+"\n");
        stringBuilder.append(currentExperience+"\n");
        stringBuilder.append(baseDamage+"\n");
        stringBuilder.append(baseDefense+"\n");
        stringBuilder.append(characterType.toString()+"\n");
        modifiers.values().stream().map(Modifier::toString).forEach(s -> stringBuilder.append(s+","));
        return stringBuilder.toString().substring(0,stringBuilder.length()-1);//Remove the last \n
    }
}
