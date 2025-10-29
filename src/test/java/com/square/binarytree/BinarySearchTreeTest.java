package com.square.binarytree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 二叉搜索树单元测试类
 * 测试BinarySearchTree的所有核心功能
 * 
 * @author Binary Tree Algorithm Design
 * @version 1.0
 */
@DisplayName("二叉搜索树测试")
public class BinarySearchTreeTest {
    
    private BinarySearchTree bst;
    
    @BeforeEach
    void setUp() {
        bst = new BinarySearchTree();
    }
    
    // ==================== 插入操作测试 ====================
    
    @Test
    @DisplayName("测试插入单个节点")
    void testInsertSingleNode() {
        bst.insert(50);
        
        assertFalse(bst.isEmpty(), "树不应为空");
        assertEquals(1, bst.getSize(), "树应包含1个节点");
        assertEquals(1, bst.getHeight(), "树的高度应为1");
        assertTrue(bst.search(50), "应能找到插入的节点");
    }
    
    @Test
    @DisplayName("测试插入多个节点")
    void testInsertMultipleNodes() {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        
        for (int value : values) {
            bst.insert(value);
        }
        
        assertEquals(7, bst.getSize(), "树应包含7个节点");
        
        // 验证所有节点都能找到
        for (int value : values) {
            assertTrue(bst.search(value), "应能找到值: " + value);
        }
    }
    
    @Test
    @DisplayName("测试插入重复值")
    void testInsertDuplicateValues() {
        bst.insert(50);
        bst.insert(50);
        bst.insert(50);
        
        assertEquals(1, bst.getSize(), "重复插入应被忽略，树应只包含1个节点");
    }
    
    @Test
    @DisplayName("测试有序插入")
    void testInsertInOrder() {
        for (int i = 1; i <= 5; i++) {
            bst.insert(i * 10);
        }
        
        assertEquals(5, bst.getSize(), "树应包含5个节点");
        assertEquals(5, bst.getHeight(), "有序插入会导致树退化为链表，高度等于节点数");
    }
    
    // ==================== 查找操作测试 ====================
    
    @Test
    @DisplayName("测试在空树中查找")
    void testSearchInEmptyTree() {
        assertFalse(bst.search(10), "在空树中查找应返回false");
        assertNull(bst.findNode(10), "在空树中查找应返回null");
    }
    
    @Test
    @DisplayName("测试查找存在的节点")
    void testSearchExistingNode() {
        int[] values = {50, 30, 70, 20, 40};
        for (int value : values) {
            bst.insert(value);
        }
        
        assertTrue(bst.search(30), "应能找到存在的节点");
        assertTrue(bst.search(70), "应能找到存在的节点");
        
        TreeNode node = bst.findNode(30);
        assertNotNull(node, "findNode应返回节点");
        assertEquals(30, node.getValue(), "节点值应正确");
    }
    
    @Test
    @DisplayName("测试查找不存在的节点")
    void testSearchNonExistingNode() {
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        
        assertFalse(bst.search(100), "不存在的节点应返回false");
        assertNull(bst.findNode(100), "不存在的节点应返回null");
    }
    
    // ==================== 删除操作测试 ====================
    
    @Test
    @DisplayName("测试删除叶子节点")
    void testDeleteLeafNode() {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int value : values) {
            bst.insert(value);
        }
        
