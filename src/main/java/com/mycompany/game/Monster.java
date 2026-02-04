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

    private String name;
    private int hp;
    private int atk;
    private Item dropItem;

    public Monster(String name, int hp, int atk, Item dropItem) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.dropItem = dropItem;
    }

    public String getName() {
        return this.name;
    }

    public int getHp() {
        return this.hp;
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
}
