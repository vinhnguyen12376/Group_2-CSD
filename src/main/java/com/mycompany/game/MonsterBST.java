/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

/**
 *
 * @author QUANG VINH
 */
public class MonsterBST {
    //Node root chính là node gốc
    
    //Binary tree là trường hợp đặc biệt của tree:
        //Trong tree một node nó có thể có n node con
        //Trong binary tree thì một node sẽ chỉ có từ 0 tới 2 node con
        //Có sự quy định về các node trái và phải
    
    //Tất cả những node không có con đều là leaf node
    //Tất cả những node có con thì không phải là leaf node
    //2 node con cùng sinh ra từ 1 node cha thì 2 node đó là 2 node anh em
    //Chiều cao của 1 tree được tính từ node root tới node leaf (Bắt đầu từ 0)

    private TreeNode root;

    public MonsterBST() {
        this.root = null;
    }

    public void insert(Monster monster) {
        // Trường hợp đặc biệt: Nếu cây chưa có Gốc, tạo Gốc mới ngay lập tức
        if (this.root == null) {
            this.root = new TreeNode(monster);
        } else {
            // Nếu đã có Gốc, gọi đệ quy bắt đầu từ Gốc
            insertRec(this.root, monster);
        }
    }
    
    public boolean isEmpty(){
        return this.root == null;
    }

    private TreeNode insertRec(TreeNode current, Monster monster) {
        if (current == null) {
            return new TreeNode(monster);
        }
        if (monster.getId().compareTo(current.getData().getId()) < 0) {
            // --- NHÁNH TRÁI ---
            // Logic trong ảnh: Kiểm tra ngay xem bên trái có trống không?
            if (current.getLeft() == null) {
                // Nếu trống (null) -> Tạo node mới gán vào luôn
                current.setLeft(new TreeNode(monster));
            } else {
                // Nếu đã có node -> Gọi đệ quy đi tiếp xuống dưới
                insertRec(current.getLeft(), monster);
            }
        }
        if (monster.getId().compareTo(current.getData().getId()) > 0) {
            // --- NHÁNH PHẢI ---
            // Logic trong ảnh: Kiểm tra ngay xem bên phải có trống không?
            if (current.getRight() == null) {
                // Nếu trống (null) -> Tạo node mới gán vào luôn
                current.setRight(new TreeNode(monster));
            } else {
                // Nếu đã có node -> Gọi đệ quy đi tiếp xuống dưới
                insertRec(current.getRight(), monster);
            }
        }
        return current;
    }

    public Monster search(String id) {
        TreeNode resultNode = searchRec(this.root, id);
        if (resultNode != null) {
            return resultNode.getData();
        } else {
            return null;
        }
    }

    private TreeNode searchRec(TreeNode current, String id) {
        if (current == null) {
            return null; 
        }
        if(id.compareTo(current.getData().getId()) == 0){
            return current;
        }
        if (id.compareTo(current.getData().getId()) < 0) {
            return searchRec(current.getLeft(), id);
        }
        return searchRec(current.getRight(), id);
    }
    
    public void deleteNode(String id) {
        this.root = deleteRec(this.root, id);
    }
    
    private TreeNode deleteRec(TreeNode current, String id){
        if(current == null){
            return null;
        }
        //Xác định node cần xóa
        if(id.compareTo(current.getData().getId()) < 0){
            current.setLeft(deleteRec(current.getLeft(), id));
        }else if(id.compareTo(current.getData().getId()) > 0){
            current.setRight(deleteRec(current.getRight(), id));
        }else{
            //Nếu root là node lá không có con
            if(current.getLeft() == null && current.getRight() == null){
                return null;
            }
            //Có 1 con bên trái
            if(current.getLeft() != null && current.getRight() == null){
                return current.getLeft();
            }
            //Có 1 con bên phải
            if(current.getLeft() == null && current.getRight() != null){
                return current.getRight();
            }
            //Có 2 node con
            //Sau khi xác định thì sẽ ra các trường hợp:
            //1. Node cần xóa là node lá không phải node con
                //Xác định node cha của node con cần xóa, thay vì trỏ vào node cần xóa thì sẽ trỏ vào null 
            //2. Node cần xóa có 1 node con 
                //Xác định node cha của node con cần xóa, thay vì node cha trỏ vào node con của nó, node cha
                    //sẽ trỏ vào node con của node con của mình mà đang cần xóa đi
            //3. Node cần xóa có 2 node con
                //Thay bởi trái cùng của cây con bên phải
                Monster leftModeNode = findLeftModeNode(current.getRight());
                current.setData(leftModeNode);
                current.setRight(deleteRec(current.getRight(), leftModeNode.getId()));
        }
        return current;
        
    }
    private Monster findLeftModeNode(TreeNode current){
        if(current == null){
            return null;
        }
        Monster leftModeNode = current.getData();
        while (current.getLeft() != null) {            
            leftModeNode = current.getLeft().getData();
            current = current.getLeft();
        }
        return leftModeNode;
    }
}


