package com.mycompany.game;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import java.util.*;

/**
 * GameServer - Xử lý logic và API cho trò chơi Dungeon Crawler.
 */
public class GameServer {

    private static DungeonGraph dungeon;
    private static Player player;
    private static int currentRoomId = 7;
    private static boolean isGameOver = false;

    public static void main(String[] args) {
        initGameData();

        // Khởi tạo server Javalin
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public", Location.CLASSPATH);
            config.addStaticFiles("C:/Users/ACER/Documents/NetBeansProjects/Game/anh", Location.EXTERNAL);
        }).start(7000);

        System.out.println("--- DUNGEON CRAWLER SERVER v2.2 (Cleaned) ---");
        System.out.println("Giao diện: http://localhost:7000");

        // Đăng ký các Endpoint API
        app.get("/api/status", GameServer::handleGetStatus);
        app.post("/api/move", GameServer::handlePostMove);
        app.post("/api/attack", GameServer::handlePostAttack);
        app.get("/api/find-exit", GameServer::handleGetFindExit);
    }

    // --- HANDLERS (Xử lý API) ---

    /**
     * Lấy trạng thái hiện tại của người chơi và phòng.
     */
    private static void handleGetStatus(Context ctx) {
        Room r = dungeon.getRoom(currentRoomId);
        boolean isLocked = r != null && r.isLocked();
        Monster monster = findFirstAliveMonster(r);
        String firstMonster = (monster != null) ? monster.getName() : null;

        Map<String, Object> status = new LinkedHashMap<>();
        status.put("playerName", player.getUsername());
        status.put("hp", player.getHp());
        status.put("currentRoom", currentRoomId);
        status.put("isGameOver", isGameOver);
        status.put("isLocked", isLocked);
        status.put("firstMonsterName", firstMonster);
        status.put("inventory", getInventoryList());
        status.put("hasMap", checkInventoryFor("Bản đồ hầm ngục"));

        ctx.json(status);
    }

    /**
     * Xử lý di chuyển giữa các phòng.
     */
    private static void handlePostMove(Context ctx) {
        if (isGameOver) {
            ctx.result("Game đã kết thúc!");
            return;
        }

        Room currentRoom = dungeon.getRoom(currentRoomId);
        if (currentRoom != null && currentRoom.isLocked()) {
            ctx.result("ERROR|Phòng đã bị khóa! Hãy đánh bại quái vật trước.");
            return;
        }

        try {
            int nextRoomId = Integer.parseInt(ctx.queryParam("target"));
            if (!checkAdjacency(currentRoomId, nextRoomId)) {
                ctx.result("ERROR|Không thể di chuyển trực tiếp từ Phòng " + currentRoomId + " đến Phòng " + nextRoomId
                        + "!");
                return;
            }

            Room nextRoom = dungeon.getRoom(nextRoomId);
            currentRoomId = nextRoomId;
            if (nextRoom != null && hasAliveMonsters(nextRoom)) {
                nextRoom.setLocked(true);
            }

            ctx.result("SUCCESS|Bạn đã bước vào Phòng " + currentRoomId);
        } catch (Exception e) {
            ctx.status(400).result("ERROR|ID phòng không hợp lệ");
        }
    }

    /**
     * Xử lý hành động tấn công quái vật.
     */
    private static void handlePostAttack(Context ctx) {
        if (isGameOver) {
            ctx.result("Bạn đã thắng game rồi!");
            return;
        }

        Room currentRoom = dungeon.getRoom(currentRoomId);
        if (currentRoom == null || currentRoom.getMonsters() == null) {
            ctx.result("Phòng này trống trải.");
            return;
        }

        Monster target = findFirstAliveMonster(currentRoom);
        if (target == null) {
            ctx.result("Phòng này không còn ai cản đường.");
            return;
        }

        target.setHp(target.getHp() - 50);
        String msg = "Conan tấn công " + target.getName() + "!";

        if (target.getHp() <= 0) {
            msg += " " + target.getName() + " đã bị hạ gục!";
            if (target.getDropItem() != null) {
                player.getInventory().addItem(target.getDropItem());
                msg += " Nhặt được: " + target.getDropItem().getName();

                if (target.getDropItem().getName().equals("Chìa khóa vàng")) {
                    isGameOver = true;
                    msg += " | CHÚC MỪNG! Bạn đã thoát khỏi hầm ngục!";
                }
            }

            if (!hasAliveMonsters(currentRoom)) {
                currentRoom.setLocked(false);
                msg += " | Phòng đã được MỞ KHÓA!";
            }
        }
        ctx.result(msg);
    }

    /**
     * Tìm đường thoát ngắn nhất bằng BFS.
     */
    private static void handleGetFindExit(Context ctx) {
        if (!checkInventoryFor("Bản đồ hầm ngục")) {
            ctx.result("Bạn chưa có Bản đồ hầm ngục để sử dụng tính năng tìm đường!");
            return;
        }

        int start = currentRoomId;
        int end = 2; // Lối thoát mặc định

        if (start == end) {
            ctx.result("Bản đồ: Bạn đã đứng tại lối thoát!");
            return;
        }

        List<Integer> pathList = findPathInternal(dungeon, start, end);
        if (pathList != null && !pathList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pathList.size(); i++) {
                sb.append(pathList.get(i));
                if (i < pathList.size() - 1)
                    sb.append(" -> ");
            }
            ctx.result("Bản đồ chỉ dẫn lộ trình ngắn nhất: " + sb.toString());
        } else {
            ctx.result("Bản đồ: Không tìm thấy lộ trình tới lối thoát!");
        }
    }

    // --- LOGIC HỖ TRỢ (Helpers) ---

    /**
     * Thuật toán BFS tìm đường trong đồ thị.
     */
    private static List<Integer> findPathInternal(DungeonGraph graph, int startId, int exitId) {
        if (startId == exitId)
            return Collections.singletonList(startId);

        int numRooms = graph.getNumRooms();
        Inventory[] adj = graph.getAdjacencyList();

        // Đánh dấu lại tất cả phòng là chưa thăm
        for (int i = 1; i <= numRooms; i++) {
            Room r = graph.getRoom(i);
            if (r != null)
                r.setVisited(false);
        }

        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> parentMap = new HashMap<>();

        queue.add(startId);
        Room startRoom = graph.getRoom(startId);
        if (startRoom != null)
            startRoom.setVisited(true);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == exitId) {
                List<Integer> path = new ArrayList<>();
                Integer temp = current;
                while (temp != null) {
                    path.add(0, temp);
                    temp = parentMap.get(temp);
                }
                return path;
            }

            Node node = (adj[current] != null) ? adj[current].getHead() : null;
            while (node != null) {
                int neighbor = Integer.parseInt(node.getData().getId());
                Room neighborRoom = graph.getRoom(neighbor);
                if (neighborRoom != null && !neighborRoom.isVisited()) {
                    neighborRoom.setVisited(true);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
                node = node.getNext();
            }
        }
        return null;
    }

    private static List<Map<String, String>> getInventoryList() {
        List<Map<String, String>> list = new ArrayList<>();
        Node current = player.getInventory().getHead();
        while (current != null) {
            Map<String, String> item = new HashMap<>();
            item.put("name", current.getData().getName());
            item.put("type", current.getData().getType());
            list.add(item);
            current = current.getNext();
        }
        return list;
    }

    private static boolean checkAdjacency(int u, int v) {
        Inventory[] adj = dungeon.getAdjacencyList();
        Node current = adj[u].getHead();
        while (current != null) {
            if (Integer.parseInt(current.getData().getId()) == v)
                return true;
            current = current.getNext();
        }
        return false;
    }

    private static boolean hasAliveMonsters(Room r) {
        return findFirstAliveMonster(r) != null;
    }

    private static Monster findFirstAliveMonster(Room r) {
        if (r == null || r.getMonsters() == null)
            return null;
        for (Monster m : r.getMonsters()) {
            if (m != null && m.getHp() > 0)
                return m;
        }
        return null;
    }

    private static boolean checkInventoryFor(String itemName) {
        Node current = player.getInventory().getHead();
        while (current != null) {
            if (current.getData().getName().equals(itemName))
                return true;
            current = current.getNext();
        }
        return false;
    }

    private static void initGameData() {
        player = new Player("Conan", "Detective");
        player.setHp(100);
        player.getInventory().addItem(new Item("W1", "Đồng hồ gây mê", "Weapon", 100));

        dungeon = new DungeonGraph(8);

        Monster doraemon = new Monster("BOSS", "Doraemon", 200, 0, new Item("KEY", "Chìa khóa vàng", "QuestItem", 0));
        Monster chaien = new Monster("M81", "Chaien", 80, 0, new Item("MAP", "Bản đồ hầm ngục", "Tool", 0));
        Monster suneo = new Monster("M82", "Suneo", 40, 0, null);
        Monster nobita = new Monster("M31", "Nobita", 50, 0, null);
        Monster shizuka = new Monster("M32", "Shizuka", 50, 0, null);
        Monster chaiko = new Monster("M11", "Chaiko", 60, 0, null);
        Monster tamako = new Monster("M12", "Tamako", 70, 0, null);
        Monster nobisuke = new Monster("M41", "Nobisuke", 70, 0, null);
        Monster dekhisugi = new Monster("M42", "Dekhisugi", 50, 0, null);

        dungeon.addRoom(new Room(7, "Bắt đầu", new Monster[] {}, null));
        dungeon.addRoom(new Room(3, "Phòng 3", new Monster[] { nobita, shizuka }, null));
        dungeon.addRoom(new Room(8, "Phòng 8", new Monster[] { chaien, suneo }, null));
        dungeon.addRoom(new Room(1, "Phòng 1", new Monster[] { chaiko, tamako }, null));
        dungeon.addRoom(new Room(4, "Phòng 4", new Monster[] { nobisuke, dekhisugi }, null));
        dungeon.addRoom(new Room(2, "Lối thoát", new Monster[] { doraemon }, null));

        dungeon.loadMapFromFile("map.txt");
    }
}
