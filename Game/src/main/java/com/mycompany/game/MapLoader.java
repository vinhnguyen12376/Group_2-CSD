package com.mycompany.game;

import java.io.File;
import java.util.Scanner;

public class MapLoader {
    
    public static DungeonGraph loadMap(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            
            int numRooms = scanner.nextInt();
            int numEdges = scanner.nextInt();
            
            DungeonGraph graph = new DungeonGraph(numRooms);
            
            for (int i = 1; i <= numRooms; i++) {
                Room room = new Room(i, "Phong hầm ngục " + i, null, null);
                graph.addRoom(room);
            }
            
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