package com.mycompany.game;

public class TreeNode {
    private Monster data;
    private TreeNode left;
    private TreeNode right;
    public TreeNode(Monster data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
    public Monster getData() {
        return this.data;
    }
    public void setData(Monster data) {
        this.data = data;
    }
    public TreeNode getLeft() {
        return this.left;
    }
    public void setLeft(TreeNode left) {
        this.left = left;
    }
    public TreeNode getRight() {
        return this.right;
    }
    public void setRight(TreeNode right) {
        this.right = right;
    }
}
