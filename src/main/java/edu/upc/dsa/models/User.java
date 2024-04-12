package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    private String id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String birthDate;
    private int dsaCoins;
    private List<Object> purchasedObjects;

    public List<Object> getPurchasedObjects() {
        return purchasedObjects;
    }

    public void setPurchasedObjects(List<Object> purchasedObjects) {
        this.purchasedObjects = purchasedObjects;
    }

    public User(String name, String surname, String birthDate, String email, String password) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.dsaCoins = 50;
        this.purchasedObjects = new ArrayList<>();
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getDsaCoins() {
        return dsaCoins;
    }

    public void setDsaCoins(int dsaCoins) {
        this.dsaCoins = dsaCoins;
    }
}
