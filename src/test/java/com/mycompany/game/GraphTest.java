package com.mycompany.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    public void testBFSFindExit() {
        System.out.println("Test BFS tim duong tu 7 -> 2");
        DungeonGraph graph = new DungeonGraph(8);
        graph.addRoom(new Room(1, "Room1", null, null));
        graph.addRoom(new Room(2, "Room2", null, null));
        graph.addRoom(new Room(3, "Room3", null, null));
        graph.addRoom(new Room(4, "Room4", null, null));
        graph.addRoom(new Room(5, "Room5", null, null));
        graph.addRoom(new Room(6, "Room6", null, null));
        graph.addRoom(new Room(7, "Room7", null, null));
        graph.addRoom(new Room(8, "Room8", null, null));

        graph.addEdge(7, 3);
        graph.addEdge(7, 8);
        graph.addEdge(3, 1);
        graph.addEdge(3, 4);
        graph.addEdge(1, 2);

        boolean found = graph.findExitBFS(7, 2);
        System.out.println("Ket qua tim duong: " + found);
        assertTrue(found);
    }

    @Test
    public void testBFSNoPath() {
        System.out.println("Test BFS khong co duong");
        DungeonGraph graph = new DungeonGraph(4);
        graph.addRoom(new Room(1, "R1", null, null));
        graph.addRoom(new Room(2, "R2", null, null));
        graph.addRoom(new Room(3, "R3", null, null));
        graph.addRoom(new Room(4, "R4", null, null));
        graph.addEdge(1, 2);
        boolean found = graph.findExitBFS(1, 4);
        System.out.println("Ket qua tim duong: " + found);
        assertFalse(found);
    }

    @Test
    public void testLoadMapFromFile() {
        System.out.println("Test doc map.txt");
        DungeonGraph graph = MapLoader.loadMap("map.txt");
        assertNotNull(graph);
        boolean found = graph.findExitBFS(7, 2);
        System.out.println("Ket qua BFS sau khi doc file: " + found);
        assertTrue(found);
    }
}