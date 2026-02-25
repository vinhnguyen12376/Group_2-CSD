package com.mycompany.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
    }

    @Test
    public void testRemoveFromEmpty() {
        boolean result = inventory.removeItem("ABC");
        assertFalse(result, "Xóa vật phẩm trong túi đồ rỗng phải trả về false.");
    }

    @Test
    public void testRemoveNullId() {
        inventory.addItem(new Item("A1", "Test", "Test", 1));
        boolean result = inventory.removeItem(null);
        assertFalse(result, "Xóa với ID null phải trả về false.");
    }

    @Test
    public void testAddNullItem() {
        assertDoesNotThrow(() -> inventory.addItem(null));
        assertFalse(inventory.removeItem("UNKNOWN"));
    }

    @Test
    public void testAddAndRemoveNormal() {
        inventory.addItem(new Item("K01", "Chia khoa", "Cong cu", 0));

        boolean firstRemove = inventory.removeItem("K01");
        assertTrue(firstRemove, "Lần đầu xóa vật phẩm tồn tại phải trả về true.");

        boolean secondRemove = inventory.removeItem("K01");
        assertFalse(secondRemove, "Xóa cùng một vật phẩm lần nữa phải trả về false.");
    }

    @Test
    public void testRemoveHead() {
        inventory.addItem(new Item("1", "Dau", "Loai", 1));
        inventory.addItem(new Item("2", "Giua", "Loai", 1));

        boolean result = inventory.removeItem("1");
        assertTrue(result, "Phải xóa được phần tử đầu danh sách.");
        assertTrue(inventory.removeItem("2"), "Phần tử sau phần tử đầu phải vẫn truy cập được.");
    }

    @Test
    public void testRemoveTail() {
        inventory.addItem(new Item("1", "Dau", "Loai", 1));
        inventory.addItem(new Item("2", "Cuoi", "Loai", 1));

        boolean result = inventory.removeItem("2");
        assertTrue(result, "Phải xóa được phần tử cuối danh sách.");
        assertTrue(inventory.removeItem("1"), "Phần tử đầu danh sách phải vẫn còn tồn tại.");
    }

    @Test
    public void testRemoveMiddle() {
        inventory.addItem(new Item("1", "Dau", "Loai", 1));
        inventory.addItem(new Item("2", "Giua", "Loai", 1));
        inventory.addItem(new Item("3", "Cuoi", "Loai", 1));

        boolean result = inventory.removeItem("2");
        assertTrue(result, "Phải xóa được phần tử ở giữa danh sách.");

        assertTrue(inventory.removeItem("1"), "Phần tử đầu phải vẫn còn tồn tại.");
        assertTrue(inventory.removeItem("3"), "Phần tử cuối phải vẫn còn tồn tại.");
    }

    @Test
    public void testRemoveNonExistentItem() {
        inventory.addItem(new Item("A", "A", "A", 1));
        inventory.addItem(new Item("B", "B", "B", 1));

        boolean result = inventory.removeItem("C"); 
        assertFalse(result, "Xóa một ID không tồn tại phải trả về false.");
    }
}