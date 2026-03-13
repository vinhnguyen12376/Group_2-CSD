package com.mycompany.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TestBST {

    private MonsterBST monsterDex;

    @BeforeEach
    void setUp() {
        monsterDex = new MonsterBST();
        monsterDex.insert(new Monster("M05", "Nobita", 5000, 300, null));
        monsterDex.insert(new Monster("M03", "Shizuka", 200, 50, null));
        monsterDex.insert(new Monster("M08", "Suneo", 300, 80, null));
        monsterDex.insert(new Monster("M01", "Chaien", 50, 10, null));
        monsterDex.insert(new Monster("M04", "Tamako", 150, 40, null));
        monsterDex.insert(new Monster("M07", "Nobisuke", 100, 25, null));
        monsterDex.insert(new Monster("M02", "Doraemon", 500, 90, null));
    }

    @Test
    @DisplayName("Kiểm tra thêm quái vật và tìm kiếm")
    void testInsertAndSearch() {
        assertNotNull(monsterDex.search("M05"), "Phải tìm thấy Nobita");
        assertNotNull(monsterDex.search("M01"), "Phải tìm thấy Chaien");
        assertNull(monsterDex.search("M99"), "Không được tìm thấy mã ID không tồn tại");
    }

    @Test
    @DisplayName("Trường hợp A: Xóa Node lá (M04)")
    void testDeleteLeafNode() {
        monsterDex.deleteNode("M04");
        assertNull(monsterDex.search("M04"), "M04 phải bị xóa khỏi cây");
        assertNotNull(monsterDex.search("M03"), "Node cha (M03) vẫn phải tồn tại");
    }

    @Test
    @DisplayName("Trường hợp B: Xóa Node có 1 con (M08)")
    void testDeleteNodeWithOneChild() {
        monsterDex.deleteNode("M08");
        assertNull(monsterDex.search("M08"), "M08 phải bị xóa");
        assertNotNull(monsterDex.search("M07"), "Con của M08 là M07 phải được nối lên và tồn tại");
    }

    @Test
    @DisplayName("Trường hợp C: Xóa Node gốc có 2 con (M05)")
    void testDeleteRootWithTwoChildren() {
        monsterDex.deleteNode("M05");
        assertNull(monsterDex.search("M05"), "Node gốc M05 phải bị xóa");
        assertNotNull(monsterDex.search("M03"), "Nhánh trái cũ phải còn truy cập được");
        assertNotNull(monsterDex.search("M07"), "Nhánh phải cũ phải còn truy cập được");
        assertNotNull(monsterDex.search("M01"), "Các node sâu hơn vẫn phải tồn tại");
    }

    @Test
    @DisplayName("Kiểm tra xóa node không tồn tại")
    void testDeleteNonExistentNode() {
        assertDoesNotThrow(() -> monsterDex.deleteNode("M99"), "Xóa mã không tồn tại không được gây lỗi");
    }
}