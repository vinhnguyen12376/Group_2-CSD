/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

/**
 *
 * @author ACER
 */
public class Player {
    private String username;
    private String classType;
    private int hp;
    private int atk;
    private Inventory inventory;

    public Player(String username, String classType) {
        this.username = username;
        this.classType = classType;
        this.inventory = new Inventory();
        initStats();
    }

    private void initStats() {
        switch (classType.toLowerCase()) {
            case "archer": this.hp = 80; this.atk = 25; break;
            case "warrior": this.hp = 150; this.atk = 20; break;
            case "mage": this.hp = 100; this.atk = 30; break;
        }
    }

    // Getters
    public String getUsername() { return username; }
    public Inventory getInventory() { return inventory; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
}
