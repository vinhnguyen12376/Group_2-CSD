package com.mycompany.game;

public class Node {

    private Item data;
    private Node next;

    // Sửa lại Constructor: Không cho phép data bị null
    public Node(Item data) {
        if (data == null) {
            // Nếu data bị null, ta tạo một Item rỗng để thế chỗ
            // Giúp tránh lỗi NullPointer khi gọi .getData() sau này
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
        // Cũng kiểm tra ở đây cho chắc chắn
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