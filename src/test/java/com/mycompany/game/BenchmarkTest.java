package com.mycompany.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BenchmarkTest {

    private MonsterBST myBST;
    private LinkedList<Monster> javaLinkedList; 
    private List<String> shuffledIds;
    private final int DATA_SIZE = 10000;

    @BeforeEach
    public void setUp() {
        this.myBST = new MonsterBST();
        this.javaLinkedList = new LinkedList<>();
        this.shuffledIds = new ArrayList<>();

        System.out.println("==================================================");
        System.out.println("KHOI TAO DU LIEU KIEM TRA (" + DATA_SIZE + " quai vat)");
        System.out.println("==================================================");
        
        for (int i = 0; i < DATA_SIZE; i++) {
            shuffledIds.add(String.format("QE%05d", i));
        }

        Collections.shuffle(shuffledIds);

        for (String id : shuffledIds) {
            Monster m = new Monster(id, "Monster_" + id, 100, 20, null);
            myBST.insert(m);       
            javaLinkedList.add(m);  
        }
        System.out.println("-> Thanh cong: Da nap " + DATA_SIZE + " quai vat vào cau truc.\n");
    }

    @Test
    public void testPerformanceListVsBST() {
        String targetId = shuffledIds.get(DATA_SIZE - 1);
        int loopCount = 1000; 

        System.out.println("==================================================");
        System.out.println("CUOC DUA HIEU NANG (" + loopCount + " lan tim kiem)");
        System.out.println("Ma ID muc tieu: " + targetId);
        System.out.println("==================================================");

        long startListTime = System.nanoTime();
        for (int i = 0; i < loopCount; i++) {
            searchInList(targetId);
        }
        long endListTime = System.nanoTime();
        long totalListTime = endListTime - startListTime;

        long startBSTTime = System.nanoTime();
        for (int i = 0; i < loopCount; i++) {
            myBST.search(targetId);
        }
        long endBSTTime = System.nanoTime();
        long totalBSTTime = endBSTTime - startBSTTime;

        System.out.printf("[O(n)]     Thoi gian Java Linked List: %,15d ns\n", totalListTime);
        System.out.printf("[O(log n)] Thoi gian Custom BST      : %,15d ns\n", totalBSTTime);
        System.out.println("--------------------------------------------------");

        if (totalBSTTime > 0 && totalBSTTime < totalListTime) { 
            double times = (double) totalListTime / totalBSTTime;
            System.out.printf("=> KET LUAN: BST nhanh gap %.2f lan so voi Linked List!\n", times);
            System.out.println("=> TRANG THAI: Yeu cau toi uu hieu nang da dat.");
        } else {
            System.out.println("=> CANH BAO: BST dang cham hon du kien. Kiem tra lai do can bang cua cay.");
        }
        
        assertTrue(totalBSTTime < totalListTime, "Loi hieu nang: BST phai nhanh hon Linked List!");
        
        Monster foundInBST = myBST.search(targetId);
        assertNotNull(foundInBST, "Loi logic: Khong tim thay quai vat trong BST!");
        assertEquals(targetId, foundInBST.getId(), "Loi logic: Sai lệch ID khi tim kiem!");
    }

    private Monster searchInList(String id) {
        for (Monster m : javaLinkedList) {
            if (m.getId().equals(id)) {
                return m; 
            }
        }
        return null; 
    }
}