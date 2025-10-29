#!/bin/bash
# 二叉树算法程序 - 运行脚本

echo "=========================================="
echo "  二叉树算法程序 - 运行演示"
echo "=========================================="

# 检查是否已编译
if [ ! -d "target/classes" ]; then
    echo "未找到编译输出，正在编译..."
    ./compile.sh
fi

# 运行主程序
echo ""
java -cp target/classes com.square.binarytree.Main

echo ""
echo "=========================================="
echo "  程序运行完毕"
echo "=========================================="
