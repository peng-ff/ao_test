#!/bin/bash
# 代码验证脚本 - 不需要Java环境

echo "=========================================="
echo "  二叉树算法程序 - 代码结构验证"
echo "=========================================="
echo ""

# 检查项目结构
echo "【1. 检查项目结构】"
echo "--------------------------------------"

check_file() {
    if [ -f "$1" ]; then
        echo "✓ $1"
        return 0
    else
        echo "✗ $1 (缺失)"
        return 1
    fi
}

check_dir() {
    if [ -d "$1" ]; then
        echo "✓ $1/"
        return 0
    else
        echo "✗ $1/ (缺失)"
        return 1
    fi
}

# 检查目录结构
check_dir "src/main/java/com/square/binarytree"
check_dir "src/test/java/com/square/binarytree"

echo ""

# 检查核心文件
echo "【2. 检查核心文件】"
echo "--------------------------------------"
check_file "src/main/java/com/square/binarytree/TreeNode.java"
check_file "src/main/java/com/square/binarytree/BinarySearchTree.java"
check_file "src/main/java/com/square/binarytree/Main.java"
check_file "src/test/java/com/square/binarytree/BinarySearchTreeTest.java"
check_file "pom.xml"
check_file "README.md"

echo ""

# 检查脚本文件
echo "【3. 检查脚本文件】"
echo "--------------------------------------"
check_file "compile.sh"
check_file "run.sh"
check_file "run_tests.sh"

echo ""

# 统计代码行数
echo "【4. 代码统计】"
echo "--------------------------------------"

count_lines() {
    if [ -f "$1" ]; then
        lines=$(wc -l < "$1")
        echo "$2: $lines 行"
    fi
}

count_lines "src/main/java/com/square/binarytree/TreeNode.java" "TreeNode.java"
count_lines "src/main/java/com/square/binarytree/BinarySearchTree.java" "BinarySearchTree.java"
count_lines "src/main/java/com/square/binarytree/Main.java" "Main.java"
count_lines "src/test/java/com/square/binarytree/BinarySearchTreeTest.java" "BinarySearchTreeTest.java"

# 总计
total_lines=0
for file in src/main/java/com/square/binarytree/*.java; do
    if [ -f "$file" ]; then
        lines=$(wc -l < "$file")
        total_lines=$((total_lines + lines))
    fi
done

echo "--------------------------------------"
echo "主代码总行数: $total_lines 行"

test_lines=0
for file in src/test/java/com/square/binarytree/*.java; do
    if [ -f "$file" ]; then
        lines=$(wc -l < "$file")
        test_lines=$((test_lines + lines))
    fi
done

echo "测试代码总行数: $test_lines 行"
echo "代码总计: $((total_lines + test_lines)) 行"

echo ""

# 检查关键类和方法
echo "【5. 检查关键功能实现】"
echo "--------------------------------------"

check_method() {
    if grep -q "$2" "$1"; then
        echo "✓ $3"
    else
        echo "✗ $3 (未找到)"
    fi
}

BST_FILE="src/main/java/com/square/binarytree/BinarySearchTree.java"

if [ -f "$BST_FILE" ]; then
    check_method "$BST_FILE" "public void insert" "insert() 方法"
    check_method "$BST_FILE" "public boolean delete" "delete() 方法"
    check_method "$BST_FILE" "public boolean search" "search() 方法"
    check_method "$BST_FILE" "public TreeNode findNode" "findNode() 方法"
    check_method "$BST_FILE" "public int getHeight" "getHeight() 方法"
    check_method "$BST_FILE" "public int getSize" "getSize() 方法"
    check_method "$BST_FILE" "public boolean isEmpty" "isEmpty() 方法"
    check_method "$BST_FILE" "public void clear" "clear() 方法"
    check_method "$BST_FILE" "public void printTree" "printTree() 方法"
    check_method "$BST_FILE" "public List<Integer> inorderTraversal" "inorderTraversal() 方法"
    check_method "$BST_FILE" "public List<Integer> preorderTraversal" "preorderTraversal() 方法"
    check_method "$BST_FILE" "public List<Integer> postorderTraversal" "postorderTraversal() 方法"
fi

echo ""

# 检查测试覆盖
echo "【6. 检查测试覆盖】"
echo "--------------------------------------"

TEST_FILE="src/test/java/com/square/binarytree/BinarySearchTreeTest.java"

if [ -f "$TEST_FILE" ]; then
    test_count=$(grep -c "@Test" "$TEST_FILE")
    echo "测试方法数量: $test_count"
    
    check_method "$TEST_FILE" "testInsert" "插入操作测试"
    check_method "$TEST_FILE" "testSearch" "查找操作测试"
    check_method "$TEST_FILE" "testDelete" "删除操作测试"
    check_method "$TEST_FILE" "testTraversal" "遍历操作测试"
    check_method "$TEST_FILE" "testGetHeight" "高度计算测试"
    check_method "$TEST_FILE" "testGetSize" "大小计算测试"
fi

echo ""
echo "=========================================="
echo "  验证完成"
echo "=========================================="
echo ""
echo "提示："
echo "1. 如需编译运行，请确保已安装JDK 8+"
echo "2. 编译命令: ./compile.sh"
echo "3. 运行演示: ./run.sh"
echo "4. 运行测试: ./run_tests.sh (需要Maven或手动下载JUnit)"
echo ""
