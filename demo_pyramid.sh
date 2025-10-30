#!/bin/bash
# 三棱锥体积计算程序 - 演示脚本

echo "=========================================="
echo "  三棱锥体积计算程序 - 自动演示"
echo "=========================================="

# 创建目标目录
mkdir -p target/classes

# 编译源代码
echo "正在编译源代码..."
javac -d target/classes -encoding UTF-8 \
    src/main/java/com/square/Pyramid.java \
    src/main/java/com/square/PyramidVolumeCalculator.java

if [ $? -eq 0 ]; then
    echo "✓ 编译成功！"
    echo ""
    echo "=========================================="
    echo "  运行演示"
    echo "=========================================="
    echo ""
    
    # 使用 EOF 提供预设输入进行演示
    java -cp target/classes com.square.PyramidVolumeCalculator <<EOF
12.0
5.0
y
20.0
8.0
y
15.5
10.3
n
EOF
else
    echo "✗ 编译失败！请检查是否安装了Java编译器(javac)"
    exit 1
fi

echo ""
echo "=========================================="
echo "演示完成"
echo "=========================================="
