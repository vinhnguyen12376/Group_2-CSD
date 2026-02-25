/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

/**
 *
 * @author ACER
 */
public class MonsterBST {
    private TreeNode root;

    public MonsterBST() {
        this.root = null;
    }

    public void insert(Monster monster) {
        if (this.root == null) {
            this.root = new TreeNode(monster);
        } else {
            insertRec(this.root, monster);
        }
    }
    
    public boolean isEmpty(){
        return this.root == null;
    }

    private TreeNode insertRec(TreeNode current, Monster monster) {
        if (current == null) {
            return new TreeNode(monster);
        }
        if (monster.getId().compareTo(current.getData().getId()) < 0) {
            if (current.getLeft() == null) {
                current.setLeft(new TreeNode(monster));
            } else {
                insertRec(current.getLeft(), monster);
            }
        }
        if (monster.getId().compareTo(current.getData().getId()) > 0) {
            if (current.getRight() == null) {
                current.setRight(new TreeNode(monster));
            } else {
                insertRec(current.getRight(), monster);
            }
        }
        return current;
    }

    public Monster search(String id) {
        TreeNode resultNode = searchRec(this.root, id);
        if (resultNode != null) {
            return resultNode.getData();
        } else {
            return null;
        }
    }

    private TreeNode searchRec(TreeNode current, String id) {
        if (current == null) {
            return null; 
        }
        if(id.compareTo(current.getData().getId()) == 0){
            return current;
        }
        if (id.compareTo(current.getData().getId()) < 0) {
            return searchRec(current.getLeft(), id);
        }
        return searchRec(current.getRight(), id);
    }
    
    public void deleteNode(String id) {
        this.root = deleteRec(this.root, id);
    }
    
    private TreeNode deleteRec(TreeNode current, String id){
        if(current == null){
            return null;
        }
        if(id.compareTo(current.getData().getId()) < 0){
            current.setLeft(deleteRec(current.getLeft(), id));
        }else if(id.compareTo(current.getData().getId()) > 0){
            current.setRight(deleteRec(current.getRight(), id));
        }else{
            
            if(current.getLeft() == null && current.getRight() == null){
                return null;
            }
            
            if(current.getLeft() != null && current.getRight() == null){
                return current.getLeft();
            }
            
            if(current.getLeft() == null && current.getRight() != null){
                return current.getRight();
            }
                Monster leftModeNode = findLeftModeNode(current.getRight());
                current.setData(leftModeNode);
                current.setRight(deleteRec(current.getRight(), leftModeNode.getId()));
        }
        return current;
        
    }
    private Monster findLeftModeNode(TreeNode current){
        if(current == null){
            return null;
        }
        Monster leftModeNode = current.getData();
        while (current.getLeft() != null) {            
            leftModeNode = current.getLeft().getData();
            current = current.getLeft();
        }
        return leftModeNode;
    }
}
