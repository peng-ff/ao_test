#!/bin/bash
# 圆锥体体积计算程序 - 编译和运行脚本

echo "=========================================="
echo "  圆锥体体积计算程序"
echo "=========================================="

# 创建目标目录
mkdir -p target/classes

# 编译源代码
echo "正在编译源代码..."
javac -d target/classes -encoding UTF-8 \
    src/main/java/com/square/Cone.java \
    src/main/java/com/square/ConeVolumeCalculator.java

if [ $? -eq 0 ]; then
    echo "✓ 编译成功！"
    echo ""
    echo "=========================================="
    echo "  启动程序"
    echo "=========================================="
    echo ""
    
    # 运行程序
    java -cp target/classes com.square.ConeVolumeCalculator
else
    echo "✗ 编译失败！"
    exit 1
fi

echo ""
echo "=========================================="
echo "程序已退出"
echo "=========================================="
