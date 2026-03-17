package com.mycompany.game;

import java.io.File;
import java.util.Scanner;

public class DungeonGraph {

    private int numRooms;
    private Inventory[] adjacencyList;
    private Room[] rooms;

    public DungeonGraph(int numRooms) {
        this.numRooms = numRooms;
        this.rooms = new Room[numRooms + 1];
        this.adjacencyList = new Inventory[numRooms + 1];
        for (int i = 1; i <= numRooms; i++) {
            adjacencyList[i] = new Inventory();
        }
    }

    public void addRoom(Room room) {
        if (room != null && room.getId() <= numRooms) {
            rooms[room.getId()] = room;
        }
    }

    public Room getRoom(int id) {
        if (id >= 1 && id <= numRooms) {
            return rooms[id];
        }
        return null;
    }

    public void addEdge(int u, int v) {
        Item roomConnectorU = new Item(String.valueOf(v), "Path to Room " + v, "Connector", 0);
        Item roomConnectorV = new Item(String.valueOf(u), "Path to Room " + u, "Connector", 0);

        adjacencyList[u].addItem(roomConnectorU);
        adjacencyList[v].addItem(roomConnectorV);
    }

    public boolean findExitBFS(int startId, int exitId) {
        for (int i = 1; i <= numRooms; i++) {
            if (rooms[i] != null) {
                rooms[i].setVisited(false);
            }
        }

        int[] queue = new int[numRooms];
        int front = 0, rear = 0;

        queue[rear++] = startId;
        rooms[startId].setVisited(true);

        while (front < rear) {
            int current = queue[front++];
            if (current == exitId) {
                return true;
            }

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
        return false;
    }

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

    public Inventory[] getAdjacencyList() {
        return adjacencyList;
    }

    public int getNumRooms() {
        return numRooms;
    }
}