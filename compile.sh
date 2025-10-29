#!/bin/bash
# 二叉树算法程序 - 编译脚本

echo "=========================================="
echo "  二叉树算法程序 - 编译"
echo "=========================================="

# 创建目标目录
mkdir -p target/classes

# 编译源代码
echo "正在编译源代码..."
javac -d target/classes -encoding UTF-8 src/main/java/com/square/binarytree/*.java

if [ $? -eq 0 ]; then
    echo "✓ 编译成功！"
    echo "编译输出目录: target/classes"
else
    echo "✗ 编译失败！"
    exit 1
fi

echo "=========================================="
