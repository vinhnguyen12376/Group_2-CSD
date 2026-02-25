package com.mycompany.game;

public class TestBST {

    public static void main(String[] args) {
        System.out.println("=== BAT DAU KIEM TRA DU AN: DUNGEON CRAWLER ===");

        MonsterBST monsterDex = new MonsterBST();

        Item door = new Item("I01", "Canh cua than ki", "Weapon", 15);
        Item quat = new Item("I02", "Quat ba tieu", "Weapon", 50);
        Item khan = new Item("I03", "Khan phan don", "Weapon", 18);
        Item gun = new Item("I04", "Sung khong khi", "Weapon", 900);
        Item tui = new Item("I05", "Tui than ki", "Weapon", 1000);

        System.out.println("\n[KIEM TRA 1] Dang nap Quai vat vao cay BST...");
        try {
            monsterDex.insert(new Monster("M05", "Nobita", 5000, 300, gun)); 
            monsterDex.insert(new Monster("M03", "Shizuka", 200, 50, quat));
            monsterDex.insert(new Monster("M08", "Suneo", 300, 80, khan));   
            monsterDex.insert(new Monster("M01", "Chaien", 50, 10, door));
            monsterDex.insert(new Monster("M04", "Tamako", 150, 40, null));  
            monsterDex.insert(new Monster("M07", "Nobisuke", 100, 25, null)); 
            monsterDex.insert(new Monster("M02", "Doraemon", 500, 90, null)); 

            System.out.println("=> Trang thai: Nap du lieu thanh cong.");
        } catch (Exception e) {
            System.err.println("=> Trang thai: Nap du lieu that bai: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n[KIEM TRA 2] Cac tinh huong xoa node...");

        System.out.print("- Truong hop A: Xoa M04 (Node la): ");
        monsterDex.deleteNode("M04");
        System.out.println(monsterDex.search("M04") == null ? "THANH CONG" : "THAT BAI");

        System.out.print("- Truong hop B: Xoa M08 (Co 1 con): ");
        monsterDex.deleteNode("M08");
        boolean m08Deleted = (monsterDex.search("M08") == null);
        boolean m07Exists = (monsterDex.search("M07") != null);
        System.out.println(m08Deleted && m07Exists ? "THANH CONG" : "THAT BAI");

        System.out.print("- Truong hop C: Xoa M05 (Goc/Co 2 con): ");
        monsterDex.deleteNode("M05");
        boolean m05Deleted = (monsterDex.search("M05") == null);
        boolean treeIntegrity = (monsterDex.search("M03") != null && monsterDex.search("M07") != null);
        System.out.println(m05Deleted && treeIntegrity ? "THANH CONG" : "THAT BAI");

        System.out.println("\n=== KIEM TRA HOAN TAT ===");
    }
}