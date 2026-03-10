package com.mycompany.game;

import java.io.File;
import java.util.Scanner; // Chỉ dùng Scanner để đọc file theo yêu cầu [cite: 84]

public class DungeonGraph {

    private int numRooms;
    private Inventory[] adjacencyList; // Mảng các Linked List tự xây dựng 
    private Room[] rooms; // Lưu trữ thông tin các phòng

    public DungeonGraph(int numRooms) {
        this.numRooms = numRooms;
        this.rooms = new Room[numRooms + 1]; // Giả sử ID phòng chạy từ 1
        this.adjacencyList = new Inventory[numRooms + 1];
        for (int i = 1; i <= numRooms; i++) {
            adjacencyList[i] = new Inventory(); // Khởi tạo danh sách kề cho mỗi phòng [cite: 42]
        }
    }

    // Thêm phòng vào đồ thị
    public void addRoom(Room room) {
        if (room != null && room.getId() <= numRooms) {
            rooms[room.getId()] = room;
        }
    }

    public void addEdge(int u, int v) {
        Item roomConnectorU = new Item(String.valueOf(v), "Path to Room " + v, "Connector", 0);
        Item roomConnectorV = new Item(String.valueOf(u), "Path to Room " + u, "Connector", 0);

        adjacencyList[u].addItem(roomConnectorU);
        adjacencyList[v].addItem(roomConnectorV);
    }

    public void findExitBFS(int startId, int exitId) {

        // Reset visited
        for (int i = 1; i <= numRooms; i++) {
            if (rooms[i] != null) {
                rooms[i].setVisited(false);
            }
        }

        int[] queue = new int[numRooms];
        int front = 0;
        int rear = 0;

        // Thêm phòng bắt đầu vào queue
        queue[rear++] = startId;
        rooms[startId].setVisited(true);

        while (front < rear) {

            int current = queue[front++];

            // Kiểm tra nếu đã tới exit
            if (current == exitId) {
                System.out.println("Tim thay loi thoat: " + exitId);
                return;
            }

            // Duyệt các phòng kề
            Node node = adjacencyList[current].getHead();

            while (node != null) {

                int neighbor = Integer.parseInt(node.getData().getId());

                if (!rooms[neighbor].isVisited()) {

                    rooms[neighbor].setVisited(true);
                    queue[rear++] = neighbor;

                }

                node = node.getNext();
            }
        }

        System.out.println("Khong tim thay duong den loi thoat.");
    }

    // Đọc dữ liệu bản đồ từ file [cite: 84]
    public void loadMapFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            if (sc.hasNextInt()) {
                int V = sc.nextInt();
                int E = sc.nextInt();
                for (int i = 0; i < E; i++) {
                    int u = sc.nextInt();
                    int v = sc.nextInt();
                    addEdge(u, v);
                }
            }
            sc.close();
            System.out.println("Nap ban do thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi doc file ban do: " + e.getMessage());
        }
    }
}
