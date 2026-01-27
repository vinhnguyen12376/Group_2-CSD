package com.mycompany.game;

public class Inventory {

    private Node head;

    public Inventory() {
        this.head = null;
    }

    public void addItem(Item item) {
        // 1. Nếu item đưa vào là null thì không làm gì cả
        if (item == null) {
            System.out.println("Loi: Khong the them item null vao tui!");
            return;
        }

        Node newNode = new Node(item);
        
        // Thêm vào đầu hoặc cuối tùy logic (ở đây code cũ của bạn là thêm vào cuối)
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

    // Hàm xóa được viết lại theo phong cách "Dễ hiểu - An toàn"
    public boolean removeItem(String id) {
        // TRƯỜNG HỢP BIÊN 1: Đầu vào bị null hoặc Túi rỗng
        if (id == null) {
            return false; // Không có ID thì không xóa được
        }
        if (head == null) {
            return false; // Túi rỗng thì không có gì để xóa
        }

        // TRƯỜNG HỢP BIÊN 2: Xóa ngay tại Node đầu tiên (Head)
        // Lấy dữ liệu ra biến riêng để kiểm tra cho dễ
        Item headItem = head.getData();
        
        // Kiểm tra an toàn: headItem có tồn tại không? ID có khớp không?
        if (headItem != null && id.equals(headItem.getId())) {
            head = head.getNext(); // Cập nhật head mới
            return true;
        }

        // TRƯỜNG HỢP 3: Tìm ở các Node phía sau
        Node current = head;

        // Duyệt danh sách
        while (current.getNext() != null) {
            Node nextNode = current.getNext();
            Item nextItem = nextNode.getData();

            // Kiểm tra an toàn trước khi so sánh
            if (nextItem != null) {
                String nextId = nextItem.getId();
                
                // So sánh ID (Dùng equals vì là String)
                if (id.equals(nextId)) {
                    // Tìm thấy! Nối node hiện tại với node sau nữa (bỏ qua nextNode)
                    current.setNext(nextNode.getNext());
                    return true;
                }
            }

            // Chuyển sang node tiếp theo
            current = current.getNext();
        }

        // Đi hết danh sách mà không thấy
        return false;
    }
}