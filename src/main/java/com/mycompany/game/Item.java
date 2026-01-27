/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

/**
 *
 * @author ACER
 */
public class Item {

    private String id;
    private String name;
    private String type; 
    private int power;

    public Item(String id, String name, String type, int power) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.power = power;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPower() {
        return power;
    }
}
