package com.square.binarytree;

import java.util.List;

/**
 * 二叉搜索树演示程序
 * 展示二叉搜索树的各种操作和使用方法
 * 
 * @author Binary Tree Algorithm Design
 * @version 1.0
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("      二叉搜索树算法程序演示");
        System.out.println("===========================================\n");
        
        // 创建二叉搜索树
        BinarySearchTree bst = new BinarySearchTree();
        
        // 演示1：插入节点
        demonstrateInsert(bst);
        
        // 演示2：查找节点
        demonstrateSearch(bst);
        
        // 演示3：遍历树
        demonstrateTraversal(bst);
        
        // 演示4：树的属性
        demonstrateProperties(bst);
        
        // 演示5：删除节点
        demonstrateDelete(bst);
        
        // 演示6：边界情况测试
        demonstrateBoundary();
        
        System.out.println("===========================================");
        System.out.println("      演示结束");
        System.out.println("===========================================");
    }
    
    /**
     * 演示插入操作
     */
    private static void demonstrateInsert(BinarySearchTree bst) {
        System.out.println("\n【演示1：插入节点】");
        System.out.println("--------------------------------------");
        
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 65};
        
        System.out.println("插入节点序列：");
        for (int value : values) {
            System.out.print(value + " ");
            bst.insert(value);
        }
        System.out.println("\n");
        
        bst.printTree();
    }
    
    /**
     * 演示查找操作
     */
    private static void demonstrateSearch(BinarySearchTree bst) {
        System.out.println("\n【演示2：查找节点】");
        System.out.println("--------------------------------------");
        
        int[] searchValues = {40, 65, 100, 10};
        
        for (int value : searchValues) {
            boolean found = bst.search(value);
            System.out.println("查找值 " + value + ": " + (found ? "找到 ✓" : "未找到 ✗"));
        }
        
        System.out.println();
    }
    
    /**
     * 演示遍历操作
     */
    private static void demonstrateTraversal(BinarySearchTree bst) {
        System.out.println("\n【演示3：树的遍历】");
        System.out.println("--------------------------------------");
        
        // 前序遍历
        List<Integer> preorder = bst.preorderTraversal();
        System.out.println("前序遍历（根-左-右）：" + preorder);
        
        // 中序遍历（对于BST，结果是有序的）
        List<Integer> inorder = bst.inorderTraversal();
        System.out.println("中序遍历（左-根-右）：" + inorder);
        System.out.println("  ↑ 注意：BST的中序遍历结果是有序的");
        
        // 后序遍历
        List<Integer> postorder = bst.postorderTraversal();
        System.out.println("后序遍历（左-右-根）：" + postorder);
        
        System.out.println();
    }
    
    /**
     * 演示树的属性
     */
    private static void demonstrateProperties(BinarySearchTree bst) {
        System.out.println("\n【演示4：树的属性】");
        System.out.println("--------------------------------------");
        
        System.out.println(bst.getStatistics());
    }
    
    /**
     * 演示删除操作
     */
    private static void demonstrateDelete(BinarySearchTree bst) {
        System.out.println("\n【演示5：删除节点】");
        System.out.println("--------------------------------------");
        
        // 删除叶子节点
        System.out.println("\n1. 删除叶子节点 (10):");
        bst.delete(10);
        bst.printTree();
        System.out.println("中序遍历: " + bst.inorderTraversal());
        
        // 删除只有一个子节点的节点
        System.out.println("\n2. 删除只有一个子节点的节点 (20):");
        bst.delete(20);
        bst.printTree();
        System.out.println("中序遍历: " + bst.inorderTraversal());
        
        // 删除有两个子节点的节点
        System.out.println("\n3. 删除有两个子节点的节点 (30):");
        bst.delete(30);
        bst.printTree();
        System.out.println("中序遍历: " + bst.inorderTraversal());
        
        // 删除根节点
        System.out.println("\n4. 删除根节点 (50):");
        bst.delete(50);
        bst.printTree();
        System.out.println("中序遍历: " + bst.inorderTraversal());
        
        // 尝试删除不存在的节点
        System.out.println("\n5. 尝试删除不存在的节点 (999):");
        boolean result = bst.delete(999);
        System.out.println("删除结果: " + (result ? "成功" : "失败（节点不存在）"));
    }
    
    /**
     * 演示边界情况
     */
    private static void demonstrateBoundary() {
        System.out.println("\n【演示6：边界情况测试】");
        System.out.println("--------------------------------------");
        
        BinarySearchTree emptyTree = new BinarySearchTree();
        
        // 空树操作
        System.out.println("\n1. 空树操作:");
        System.out.println("  空树查找: " + emptyTree.search(10));
        System.out.println("  空树删除: " + emptyTree.delete(10));
        System.out.println("  空树大小: " + emptyTree.getSize());
        System.out.println("  空树高度: " + emptyTree.getHeight());
        System.out.println("  是否为空: " + emptyTree.isEmpty());
        
        // 单节点树
        System.out.println("\n2. 单节点树操作:");
        emptyTree.insert(100);
        System.out.println("  插入节点100后:");
        emptyTree.printTree();
        System.out.println("  树的大小: " + emptyTree.getSize());
        System.out.println("  树的高度: " + emptyTree.getHeight());
        
        System.out.println("\n  删除唯一节点后:");
        emptyTree.delete(100);
        System.out.println("  是否为空: " + emptyTree.isEmpty());
        
        // 重复值插入
        System.out.println("\n3. 重复值插入测试:");
        BinarySearchTree dupTree = new BinarySearchTree();
        dupTree.insert(50);
        dupTree.insert(50);
        dupTree.insert(50);
        System.out.println("  插入3个相同值(50)后，树的大小: " + dupTree.getSize());
        dupTree.printTree();
        
        // 有序插入（会退化为链表）
        System.out.println("\n4. 有序插入测试（注意：会导致树不平衡）:");
        BinarySearchTree orderedTree = new BinarySearchTree();
        for (int i = 1; i <= 5; i++) {
            orderedTree.insert(i * 10);
        }
        orderedTree.printTree();
        System.out.println("  树的高度: " + orderedTree.getHeight());
        System.out.println("  说明：有序插入会使BST退化为链表，高度为节点数");
        
        System.out.println();
    }
}

