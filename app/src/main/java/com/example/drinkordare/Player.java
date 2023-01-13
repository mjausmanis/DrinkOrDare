package com.example.drinkordare;

public class Player {
    String name;
    int drinks;

    Player(String name) {
        this.name = name;
    }

    public int getDrinks() {
        return drinks;
    }

    public String getName() {
        return name;
    }

    public void addDrinks(int drinks) {
        this.drinks += drinks;
    }

}
