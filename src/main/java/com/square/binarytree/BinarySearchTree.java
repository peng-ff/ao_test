package com.square.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉搜索树类
 * 实现了二叉搜索树(BST)的基本操作，包括插入、删除、查找等核心功能
 * 
 * 二叉搜索树性质：
 * - 左子树所有节点的值都小于根节点的值
 * - 右子树所有节点的值都大于根节点的值
 * - 左右子树也分别为二叉搜索树
 * 
 * @author Binary Tree Algorithm Design
 * @version 1.0
 */
public class BinarySearchTree {
    /**
     * 树的根节点
     */
    private TreeNode root;
    
    /**
     * 构造器：创建空的二叉搜索树
     */
    public BinarySearchTree() {
        this.root = null;
    }
    
    /**
     * 插入新节点（公共接口）
     * 
     * @param value 要插入的值
     */
    public void insert(int value) {
        root = insertRecursive(root, value);
    }
    
    /**
     * 插入新节点（递归实现）
     * 
     * @param current 当前节点
     * @param value 要插入的值
     * @return 插入后的子树根节点
     */
    private TreeNode insertRecursive(TreeNode current, int value) {
        // 如果当前位置为空，创建新节点
        if (current == null) {
            return new TreeNode(value);
        }
        
        // 比较值的大小，决定插入位置
        if (value < current.getValue()) {
            // 插入左子树
            current.setLeft(insertRecursive(current.getLeft(), value));
        } else if (value > current.getValue()) {
            // 插入右子树
            current.setRight(insertRecursive(current.getRight(), value));
        }
        // 如果值相等，忽略重复值（也可以选择更新或插入到右子树）
        
        return current;
    }
    
    /**
     * 查找指定值是否存在（公共接口）
     * 
     * @param value 要查找的值
     * @return 如果找到返回true，否则返回false
     */
    public boolean search(int value) {
        return searchRecursive(root, value);
    }
    
    /**
     * 查找指定值是否存在（递归实现）
     * 
     * @param current 当前节点
     * @param value 要查找的值
     * @return 如果找到返回true，否则返回false
     */
    private boolean searchRecursive(TreeNode current, int value) {
        // 当前节点为空，未找到
        if (current == null) {
            return false;
        }
        
        // 找到目标值
        if (value == current.getValue()) {
            return true;
        }
        
        // 根据值的大小决定搜索方向
        if (value < current.getValue()) {
            return searchRecursive(current.getLeft(), value);
        } else {
            return searchRecursive(current.getRight(), value);
        }
    }
    
    /**
     * 查找并返回指定值的节点（公共接口）
     * 
     * @param value 要查找的值
     * @return 找到的节点，如果不存在返回null
     */
    public TreeNode findNode(int value) {
        return findNodeRecursive(root, value);
    }
    
    /**
     * 查找并返回指定值的节点（递归实现）
     * 
     * @param current 当前节点
     * @param value 要查找的值
     * @return 找到的节点，如果不存在返回null
     */
    private TreeNode findNodeRecursive(TreeNode current, int value) {
        // 当前节点为空，未找到
        if (current == null) {
            return null;
        }
        
        // 找到目标值
        if (value == current.getValue()) {
            return current;
        }
        
        // 根据值的大小决定搜索方向
        if (value < current.getValue()) {
            return findNodeRecursive(current.getLeft(), value);
        } else {
            return findNodeRecursive(current.getRight(), value);
        }
    }
    
    /**
     * 删除指定值的节点（公共接口）
     * 
     * @param value 要删除的值
     * @return 删除成功返回true，否则返回false
     */
    public boolean delete(int value) {
        // 先检查节点是否存在
        if (!search(value)) {
            return false;
        }
        
        root = deleteRecursive(root, value);
        return true;
    }
    
    /**
     * 删除指定值的节点（递归实现）
     * 
     * @param current 当前节点
     * @param value 要删除的值
     * @return 删除后的子树根节点
     */
    private TreeNode deleteRecursive(TreeNode current, int value) {
        // 当前节点为空
        if (current == null) {
            return null;
        }
        
        // 查找要删除的节点
        if (value < current.getValue()) {
            current.setLeft(deleteRecursive(current.getLeft(), value));
        } else if (value > current.getValue()) {
            current.setRight(deleteRecursive(current.getRight(), value));
        } else {
            // 找到要删除的节点
            
            // 情况1：叶子节点，直接删除
            if (current.isLeaf()) {
                return null;
            }
            
            // 情况2：只有一个子节点，用子节点替换
            if (current.hasOneChild()) {
                return current.getLeft() != null ? current.getLeft() : current.getRight();
            }
            
            // 情况3：有两个子节点，找到右子树的最小节点（中序后继）
            TreeNode minNode = findMin(current.getRight());
            // 用后继节点的值替换当前节点的值
            current.setValue(minNode.getValue());
            // 删除后继节点
            current.setRight(deleteRecursive(current.getRight(), minNode.getValue()));
        }
        
        return current;
    }
    
    /**
     * 找到子树中的最小节点
     * 
     * @param node 子树的根节点
     * @return 最小节点
     */
    private TreeNode findMin(TreeNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }
    
    /**
     * 找到子树中的最大节点
     * 
     * @param node 子树的根节点
     * @return 最大节点
     */
    private TreeNode findMax(TreeNode node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }
    
