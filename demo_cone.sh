#!/bin/bash
# 圆锥体计算程序 - 演示示例

echo "=========================================="
echo "  圆锥体计算程序 - 演示示例"
echo "=========================================="

# 创建目标目录
mkdir -p target/classes

# 编译源代码
echo "正在编译源代码..."
javac -d target/classes -encoding UTF-8 \
    src/main/java/com/square/Cone.java \
    src/main/java/com/square/ConeVolumeCalculator.java 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✓ 编译成功！"
    echo ""
    
    # 运行演示
    java -cp target/classes com.square.ConeVolumeCalculator <<EOF
5
10
n
EOF
    
    echo ""
    echo "=========================================="
    echo "演示完成！"
    echo "=========================================="
    echo ""
    echo "提示:"
    echo "  - 查看源代码: src/main/java/com/square/Cone.java"
    echo "  - 查看主程序: src/main/java/com/square/ConeVolumeCalculator.java"
    echo "  - 查看说明文档: CONE_README.md"
    echo "  - 运行完整程序: ./run_cone.sh"
else
    echo "✗ 编译失败！请检查是否安装了Java编译器(javac)"
    exit 1
fi
