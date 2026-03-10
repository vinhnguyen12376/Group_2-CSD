package com.mycompany.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    public void testBFSFindExit() {

        System.out.println("Test BFS tim duong tu 7 -> 2");

        // tạo đồ thị 8 phòng
        DungeonGraph graph = new DungeonGraph(8);

        // thêm phòng
        graph.addRoom(new Room(1, "Room1", null, null));
        graph.addRoom(new Room(2, "Room2", null, null));
        graph.addRoom(new Room(3, "Room3", null, null));
        graph.addRoom(new Room(4, "Room4", null, null));
        graph.addRoom(new Room(5, "Room5", null, null));
        graph.addRoom(new Room(6, "Room6", null, null));
        graph.addRoom(new Room(7, "Room7", null, null));
        graph.addRoom(new Room(8, "Room8", null, null));

        // tạo đường đi giống cây trong hình
        graph.addEdge(7,3);
        graph.addEdge(7,8);
        graph.addEdge(3,1);
        graph.addEdge(3,4);
        graph.addEdge(1,2);

        // kiểm tra BFS chạy không lỗi
        assertDoesNotThrow(() -> {
            graph.findExitBFS(7,8);
        });
    }


    @Test
    public void testLoadMapFromFile() {

        System.out.println("Test doc map.txt");

        DungeonGraph graph = MapLoader.loadMap("map.txt");

        // đồ thị phải tồn tại
        assertNotNull(graph);

        // chạy BFS thử
        assertDoesNotThrow(() -> {
            graph.findExitBFS(7,2);
        });
    }
}