    /**
     * 判断树是否为空
     * 
     * @return 如果树为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return root == null;
    }
    
    /**
     * 清空树
     */
    public void clear() {
        root = null;
    }
    
    /**
     * 获取根节点（用于测试和遍历）
     * 
     * @return 根节点
     */
    public TreeNode getRoot() {
        return root;
    }
    
    // ==================== 遍历方法 ====================
    
    /**
     * 中序遍历（左-根-右）
     * 对于二叉搜索树，中序遍历的结果是有序的
     * 
     * @return 中序遍历结果列表
     */
    public List<Integer> inorderTraversal() {
        List<Integer> result = new ArrayList<>();
        inorderTraversalRecursive(root, result);
        return result;
    }
    
    /**
     * 中序遍历（递归实现）
     * 
     * @param node 当前节点
     * @param result 结果列表
     */
    private void inorderTraversalRecursive(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // 左子树
        inorderTraversalRecursive(node.getLeft(), result);
        // 根节点
        result.add(node.getValue());
        // 右子树
        inorderTraversalRecursive(node.getRight(), result);
    }
    
    /**
     * 前序遍历（根-左-右）
     * 
     * @return 前序遍历结果列表
     */
    public List<Integer> preorderTraversal() {
        List<Integer> result = new ArrayList<>();
        preorderTraversalRecursive(root, result);
        return result;
    }
    
    /**
     * 前序遍历（递归实现）
     * 
     * @param node 当前节点
     * @param result 结果列表
     */
    private void preorderTraversalRecursive(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // 根节点
        result.add(node.getValue());
        // 左子树
        preorderTraversalRecursive(node.getLeft(), result);
        // 右子树
        preorderTraversalRecursive(node.getRight(), result);
    }
    
    /**
     * 后序遍历（左-右-根）
     * 
     * @return 后序遍历结果列表
     */
    public List<Integer> postorderTraversal() {
        List<Integer> result = new ArrayList<>();
        postorderTraversalRecursive(root, result);
        return result;
    }
    
    /**
     * 后序遍历（递归实现）
     * 
     * @param node 当前节点
     * @param result 结果列表
     */
    private void postorderTraversalRecursive(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // 左子树
        postorderTraversalRecursive(node.getLeft(), result);
        // 右子树
        postorderTraversalRecursive(node.getRight(), result);
        // 根节点
        result.add(node.getValue());
    }
    
    // ==================== 辅助方法 ====================
    
    /**
     * 获取树的高度
     * 空树的高度为0，只有根节点的树高度为1
     * 
     * @return 树的高度
     */
    public int getHeight() {
        return getHeightRecursive(root);
    }
    
    /**
     * 获取子树的高度（递归实现）
     * 
     * @param node 子树的根节点
     * @return 子树的高度
     */
    private int getHeightRecursive(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        int leftHeight = getHeightRecursive(node.getLeft());
        int rightHeight = getHeightRecursive(node.getRight());
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    /**
     * 获取树中节点的总数
     * 
     * @return 节点总数
     */
    public int getSize() {
        return getSizeRecursive(root);
    }
    
    /**
     * 获取子树中节点的总数（递归实现）
     * 
     * @param node 子树的根节点
     * @return 子树的节点总数
     */
    private int getSizeRecursive(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        return 1 + getSizeRecursive(node.getLeft()) + getSizeRecursive(node.getRight());
    }
    
    /**
     * 打印树结构（控制台输出）
     * 以可视化方式展示树的结构
     */
    public void printTree() {
        System.out.println("\n========== 二叉搜索树结构 ==========");
        if (isEmpty()) {
            System.out.println("树为空");
        } else {
            printTreeRecursive(root, "", true);
        }
        System.out.println("====================================\n");
    }
    
    /**
     * 打印树结构（递归实现）
     * 
     * @param node 当前节点
     * @param prefix 前缀字符串
     * @param isTail 是否为最后一个子节点
     */
    private void printTreeRecursive(TreeNode node, String prefix, boolean isTail) {
        if (node == null) {
            return;
        }
        
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.getValue());
        
        List<TreeNode> children = new ArrayList<>();
        if (node.getLeft() != null) children.add(node.getLeft());
        if (node.getRight() != null) children.add(node.getRight());
        
        for (int i = 0; i < children.size(); i++) {
            TreeNode child = children.get(i);
            boolean isLast = (i == children.size() - 1);
            String childPrefix = prefix + (isTail ? "    " : "│   ");
            
            if (child == node.getLeft()) {
                printTreeRecursive(child, childPrefix, node.getRight() == null);
            } else {
                printTreeRecursive(child, childPrefix, true);
            }
        }
    }
    
    /**
     * 获取树的统计信息
     * 
     * @return 统计信息字符串
     */
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== 树的统计信息 ==========");
        sb.append("\n是否为空: ").append(isEmpty());
        sb.append("\n节点总数: ").append(getSize());
        sb.append("\n树的高度: ").append(getHeight());
        
        if (!isEmpty()) {
            sb.append("\n最小值: ").append(findMin(root).getValue());
            sb.append("\n最大值: ").append(findMax(root).getValue());
        }
        
        sb.append("\n====================================\n");
        return sb.toString();
    }
}
