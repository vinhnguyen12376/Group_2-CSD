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
        myBST = new MonsterBST();
        javaLinkedList = new LinkedList<>();
        shuffledIds = new ArrayList<>();

        for (int i = 0; i < DATA_SIZE; i++) {
            shuffledIds.add(String.format("QE%05d", i));
        }

        Collections.shuffle(shuffledIds);

        for (String id : shuffledIds) {
            Monster m = new Monster(id, "Monster_" + id, 100, 20, null);
            myBST.insert(m);
            javaLinkedList.add(m);
        }
    }

    @Test
    public void testSpeedPerformance() {
        String targetId = shuffledIds.get(DATA_SIZE - 1);
        int iterations = 1000;

        // Đo thời gian Linked List 
        long startList = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            searchInList(targetId);
        }
        long totalList = System.nanoTime() - startList;

        // Đo thời gian BST 
        long startBST = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            myBST.search(targetId);
        }
        long totalBST = System.nanoTime() - startBST;

        // In kết quả so sánh
        System.out.printf("Thoi gian Linked List: %,15d ns\n", totalList);
        System.out.printf("Thoi gian Custom BST : %,15d ns\n", totalBST);

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
