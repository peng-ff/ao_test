package com.square.binarytree;

/**
 * 二叉树节点类
 * 表示二叉树的一个节点，包含数据域和左右子节点引用
 * 
 * @author Binary Tree Algorithm Design
 * @version 1.0
 */
public class TreeNode {
    /**
     * 节点存储的数据值
     */
    private int value;
    
    /**
     * 左子节点引用
     */
    private TreeNode left;
    
    /**
     * 右子节点引用
     */
    private TreeNode right;
    
    /**
     * 构造器：创建新节点
     * 
     * @param value 节点的值
     */
    public TreeNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
    
    /**
     * 获取节点值
     * 
     * @return 节点的值
     */
    public int getValue() {
        return value;
    }
    
    /**
     * 设置节点值
     * 
     * @param value 新的节点值
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    /**
     * 获取左子节点
     * 
     * @return 左子节点引用
     */
    public TreeNode getLeft() {
        return left;
    }
    
    /**
     * 设置左子节点
     * 
     * @param left 新的左子节点
     */
    public void setLeft(TreeNode left) {
        this.left = left;
    }
    
    /**
     * 获取右子节点
     * 
     * @return 右子节点引用
     */
    public TreeNode getRight() {
        return right;
    }
    
    /**
     * 设置右子节点
     * 
     * @param right 新的右子节点
     */
    public void setRight(TreeNode right) {
        this.right = right;
    }
    
    /**
     * 判断当前节点是否为叶子节点
     * 
     * @return 如果是叶子节点返回true，否则返回false
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }
    
    /**
     * 判断当前节点是否只有一个子节点
     * 
     * @return 如果只有一个子节点返回true，否则返回false
     */
    public boolean hasOneChild() {
        return (left != null && right == null) || (left == null && right != null);
    }
    
    /**
     * 判断当前节点是否有两个子节点
     * 
     * @return 如果有两个子节点返回true，否则返回false
     */
    public boolean hasTwoChildren() {
        return left != null && right != null;
    }
    
    @Override
    public String toString() {
        return "TreeNode{value=" + value + "}";
    }
}
