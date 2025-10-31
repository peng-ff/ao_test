#!/bin/bash
# 编译并运行升序排序程序

echo "=== 编译 SortProgram.java ==="

# 创建输出目录
mkdir -p build/classes

# 编译主程序
javac -d build/classes src/main/java/com/square/SortProgram.java

if [ $? -eq 0 ]; then
    echo "✓ 编译成功!"
    echo ""
    echo "=== 运行排序程序 ==="
    java -cp build/classes com.square.SortProgram
else
    echo "✗ 编译失败!"
    exit 1
fi
