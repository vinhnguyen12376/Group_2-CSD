/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

/**
 *
 * @author ACER
 */
public class Monster {

    private String id;
    private String name;
    private int hp;
    private int atk;
    private Item dropItem;

    public Monster(String id, String name, int hp, int atk, Item dropItem) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.dropItem = dropItem;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getHp() {
        return this.hp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return this.atk;
    }

    public Item getDropItem() {
        return this.dropItem;
    }

    @Override
    public String toString() {
        String dropInfo = (dropItem != null) ? dropItem.toString() : "Nothing";
        return "[" + id + "] " + name + " | HP: " + hp + " | Drop: " + dropInfo;
    }
}
