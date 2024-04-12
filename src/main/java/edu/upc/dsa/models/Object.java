package edu.upc.dsa.models;

import java.util.List;

public class Object {
    private String name;
    private String description;
    private int coins;


    public Object(){}

    public Object(String name, String description, int coins){
        this.name = name;
        this.description = description;
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
