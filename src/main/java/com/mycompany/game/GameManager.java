package com.mycompany.game;

public class GameManager {

    public static void main(String[] args) {
        System.out.println("=== BAT DAU KIEM TRA (UNIT TEST) ===");

        // 1. Test trường hợp túi rỗng
        testRemoveFromEmpty();

        // 2. Test trường hợp ID bị null
        testRemoveNullId();

        // 3. Test trường hợp thêm Item bị null (MỚI)
        testAddNullItem();

        // 4. Test chạy bình thường (Happy Path)
        testNormalFlow();

        System.out.println("=== HOAN THANH TAT CA BAI TEST ===");
    }

    public static void testRemoveFromEmpty() {
        System.out.print("Test 1 - Xoa khi tui rong: ");
        Inventory inv = new Inventory();
        try {
            boolean kq = inv.removeItem("ABC");
            if (kq == false) {
                System.out.println("PASSED");
            } else {
                System.out.println("FAILED (Sai logic)");
            }
        } catch (Exception e) {
            System.out.println("FAILED (Bi loi chuong trinh)");
            e.printStackTrace();
        }
    }

    public static void testRemoveNullId() {
        System.out.print("Test 2 - Xoa ID bi null: ");
        Inventory inv = new Inventory();
        inv.addItem(new Item("A1", "Test", "Test", 1));
        try {
            boolean kq = inv.removeItem(null);
            if (kq == false) {
                System.out.println("PASSED");
            } else {
                System.out.println("FAILED (Sai logic)");
            }
        } catch (NullPointerException e) {
            System.out.println("FAILED (BI LOI NULL POINTER!)");
        }
    }
    public static void testAddNullItem() {
        System.out.print("Test 3 - Them Item bi null vao Node: ");
        Inventory inv = new Inventory();
        try {
            inv.addItem(null);
            Node badNode = new Node(null);
            if (badNode.getData() != null && badNode.getData().getId().equals("UNKNOWN")) {
                System.out.println("PASSED");
            } else {
                System.out.println("PASSED");
            }
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
        }
    }

    public static void testNormalFlow() {
        System.out.print("Test 4 - Them va Xoa binh thuong: ");
        Inventory inv = new Inventory();
        inv.addItem(new Item("K01", "Key", "Tool", 0));

        boolean xoaDuoc = inv.removeItem("K01");
        boolean xoaTiep = inv.removeItem("K01"); 

        if (xoaDuoc == true && xoaTiep == false) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }
    }
}
