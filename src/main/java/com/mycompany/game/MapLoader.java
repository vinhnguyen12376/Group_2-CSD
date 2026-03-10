package com.mycompany.game;

import java.io.File;
import java.util.Scanner;

public class MapLoader {
    
    // Hàm tĩnh (static) để tiện gọi ở mọi nơi: MapLoader.loadMap("map.txt")
    public static DungeonGraph loadMap(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            
            // Đọc dòng đầu tiên: Số lượng phòng (V) và số lối đi (E)
            int numRooms = scanner.nextInt();
            int numEdges = scanner.nextInt();
            
            DungeonGraph graph = new DungeonGraph(numRooms);
            
            // Khởi tạo các phòng cơ bản để nạp vào đồ thị
            for (int i = 1; i <= numRooms; i++) {
                // Truyền null cho quái vật và vật phẩm vì ta đang tập trung test lộ trình
                Room room = new Room(i, "Phong hầm ngục " + i, null, null);
                graph.addRoom(room);
            }
            
            // Đọc các dòng tiếp theo để tạo cạnh (lối đi)
            for (int i = 0; i < numEdges; i++) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                graph.addEdge(u, v);
            }
            
            scanner.close();
            System.out.println("Nap ban do thanh cong tu file: " + filePath);
            return graph;
            
        } catch (Exception e) {
            System.out.println("Loi khi doc file ban do: " + e.getMessage());
            return null;
        }
    }
}