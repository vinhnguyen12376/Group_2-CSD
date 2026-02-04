package com.mycompany.game;

public class Inventory {

    private Node head;

    public Inventory() {
        this.head = null;
    }

    public void addItem(Item item) {
        if (item == null) {
            System.out.println("Loi: Khong the them item null vao tui!");
            return;
        }

        Node newNode = new Node(item);
        if (head == null) {
            this.head = newNode;
        } else {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(newNode);
        }
    }

    public boolean removeItem(String id) {
        if (id == null) {
            return false;
        }
        if (head == null) {
            return false;
        }

        Item headItem = head.getData();
        if (headItem != null && id.equals(headItem.getId())) {
            head = head.getNext();
            return true;
        }

        Node current = head;
        while (current.getNext() != null) {
            Node nextNode = current.getNext();
            Item nextItem = nextNode.getData();
            if (nextItem != null) {
                String nextId = nextItem.getId();
                if (id.equals(nextId)) {
                    current.setNext(nextNode.getNext());
                    return true;
                }
            }
            current = current.getNext();
        }
        return false;
    }
}