        assertTrue(bst.delete(20), "删除叶子节点应成功");
        assertEquals(6, bst.getSize(), "删除后节点数应减少");
        assertFalse(bst.search(20), "删除的节点不应存在");
    }
    
    @Test
    @DisplayName("测试删除只有一个子节点的节点")
    void testDeleteNodeWithOneChild() {
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        
        assertTrue(bst.delete(30), "删除有一个子节点的节点应成功");
        assertEquals(2, bst.getSize(), "删除后节点数应减少");
        assertFalse(bst.search(30), "删除的节点不应存在");
        assertTrue(bst.search(20), "子节点应仍然存在");
    }
    
    @Test
    @DisplayName("测试删除有两个子节点的节点")
    void testDeleteNodeWithTwoChildren() {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int value : values) {
            bst.insert(value);
        }
        
        assertTrue(bst.delete(30), "删除有两个子节点的节点应成功");
        assertEquals(6, bst.getSize(), "删除后节点数应减少");
        assertFalse(bst.search(30), "删除的节点不应存在");
        assertTrue(bst.search(20), "左子节点应仍然存在");
        assertTrue(bst.search(40), "右子节点应仍然存在");
        
        // 验证BST性质
        List<Integer> inorder = bst.inorderTraversal();
        for (int i = 1; i < inorder.size(); i++) {
            assertTrue(inorder.get(i - 1) < inorder.get(i), 
                "删除后中序遍历应仍然有序");
        }
    }
    
    @Test
    @DisplayName("测试删除根节点")
    void testDeleteRootNode() {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int value : values) {
            bst.insert(value);
        }
        
        assertTrue(bst.delete(50), "删除根节点应成功");
        assertEquals(6, bst.getSize(), "删除后节点数应减少");
        assertFalse(bst.search(50), "删除的根节点不应存在");
        assertFalse(bst.isEmpty(), "删除根节点后树不应为空");
    }
    
    @Test
    @DisplayName("测试删除不存在的节点")
    void testDeleteNonExistingNode() {
        bst.insert(50);
        
        assertFalse(bst.delete(100), "删除不存在的节点应返回false");
        assertEquals(1, bst.getSize(), "树的大小不应改变");
    }
    
    @Test
    @DisplayName("测试从空树删除")
    void testDeleteFromEmptyTree() {
        assertFalse(bst.delete(10), "从空树删除应返回false");
    }
    
    // ==================== 遍历操作测试 ====================
    
    @Test
    @DisplayName("测试中序遍历的有序性")
    void testInorderTraversalOrdering() {
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 65};
        for (int value : values) {
            bst.insert(value);
        }
        
        List<Integer> inorder = bst.inorderTraversal();
        
        assertEquals(11, inorder.size(), "中序遍历应包含所有节点");
        
        // 验证有序性
        for (int i = 1; i < inorder.size(); i++) {
            assertTrue(inorder.get(i - 1) < inorder.get(i), 
                "中序遍历结果应是升序的");
        }
    }
    
    @Test
    @DisplayName("测试前序遍历")
    void testPreorderTraversal() {
        int[] values = {50, 30, 70};
        for (int value : values) {
            bst.insert(value);
        }
        
        List<Integer> preorder = bst.preorderTraversal();
        
        assertEquals(3, preorder.size(), "前序遍历应包含所有节点");
        assertEquals(50, preorder.get(0), "前序遍历第一个应是根节点");
        assertEquals(30, preorder.get(1), "前序遍历第二个应是左子节点");
        assertEquals(70, preorder.get(2), "前序遍历第三个应是右子节点");
    }
    
    @Test
    @DisplayName("测试后序遍历")
    void testPostorderTraversal() {
        int[] values = {50, 30, 70};
        for (int value : values) {
            bst.insert(value);
        }
        
        List<Integer> postorder = bst.postorderTraversal();
        
        assertEquals(3, postorder.size(), "后序遍历应包含所有节点");
        assertEquals(30, postorder.get(0), "后序遍历第一个应是左子节点");
        assertEquals(70, postorder.get(1), "后序遍历第二个应是右子节点");
        assertEquals(50, postorder.get(2), "后序遍历最后一个应是根节点");
    }
    
    @Test
    @DisplayName("测试空树遍历")
    void testTraversalOnEmptyTree() {
        List<Integer> inorder = bst.inorderTraversal();
        List<Integer> preorder = bst.preorderTraversal();
        List<Integer> postorder = bst.postorderTraversal();
        
        assertTrue(inorder.isEmpty(), "空树的中序遍历应为空");
        assertTrue(preorder.isEmpty(), "空树的前序遍历应为空");
        assertTrue(postorder.isEmpty(), "空树的后序遍历应为空");
    }
    
    // ==================== 辅助方法测试 ====================
    
    @Test
    @DisplayName("测试树的高度计算")
    void testGetHeight() {
        assertEquals(0, bst.getHeight(), "空树高度应为0");
        
        bst.insert(50);
        assertEquals(1, bst.getHeight(), "单节点树高度应为1");
        
        bst.insert(30);
        bst.insert(70);
        assertEquals(2, bst.getHeight(), "三节点平衡树高度应为2");
        
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);
        assertEquals(3, bst.getHeight(), "七节点完全树高度应为3");
    }
    
    @Test
    @DisplayName("测试树的大小计算")
    void testGetSize() {
        assertEquals(0, bst.getSize(), "空树大小应为0");
        
        for (int i = 1; i <= 10; i++) {
            bst.insert(i * 10);
            assertEquals(i, bst.getSize(), "插入后大小应增加");
        }
    }
    
    @Test
    @DisplayName("测试isEmpty方法")
    void testIsEmpty() {
        assertTrue(bst.isEmpty(), "新建的树应为空");
        
        bst.insert(50);
        assertFalse(bst.isEmpty(), "插入节点后树不应为空");
        
        bst.delete(50);
        assertTrue(bst.isEmpty(), "删除唯一节点后树应为空");
    }
    
    @Test
    @DisplayName("测试clear方法")
    void testClear() {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int value : values) {
            bst.insert(value);
        }
        
        assertFalse(bst.isEmpty(), "清空前树不应为空");
        
        bst.clear();
        
        assertTrue(bst.isEmpty(), "清空后树应为空");
        assertEquals(0, bst.getSize(), "清空后大小应为0");
        assertEquals(0, bst.getHeight(), "清空后高度应为0");
    }
    
    // ==================== 综合场景测试 ====================
    
    @Test
    @DisplayName("测试连续插入和删除")
    void testMixedOperations() {
        // 插入一组数据
        int[] insertValues = {50, 30, 70, 20, 40, 60, 80};
        for (int value : insertValues) {
            bst.insert(value);
        }
        
        assertEquals(7, bst.getSize(), "初始插入后应有7个节点");
        
        // 删除部分数据
        bst.delete(20);
        bst.delete(80);
        
        assertEquals(5, bst.getSize(), "删除后应有5个节点");
        
        // 验证BST性质保持
        List<Integer> inorder = bst.inorderTraversal();
        for (int i = 1; i < inorder.size(); i++) {
            assertTrue(inorder.get(i - 1) < inorder.get(i), 
                "混合操作后BST性质应保持");
        }
    }
    
    @Test
    @DisplayName("测试边界值")
    void testBoundaryValues() {
        bst.insert(Integer.MAX_VALUE);
        bst.insert(Integer.MIN_VALUE);
        bst.insert(0);
        
        assertTrue(bst.search(Integer.MAX_VALUE), "应能处理最大整数");
        assertTrue(bst.search(Integer.MIN_VALUE), "应能处理最小整数");
        assertTrue(bst.search(0), "应能处理0");
        
        assertEquals(3, bst.getSize(), "边界值应正确插入");
    }
}
