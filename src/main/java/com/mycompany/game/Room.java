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
    private Monster[] monsters; // Chứa 3-4 quái vật mỗi phòng
    private boolean isLocked;
    private Item chestItem; // Chìa khóa hầm tiếp theo

    public Room(int id, String description, Monster[] monsters, Item chestItem) {
        this.id = id;
        this.description = description;
        this.monsters = monsters;
        this.isLocked = (id != 1); // Hầm 1 mở sẵn, hầm khác khóa
        this.chestItem = chestItem;
    }

    // Getters & Setters
    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Monster[] getMonsters() {
        return monsters;
    }

    public Item getChestItem() {
        return chestItem;
    }
}
