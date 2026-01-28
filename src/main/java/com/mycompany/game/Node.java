package com.mycompany.game;

public class Node {

    private Item data;
    private Node next;

    public Node(Item data) {
        if (data == null) {
         
            this.data = new Item("UNKNOWN", "Empty Item", "None", 0);
            System.out.println("Canh bao: Da tu dong xu ly mot Node bi null data!");
        } else {
            this.data = data;
        }
        this.next = null;
    }
    
    public Item getData() {
        return data;
    }

    public void setData(Item data) {
        if (data != null) {
            this.data = data;
        }
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}