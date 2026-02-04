/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

/**
 *
 * @author ACER
 */
public class Room {

    private int id;
    private String description;
    private Monster[] monsters; 
    private boolean isLocked;
    private Item chestItem; 

    public Room(int id, String description, Monster[] monsters, Item chestItem) {
        this.id = id;
        this.description = description;
        this.monsters = monsters;
        this.isLocked = (id != 1);
        this.chestItem = chestItem;
    }
    public boolean isLocked() {
        return this.isLocked;
    }

    public void setLocked(boolean locked) {
        this.isLocked = locked;
    }

    public Monster[] getMonsters() {
        return this.monsters;
    }

    public Item getChestItem() {
        return this.chestItem;
    }
}
