/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

/**
 *
 * @author ACER
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
    }

    // --- CASE 1: Xóa khi túi rỗng (tương đương testRemoveFromEmpty) ---
    @Test
    public void testRemoveFromEmpty() {
        boolean result = inventory.removeItem("ABC");
        assertFalse(result, "Xóa item trong túi rỗng phải trả về false");
    }

    // --- CASE 2: Xóa ID bị null (tương đương testRemoveNullId) ---
    @Test
    public void testRemoveNullId() {
        inventory.addItem(new Item("A1", "Test", "Test", 1));
        boolean result = inventory.removeItem(null);
        assertFalse(result, "Xóa ID null phải trả về false");
    }

    // --- CASE 3: Thêm Item null (tương đương testAddNullItem) ---
    @Test
    public void testAddNullItem() {
        assertDoesNotThrow(() -> inventory.addItem(null));

        assertFalse(inventory.removeItem("UNKNOWN"));
    }

    // --- CASE 4: Luồng bình thường (tương đương testNormalFlow) ---
    @Test
    public void testAddAndRemoveNormal() {
        inventory.addItem(new Item("K01", "Key", "Tool", 0));

        boolean firstRemove = inventory.removeItem("K01");
        assertTrue(firstRemove, "Lần đầu xóa item tồn tại phải trả về true");

        boolean secondRemove = inventory.removeItem("K01");
        assertFalse(secondRemove, "Xóa item không còn tồn tại phải trả về false");
    }

    // Case 5: Xóa phần tử đầu danh sách (Head)
    @Test
    public void testRemoveHead() {
        inventory.addItem(new Item("1", "Head", "Type", 1));
        inventory.addItem(new Item("2", "Middle", "Type", 1));

        boolean result = inventory.removeItem("1");
        assertTrue(result, "Phải xóa được phần tử đầu (Head)");

        assertTrue(inventory.removeItem("2"), "Phần tử sau Head vẫn phải truy cập được");
    }

    // Case 6: Xóa phần tử cuối danh sách (Tail)
    @Test
    public void testRemoveTail() {
        inventory.addItem(new Item("1", "Head", "Type", 1));
        inventory.addItem(new Item("2", "Tail", "Type", 1));

        boolean result = inventory.removeItem("2");
        assertTrue(result, "Phải xóa được phần tử cuối (Tail)");

        assertTrue(inventory.removeItem("1"), "Phần tử Head vẫn phải còn nguyên");
    }

    // Case 7: Xóa phần tử ở giữa (Middle)
    @Test
    public void testRemoveMiddle() {
        inventory.addItem(new Item("1", "Head", "Type", 1));
        inventory.addItem(new Item("2", "Middle", "Type", 1));
        inventory.addItem(new Item("3", "Tail", "Type", 1));

        boolean result = inventory.removeItem("2");
        assertTrue(result, "Phải xóa được phần tử giữa");

        assertTrue(inventory.removeItem("1"), "Head phải còn");
        assertTrue(inventory.removeItem("3"), "Tail phải còn");
    }

    // Case 8: Xóa phần tử không tồn tại trong danh sách có dữ liệu
    @Test
    public void testRemoveNonExistentItem() {
        inventory.addItem(new Item("A", "A", "A", 1));
        inventory.addItem(new Item("B", "B", "B", 1));

        boolean result = inventory.removeItem("C"); // ID "C" không có
        assertFalse(result, "Xóa ID không tồn tại phải trả về false");
    }
}
